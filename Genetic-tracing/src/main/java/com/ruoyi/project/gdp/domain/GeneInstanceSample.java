package com.ruoyi.project.gdp.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 样本合并对象 gdp_sample
 * 
 * @author ruoyi
 * @date 2023-04-11
 */
public class GeneInstanceSample extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @Excel(name = "id")
    private String id;

    /** 实例id */
    @Excel(name = "实例id")
    private String instanceId;

    /** 样本表编号对应的国家名称 */
    @Excel(name = "样本表编号对应的国家名称")
    private String countries;

    /** 样本表编号 */
    @Excel(name = "样本表编号")
    private String serialNumber;

    /** 样本表编号对应的列数 */
    @Excel(name = "样本表编号对应的列数")
    private Integer columnNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public void setCountries(String countries) 
    {
        this.countries = countries;
    }

    public String getCountries() 
    {
        return countries;
    }
    public void setSerialNumber(String serialNumber) 
    {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber() 
    {
        return serialNumber;
    }
    public void setColumnNumber(Integer columnNumber) 
    {
        this.columnNumber = columnNumber;
    }

    public Integer getColumnNumber() 
    {
        return columnNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("instancesId", getInstanceId())
            .append("countries", getCountries())
            .append("serialNumber", getSerialNumber())
            .append("columnNumber", getColumnNumber())
            .toString();
    }
}
