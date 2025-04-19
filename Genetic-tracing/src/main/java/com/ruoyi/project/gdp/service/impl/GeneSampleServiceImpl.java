package com.ruoyi.project.gdp.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.ruoyi.common.utils.text.Convert;
import com.ruoyi.project.gdp.domain.GeneInstanceSample;
import com.ruoyi.project.gdp.domain.GeneSample;
import com.ruoyi.project.gdp.mapper.GeneSampleMapper;
import com.ruoyi.project.gdp.service.IGeneSampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 样本合并Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-04-11
 */
@Service
public class GeneSampleServiceImpl implements IGeneSampleService
{
    @Autowired
    private GeneSampleMapper geneSampleMapper;

    /**
     * 查询样本合并
     * 
     * @param serialNumber 样本合并主键
     * @return 样本合并
     */
    @Override
    public GeneSample selectGeneSampleBySerialNumber(String serialNumber)
    {
        return geneSampleMapper.selectGeneSampleBySerialNumber(serialNumber);
    }

    /**
     * 查询样本合并列表
     * 
     * @param geneSample 样本合并
     * @return 样本合并
     */
    @Override
    public List<GeneSample> selectGeneSampleList(GeneSample geneSample)
    {
        return geneSampleMapper.selectGeneSampleList(geneSample);
    }

    @Override
    public List<GeneInstanceSample> listSelectedSamples(GeneInstanceSample instanceSample)
    {
        return geneSampleMapper.listSelectedSamples(instanceSample);
    }

    @Override
    public List<GeneInstanceSample> listUnSelectedSamples(GeneInstanceSample instanceSample)
    {
        return geneSampleMapper.listUnSelectedSamples(instanceSample);
    }

    /**
     * 新增样本合并
     * 
     * @param geneSample 样本合并
     * @return 结果
     */
    @Override
    public int insertGeneSample(GeneSample geneSample)
    {
        return geneSampleMapper.insertGeneSample(geneSample);
    }

    /**
     * 修改样本合并
     * 
     * @param geneSample 样本合并
     * @return 结果
     */
    @Override
    public int updateGeneSample(GeneSample geneSample)
    {
        return geneSampleMapper.updateGeneSample(geneSample);
    }

    /**
     * 批量删除样本合并
     * 
     * @param serialNumbers 需要删除的样本合并主键
     * @return 结果
     */
    @Override
    public int deleteGeneSampleBySerialNumbers(String serialNumbers)
    {
        return geneSampleMapper.deleteGeneSampleBySerialNumbers(Convert.toStrArray(serialNumbers));
    }

    /**
     * 删除样本合并信息
     * 
     * @param serialNumber 样本合并主键
     * @return 结果
     */
    @Override
    public int deleteGeneSampleBySerialNumber(String serialNumber)
    {
        return geneSampleMapper.deleteGeneSampleBySerialNumber(serialNumber);
    }

    @Override
    public int saveSelectedSamples(GeneInstanceSample instanceSample)
    {
        return geneSampleMapper.saveSelectedSamples(instanceSample);
    }

    @Override
    public int removeSelectedSamples(String id)
    {
        return geneSampleMapper.removeSelectedSamples(id);
    }

    @Override
    public int removeAllSelectedSamples(String instanceId)
    {
        return geneSampleMapper.removeAllSelectedSamples(instanceId);
    }

    @Override
    public String getCountriesBySerialNumber(String serialNumber)
    {
        return geneSampleMapper.getCountriesBySerialNumber(serialNumber);
    }

    @Override
    public String getCountriesByColumnNumber(Integer columnNumber)
    {
        return geneSampleMapper.getCountriesByColumnNumber(columnNumber);
    }

    @Override
    public List<String> randomSample(){
        List<GeneSample> list = geneSampleMapper.selectGeneSampleList(new GeneSample());
        // 按分类分组
        Map<String, List<GeneSample>> grouped = list.stream().collect(Collectors.groupingBy(GeneSample::getCountries));

        Random random = new Random();
        List<String> result = new ArrayList<>();
        grouped.forEach((country, items) -> {
            List<GeneSample> samples = new ArrayList<>();
            int itemCount = items.size();
            int sampleSize = Math.min(400, itemCount);

            if (itemCount >= 400) {
                List<GeneSample> shuffled = new ArrayList<>(items);
                Collections.shuffle(shuffled, random);
                samples = shuffled.subList(0, 400);
            } else if(itemCount >= 50) {
                samples = new ArrayList<>(items);
            }
            result.addAll(samples.stream().map(GeneSample::getSerialNumber).collect(Collectors.toList()));
        });
        return result;
    }
}
