package com.ruoyi.project.gdp.service.impl;

import java.io.File;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.command.CommandUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.common.utils.text.Convert;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.project.gdp.domain.GeneInstance;
import com.ruoyi.project.gdp.domain.GenePca;
import com.ruoyi.project.gdp.mapper.GenePcaMapper;
import com.ruoyi.project.gdp.service.IGeneInstanceService;
import com.ruoyi.project.gdp.service.IGenePcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 数据降维Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
@Service
public class GenePcaServiceImpl implements IGenePcaService
{
    @Autowired
    private GenePcaMapper genePcaMapper;

    @Value("${ruoyi.basedir}")
    private String dataPath;

    /**
     * 查询数据降维
     * 
     * @param name 数据降维主键
     * @return 数据降维
     */
    @Override
    public GenePca selectGenePcaByName(String name)
    {
        return genePcaMapper.selectGenePcaByName(name);
    }

    /**
     * 查询数据降维列表
     * 
     * @param genePca 数据降维
     * @return 数据降维
     */
    @Override
    public List<GenePca> selectGenePcaList(GenePca genePca)
    {
        return genePcaMapper.selectGenePcaList(genePca);
    }

    /**
     * 新增数据降维
     * 
     * @param genePca 数据降维
     * @return 结果
     */
    @Override
    public int insertGenePca(GenePca genePca)
    {
        return genePcaMapper.insertGenePca(genePca);
    }

    /**
     * 修改数据降维
     * 
     * @param genePca 数据降维
     * @return 结果
     */
    @Override
    public int updateGenePca(GenePca genePca)
    {
        return genePcaMapper.updateGenePca(genePca);
    }

    /**
     * 批量删除数据降维
     * 
     * @param names 需要删除的数据降维主键
     * @return 结果
     */
    @Override
    public int deleteGenePcaByNames(String names)
    {
        return genePcaMapper.deleteGenePcaByNames(Convert.toStrArray(names));
    }

    /**
     * 删除数据降维信息
     * 
     * @param name 数据降维主键
     * @return 结果
     */
    @Override
    public int deleteGenePcaByName(String name)
    {
        return genePcaMapper.deleteGenePcaByName(name);
    }

    @Override
    public GenePca getByName(String name) {
        return genePcaMapper.getByName(name);
    }

    @Override
    public Map<String, Object> processPca(String name){
        Map<String, Object> data = new HashMap<>();
        String workingDir = dataPath + "/original/" + name + "/pca";
        File f = new File(workingDir);
        if(!f.exists()){
            f.mkdir();
        }
        String rscript = "Rscript " + dataPath + "/software/pca/Rscript.r arg[1] arg[2]";
        String excel = dataPath + "/original/" + name + "/mergedVcf.csv";

        rscript = rscript.replace("arg[1]",workingDir).replace("arg[2]",excel);
        System.out.println(rscript);
        Instant start = Instant.now();
        Map<String, Object> cmdData = CommandUtils.exeCmd(rscript, "rscript");
        boolean flag = Boolean.parseBoolean(cmdData.get("result_rscript").toString());

        if(flag) {
            String record = cmdData.get("s_msg_rscript").toString();
//            String record = "111";
            record = record.length() > 5000 ? record.substring(0, 5000) : record;
            data.put("result", "true");
            data.put("msg", record);
            String time = DateUtils.formatDateTime(ChronoUnit.MILLIS.between(start, Instant.now()));
            data.put("time", time);
            String src1 = "/original/" + name + "/pca/sandian.jpeg";
            String src2 = "/original/" + name + "/pca/sandian2.jpeg";
            data.put("src1", src1);
            data.put("src2", src2);

            GenePca genePca = new GenePca();
            genePca.setId(IdUtils.simpleUUID());
            genePca.setCreateBy(ShiroUtils.getLoginName());
            genePca.setName(name);
            genePca.setResultInfo(record);
            genePca.setResultStatus(1);
            genePca.setTime(time);
            genePca.setSrc1(src1);
            genePca.setSrc2(src2);
            genePcaMapper.insertGenePca(genePca);
        } else {
            String record = cmdData.get("e_msg").toString();
//            String record = "222";
            record = record.length() > 5000 ? record.substring(0, 5000) : record;
            data.put("result", "false");
            data.put("msg",record);
            GenePca genePca = new GenePca();
            genePca.setId(IdUtils.simpleUUID());
            genePca.setCreateBy(ShiroUtils.getLoginName());
            genePca.setName(name);
            genePca.setResultInfo(record);
            genePca.setResultStatus(0);
            genePcaMapper.insertGenePca(genePca);
        }

        return data;
    }
}
