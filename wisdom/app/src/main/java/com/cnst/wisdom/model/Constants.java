package com.cnst.wisdom.model;

/**
 * Created by Jonas on 2015/11/19.
 * 常量
 */
public class Constants
{

    public static final String baidumeinv = "http://image.baidu.com/channel/listjson?rn=6&tag1=美女&tag2=可爱&ie=utf8&pn=1";
    public static final String baidumeinv2 = "http://image.baidu.com/channel/listjson?rn=6&tag1=美女&tag2=可爱&ie=utf8&pn=";

    public static String screenh = "screenhwith";
    public static String screenw = "screenwidth";

    //服务器地址
    public static final String SERVER = "http://192.168.11.125:8080/school/";

    //APP教师端登录接口
    public static final String LOGIN = "tchLogin_app.kq";


    //状态码-操作成功
    public static final int STATUS_SUCCESS = 200;
    //状态码-操作失败
    public static final int STATUS_FAIL = -1;
    //状态码-服务器内部异常
    public static final int STATUS_SERVER_EXCEPTION = 500;
    //状态码-数据查询不存在
    public static final int STATUS_DATA_NOTFOUND = 404;
    //状态码-缺少参数，或参数不正确
    public static final int STATUS_ARGUMENT_ERROR = 417;
    //状态码-连接超时
    public static final int STATUS_TIMEOUT = 504;
    //状态码-非法请求
    public static final int STATUS_ILLEGAL = 503;
    //从sharepreference获取服务器地址
    public static final String GET_SERVER = "server";
    //从sharepreference获取sessionId的KEY
    public static final String GET_SESSIONID = "sessionId";
    //从sharepreference获取用户姓名name的KEY
    public static final String GET_NAME = "name";
    //从sharepreference获取school的KEY
    public static final String GET_SCHOOL = "school";
    //从sharepreference获取职位名称stationName的KEY
    public static final String GET_STATIONNAME = "stationName";

    /**
     * 获取素材分类列表信息
     * 获取列表对应资源素材信息
     */

    //获取素材分类列表
    public static final String MATERIAL_DICTLIST = "app/queryDictList_app.kq";
    //获取列表对应资源素材信息
    public static final String QUERY_MATERIAL = "app/queryResourceMaterial_app.kq";

    /**
     * 获取学科信息 subject
     * 获取学期信息 term
     * 获取课名信息 classname
     */
    public static String getTeachCategorys = "/teachPlan/getTeachCategorys_app.kq";
    //教学进度和修改计划时展示的教学计划列表
    public static String getTeachPlanList = "/teachPlan/getTeachPlanList_app.kq";
    //保存设定的教学计划
    public static String saveTeachPlan = "/teachPlan/saveTeachPlan_app.kq";
    //修改教学计划
    public static String modifyTeachPlan = "modifyTeachPlan_app.kg";
    //教学进度确认或还原
    public static String changePlanState = "changePlanState_app.kg";
}
