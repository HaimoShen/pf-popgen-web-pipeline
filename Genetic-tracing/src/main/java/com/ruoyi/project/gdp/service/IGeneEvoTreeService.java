package com.ruoyi.project.gdp.service;

import com.ruoyi.project.gdp.domain.GeneEvoTree;

import java.util.List;
import java.util.Map;

/**
 * 进化树Service接口
 *
 * @author ruoyi
 * @date 2023-03-10
 */
public interface IGeneEvoTreeService
{
    /**
     * 查询进化树
     *
     * @param name 进化树主键
     * @return 进化树
     */
    public GeneEvoTree selectGeneEvoTreeByName(String name);

    /**
     * 查询进化树列表
     *
     * @param geneEvoTree 进化树
     * @return 进化树集合
     */
    public List<GeneEvoTree> selectGeneEvoTreeList(GeneEvoTree geneEvoTree);

    /**
     * 新增进化树
     *
     * @param geneEvoTree 进化树
     * @return 结果
     */
    public int insertGeneEvoTree(GeneEvoTree geneEvoTree);

    /**
     * 修改进化树
     *
     * @param geneEvoTree 进化树
     * @return 结果
     */
    public int updateGeneEvoTree(GeneEvoTree geneEvoTree);

    /**
     * 批量删除进化树
     *
     * @param names 需要删除的进化树主键集合
     * @return 结果
     */
    public int deleteGeneEvoTreeByNames(String names);

    /**
     * 删除进化树信息
     *
     * @param name 进化树主键
     * @return 结果
     */
    public int deleteGeneEvoTreeByName(String name);

    public GeneEvoTree getByName(String name);
}
