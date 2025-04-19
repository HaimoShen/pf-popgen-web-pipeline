package com.ruoyi.project.gdp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.text.Convert;
import com.ruoyi.project.gdp.domain.GeneInstance;
import com.ruoyi.project.gdp.mapper.GeneInstanceMapper;
import com.ruoyi.project.gdp.service.IGeneInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 处理实例Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
@Service
public class GeneInstanceServiceImpl implements IGeneInstanceService
{
    @Autowired
    private GeneInstanceMapper geneInstanceMapper;

    /**
     * 查询处理实例
     * 
     * @param id 处理实例主键
     * @return 处理实例
     */
    @Override
    public GeneInstance selectGeneInstanceById(String id)
    {
        return geneInstanceMapper.selectGeneInstanceById(id);
    }

    /**
     * 查询处理实例列表
     * 
     * @param geneInstance 处理实例
     * @return 处理实例
     */
    @Override
    public List<GeneInstance> selectGeneInstanceList(GeneInstance geneInstance)
    {
        return geneInstanceMapper.selectGeneInstanceList(geneInstance);
    }

    /**
     * 新增处理实例
     * 
     * @param geneInstance 处理实例
     * @return 结果
     */
    @Override
    @Transactional(readOnly = false)
    public int insertGeneInstance(GeneInstance geneInstance)
    {
        return geneInstanceMapper.insertGeneInstance(geneInstance);
    }

    /**
     * 修改处理实例
     * 
     * @param geneInstance 处理实例
     * @return 结果
     */
    @Override
    @Transactional(readOnly = false)
    public int updateGeneInstance(GeneInstance geneInstance)
    {
        return geneInstanceMapper.updateGeneInstance(geneInstance);
    }

    /**
     * 批量删除处理实例
     * 
     * @param ids 需要删除的处理实例主键
     * @return 结果
     */
    @Override
    @Transactional(readOnly = false)
    public int deleteGeneInstanceByIds(String ids)
    {
        return geneInstanceMapper.deleteGeneInstanceByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除处理实例信息
     * 
     * @param id 处理实例主键
     * @return 结果
     */
    @Override
    @Transactional(readOnly = false)
    public int deleteGeneInstanceById(String id)
    {
        return geneInstanceMapper.deleteGeneInstanceById(id);
    }

    @Override
    public GeneInstance getByName(String name) {
        return geneInstanceMapper.getByName(name);
    }
}
