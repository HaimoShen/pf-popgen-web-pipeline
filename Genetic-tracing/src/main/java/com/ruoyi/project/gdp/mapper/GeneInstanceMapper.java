package com.ruoyi.project.gdp.mapper;

import com.ruoyi.project.gdp.domain.GeneInstance;

import java.util.List;

/**
 * 处理实例Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
public interface GeneInstanceMapper 
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
     * 删除处理实例
     * 
     * @param id 处理实例主键
     * @return 结果
     */
    public int deleteGeneInstanceById(String id);

    /**
     * 批量删除处理实例
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGeneInstanceByIds(String[] ids);

    public GeneInstance getByName(String name);
}
