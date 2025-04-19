package com.ruoyi.project.gdp.service;

import java.util.List;

import com.ruoyi.project.gdp.domain.GeneInstanceSample;
import com.ruoyi.project.gdp.domain.GeneSample;

/**
 * 样本合并Service接口
 * 
 * @author ruoyi
 * @date 2023-04-11
 */
public interface IGeneSampleService 
{
    /**
     * 查询样本合并
     * 
     * @param serialNumber 样本合并主键
     * @return 样本合并
     */
    public GeneSample selectGeneSampleBySerialNumber(String serialNumber);

    /**
     * 查询样本合并列表
     * 
     * @param geneSample 样本合并
     * @return 样本合并集合
     */
    public List<GeneSample> selectGeneSampleList(GeneSample geneSample);

    public List<GeneInstanceSample> listSelectedSamples(GeneInstanceSample instanceSample);

    public List<GeneInstanceSample> listUnSelectedSamples(GeneInstanceSample instanceSample);

    /**
     * 新增样本合并
     * 
     * @param geneSample 样本合并
     * @return 结果
     */
    public int insertGeneSample(GeneSample geneSample);

    /**
     * 修改样本合并
     * 
     * @param geneSample 样本合并
     * @return 结果
     */
    public int updateGeneSample(GeneSample geneSample);

    /**
     * 批量删除样本合并
     * 
     * @param serialNumbers 需要删除的样本合并主键集合
     * @return 结果
     */
    public int deleteGeneSampleBySerialNumbers(String serialNumbers);

    /**
     * 删除样本合并信息
     * 
     * @param serialNumber 样本合并主键
     * @return 结果
     */
    public int deleteGeneSampleBySerialNumber(String serialNumber);

    public int saveSelectedSamples(GeneInstanceSample instanceSample);

    public int removeSelectedSamples(String id);

    public int removeAllSelectedSamples(String instanceId);

    public String getCountriesBySerialNumber(String serialNumber);
    public String getCountriesByColumnNumber(Integer columnNumber);

    public List<String> randomSample();
}
