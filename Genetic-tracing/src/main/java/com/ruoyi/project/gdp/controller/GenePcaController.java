package com.ruoyi.project.gdp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.gdp.domain.GeneInstance;
import com.ruoyi.project.gdp.domain.GenePca;
import com.ruoyi.project.gdp.service.IGeneInstanceService;
import com.ruoyi.project.gdp.service.IGenePcaService;
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
 * 数据降维Controller
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
@Controller
@RequestMapping("/gdp/pca")
public class GenePcaController extends BaseController
{
    private String prefix = "gdp/pca";

    @Autowired
    private IGenePcaService genePcaService;

    @Autowired
    private IGeneInstanceService geneInstanceService;

    @RequiresPermissions("gdp:pca:view")
    @GetMapping()
    public String pca()
    {
        return prefix + "/pca";
    }

    /**
     * 查询数据降维列表
     */
    @RequiresPermissions("gdp:pca:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(GenePca genePca)
    {
        startPage();
        List<GenePca> list = genePcaService.selectGenePcaList(genePca);
        return getDataTable(list);
    }

    /**
     * 导出数据降维列表
     */
    @RequiresPermissions("gdp:pca:export")
    @Log(title = "数据降维", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GenePca genePca)
    {
        List<GenePca> list = genePcaService.selectGenePcaList(genePca);
        ExcelUtil<GenePca> util = new ExcelUtil<GenePca>(GenePca.class);
        return util.exportExcel(list, "数据降维数据");
    }

    /**
     * 新增数据降维
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存数据降维
     */
    @RequiresPermissions("gdp:pca:add")
    @Log(title = "数据降维", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(GenePca genePca)
    {
        return toAjax(genePcaService.insertGenePca(genePca));
    }

    /**
     * 修改数据降维
     */
    @RequiresPermissions("gdp:pca:edit")
    @GetMapping("/edit/{name}")
    public String edit(@PathVariable("name") String name, ModelMap mmap)
    {
        GenePca genePca = genePcaService.selectGenePcaByName(name);
        mmap.put("genePca", genePca);
        return prefix + "/edit";
    }

    /**
     * 修改保存数据降维
     */
    @RequiresPermissions("gdp:pca:edit")
    @Log(title = "数据降维", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(GenePca genePca)
    {
        return toAjax(genePcaService.updateGenePca(genePca));
    }

    /**
     * 删除数据降维
     */
    @RequiresPermissions("gdp:pca:remove")
    @Log(title = "数据降维", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(genePcaService.deleteGenePcaByNames(ids));
    }

    @PostMapping(value = "findPcaInstanceByName")
    @ResponseBody
    public AjaxResult findPcaInstanceByName(String name)  {
        Map<String,Object> data = new HashMap<>();
        GeneInstance geneInstance = geneInstanceService.getByName(name);
        data.put("result","false");
        if(geneInstance == null){
            data.put("msg","基因处理实例不存在");
        } else {
            GenePca genePca = genePcaService.getByName(name);
            if (genePca != null)
              data.put("msg","降维实例已存在");
            else
              data.put("result","true");
        }
        return success(data);
    }

    @RequestMapping(value = "processPca")
    @ResponseBody
    public AjaxResult processPca(String name){
        Map<String,Object> data = genePcaService.processPca(name);
        return success(data);
    }
}
