package com.ruoyi.project.gdp.mapper;

import java.util.List;
import com.ruoyi.project.gdp.domain.GeneProcess;
import org.apache.ibatis.annotations.Param;

/**
 * 处理流程Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
public interface GeneProcessMapper 
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

    public int batchUpdate(@Param("array") String[] ids,@Param("status") Integer status);

    /**
     * 删除处理流程
     * 
     * @param id 处理流程主键
     * @return 结果
     */
    public int deleteGeneProcessById(String id);

    /**
     * 批量删除处理流程
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGeneProcessByIds(String[] ids);
}
