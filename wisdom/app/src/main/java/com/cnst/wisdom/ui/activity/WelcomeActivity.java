package com.cnst.wisdom.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;

import com.cnst.wisdom.BaseApplication;
import com.cnst.wisdom.R;
import com.cnst.wisdom.utills.DeviceUtils;
import com.cnst.wisdom.utills.DisplayUtils;

/**
 * @author ?,yutianhua
 * <欢迎界面>
 * 启动APP时即启动本Activity，然后转到登陆界面
 */

public class WelcomeActivity extends Activity implements AnimationListener
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        BaseApplication.setScreenwidth(DisplayUtils.getWidthPx(this));
        BaseApplication.setScreenHeight(DisplayUtils.getHeightPx(this));

        boolean networkConnected = DeviceUtils.isNetworkConnected(this);
        init();
    }

    private void init()
    {
        View rootView = findViewById(R.id.rl_welcome_root);

        RotateAnimation rotateAnima = new RotateAnimation(0, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnima.setDuration(1000);
        rotateAnima.setFillAfter(true); // 设置动画执行完毕时, 停留在完毕的状态下.


        // 把三个动画合在一起, 组成一个集合动画
        AnimationSet setAnima = new AnimationSet(false);
        setAnima.addAnimation(rotateAnima);
        setAnima.setAnimationListener(this);

        rootView.startAnimation(setAnima);
    }

    @Override
    public void onAnimationEnd(Animation animation)
    {
        // 去文件中取是否打开过程序的值
        Intent intent = new Intent();
        //		if(isOpenMainPager) {
        //			// 已经打开过一次主界面, 直接进入主界面. （进入登陆界面）
        intent.setClass(WelcomeActivity.this, LoginActivity.class);
        //		}else {
        //			// 没有打开过主界面, 进入引导页面.
        //			intent.setClass(WelcomeActivity.this, .class);
        //		}
        startActivity(intent);

        // 关闭掉欢迎界面
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationStart(Animation animation)
    {
        // TODO Auto-generated method stub

    }

}
