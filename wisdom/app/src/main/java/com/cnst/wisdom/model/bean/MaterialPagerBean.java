package com.cnst.wisdom.model.bean;

import java.util.ArrayList;

/**
 * 资源素材页面对象
 * <功能详细描述>
 *
 * @author tangbinfeng
 * @see [相关类/方法]
 * @since [产品/模板版本]
 *
 */
public class MaterialPagerBean
{
    /**
     * 字段释义：
     id   素材id
     isVideo  是不是视频  true 是   false 不是
     thumbnailPath  文件缩略图地址
     fileSize  文件大小  单位KB
     title  素材标题
     videoId  视频对应的优酷id
     pageInfo  分页信息
     pageCount  总页数
     rowCount  总记录数
     */
    public int code;
    public ArrayList<MaterialPager> data;
    public class MaterialPager{
        public String id;
        public String isVideo;
        public String thumbnailPath;
        public String fileSize;
        public String title;
        public String videoId;

        @Override
        public String toString()
        {
            return "MaterialPager{"+
                    "id='"+id+'\''+
                    ", isVideo='"+isVideo+'\''+
                    ", thumbnailPath='"+thumbnailPath+'\''+
                    ", fileSize='"+fileSize+'\''+
                    ", title='"+title+'\''+
                    ", videoId='"+videoId+'\''+
                    '}';
        }
    }
    public PageInfo pageInfo;
    public class PageInfo{
        public String pageCount;
        public String rowCount;
    }

    @Override
    public String toString()
    {
        return "MaterialPagerBean{"+
                "code="+code+
                ", data="+data+
                ", pageInfo="+pageInfo+
                '}';
    }
}
