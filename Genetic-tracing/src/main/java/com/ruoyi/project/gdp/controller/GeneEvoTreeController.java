package com.ruoyi.project.gdp.controller;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.command.CommandUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.gdp.domain.GeneInstance;
import com.ruoyi.project.gdp.domain.GeneEvoTree;
import com.ruoyi.project.gdp.domain.GenePca;
import com.ruoyi.project.gdp.service.IGeneEvoTreeService;
import com.ruoyi.project.gdp.service.IGeneInstanceService;
import com.ruoyi.project.gdp.service.IGeneEvoTreeService;
import com.ruoyi.project.gdp.service.IGeneSampleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;


/**
 * 进化树Controller
 *
 * @author ruoyi
 * @date 2023-03-10
 */
@Controller
@RequestMapping("/gdp/evoTree")
public class GeneEvoTreeController extends BaseController
{
    private String prefix = "gdp/evoTree";

    @Autowired
    private IGeneEvoTreeService evoTreeService;

    @Autowired
    private IGeneInstanceService geneInstanceService;

    @Autowired
    private IGeneSampleService geneSampleService;

    private static final Logger log = LoggerFactory.getLogger(GeneEvoTreeController.class);

    @Value("${ruoyi.basedir}")
    private String dataPath;

    @RequiresPermissions("gdp:evoTree:view")
    @GetMapping()
    public String evoTree()
    {
        return prefix + "/evoTree";
    }

    @RequiresPermissions("gdp:evoTree:view")
    @GetMapping("/example")
    public String example()
    {
        return prefix + "/example";
    }

