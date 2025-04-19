package com.ruoyi.project.gdp.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 处理实例对象 gdp_instance
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
public class GeneInstance extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;

    /** 名称 */
    private String name;

    /** 最后运行的流程id */
    @Excel(name = "最后运行的流程id")
    private String finalprocessid;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /** 更新时间 */
    private Date updateDate;

    private String finalProcessName;	// 最后运行的流程名称
    private String finalProcessStatus;	// 最后运行的流程的运行结果

    /** 是否合并：1是0否 */
    private Integer combinestatus;

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
    public void setFinalprocessid(String finalprocessid) 
    {
        this.finalprocessid = finalprocessid;
    }

    public String getFinalprocessid() 
    {
        return finalprocessid;
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
    public void setCombinestatus(Integer combinestatus) 
    {
        this.combinestatus = combinestatus;
    }

    public Integer getCombinestatus() 
    {
        return combinestatus;
    }

    public String getFinalProcessName() {
        return finalProcessName;
    }

    public void setFinalProcessName(String finalProcessName) {
        this.finalProcessName = finalProcessName;
    }

    public String getFinalProcessStatus() {
        return finalProcessStatus;
    }

    public void setFinalProcessStatus(String finalProcessStatus) {
        this.finalProcessStatus = finalProcessStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("finalprocessid", getFinalprocessid())
            .append("createBy", getCreateBy())
            .append("createDate", getCreateDate())
            .append("updateBy", getUpdateBy())
            .append("updateDate", getUpdateDate())
            .append("finalProcessName", getFinalProcessName())
            .append("finalProcessStatus", getFinalProcessStatus())
            .append("combinestatus", getCombinestatus())
            .toString();
    }
}
