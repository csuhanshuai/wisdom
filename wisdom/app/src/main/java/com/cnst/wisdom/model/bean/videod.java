package com.cnst.wisdom.model.bean;

import java.util.List;

/**
 * @author jiangzuyun.
 * @date 2016/1/29
 * @des [一句话描述]
 * @since [产品/模版版本]
 */
public class videod {

    /**
     * code : 200
     * data : [{"code":"huiben","id":"34j54y54hl3fb34ny454","name":"绘本"},{"code":"jiaoju","id":"56sd6d7898df78dfs9","name":"教具"},{"code":"jzgt","id":"677878gGHjhghf67gyut","name":"家长沟通"},{"code":"bwgl","id":"677887hhjHHJJJKjkHj","name":"班务管理"},{"code":"ppt","id":"67ds8sd6fd6fd7fsd8sd","name":"ppt"},{"code":"ysmhml","id":"6ds7sd89gdsf7sdfuw5834h5","name":"园所美化美劳"},{"code":"jxjs","id":"7878sdfjgjksdfjksdfkj","name":"教学技术"},{"code":"zykc","id":"7889gfr567vhu788yui87","name":"专业课程"},{"code":"jiemu","id":"bvgh57sd7f6s7d7sd9","name":"节目"},{"code":"shipin","id":"dfsdsf233243fd55dffd","name":"视频"},{"code":"kejian","id":"e346as6d7ds7hjf8fgkd9","name":"课件"},{"code":"youxi","id":"pd6hsdf68sdnsd7f","name":"游戏"},{"code":"yinyue","id":"sdsdasdfsdaf23wdsfewrsdf","name":"音乐"},{"code":"jiaoan","id":"sdsdsfddfdfd32454kj","name":"教案"}]
     * msg : 查询正常.
     */

    private String code;
    private String msg;
    /**
     * code : huiben
     * id : 34j54y54hl3fb34ny454
     * name : 绘本
     */

    private List<DataEntity> data;

    public void setCode(String code){
        this.code = code;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public void setData(List<DataEntity> data){
        this.data = data;
    }

    public String getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }

    public List<DataEntity> getData(){
        return data;
    }

    public static class DataEntity {
        private String code;
        private String id;
        private String name;

        public void setCode(String code){
            this.code = code;
        }

        public void setId(String id){
            this.id = id;
        }

        public void setName(String name){
            this.name = name;
        }

        public String getCode(){
            return code;
        }

        public String getId(){
            return id;
        }

        public String getName(){
            return name;
        }
    }
}
