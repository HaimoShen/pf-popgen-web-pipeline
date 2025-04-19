package com.ruoyi.project.gdp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.gdp.domain.GeneInstance;
import com.ruoyi.project.gdp.domain.GeneProcess;
import com.ruoyi.project.gdp.service.IGeneInstanceService;
import com.ruoyi.project.gdp.service.IGeneProcessService;
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
 * 处理流程Controller
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
@Controller
@RequestMapping("/gdp/process")
public class GeneProcessController extends BaseController
{
    private String prefix = "gdp/process";

    @Autowired
    private IGeneProcessService geneProcessService;

    @Autowired
    private IGeneInstanceService geneInstanceService;

    @RequiresPermissions("gdp:process:view")
    @GetMapping()
    public String process()
    {
        return prefix + "/process";
    }

    /**
     * 查询处理流程列表
     */
    @RequiresPermissions("gdp:process:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(GeneProcess geneProcess)
    {
        startPage();
        List<GeneProcess> list = geneProcessService.selectGeneProcessList(geneProcess);
        return getDataTable(list);
    }

    /**
     * 导出处理流程列表
     */
    @RequiresPermissions("gdp:process:export")
    @Log(title = "处理流程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GeneProcess geneProcess)
    {
        List<GeneProcess> list = geneProcessService.selectGeneProcessList(geneProcess);
        ExcelUtil<GeneProcess> util = new ExcelUtil<GeneProcess>(GeneProcess.class);
        return util.exportExcel(list, "处理流程数据");
    }

    /**
     * 新增处理流程
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存处理流程
     */
    @RequiresPermissions("gdp:process:add")
    @Log(title = "处理流程", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(GeneProcess geneProcess)
    {
        return toAjax(geneProcessService.insertGeneProcess(geneProcess));
    }

    /**
     * 修改处理流程
     */
    @RequiresPermissions("gdp:process:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        GeneProcess geneProcess = geneProcessService.selectGeneProcessById(id);
        mmap.put("geneProcess", geneProcess);
        return prefix + "/edit";
    }

    /**
     * 修改保存处理流程
     */
    @RequiresPermissions("gdp:process:edit")
    @Log(title = "处理流程", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(GeneProcess geneProcess)
    {
        return toAjax(geneProcessService.updateGeneProcess(geneProcess));
    }

    /**
     * 批量更新状态
     */
    @RequiresPermissions("gdp:process:edit")
    @PostMapping("/batchUpdate")
    @ResponseBody
    public AjaxResult batchUpdate(String ids,Integer status)
    {
        return toAjax(geneProcessService.batchUpdate(ids,status));
    }

    /**
     * 删除处理流程
     */
    @RequiresPermissions("gdp:process:remove")
    @Log(title = "处理流程", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(geneProcessService.deleteGeneProcessByIds(ids));
    }

    @PostMapping(value = "findStatusListAndCreateInstance")
    @ResponseBody
    public AjaxResult findStatusListAndCreateInstance(GeneProcess geneProcess, String dirName, boolean flag, String combineStatus)  {
        Map<String,Object> data = new HashMap<>();
        String instanceId = flag ? IdUtils.simpleUUID() : "";
        data.put("list",geneProcessService.findStatusListAndCreateInstance(geneProcess,dirName,instanceId,combineStatus));
        data.put("instanceId",instanceId);
        data.put("message","创建实例和获取需要进行处理的流程列表成功！");
        return success(data);
    }

    @RequestMapping(value = "processGene")
    @ResponseBody
    public AjaxResult processGene(String id,String dirName, String instanceId){
        GeneProcess geneProcess = geneProcessService.selectGeneProcessById(id);
        Map<String,Object> data = geneProcessService.processGene(geneProcess,dirName,instanceId);
        return success(data);
    }
}
