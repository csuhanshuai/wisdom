package com.cnst.wisdom.model.bean;

import java.io.Serializable;

/**
 * 班级对象
 * <功能详细描述>
 * @author taoyuan
 * @see [相关类/方法]
 * @since [产品/模版版本]
 */
public class Clazz implements Serializable{
    /**
     * id : hsdjdf778sdfusbf8sdfgh
     * createTime : {"nanos":0,"time":1453184260000,"minutes":17,"seconds":40,"hours":14,"month":0,"timezoneOffset":-480,"year":116,"day":2,"date":19}
     * order : 0
     * remark : 说多的
     * name : 数学
     * schoolId : 4028880d2f81b335012f81bf1e30000k
     * totalTermCount : 3
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
        return "Clazz{" +
                "pk='" + pk + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
