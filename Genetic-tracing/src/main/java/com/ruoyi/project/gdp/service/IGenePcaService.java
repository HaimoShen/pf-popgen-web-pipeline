package com.ruoyi.project.gdp.service;

import com.ruoyi.project.gdp.domain.GenePca;

import java.util.List;
import java.util.Map;

/**
 * 数据降维Service接口
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
public interface IGenePcaService 
{
    /**
     * 查询数据降维
     * 
     * @param name 数据降维主键
     * @return 数据降维
     */
    public GenePca selectGenePcaByName(String name);

    /**
     * 查询数据降维列表
     * 
     * @param genePca 数据降维
     * @return 数据降维集合
     */
    public List<GenePca> selectGenePcaList(GenePca genePca);

    /**
     * 新增数据降维
     * 
     * @param genePca 数据降维
     * @return 结果
     */
    public int insertGenePca(GenePca genePca);

    /**
     * 修改数据降维
     * 
     * @param genePca 数据降维
     * @return 结果
     */
    public int updateGenePca(GenePca genePca);

    /**
     * 批量删除数据降维
     * 
     * @param names 需要删除的数据降维主键集合
     * @return 结果
     */
    public int deleteGenePcaByNames(String names);

    /**
     * 删除数据降维信息
     * 
     * @param name 数据降维主键
     * @return 结果
     */
    public int deleteGenePcaByName(String name);

    public GenePca getByName(String name);

    public Map<String, Object> processPca(String name);
}
