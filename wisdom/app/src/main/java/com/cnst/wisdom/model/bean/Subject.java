package com.cnst.wisdom.model.bean;

import java.io.Serializable;

/**
 * 科目对象
 * <功能详细描述>
 * @author taoyuan.
 * @since 1.0
 */
public class Subject implements Serializable
{
    /**
     * pk : 67sd76fdsbh34u34r8sdhds
     * createTime : {"nanos":0,"time":1453443554000,"minutes":19,"seconds":14,"hours":14,"month":0,"timezoneOffset":-480,"year":116,"day":5,"date":22}
     * order : 2
     * remark : 李连杰客户
     * name : 英语
     * schoolId : 4028880d2f81b335012f81bf1e30000k
     * totalTermCount : 2
     */

    private String pk;
    private CreateTimeEntity createTime;
    private int order;
    private String remark;
    private String name;
    private String schoolId;
    private int totalTermCount;

    public void setPk(String pk)
    {
        this.pk = pk;
    }

    public void setCreateTime(CreateTimeEntity createTime)
    {
        this.createTime = createTime;
    }

    public void setOrder(int order)
    {
        this.order = order;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setSchoolId(String schoolId)
    {
        this.schoolId = schoolId;
    }

    public void setTotalTermCount(int totalTermCount)
    {
        this.totalTermCount = totalTermCount;
    }

    public String getPk()
    {
        return pk;
    }

    public CreateTimeEntity getCreateTime()
    {
        return createTime;
    }

    public int getOrder()
    {
        return order;
    }

    public String getRemark()
    {
        return remark;
    }

    public String getName()
    {
        return name;
    }

    public String getSchoolId()
    {
        return schoolId;
    }

    public int getTotalTermCount()
    {
        return totalTermCount;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", pk='" + pk + '\'' +
                '}';
    }
}
