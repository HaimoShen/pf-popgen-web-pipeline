package com.ruoyi.project.gdp.mapper;

import com.ruoyi.project.gdp.domain.GenePca;

import java.util.List;

/**
 * 数据降维Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
public interface GenePcaMapper 
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
     * 删除数据降维
     * 
     * @param name 数据降维主键
     * @return 结果
     */
    public int deleteGenePcaByName(String name);

    /**
     * 批量删除数据降维
     * 
     * @param names 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGenePcaByNames(String[] names);

    public GenePca getByName(String name);
}
