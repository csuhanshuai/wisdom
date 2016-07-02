package com.cnst.wisdom.presenter;

import android.content.Context;

import com.cnst.wisdom.model.net.VolleyManager;
import com.cnst.wisdom.ui.view.BaseView;

/**
 * Created by GuDong on 10/29/15 14:08.
 * Contact with 1252768410@qq.com.
 */
public class BasePresenter<JV extends BaseView> {

    protected JV mView;

    protected Context mContext;

    public VolleyManager mVolleyManager = VolleyManager.getInstance();

    public BasePresenter(Context context, JV view) {
        mContext = context;
        mView = view;
    }
}