    /**
     * 查询进化树列表
     */
    @RequiresPermissions("gdp:evoTree:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(GeneEvoTree geneEvoTree)
    {
        startPage();
        List<GeneEvoTree> list = evoTreeService.selectGeneEvoTreeList(geneEvoTree);
        return getDataTable(list);
    }

    /**
     * 导出进化树列表
     */
    @RequiresPermissions("gdp:evoTree:export")
    @Log(title = "进化树", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GeneEvoTree geneEvoTree)
    {
        List<GeneEvoTree> list = evoTreeService.selectGeneEvoTreeList(geneEvoTree);
        ExcelUtil<GeneEvoTree> util = new ExcelUtil<GeneEvoTree>(GeneEvoTree.class);
        return util.exportExcel(list, "进化树数据");
    }

    /**
     * 新增进化树
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存进化树
     */
    @RequiresPermissions("gdp:evoTree:add")
    @Log(title = "进化树", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(GeneEvoTree geneEvoTree)
    {
        return toAjax(evoTreeService.insertGeneEvoTree(geneEvoTree));
    }

    /**
     * 修改进化树
     */
    @RequiresPermissions("gdp:evoTree:edit")
    @GetMapping("/edit/{name}")
    public String edit(@PathVariable("name") String name, ModelMap mmap)
    {
        GeneEvoTree geneEvoTree = evoTreeService.selectGeneEvoTreeByName(name);
        mmap.put("geneEvoTree", geneEvoTree);
        return prefix + "/edit";
    }

    /**
     * 修改保存进化树
     */
    @RequiresPermissions("gdp:evoTree:edit")
    @Log(title = "进化树", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(GeneEvoTree geneEvoTree)
    {
        return toAjax(evoTreeService.updateGeneEvoTree(geneEvoTree));
    }

    /**
     * 删除进化树
     */
    @RequiresPermissions("gdp:evoTree:remove")
    @Log(title = "进化树", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(evoTreeService.deleteGeneEvoTreeByNames(ids));
    }

    @PostMapping(value = "findEvoTreeInstanceByName")
    @ResponseBody
    public AjaxResult findEvoTreeInstanceByName(String name)  {
        Map<String,Object> data = new HashMap<>();
        GeneInstance geneInstance = geneInstanceService.getByName(name);
        data.put("result","false");
        if(geneInstance == null){
            data.put("msg","基因处理实例不存在");
        } else {
            GeneEvoTree geneEvoTree = evoTreeService.getByName(name);
            if (geneEvoTree != null)
                data.put("msg","进化树实例已存在");
            else
                data.put("result","true");
        }
        return success(data);
    }

    //转置
    @RequestMapping(value = "transform")
    @ResponseBody
    public AjaxResult transform(String instanceName){
        log.info("transform:start");
        log.info("instanceName:"+instanceName);
        long starttime = System.currentTimeMillis();
        Map<String,Object> data = new HashMap<>();

//        String filename = "D:\\suyuan\\20240328\\mergeVcf\\transform.txt";
        String filename = "original/"+ instanceName + "/transform.txt";
        try{
            File file1 = new File(filename);
            if(file1.exists()){
                file1.delete();
            }
            file1.createNewFile();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        //合并的文件
//        String path = "D:\\suyuan\\20240328\\mergeVcf\\mergedVcf.txt";
        String path = "original/"+ instanceName + "/mergedVcf.txt";
        File file = new File(path);

        if(!file.exists()){
            data.put("status","fail");
            data.put("msg","合并的文件：mergedVcf.txt不存在！");
            return success(data);
        }

        StringBuilder result = new StringBuilder();
        try{
            //构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8));
            String row = null;
            int length = 0;
            List<String> list = new ArrayList<>();

            //使用readLine方法，一次读一行
            while((row = br.readLine()) != null) {

                String[] array = row.split("\t");
                length = array.length-4;
                String str = "";
                if(row.startsWith("#")) {
                    for (int i = 4; i < array.length; i++) {
                        str = str + ">" + array[i] + "@" + "\t";
                    }
                    list.add(str);
                    //追加写模式
//                    Files.write(Paths.get(filename), str.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                }else {
                    String ref = array[2];
                    String alt = array[3];
                    for (int i = 4; i < array.length; i++) {
                        str = str + array[i] + "\t";
                    }
                    str = str.replace("0",ref).replace("1",alt);
                    list.add(str);
                    //追加写模式
//                    Files.write(Paths.get(filename), str.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                }
            }
            br.close();

            Files.write(Paths.get(filename), "# STOCKHOLM 1.0\n".getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            for(int i =0;i<length;i++){
                String str = "";
                for(String rowStr:list) {
                    String[] array = rowStr.split("\t");
                    str = str + array[i];
                    str = str.replace("@","\t");
                }
                //追加写模式
                str = str + "\n";
                Files.write(Paths.get(filename), str.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            }
            Files.write(Paths.get(filename), "//\n".getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);

            log.info("transform:finish");
            log.info("transform:耗时--->{}",(System.currentTimeMillis() - starttime) + "ms");
            data.put("status","success");

        }catch(Exception e){
            e.printStackTrace();
        }
        data.put("status","success");
        data.put("msg","转化失败！");
        return success(data);
    }

    @RequestMapping(value = "quicktree")
    @ResponseBody
    public AjaxResult processEvoTree(String instanceName) throws IOException {
        log.info("quicktree:start");
        log.info("instanceName:"+instanceName);
        long starttime = System.currentTimeMillis();
        Map<String, Object> data = new HashMap<>();

        String cmd = dataPath + "/software/quicktree-master/quicktree -in a -out t -boot 200 " + dataPath + "/original/"+instanceName+"/transform.txt > original/"+instanceName+"/tree.txt";

        Instant start = Instant.now();
        Map<String, Object> cmdData = CommandUtils.exeCmd(cmd, "quicktree");
        boolean flag = Boolean.parseBoolean(cmdData.get("result_quicktree").toString());
        if(flag) {

//            FileInputStream fis = new FileInputStream("D:\\suyuan\\20240328\\mergeVcf\\tree.txt");
            FileInputStream fis = new FileInputStream("original/"+instanceName+"/tree.txt");
            byte[] buffer = new byte[10];
            StringBuilder sb = new StringBuilder();
            while (fis.read(buffer) != -1) {
                sb.append(new String(buffer));
                buffer = new byte[10];
            }
            fis.close();
            String content = sb.toString();

            String record = cmdData.get("s_msg_quicktree").toString();
            data.put("status", "success");
            data.put("msg", record);
            String time = DateUtils.formatDateTime(ChronoUnit.MILLIS.between(start, Instant.now()));
            data.put("time", time);
            data.put("treedata",content);

            GeneEvoTree evoTree = new GeneEvoTree();
            evoTree.setId(IdUtils.simpleUUID());
            evoTree.setCreateBy(ShiroUtils.getLoginName());
            evoTree.setName(instanceName);
            evoTree.setResultInfo(content);
            evoTree.setResultStatus(1);
            evoTree.setTime(time);
            evoTreeService.insertGeneEvoTree(evoTree);

        } else {
            String record = cmdData.get("e_msg").toString();
            record = record.length() > 5000 ? record.substring(0, 5000) : record;
            data.put("status", "false");
            data.put("msg",record);

            GeneEvoTree evoTree = new GeneEvoTree();
            evoTree.setId(IdUtils.simpleUUID());
            evoTree.setCreateBy(ShiroUtils.getLoginName());
            evoTree.setName(instanceName);
            evoTree.setResultInfo(record);
            evoTree.setResultStatus(0);
            evoTreeService.insertGeneEvoTree(evoTree);
        }

        log.info("quicktree:finish");
        log.info("quicktree:耗时--->{}",(System.currentTimeMillis() - starttime) + "ms");

        return AjaxResult.success(data);
    }
}
