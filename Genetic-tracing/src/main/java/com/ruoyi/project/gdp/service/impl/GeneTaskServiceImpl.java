package com.ruoyi.project.gdp.service.impl;

import com.ruoyi.common.utils.text.Convert;
import com.ruoyi.project.gdp.domain.GeneTask;
import com.ruoyi.project.gdp.mapper.GeneTaskMapper;
import com.ruoyi.project.gdp.service.IGeneTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 处理任务Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
@Service
public class GeneTaskServiceImpl implements IGeneTaskService
{
    @Autowired
    private GeneTaskMapper geneTaskMapper;

    /**
     * 查询处理任务
     * 
     * @param id 处理任务主键
     * @return 处理任务
     */
    @Override
    public GeneTask selectGeneTaskById(String id)
    {
        return geneTaskMapper.selectGeneTaskById(id);
    }

    /**
     * 查询处理任务列表
     * 
     * @param geneTask 处理任务
     * @return 处理任务
     */
    @Override
    public List<GeneTask> selectGeneTaskList(GeneTask geneTask)
    {
        return geneTaskMapper.selectGeneTaskList(geneTask);
    }

    /**
     * 新增处理任务
     * 
     * @param geneTask 处理任务
     * @return 结果
     */
    @Override
    @Transactional(readOnly = false)
    public int insertGeneTask(GeneTask geneTask)
    {
        return geneTaskMapper.insertGeneTask(geneTask);
    }

    /**
     * 修改处理任务
     * 
     * @param geneTask 处理任务
     * @return 结果
     */
    @Override
    @Transactional(readOnly = false)
    public int updateGeneTask(GeneTask geneTask)
    {
        return geneTaskMapper.updateGeneTask(geneTask);
    }

    /**
     * 批量删除处理任务
     * 
     * @param ids 需要删除的处理任务主键
     * @return 结果
     */
    @Override
    @Transactional(readOnly = false)
    public int deleteGeneTaskByIds(String ids)
    {
        return geneTaskMapper.deleteGeneTaskByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除处理任务信息
     * 
     * @param id 处理任务主键
     * @return 结果
     */
    @Override
    @Transactional(readOnly = false)
    public int deleteGeneTaskById(String id)
    {
        return geneTaskMapper.deleteGeneTaskById(id);
    }
}
