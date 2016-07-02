package com.cnst.wisdom.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cnst.wisdom.BaseApplication;
import com.cnst.wisdom.R;
import com.cnst.wisdom.ui.base.BaseFragment;

/**
 * 动态界面
 */
public class DynamicFm extends BaseFragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View recomment = View.inflate(BaseApplication.getContext(), R.layout.fm_dynamic, null);
        return recomment;
    }

    @Override
    protected void initPresenter(){

    }
}
