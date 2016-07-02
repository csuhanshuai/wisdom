package com.cnst.wisdom.ui.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


/**
 * 基类activity
 * @author jiangzuyun.
 * @since [产品/模版版本]
 */
public abstract class BaseActivity extends FragmentActivity
{

    protected abstract void initPresenter();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        initPresenter();
        super.onCreate(savedInstanceState);
    }
}
