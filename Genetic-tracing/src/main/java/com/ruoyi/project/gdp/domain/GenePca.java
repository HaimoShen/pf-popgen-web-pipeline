package com.ruoyi.project.gdp.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 数据降维对象 gdp_pca
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
public class GenePca extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 创建时间 */
    private Date createDate;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateDate;

    /** 结果：状态 */
    @Excel(name = "结果：状态")
    private Integer resultStatus;

    /** 结果：信息 */
    @Excel(name = "结果：信息")
    private String resultInfo;

    /** 耗时 */
    @Excel(name = "耗时")
    private String time;

    /** 源Excel路径 */
    @Excel(name = "源Excel路径")
    private String path;

    /** Excel数据选取范围 */
    @Excel(name = "Excel数据选取范围")
    private String excelRange;

    /**  */
    @Excel(name = "")
    private String src1;

    /**  */
    @Excel(name = "")
    private String src2;

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
    public void setResultStatus(Integer resultStatus) 
    {
        this.resultStatus = resultStatus;
    }

    public Integer getResultStatus() 
    {
        return resultStatus;
    }
    public void setResultInfo(String resultInfo) 
    {
        this.resultInfo = resultInfo;
    }

    public String getResultInfo() 
    {
        return resultInfo;
    }
    public void setTime(String time) 
    {
        this.time = time;
    }

    public String getTime() 
    {
        return time;
    }
    public void setPath(String path) 
    {
        this.path = path;
    }

    public String getPath() 
    {
        return path;
    }
    public void setExcelRange(String excelRange) 
    {
        this.excelRange = excelRange;
    }

    public String getExcelRange() 
    {
        return excelRange;
    }
    public void setSrc1(String src1) 
    {
        this.src1 = src1;
    }

    public String getSrc1() 
    {
        return src1;
    }
    public void setSrc2(String src2) 
    {
        this.src2 = src2;
    }

    public String getSrc2() 
    {
        return src2;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("createBy", getCreateBy())
            .append("createDate", getCreateDate())
            .append("updateBy", getUpdateBy())
            .append("updateDate", getUpdateDate())
            .append("resultStatus", getResultStatus())
            .append("resultInfo", getResultInfo())
            .append("time", getTime())
            .append("path", getPath())
            .append("excelRange", getExcelRange())
            .append("src1", getSrc1())
            .append("src2", getSrc2())
            .toString();
    }
}
