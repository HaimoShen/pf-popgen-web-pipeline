package com.ruoyi.project.gdp.controller;

import java.util.List;

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
import com.ruoyi.project.system.user.domain.User;
import com.ruoyi.project.system.user.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 处理任务Controller
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
@Controller
@RequestMapping("/gdp/task")
public class GeneTaskController extends BaseController
{
    private String prefix = "gdp/task";

    @Autowired
    private IGeneInstanceService geneInstanceService;

    @Autowired
    private IGeneProcessService geneProcessService;

    @Autowired
    private IGeneTaskService geneTaskService;

    @Autowired
    private IUserService userService;

    @RequiresPermissions("gdp:task:view")
    @GetMapping()
    public String task()
    {
        return prefix + "/task";
    }

    /**
     * 查询处理任务列表
     */
    @RequiresPermissions("gdp:task:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(GeneTask geneTask)
    {
        startPage();
        List<GeneTask> list = geneTaskService.selectGeneTaskList(geneTask);
        return getDataTable(list);
    }

    /**
     * 导出处理任务列表
     */
    @RequiresPermissions("gdp:task:export")
    @Log(title = "处理任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GeneTask geneTask)
    {
        List<GeneTask> list = geneTaskService.selectGeneTaskList(geneTask);
        ExcelUtil<GeneTask> util = new ExcelUtil<GeneTask>(GeneTask.class);
        return util.exportExcel(list, "处理任务数据");
    }

    /**
     * 新增处理任务
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存处理任务
     */
    @RequiresPermissions("gdp:task:add")
    @Log(title = "处理任务", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(GeneTask geneTask)
    {
        return toAjax(geneTaskService.insertGeneTask(geneTask));
    }

    /**
     * 修改处理任务
     */
    @RequiresPermissions("gdp:task:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        GeneTask geneTask = geneTaskService.selectGeneTaskById(id);
        GeneInstance geneInstance = geneInstanceService.selectGeneInstanceById(geneTask.getInstanceId());
        GeneProcess geneProcess = geneProcessService.selectGeneProcessById(geneTask.getProcessId());
        mmap.put("geneInstance", geneInstance);
        mmap.put("geneProcess", geneProcess);
        mmap.put("geneTask", geneTask);
        return prefix + "/edit";
    }

    /**
     * 修改保存处理任务
     */
    @RequiresPermissions("gdp:task:edit")
    @Log(title = "处理任务", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(GeneTask geneTask)
    {
        return toAjax(geneTaskService.updateGeneTask(geneTask));
    }

    /**
     * 删除处理任务
     */
    @RequiresPermissions("gdp:task:remove")
    @Log(title = "处理任务", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(geneTaskService.deleteGeneTaskByIds(ids));
    }
}
