package com.ruoyi.project.gdp.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 处理流程对象 gdp_process
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
public class GeneProcess extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;

    /** 流程名称 */
    @Excel(name = "流程名称")
    private String name;

    /** 流程步骤 */
    @Excel(name = "流程步骤")
    private Integer step;

    /** 流程命令 */
    @Excel(name = "流程命令")
    private String command;

    /** 流程内容 */
    @Excel(name = "流程内容")
    private String content;

    /** 状态：0-无效 1-有效 */
    @Excel(name = "状态：0-无效 1-有效")
    private Integer status;

    /** 忽略运行结果：0-否,1-是 */
    @Excel(name = "忽略运行结果：0-否,1-是")
    private Integer ignorecommand;

    /** 创建时间 */
    private Date createDate;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateDate;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setStep(Integer step) 
    {
        this.step = step;
    }

    public Integer getStep() 
    {
        return step;
    }
    public void setCommand(String command) 
    {
        this.command = command;
    }

    public String getCommand() 
    {
        return command;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setIgnorecommand(Integer ignorecommand) 
    {
        this.ignorecommand = ignorecommand;
    }

    public Integer getIgnorecommand() 
    {
        return ignorecommand;
    }
    public void setCreateDate(Date createDate) 
    {
        this.createDate = createDate;
    }

    public Date getCreateDate() 
    {
        return createDate;
    }
    public void setUpdateDate(Date updateDate) 
    {
        this.updateDate = updateDate;
    }

    public Date getUpdateDate() 
    {
        return updateDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("step", getStep())
            .append("command", getCommand())
            .append("content", getContent())
            .append("status", getStatus())
            .append("ignorecommand", getIgnorecommand())
            .append("createBy", getCreateBy())
            .append("createDate", getCreateDate())
            .append("updateBy", getUpdateBy())
            .append("updateDate", getUpdateDate())
            .toString();
    }
}
