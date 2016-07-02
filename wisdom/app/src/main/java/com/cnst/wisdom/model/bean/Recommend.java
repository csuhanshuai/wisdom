package com.cnst.wisdom.model.bean;

import com.cnst.wisdom.model.bean.bdmeizhi;

/**
 * Created by Jonas on 2015/11/22.
 */
public class Recommend {
    public String title;
    public bdmeizhi meizhi;

    public Recommend(String title, bdmeizhi meizhi){
        this.title = title;
        this.meizhi = meizhi;
    }
}
