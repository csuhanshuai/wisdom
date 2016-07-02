package com.cnst.wisdom.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 接口获取的类
 * <功能详细描述>
 *
 * @author taoyuan
 * @see [相关类/方法]
 * @since [产品/模版版本]
 */
public class TeachBaseBean<T>  implements Serializable{

    /*
     * data : [{"id":"hsdjdf778sdfusbf8sdfgh","createTime":{"nanos":0,"time":1453184260000,"minutes":17,"seconds":40,"hours":14,"month":0,"timezoneOffset":-480,"year":116,"day":2,"date":19},"order":0,"remark":"说多的","name":"数学","schoolId":"4028880d2f81b335012f81bf1e30000k","totalTermCount":3},{"id":"6sd67fdgr3ybfs7t34uhg3","createTime":{"nanos":0,"time":1453443511000,"minutes":18,"seconds":31,"hours":14,"month":0,"timezoneOffset":-480,"year":116,"day":5,"date":22},"order":1,"remark":"打得过","name":"语文","schoolId":"4028880d2f81b335012f81bf1e30000k","totalTermCount":2},{"id":"67sd76fdsbh34u34r8sdhds","createTime":{"nanos":0,"time":1453443554000,"minutes":19,"seconds":14,"hours":14,"month":0,"timezoneOffset":-480,"year":116,"day":5,"date":22},"order":2,"remark":"李连杰客户","name":"英语","schoolId":"4028880d2f81b335012f81bf1e30000k","totalTermCount":2}]
     * code : 200
     * msg : 查询成功
     */

    private String code;
    private String msg;
    private List<T> data;

    public void setCode(String code)
    {
        this.code = code;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public void setData(List<T> data)
    {
        this.data = data;
    }

    public String getCode()
    {
        return code;
    }

    public String getMsg()
    {
        return msg;
    }

    public List<T> getData()
    {
        return data;
    }

    public static class DataEntity
    {

    }
}
