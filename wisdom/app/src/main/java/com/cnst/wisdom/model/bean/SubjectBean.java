package com.cnst.wisdom.model.bean;

import java.util.ArrayList;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author meilianbing.
 * @see [相关类/方法]
 * @since [产品/模版版本]
 */
public class SubjectBean {

    /**
     * code:200
     * msg:查询正常.
     * data:
     */
    private int code;
    private String msg;
    private ArrayList<Subject> data;

    public int getCode(){
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public ArrayList<Subject> getData() {
        return data;
    }
}
