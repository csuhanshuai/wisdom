package com.cnst.wisdom.model.bean;

import java.io.Serializable;

/**
 * 学期对象
 * <功能详细描述>
 *
 * @author taoyuan
 * @see [相关类/方法]
 * @since [产品/模版版本]
 */
public class Term implements Serializable
{

    /**
     * id : 78sdu88sfb7
     * createTime : {"nanos":0,"time":1453530096000,"minutes":21,"seconds":36,"hours":14,"month":0,"timezoneOffset":-480,"year":116,"day":6,"date":23}
     * school : {"id":"4028880d2f81b335012f81bf1e30000k","createTime":{"nanos":0,"time":1452721950000,"minutes":52,"seconds":30,"hours":5,"month":0,"timezoneOffset":-480,"year":116,"day":4,"date":14},"address":"深圳市宝安区河西四坊157号","regTime":{"nanos":0,"time":1265040000000,"minutes":0,"seconds":0,"hours":0,"month":1,"timezoneOffset":-480,"year":110,"day":2,"date":2},"remark":"aaa","status":2,"name":"河西幼儿园1","code":"SZHESF15744522","agent":{"id":"40288bd7525e163d01525e2870d10001","createTime":{"nanos":0,"time":1453278733000,"minutes":32,"seconds":13,"hours":16,"month":0,"timezoneOffset":-480,"year":116,"day":3,"date":20},"businessLicense":"SZ100004","remark":"ddddd123","status":2,"name":"测试01","type":1,"legalPerson":"测试人01","mobile":"13632949343"},"legalPerson":"张三","loginAccount":"40288ae845df5fe30145df6522c30004","loginName":"mcms"}
     * order : 0
     * remark : 十点多舒
     * subject : {"id":"67sd76fdsbh34u34r8sdhds","createTime":{"nanos":0,"time":1453443554000,"minutes":19,"seconds":14,"hours":14,"month":0,"timezoneOffset":-480,"year":116,"day":5,"date":22},"order":2,"remark":"李连杰客户","name":"英语","schoolId":"4028880d2f81b335012f81bf1e30000k","totalTermCount":2}
     * name : 第一学期
     */

    private String pk;
    private CreateTimeEntity createTime;
    private SchoolEntity school;
    private int order;
    private String remark;
    private Subject subject;
    private String name;

    public void setPk(String pk)
    {
        this.pk = pk;
    }

    public void setCreateTime(CreateTimeEntity createTime)
    {
        this.createTime = createTime;
    }

    public void setSchool(SchoolEntity school)
    {
        this.school = school;
    }

    public void setOrder(int order)
    {
        this.order = order;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public void setSubject(Subject subject)
    {
        this.subject = subject;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPk()
    {
        return pk;
    }

    public CreateTimeEntity getCreateTime()
    {
        return createTime;
    }

    public SchoolEntity getSchool()
    {
        return school;
    }

    public int getOrder()
    {
        return order;
    }

    public String getRemark()
    {
        return remark;
    }

    public Subject getSubject()
    {
        return subject;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString() {
        return "Term{" +
                "pk='" + pk + '\'' +
                ", name='" + name + '\'' +
                ", subject=" + subject +
                '}';
    }
}
