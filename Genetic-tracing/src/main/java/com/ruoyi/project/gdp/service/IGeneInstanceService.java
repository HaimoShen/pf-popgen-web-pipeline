package com.ruoyi.project.gdp.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.project.gdp.domain.GeneInstance;

/**
 * 处理实例Service接口
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
public interface IGeneInstanceService 
{
    /**
     * 查询处理实例
     * 
     * @param id 处理实例主键
     * @return 处理实例
     */
    public GeneInstance selectGeneInstanceById(String id);

    /**
     * 查询处理实例列表
     * 
     * @param geneInstance 处理实例
     * @return 处理实例集合
     */
    public List<GeneInstance> selectGeneInstanceList(GeneInstance geneInstance);

    /**
     * 新增处理实例
     * 
     * @param geneInstance 处理实例
     * @return 结果
     */
    public int insertGeneInstance(GeneInstance geneInstance);

    /**
     * 修改处理实例
     * 
     * @param geneInstance 处理实例
     * @return 结果
     */
    public int updateGeneInstance(GeneInstance geneInstance);

    /**
     * 批量删除处理实例
     * 
     * @param ids 需要删除的处理实例主键集合
     * @return 结果
     */
    public int deleteGeneInstanceByIds(String ids);

    /**
     * 删除处理实例信息
     * 
     * @param id 处理实例主键
     * @return 结果
     */
    public int deleteGeneInstanceById(String id);

    public GeneInstance getByName(String name);

}
