package com.cnst.wisdom.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cnst.wisdom.R;
import com.cnst.wisdom.ui.base.BaseFragment;

/**
 * 消息界面
 */
public class MsgFm extends BaseFragment
{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = View.inflate(getContext(), R.layout.fm_msg, null);
        return view;
    }

    @Override
    protected void initPresenter(){

    }
}
