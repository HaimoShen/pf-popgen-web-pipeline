package com.ruoyi.project.gdp.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.project.gdp.domain.GeneInstance;
import com.ruoyi.project.gdp.domain.GeneProcess;

/**
 * 处理流程Service接口
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
public interface IGeneProcessService 
{
    /**
     * 查询处理流程
     * 
     * @param id 处理流程主键
     * @return 处理流程
     */
    public GeneProcess selectGeneProcessById(String id);

    /**
     * 查询处理流程列表
     * 
     * @param geneProcess 处理流程
     * @return 处理流程集合
     */
    public List<GeneProcess> selectGeneProcessList(GeneProcess geneProcess);

    /**
     * 新增处理流程
     * 
     * @param geneProcess 处理流程
     * @return 结果
     */
    public int insertGeneProcess(GeneProcess geneProcess);

    /**
     * 修改处理流程
     * 
     * @param geneProcess 处理流程
     * @return 结果
     */
    public int updateGeneProcess(GeneProcess geneProcess);

    public int batchUpdate(String ids,Integer status);

    /**
     * 批量删除处理流程
     * 
     * @param ids 需要删除的处理流程主键集合
     * @return 结果
     */
    public int deleteGeneProcessByIds(String ids);

    /**
     * 删除处理流程信息
     * 
     * @param id 处理流程主键
     * @return 结果
     */
    public int deleteGeneProcessById(String id);

    public List<GeneProcess> findStatusListAndCreateInstance(GeneProcess geneProcess, String dirName, String instanceId,String combineStatus);

    public Map<String, Object> processGene(GeneProcess gp,String dirName, String instanceId);

}
