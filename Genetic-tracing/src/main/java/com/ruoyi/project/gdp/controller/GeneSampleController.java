package com.ruoyi.project.gdp.controller;

import com.google.common.collect.Sets;
import com.ruoyi.common.utils.CsvUtils;
import com.ruoyi.common.utils.FormatUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.gdp.domain.GeneInstance;
import com.ruoyi.project.gdp.domain.GeneInstanceSample;
import com.ruoyi.project.gdp.domain.GeneSample;
import com.ruoyi.project.gdp.service.IGeneInstanceService;
import com.ruoyi.project.gdp.service.IGeneSampleService;
import com.ruoyi.project.system.config.service.IConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 样本合并Controller
 * 
 * @author ruoyi
 * @date 2023-04-11
 */
@Controller
@RequestMapping("/gdp/sample")
public class GeneSampleController extends BaseController
{
    private String prefix = "gdp/sample";

    @Autowired
    private IGeneSampleService geneSampleService;

    @Autowired
    private IGeneInstanceService geneInstanceService;

    @Autowired
    private IConfigService configService;

    private static final Logger log = LoggerFactory.getLogger(GeneSampleController.class);

    @RequiresPermissions("gdp:sample:view")
    @GetMapping()
    public String sample()
    {
        return prefix + "/sample";
    }

