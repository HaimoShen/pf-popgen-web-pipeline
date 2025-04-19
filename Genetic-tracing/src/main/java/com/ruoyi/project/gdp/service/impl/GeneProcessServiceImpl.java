package com.ruoyi.project.gdp.service.impl;


import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.command.CommandUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.common.utils.text.Convert;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.project.gdp.domain.GeneInstance;
import com.ruoyi.project.gdp.domain.GeneProcess;
import com.ruoyi.project.gdp.domain.GeneTask;
import com.ruoyi.project.gdp.mapper.GeneProcessMapper;
import com.ruoyi.project.gdp.service.IGeneInstanceService;
import com.ruoyi.project.gdp.service.IGeneProcessService;
import com.ruoyi.project.gdp.service.IGeneTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;


/**
 * 处理流程Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
@Service
public class GeneProcessServiceImpl implements IGeneProcessService
{
    private static final Logger logger = LoggerFactory.getLogger("gene-process");
    @Autowired
    private GeneProcessMapper geneProcessMapper;

    @Autowired
    private IGeneInstanceService geneInstanceService;

    @Autowired
    private IGeneTaskService geneTaskService;

    @Value("${ruoyi.basedir}")
    private String dataPath;

    /**
     * 查询处理流程
     * 
     * @param id 处理流程主键
     * @return 处理流程
     */
    @Override
    public GeneProcess selectGeneProcessById(String id)
    {
        return geneProcessMapper.selectGeneProcessById(id);
    }

    /**
     * 查询处理流程列表
     * 
     * @param geneProcess 处理流程
     * @return 处理流程
     */
    @Override
    public List<GeneProcess> selectGeneProcessList(GeneProcess geneProcess)
    {
        return geneProcessMapper.selectGeneProcessList(geneProcess);
    }

    /**
     * 新增处理流程
     * 
     * @param geneProcess 处理流程
     * @return 结果
     */
    @Override
    @Transactional(readOnly = false)
    public int insertGeneProcess(GeneProcess geneProcess)
    {
        return geneProcessMapper.insertGeneProcess(geneProcess);
    }

    /**
     * 修改处理流程
     * 
     * @param geneProcess 处理流程
     * @return 结果
     */
    @Override
    @Transactional(readOnly = false)
    public int updateGeneProcess(GeneProcess geneProcess)
    {
        return geneProcessMapper.updateGeneProcess(geneProcess);
    }

    @Override
    @Transactional(readOnly = false)
    public int batchUpdate(String ids,Integer status)
    {
        return geneProcessMapper.batchUpdate(Convert.toStrArray(ids),status);
    }

    /**
     * 批量删除处理流程
     * 
     * @param ids 需要删除的处理流程主键
     * @return 结果
     */
    @Override
    @Transactional(readOnly = false)
    public int deleteGeneProcessByIds(String ids)
    {
        return geneProcessMapper.deleteGeneProcessByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除处理流程信息
     * 
     * @param id 处理流程主键
     * @return 结果
     */
    @Override
    @Transactional(readOnly = false)
    public int deleteGeneProcessById(String id)
    {
        return geneProcessMapper.deleteGeneProcessById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public List<GeneProcess> findStatusListAndCreateInstance(GeneProcess geneProcess, String dirName, String instanceId,String combineStatus) {
        if(StringUtils.isNotBlank(instanceId)) {
            /*插入实例*/
            GeneInstance geneInstance = new GeneInstance();
            geneInstance.setId(instanceId);
            geneInstance.setName(dirName);
//            geneInstance.setFinalProcessName("处理中...");
//            geneInstance.setCombinestatus(Integer.valueOf(combineStatus));
            geneInstance.setCreateBy(ShiroUtils.getLoginName());
            geneInstanceService.insertGeneInstance(geneInstance);
        }
        //生效的流程状态
        geneProcess.setStatus(1);
        List<GeneProcess> geneProcessList = geneProcessMapper.selectGeneProcessList(geneProcess);
//        List<GeneProcess> geneProcessList = new ArrayList<>();
//        if (combineStatus.equals("1")){
//            geneProcessList = dao.findList2(geneProcess);
////			geneProcessList = CommandUtils.processComList(dao.findList2(geneProcess),dataPath, dirName);
//        }else{
//            geneProcessList = dao.findList3(geneProcess);
//        }
        //必需按步骤排序
        geneProcessList.sort(Comparator.comparing(GeneProcess::getStep));
        geneProcessList.forEach(p -> {
            p.setStatus(1);
            p.setCommand("");
            p.setContent("");
        });
        return geneProcessList;
    }

    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> processGene(GeneProcess gp,String dirName, String instanceId){
        Map<String, Object> data = new HashMap<>();

        if(StringUtils.isNotBlank(gp.getCommand())){
            Instant start = Instant.now();
            data = CommandUtils.exeCmd(CommandUtils.processComStr(gp.getCommand(), dataPath, dirName), String.valueOf(gp.getStep()));
            boolean flag = Boolean.parseBoolean(data.get("result_"+gp.getStep()).toString());
//            boolean flag = true;
            data.put("流程"+gp.getStep(),gp.getName());
            data.put("状态",flag?"成功":"失败");
            String time = DateUtils.formatDateTime(ChronoUnit.MILLIS.between(start, Instant.now()));
            data.put("耗时",time);

            /*插入任务*/
            GeneTask geneTask = new GeneTask();
            geneTask.setId(IdUtils.simpleUUID());
            geneTask.setInstanceId(instanceId);
            geneTask.setProcessId(gp.getId());
            geneTask.setStatus(flag ? 1:0);
            geneTask.setTime(time);
            logger.debug(geneTask.getTime());
            String record = flag ? data.get("s_msg_" + gp.getStep()).toString() : data.get("e_msg").toString();
//            String record = "111";
            record = record.length() > 5000 ? record.substring(0,5000) : record;
            geneTask.setRecord(record);
            geneTaskService.insertGeneTask(geneTask);
        }
        return data;
    }
}
