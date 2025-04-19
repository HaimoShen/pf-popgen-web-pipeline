package com.ruoyi.project.gdp.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.command.CommandUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.common.utils.text.Convert;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.project.gdp.domain.GeneEvoTree;
import com.ruoyi.project.gdp.domain.GenePca;
import com.ruoyi.project.gdp.mapper.GeneEvoTreeMapper;
import com.ruoyi.project.gdp.service.IGeneEvoTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 进化树Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-10
 */
@Service
public class GeneEvoServiceImpl implements IGeneEvoTreeService
{
    @Autowired
    private GeneEvoTreeMapper geneEvoTreeMapper;

    /**
     * 查询进化树
     *
     * @param name 进化树主键
     * @return 进化树
     */
    @Override
    public GeneEvoTree selectGeneEvoTreeByName(String name)
    {
        return geneEvoTreeMapper.selectGeneEvoTreeByName(name);
    }

    /**
     * 查询进化树列表
     *
     * @param geneEvoTree 进化树
     * @return 进化树
     */
    @Override
    public List<GeneEvoTree> selectGeneEvoTreeList(GeneEvoTree geneEvoTree)
    {
        return geneEvoTreeMapper.selectGeneEvoTreeList(geneEvoTree);
    }

    /**
     * 新增进化树
     *
     * @param geneEvoTree 进化树
     * @return 结果
     */
    @Override
    public int insertGeneEvoTree(GeneEvoTree geneEvoTree)
    {
        return geneEvoTreeMapper.insertGeneEvoTree(geneEvoTree);
    }

    /**
     * 修改进化树
     *
     * @param geneEvoTree 进化树
     * @return 结果
     */
    @Override
    public int updateGeneEvoTree(GeneEvoTree geneEvoTree)
    {
        return geneEvoTreeMapper.updateGeneEvoTree(geneEvoTree);
    }

    /**
     * 批量删除进化树
     *
     * @param names 需要删除的进化树主键
     * @return 结果
     */
    @Override
    public int deleteGeneEvoTreeByNames(String names)
    {
        return geneEvoTreeMapper.deleteGeneEvoTreeByNames(Convert.toStrArray(names));
    }

    /**
     * 删除进化树信息
     *
     * @param name 进化树主键
     * @return 结果
     */
    @Override
    public int deleteGeneEvoTreeByName(String name)
    {
        return geneEvoTreeMapper.deleteGeneEvoTreeByName(name);
    }

    @Override
    public GeneEvoTree getByName(String name) {
        return geneEvoTreeMapper.getByName(name);
    }
}
