package com.cnst.wisdom.model.bean;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author pengjingnag.
 * @see [相关类/方法]
 * @since [产品/模版版本]
 */
public class AgentEntity
{
    /**
     * id : 40288bd7525e163d01525e2870d10001
     * createTime : {"nanos":0,"time":1453278733000,"minutes":32,"seconds":13,"hours":16,"month":0,"timezoneOffset":-480,"year":116,"day":3,"date":20}
     * businessLicense : SZ100004
     * remark : ddddd123
     * status : 2
     * name : 测试01
     * type : 1
     * legalPerson : 测试人01
     * mobile : 13632949343
     */

    private String id;
    private CreateTimeEntity createTime;
    private String businessLicense;
    private String remark;
    private int status;
    private String name;
    private int type;
    private String legalPerson;
    private String mobile;

    public void setId(String id)
    {
        this.id = id;
    }

    public void setCreateTime(CreateTimeEntity createTime)
    {
        this.createTime = createTime;
    }

    public void setBusinessLicense(String businessLicense)
    {
        this.businessLicense = businessLicense;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public void setLegalPerson(String legalPerson)
    {
        this.legalPerson = legalPerson;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getId()
    {
        return id;
    }

    public CreateTimeEntity getCreateTime()
    {
        return createTime;
    }

    public String getBusinessLicense()
    {
        return businessLicense;
    }

    public String getRemark()
    {
        return remark;
    }

    public int getStatus()
    {
        return status;
    }

    public String getName()
    {
        return name;
    }

    public int getType()
    {
        return type;
    }

    public String getLegalPerson()
    {
        return legalPerson;
    }

    public String getMobile()
    {
        return mobile;
    }
}
