package com.cnst.wisdom.ui.view;

import android.view.View;

import java.util.Map;

/**
 * Created on 2016/1/17.  by Jonas{https://github.com/mychoices}
 */

public interface MainStateView extends StateView {

    /**
     * 请求 方式
     * @return
     */
    int setRequestMethod();
    /**
     * 1,网络请求参数
     */
    Map setparams();

    /**
     * 1,设置 请求 url地址 对象
     */
    String setRequestURL();


    /**
     * 2，拿到数据之前 加载界面 setSuccViewShowFirse 返回true 则优先于 onNetSucceed触发
     */
    View createSucceedView();

    /**
     * 3,拿到数据之后 解析数据 填充界面
     */
    void onNetSucceed(String response);
}
