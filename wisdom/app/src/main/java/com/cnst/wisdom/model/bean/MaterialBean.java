package com.cnst.wisdom.model.bean;

import java.util.ArrayList;

/**
 * 资源分类列表对象
 * <功能详细描述>
 *
 * @author tangbinfeng
 * @see [相关类/方法]
 * @since [产品/模板版本]
 */
public class MaterialBean
{
    /**
     * code:200
     * msg:查询正常.
     * data:[{"code":"huiben","id":"34j54y54hl3fb34ny454","name":"绘本"},{"code":"jiaoju","id":"56sd6d7898df78dfs9","name":"教具"}]
     */
    public int code;
    public String msg;
    public ArrayList<MaterialList> data;

    public class MaterialList
    {
        public String code;
        public String id;
        public String name;

        @Override
        public String toString()
        {
            return "MaterialList{"+
                    "code='"+code+'\''+
                    ", id='"+id+'\''+
                    ", name='"+name+'\''+
                    '}';
        }
    }

    @Override
    public String toString()
    {
        return "MaterialBean{"+
                "code="+code+
                ", msg='"+msg+'\''+
                ", data="+data+
                '}';
    }
}