    /**
     * 查询样本合并列表
     */
    @RequiresPermissions("gdp:sample:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(GeneSample geneSample)
    {
        startPage();
        List<GeneSample> list = geneSampleService.selectGeneSampleList(geneSample);
        return getDataTable(list);
    }

    /**
     * 查询已选择的样本合并列表
     */
    @RequiresPermissions("gdp:sample:list")
    @PostMapping("/listSelectedSamples")
    @ResponseBody
    public TableDataInfo listSelectedSamples(GeneInstanceSample instanceSample)
    {
        startPage();
        List<GeneInstanceSample> list = geneSampleService.listSelectedSamples(instanceSample);
        return getDataTable(list);
    }

    /**
     * 查询未选择的样本合并列表
     */
    @RequiresPermissions("gdp:sample:list")
    @PostMapping("/listUnSelectedSamples")
    @ResponseBody
    public TableDataInfo listUnSelectedSamples(GeneInstanceSample instanceSample)
    {
        startPage();
        List<GeneInstanceSample> list = geneSampleService.listUnSelectedSamples(instanceSample);
        return getDataTable(list);
    }

    /**
     * 导出样本合并列表
     */
    @RequiresPermissions("gdp:sample:export")
    @Log(title = "样本合并", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GeneSample geneSample)
    {
        List<GeneSample> list = geneSampleService.selectGeneSampleList(geneSample);
        ExcelUtil<GeneSample> util = new ExcelUtil<GeneSample>(GeneSample.class);
        return util.exportExcel(list, "样本合并数据");
    }

    /**
     * 新增样本合并
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存样本合并
     */
    @RequiresPermissions("gdp:sample:add")
    @Log(title = "样本合并", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(GeneSample geneSample)
    {
        return toAjax(geneSampleService.insertGeneSample(geneSample));
    }

    /**
     * 修改样本合并
     */
    @RequiresPermissions("gdp:sample:edit")
    @GetMapping("/edit/{serialNumber}")
    public String edit(@PathVariable("serialNumber") String serialNumber, ModelMap mmap)
    {
        GeneSample geneSample = geneSampleService.selectGeneSampleBySerialNumber(serialNumber);
        mmap.put("geneSample", geneSample);
        return prefix + "/edit";
    }

    /**
     * 修改保存样本合并
     */
    @RequiresPermissions("gdp:sample:edit")
    @Log(title = "样本合并", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(GeneSample geneSample)
    {
        return toAjax(geneSampleService.updateGeneSample(geneSample));
    }

    /**
     * 删除样本合并
     */
    @RequiresPermissions("gdp:sample:remove")
    @Log(title = "样本合并", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(geneSampleService.deleteGeneSampleBySerialNumbers(ids));
    }

    /**
     * 样本合并
     */
    @GetMapping("/merge/{id}")
    public String merge(@PathVariable("id") String instanceId, ModelMap mmap)
    {
        GeneInstance geneInstance = geneInstanceService.selectGeneInstanceById(instanceId);
        mmap.put("instanceId", instanceId);
        mmap.put("instanceName", geneInstance.getName());
        return prefix + "/merge";
    }

    @PostMapping("/saveSelectedSamples")
    @ResponseBody
    public AjaxResult saveSelectedSamples(@RequestParam(value = "serialNumberArr[]") String[] array,@RequestParam(value = "instanceId") String instanceId)
    {
        for (int i = 0; i < array.length; i++) {
            GeneSample sample = geneSampleService.selectGeneSampleBySerialNumber(array[i]);
            GeneInstanceSample instanceSample = new GeneInstanceSample();
            instanceSample.setId(IdUtils.simpleUUID());
            instanceSample.setInstanceId(instanceId);
            instanceSample.setColumnNumber(sample.getColumnNumber());
            instanceSample.setCountries(sample.getCountries());
            instanceSample.setSerialNumber(sample.getSerialNumber());
            geneSampleService.saveSelectedSamples(instanceSample);
        }
        return AjaxResult.success();
    }

    @PostMapping( "/removeSelectedSamples")
    @ResponseBody
    public AjaxResult removeSelectedSamples(String id)
    {
        return toAjax(geneSampleService.removeSelectedSamples(id));
    }

    @PostMapping( "/removeAllSelectedSamples")
    @ResponseBody
    public AjaxResult removeAllSelectedSamples(String instanceId)
    {
        return toAjax(geneSampleService.removeAllSelectedSamples(instanceId));
    }

    /**
     * 将流程最终vcf文件进行格式化
     * ./. 和 0/0 代表没有变异，转化成0，其余的转化成1
     * @param instanceName
     * @return
     */
    @RequestMapping(value = "formatInstanceVcf")
    @ResponseBody
    public AjaxResult formatInstanceVcf(String instanceName) {
        log.info("sampleFormat:start");
        long starttime = System.currentTimeMillis();
        String ratio = configService.selectConfigByKey("sample.filter.ratio");
        Map<String,Object> data = new HashMap<>();
//        String filename = "D:\\suyuan\\20240328\\mergeVcf\\formatInstanceVcf.txt";
        String filename = "original/"+ instanceName + "/formatInstanceVcf.txt";
        try{
            File file = new File(filename);
            if(file.exists()){
                file.delete();
            }
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        //流程最终文件
//        String path = "D:\\suyuan\\20240328\\mergeVcf\\genotype.raw4-without-indel.g.vcf.recode.vcf";
        String path = "original/"+ instanceName + "/genotype.raw4-without-indel.g.vcf.recode.vcf";

        File file = new File(path);
        if(!file.exists()){
            data.put("status","fail");
            data.put("msg","流程最终文件：genotype.raw4-without-indel.g.vcf.recode.vcf不存在！");
            return success(data);
        }
        StringBuilder result = new StringBuilder();
        try{
            //构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8));
            String row = null;
            //使用readLine方法，一次读一行
            while((row = br.readLine()) != null) {
                String sampleFormat = "";
                    int sum = 0;
                    //从非注释行开始读取
                    if (!row.startsWith("##")) {
                        String[] array = row.split("\t");
                        int count = array.length - 9;
                        //读取 CHROM POS REF ALT
                        sampleFormat = array[0] + "\t" + array[1] + "\t" + array[3] + "\t" + array[4];
                        //从第10列开始是样本数据，读取样本数据列并转化成0和1
                        for(int i = 9; i < array.length;i++){
                            String arrStr = array[i];
                            //此判断是为了读取第一行，标题行
                            if(!row.startsWith("#")) {
                                //读取数据行 0/0和./.转化成0,其他的转化成1
                                if (arrStr.contains("0/0") || arrStr.contains("./.")) {
                                    arrStr = "\t" + "0";
                                } else {
                                    arrStr = "\t" + "1";
                                    sum += 1;
                                }
                                //行数据
                                sampleFormat = sampleFormat + arrStr;
                            }else{
                                arrStr = "\t" + arrStr;
                                sum += 1;
                                //行数据
                                sampleFormat = sampleFormat + arrStr;
                            }

                        }
//                        log.info("sampleFormat:row--->{}",sampleFormat);
                        if(StringUtils.isBlank(ratio) || "0".equals(ratio) || (float)sum/count >= Float.parseFloat(ratio)) {
                            //换行
                            sampleFormat = sampleFormat + "\n";
                            //追加写模式
                            Files.write(Paths.get(filename), sampleFormat.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                        }

                    }
            }
            br.close();
            log.info("sampleFormat:finish");
            log.info("sampleFormat:耗时--->{}",(System.currentTimeMillis() - starttime) + "ms");
            data.put("status","success");
            data.put("msg","格式化流程最终文件成功！");

        }catch(Exception e){
            e.printStackTrace();
            data.put("msg","格式化流程最终文件失败！");
            data.put("status","fail");
        }
        return success(data);
    }

    /**
     * 从大样本库中提取所选择的样本
     * @param instanceId
     * @param instanceName
     * @return
     */
    @RequestMapping(value = "extractSamples")
    @ResponseBody
    public AjaxResult extractSamples(String instanceId,String instanceName) {
        log.info("extractSamples:start");
        long starttime = System.currentTimeMillis();
        String ratio = configService.selectConfigByKey("sample.filter.ratio");
        Map<String,Object> data = new HashMap<>();
        //查询所选择的样本
        GeneInstanceSample instanceSample = new GeneInstanceSample();
        instanceSample.setInstanceId(instanceId);
        List<GeneInstanceSample> sampleList = geneSampleService.listSelectedSamples(instanceSample);
        if(sampleList == null || sampleList.size() == 0){
            data.put("status","fail");
            data.put("msg","未选择样本");
            return success(data);
        }

//        String filename = "D:\\suyuan\\20240328\\mergeVcf\\extractedSamples.txt";
        String filename = "original/"+ instanceName + "/extractedSamples.txt";
        try{
            File file = new File(filename);
            if(file.exists()){
                file.delete();
            }
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        //标准样本库
//        String path = "D:\\suyuan\\20240328\\SNP_INDEL_Pf3D7_all_final_v3.combined.filtered.vcf";
        String path = "standard/SNP_INDEL_Pf3D7_all_final_v3.combined.filtered.vcf";
        File file = new File(path);
        if(!file.exists()){
            data.put("status","fail");
            data.put("msg","标准样本库：SNP_INDEL_Pf3D7_all_final_v3.combined.filtered.vcf不存在！");
            return success(data);
        }
        try{
            //构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line = null;
            //使用readLine方法，一次读一行
            while((line = br.readLine())!=null) {
                //将行数据转化成数组
                String[] ArrStr = line.split("\t");
                String str = "";
                int sum = 0;
                int count = sampleList.size();
                //根据列数从数组中选择所需数据
//                for(int i=0;i<columnNumbers.size();i++){
//                    int columnnuber = columnNumbers .get(i)-1;
//                    str = str + "\t" + ArrStr[columnnuber];
//                }
                for(GeneInstanceSample sample:sampleList){
                    int columnnuber = sample.getColumnNumber() - 1;
                    if(StringUtils.isNotBlank(sample.getSerialNumber()) && sample.getSerialNumber().equals(ArrStr[columnnuber])){
                        if(StringUtils.isNotBlank(sample.getCountries()))
                        //将标题行改为国家名
                        str = str + "\t" + sample.getCountries();
                        sum ++;
                    }else {
                        str = str + "\t" + ArrStr[columnnuber];
                        sum += Integer.parseInt(ArrStr[columnnuber]);
                    }
                }
                if(StringUtils.isBlank(ratio) || "0".equals(ratio) || (float)sum/count >= Float.parseFloat(ratio)) {
                    //拼接成新的行 CHROM POS
//                str = ArrStr[1]+ "\t" + ArrStr[2] + str + "\n";
                    str = ArrStr[0] + "\t" + ArrStr[1] + "\t" + ArrStr[2] + "\t" + ArrStr[3] + str + "\n";
//                log.info("extractSamples:row--->{}",str);
                    //追加写模式 将新的行数据写入文件
                    Files.write(Paths.get(filename), str.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                }
            }
            br.close();
            log.info("extractSamples:finish");
            log.info("extractSamples:耗时--->{}",(System.currentTimeMillis() - starttime) + "ms");
            data.put("status","success");
        }catch(Exception e){
            e.printStackTrace();
            data.put("msg","从标准样本库中抽取样本失败！");
            data.put("status","fail");
        }
        return success(data);
    }

    /**
     * 将 已格式化的流程最终文件 和 提取出来的样本文件 进行合并
     * 合并规则是:具有相同位置的行合并为一行并且要保证左右两边各自的和都大于0,其余的舍弃
     * 处理思路是:
     * 1.读取这两个文件的行并存放到两个list中
     * 2.利用set对这两个list进行取交集
     * 3.取交集之后再分别到对应的list中取出新的list
     * 4.对这两个新的list进行循环,使具有相同位置的行对应起来组成新的行
     * @param instanceName
     * @return
     */
    @RequestMapping(value = "merge")
    @ResponseBody
    public AjaxResult merge(String instanceName) {
        log.info("merge:start");
        long starttime = System.currentTimeMillis();
        Map<String,Object> data = new HashMap<>();
//        String filename = "D:\\suyuan\\20240328\\mergeVcf\\mergedVcf.txt";
        String filename = "original/"+ instanceName + "/mergedVcf.txt";

        try{
            File file = new File(filename);
            if(file.exists()){
                file.delete();
            }
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        //已格式化的流程最终文件
//        String format_path = "D:\\suyuan\\20240328\\mergeVcf\\formatInstanceVcf.txt";
        String format_path = "original/"+ instanceName + "/formatInstanceVcf.txt";

        //提取出来的样本文件
//        String extract_path = "D:\\suyuan\\20240328\\mergeVcf\\extractedSamples.txt";
        String extract_path = "original/"+ instanceName + "/extractedSamples.txt";

        File format_file = new File(format_path);
        File extract_file = new File(extract_path);
        if(!format_file.exists()){
            data.put("status","fail");
            data.put("msg","已格式化的流程最终文件：formatInstanceVcf.txt不存在！");
            return success(data);
        }
        if(!extract_file.exists()){
            data.put("status","fail");
            data.put("msg","提取的样本文件：extractedSamples.txt不存在！");
            return success(data);
        }
        try{
            //构造BufferedReader类来读取文件
            BufferedReader extract_br = new BufferedReader(new InputStreamReader(Files.newInputStream(extract_file.toPath()), StandardCharsets.UTF_8));
            String extract_row = null;
            BufferedReader format_br = new BufferedReader(new InputStreamReader(Files.newInputStream(format_file.toPath()), StandardCharsets.UTF_8));
            String format_row = null;

            //行列表（下同）
            List<String> format_list_row = new ArrayList<>();
            //位置列表，用于取交集（下同）
            List<String> format_list_pos = new ArrayList<>();
            List<String> extract_list_row = new ArrayList<>();
            List<String> extract_list_pos = new ArrayList<>();
            //合并结果列表
            List<String[]> result_list = new ArrayList<>();

            //循环读取 已格式化的流程最终文件
            while((format_row = format_br.readLine()) != null) {
                String[] format_ArrStr = format_row.split("\t");
                //将 CHROM 和 POS 组合起来组成唯一行id(基因位点)
                format_list_pos.add(format_ArrStr[0] + '&' + format_ArrStr[1]);
                //行信息
                format_list_row.add(format_row);
            }
            format_br.close();

            //循环读取 提取出来的样本文件
            while((extract_row = extract_br.readLine()) != null) {
                String[] extract_ArrStr = extract_row.split("\t");
                //将 CHROM 和 POS 组合起来组成唯一行id(基因位点)
                extract_list_pos.add(extract_ArrStr[0] + '&' + extract_ArrStr[1]);
                //行信息
                extract_list_row.add(extract_row);
            }
            extract_br.close();

            //将两个基因位点list存放到set中
            Set<String> format_set = new HashSet<>(format_list_pos);
            Set<String> extract_set = new HashSet<>(extract_list_pos);

            //取交集
            Set<String> intersectionSet = Sets.intersection(format_set, extract_set);
            List<String> intersection = new ArrayList<>(intersectionSet);

            //过滤后的list,排除了非共有行的其他行
            List<String> format_filter_row_list = new ArrayList<>();
            List<String> extract_filter_row__list = new ArrayList<>();

            //从格式化文件中筛选出共有行
            for(String row:format_list_row){
                String[] format_ArrStr = row.split("\t");
                //第一行标题
                Boolean flag1 = format_ArrStr[1].equalsIgnoreCase("pos");
                Boolean flag2 = intersection.contains(format_ArrStr[0] + '&' + format_ArrStr[1]);
                if(flag1 || flag2)
                    format_filter_row_list.add(row);
            }

            //从提取文件中筛选出共有行
            for(String row:extract_list_row){
                String[] extract_ArrStr = row.split("\t");
                //第一行标题
                Boolean flag1 = extract_ArrStr[1].equalsIgnoreCase("pos");
                Boolean flag2 = intersection.contains(extract_ArrStr[0] + '&' + extract_ArrStr[1]);
                if(flag1 || flag2)
                    extract_filter_row__list.add(row);
            }

            //对筛选后的两个list进行循环，使具有相同位点的行对应起来组成新的行
            for(String extractrow:extract_filter_row__list){
                String[] extract_ArrStr = extractrow.split("\t");
                    for(String formatrow:format_filter_row_list){
                    String str = extractrow;
                    String[] format_ArrStr = formatrow.split("\t");
                    Boolean flag1 = format_ArrStr[1].equalsIgnoreCase("pos") && extract_ArrStr[1].equalsIgnoreCase("pos");
                    Boolean flag2 = format_ArrStr[0].equals(extract_ArrStr[0]) && format_ArrStr[1].equals(extract_ArrStr[1]);
                    if(flag1 || flag2) {
                        for (int i = 4; i < format_ArrStr.length; i++) {
                            str = str + "\t" + format_ArrStr[i];
                        }
                        result_list.add(str.split("\t"));
//                        log.info("merge:row--->{}",str);
                        str = str + "\n";
                        //写入txt文件
                        Files.write(Paths.get(filename), str.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                        break;
                    }
                }
            }
            //写入csv文件（数据相同，方便后续pca处理）
//            CsvUtils.writeCSV("D:\\suyuan\\20240328\\mergeVcf\\mergedVcf.csv",result_list);
            CsvUtils.writeCSV("original/"+ instanceName + "/mergedVcf.csv",result_list);
            log.info("merge:finish");
            log.info("merge:耗时--->{}",(System.currentTimeMillis() - starttime) + "ms");
            data.put("status","success");
        }catch(Exception e){
            e.printStackTrace();
            data.put("msg","合并失败！");
            data.put("status","fail");
        }
        return success(data);
    }

}
