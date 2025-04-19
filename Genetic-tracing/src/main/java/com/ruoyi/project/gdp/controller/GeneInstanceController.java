package com.ruoyi.project.gdp.controller;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.gdp.domain.GeneInstance;
import com.ruoyi.project.gdp.domain.GeneProcess;
import com.ruoyi.project.gdp.domain.GeneTask;
import com.ruoyi.project.gdp.service.IGeneInstanceService;
import com.ruoyi.project.gdp.service.IGeneProcessService;
import com.ruoyi.project.gdp.service.IGeneTaskService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 处理实例Controller
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
@Controller
@RequestMapping("/gdp/instance")
public class GeneInstanceController extends BaseController
{
    private String prefix = "gdp/instance";

    @Autowired
    private IGeneInstanceService geneInstanceService;

    @Autowired
    private IGeneTaskService geneTaskService;

    @Autowired
    private IGeneProcessService geneProcessService;

    @RequiresPermissions("gdp:instance:view")
    @GetMapping()
    public String instance()
    {
        return prefix + "/instance";
    }

    /**
     * 查询处理实例列表
     */
    @RequiresPermissions("gdp:instance:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(GeneInstance geneInstance)
    {
        startPage();
        List<GeneInstance> list = geneInstanceService.selectGeneInstanceList(geneInstance);
        return getDataTable(list);
    }

    /**
     * 导出处理实例列表
     */
    @RequiresPermissions("gdp:instance:export")
    @Log(title = "处理实例", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GeneInstance geneInstance)
    {
        List<GeneInstance> list = geneInstanceService.selectGeneInstanceList(geneInstance);
        ExcelUtil<GeneInstance> util = new ExcelUtil<GeneInstance>(GeneInstance.class);
        return util.exportExcel(list, "处理实例数据");
    }

    /**
     * 新增处理实例
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存处理实例
     */
    @RequiresPermissions("gdp:instance:add")
    @Log(title = "处理实例", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(GeneInstance geneInstance)
    {
        return toAjax(geneInstanceService.insertGeneInstance(geneInstance));
    }

    /**
     * 修改处理实例
     */
    @RequiresPermissions("gdp:instance:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        //根据实例ID获取实例任务
        GeneTask geneTask = new GeneTask();
        geneTask.setInstanceId(id);
        List<GeneTask> geneTaskList = geneTaskService.selectGeneTaskList(geneTask);
        //判断实例任务中是否存在状态为失败且不忽略运行结果的任务的任务(存在false,不存在true)
        for(GeneTask g:geneTaskList){
            boolean a = "0".equals(String.valueOf(g.getStatus()));
            boolean b = "0".equals(String.valueOf(geneProcessService.selectGeneProcessById(g.getProcessId()).getIgnorecommand()));
            System.out.println(a);
            System.out.println(b);
        }
        boolean continueFlag = geneTaskList.stream().noneMatch(g -> "0".equals(String.valueOf(g.getStatus())) && "0".equals(String.valueOf(geneProcessService.selectGeneProcessById(g.getProcessId()).getIgnorecommand())));
        //取实例任务中的流程id组成新list(实例存在则获取，不存在则不获取)
        List<String> existgeneProcessIdList = geneTaskList.stream().map(GeneTask::getProcessId).collect(Collectors.toList());
        //判断所有可运行流程与已运行流程是否一致
        GeneProcess geneProcess = new GeneProcess();
        geneProcess.setStatus(1);
        boolean allMatchFlag = geneProcessService.selectGeneProcessList(geneProcess).stream().allMatch(g -> existgeneProcessIdList.contains(g.getId()));
        mmap.addAttribute("geneInstance", geneInstanceService.selectGeneInstanceById(id));
        mmap.addAttribute("geneTaskList", geneTaskList);
        mmap.addAttribute("continueFlag", continueFlag&&!allMatchFlag);
        mmap.addAttribute("existgeneProcessIdList", existgeneProcessIdList);
        mmap.addAttribute("timeConsum", DateUtils.dateTimeSum(geneTaskList.stream().map(GeneTask::getTime).collect(Collectors.toList())));
        return prefix + "/edit";
    }

    /**
     * 修改保存处理实例
     */
    @RequiresPermissions("gdp:instance:edit")
    @Log(title = "处理实例", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(GeneInstance geneInstance)
    {
        return toAjax(geneInstanceService.updateGeneInstance(geneInstance));
    }

    /**
     * 删除处理实例
     */
    @RequiresPermissions("gdp:instance:remove")
    @Log(title = "处理实例", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(geneInstanceService.deleteGeneInstanceByIds(ids));
    }

    @PostMapping(value = "/dataTimeSum")
    @ResponseBody
    public AjaxResult dataTimeSum(String time1,String time2)  {
        Map<String,Object> data = new HashMap<>();
        data.put("dataTimeSum", DateUtils.dateTimeSum(time1,time2));
        return success(data);
    }

    @PostMapping(value = "judgeInstanceExistByName")
    @ResponseBody
    public AjaxResult judgeInstanceExistByName(String name)  {
        Map<String,Object> data = new HashMap<>();
        data.put("result","false");
        GeneInstance geneInstance = geneInstanceService.getByName(name);
        if(geneInstance != null)
        data.put("result","true");
        return success(data);
    }
}
