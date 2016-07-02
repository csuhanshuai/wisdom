package com.cnst.wisdom.ui.fragment;

import android.view.View;

import com.cnst.wisdom.BaseApplication;
import com.cnst.wisdom.R;
import com.cnst.wisdom.ui.base.BaseNetFragment;

import java.util.Map;

/**
 * 教学界面
 */
public class TeachFm extends BaseNetFragment
{

    @Override
    public boolean setSuccViewShowFirse()
    {
        mLoadingView.setVisibility(View.GONE);
        return true;
    }

    @Override
    public String setRequestURL()
    {
        return null;
    }

    @Override
    public void onNetSucceed(String response)
    {
    }


    @Override
    public String setHeadTitle()
    {
        return BaseApplication.findString(R.string.btn_teach);
    }

    @Override
    public View createSucceedView()
    {
        View view = View.inflate(getContext(), R.layout.fm_teach, null);
        return view;
    }

    @Override
    public Map setparams()
    {
        return null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        initPresenter();
    }
}
