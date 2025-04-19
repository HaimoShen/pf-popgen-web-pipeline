package com.ruoyi.project.gdp.service;

import com.ruoyi.project.gdp.domain.GeneTask;

import java.util.List;


/**
 * 处理任务Service接口
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
public interface IGeneTaskService 
{
    /**
     * 查询处理任务
     * 
     * @param id 处理任务主键
     * @return 处理任务
     */
    public GeneTask selectGeneTaskById(String id);

    /**
     * 查询处理任务列表
     * 
     * @param geneTask 处理任务
     * @return 处理任务集合
     */
    public List<GeneTask> selectGeneTaskList(GeneTask geneTask);

    /**
     * 新增处理任务
     * 
     * @param geneTask 处理任务
     * @return 结果
     */
    public int insertGeneTask(GeneTask geneTask);

    /**
     * 修改处理任务
     * 
     * @param geneTask 处理任务
     * @return 结果
     */
    public int updateGeneTask(GeneTask geneTask);

    /**
     * 批量删除处理任务
     * 
     * @param ids 需要删除的处理任务主键集合
     * @return 结果
     */
    public int deleteGeneTaskByIds(String ids);

    /**
     * 删除处理任务信息
     * 
     * @param id 处理任务主键
     * @return 结果
     */
    public int deleteGeneTaskById(String id);
}
