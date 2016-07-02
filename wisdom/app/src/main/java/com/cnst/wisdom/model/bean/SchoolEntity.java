package com.cnst.wisdom.model.bean;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author pengjingnag.
 * @see [相关类/方法]
 * @since [产品/模版版本]
 */
public class SchoolEntity
{
    /**
     * id : 4028880d2f81b335012f81bf1e30000k
     * createTime : {"nanos":0,"time":1452721950000,"minutes":52,"seconds":30,"hours":5,"month":0,"timezoneOffset":-480,"year":116,"day":4,"date":14}
     * address : 深圳市宝安区河西四坊157号
     * regTime : {"nanos":0,"time":1265040000000,"minutes":0,"seconds":0,"hours":0,"month":1,"timezoneOffset":-480,"year":110,"day":2,"date":2}
     * remark : aaa
     * status : 2
     * name : 河西幼儿园1
     * code : SZHESF15744522
     * agent : {"id":"40288bd7525e163d01525e2870d10001","createTime":{"nanos":0,"time":1453278733000,"minutes":32,"seconds":13,"hours":16,"month":0,"timezoneOffset":-480,"year":116,"day":3,"date":20},"businessLicense":"SZ100004","remark":"ddddd123","status":2,"name":"测试01","type":1,"legalPerson":"测试人01","mobile":"13632949343"}
     * legalPerson : 张三
     * loginAccount : 40288ae845df5fe30145df6522c30004
     * loginName : mcms
     */

    private String id;
    private CreateTimeEntity createTime;
    private String address;
    private CreateTimeEntity regTime;
    private String remark;
    private int status;
    private String name;
    private String code;
    private AgentEntity agent;
    private String legalPerson;
    private String loginAccount;
    private String loginName;

    public void setId(String id)
    {
        this.id = id;
    }

    public void setCreateTime(CreateTimeEntity createTime)
    {
        this.createTime = createTime;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setRegTime(CreateTimeEntity regTime)
    {
        this.regTime = regTime;
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

    public void setCode(String code)
    {
        this.code = code;
    }

    public void setAgent(AgentEntity agent)
    {
        this.agent = agent;
    }

    public void setLegalPerson(String legalPerson)
    {
        this.legalPerson = legalPerson;
    }

    public void setLoginAccount(String loginAccount)
    {
        this.loginAccount = loginAccount;
    }

    public void setLoginName(String loginName)
    {
        this.loginName = loginName;
    }

    public String getId()
    {
        return id;
    }

    public CreateTimeEntity getCreateTime()
    {
        return createTime;
    }

    public String getAddress()
    {
        return address;
    }

    public CreateTimeEntity getRegTime()
    {
        return regTime;
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

    public String getCode()
    {
        return code;
    }

    public AgentEntity getAgent()
    {
        return agent;
    }

    public String getLegalPerson()
    {
        return legalPerson;
    }

    public String getLoginAccount()
    {
        return loginAccount;
    }

    public String getLoginName()
    {
        return loginName;
    }
}
