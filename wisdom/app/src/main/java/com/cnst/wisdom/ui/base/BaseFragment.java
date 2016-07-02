package com.cnst.wisdom.ui.base;

import android.support.v4.app.Fragment;


/**
 * 基类fragment
 * @author jiangzuyun.
 * @since [产品/模版版本]
 */
public abstract class BaseFragment extends Fragment {

    protected abstract void initPresenter();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        initPresenter();
        super.setUserVisibleHint(isVisibleToUser);
    }
}
