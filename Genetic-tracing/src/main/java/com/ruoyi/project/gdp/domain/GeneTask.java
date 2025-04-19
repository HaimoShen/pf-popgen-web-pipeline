package com.ruoyi.project.gdp.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 处理任务对象 gdp_task
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
public class GeneTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务id */
    private String id;

    /** 任务对应的流程id */
    @Excel(name = "任务对应的流程id")
    private String processId;
    private String processName;
    private String processStep;
    private String processIgnoreCommand;

    /** 任务对应的实例id */
    @Excel(name = "任务对应的实例id")
    private String instanceId;
    private String instanceName;

    /** 该任务运行的状态：0-失败，1-成功 */
    @Excel(name = "该任务运行的状态：0-失败，1-成功")
    private Integer status;

    /** 该任务运行花费时间 */
    @Excel(name = "该任务运行花费时间")
    private String time;

    /** 信息提示 */
    @Excel(name = "信息提示")
    private String record;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setProcessId(String processId) 
    {
        this.processId = processId;
    }

    public String getProcessId() 
    {
        return processId;
    }
    public void setInstanceId(String instanceId) 
    {
        this.instanceId = instanceId;
    }

    public String getInstanceId() 
    {
        return instanceId;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setTime(String time) 
    {
        this.time = time;
    }

    public String getTime() 
    {
        return time;
    }
    public void setRecord(String record) 
    {
        this.record = record;
    }

    public String getRecord() 
    {
        return record;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("processId", getProcessId())
            .append("instanceId", getInstanceId())
            .append("status", getStatus())
            .append("time", getTime())
            .append("record", getRecord())
            .toString();
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessStep() {
        return processStep;
    }

    public void setProcessStep(String processStep) {
        this.processStep = processStep;
    }

    public String getProcessIgnoreCommand() {
        return processIgnoreCommand;
    }

    public void setProcessIgnoreCommand(String processIgnoreCommand) {
        this.processIgnoreCommand = processIgnoreCommand;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }
}
