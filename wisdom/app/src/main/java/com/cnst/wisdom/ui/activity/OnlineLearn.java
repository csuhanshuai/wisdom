package com.cnst.wisdom.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.cnst.wisdom.R;
import com.cnst.wisdom.ui.fragment.online.OnlineFragmentFactory;
import com.cnst.wisdom.ui.widget.PagerSlidingTabStrip;

/**
 * <在线进修>
 * <在线进修相关信息展示>
 *
 * @author pengjingang.
 * @see [相关类/方法]
 * @since [产品/模版版本]
 *
 */

public class OnlineLearn extends AppCompatActivity implements View.OnClickListener
{


    private ViewPager mVpOnline;
    private PagerSlidingTabStrip mTabstripOnline;
    private DisplayMetrics mDm;
    private String[] mTitles;
    private ImageButton mHeadBack;
    private ImageButton mHeadMenu;
    private Button mBtnPop;
    private PopupWindow mPopupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_learndetail);
        setContentView(R.layout.activity_onlinelearn);
        mDm = getResources().getDisplayMetrics();
        initView();
        initListener();
        initData();
    }

    private void initListener()
    {
        mHeadBack.setOnClickListener(this);
        mHeadMenu.setOnClickListener(this);
    }

    private void initData()
    {
        mTitles = new String[]{"教学技术","班务管理","家长沟通","专业课程"};
        mVpOnline.setAdapter(new OnlineFragAdaptger(getSupportFragmentManager()));
        mTabstripOnline.setViewPager(mVpOnline);
    }

    private void initView()
    {
        mVpOnline = (ViewPager)findViewById(R.id.vp_online);
        mHeadBack = (ImageButton)findViewById(R.id.head_back);
        mHeadMenu = (ImageButton)findViewById(R.id.head_menu);


        mTabstripOnline = (PagerSlidingTabStrip)findViewById(R.id.tabstrip_online);
        /*
           设置tabs标签的一些属性
        */
        //设置标签自动扩展——当标签们的总宽度不够屏幕宽度时，自动扩展使每个标签宽度递增匹配屏幕宽度，注意！这一条代码必须在setViewPager前方可生效
        mTabstripOnline.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        mTabstripOnline.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
        mTabstripOnline.setUnderlineHeight((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, mDm));
        // 设置Tab Indicator的高度
        mTabstripOnline.setIndicatorHeight((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, mDm));
        // 设置Tab标题文字的大小
        mTabstripOnline.setTextSize((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, mDm));
        // 设置Tab Indicator的颜色
        mTabstripOnline.setIndicatorColor(Color.parseColor("#45c01a"));
        // 设置选中Tab文字的颜色
        mTabstripOnline.setSelectedTextColor(Color.parseColor("#45c01a"));
        // 取消点击Tab时的背景色
        mTabstripOnline.setTabBackground(0);
        //左右间隔尽可能小，一边可以自动扩展，使每个标签均匀平分屏幕宽度
        mTabstripOnline.setTabPaddingLeftRight(1);

    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId()){
            case R.id.head_back:
                finish();
                break;
            case R.id.head_menu:
                //弹出pop
                initPopView();
                break;
            case R.id.btn_pop:
                Intent intent = new Intent(this, Feedback.class);
                startActivity(intent);
                mPopupWindow.dismiss();

                break;



            default:
                break;
        }

    }

    private void initPopView()
    {
        View view = View.inflate(this, R.layout.popup_online, null);
        mBtnPop = (Button)view.findViewById(R.id.btn_pop);
        mBtnPop.setOnClickListener(this);

        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setFocusable(true);

        //点击pop以外，pop消失
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //设置动画效果
        mPopupWindow.setAnimationStyle(android.support.v7.appcompat.R.style.Animation_AppCompat_DropDownUp);
        mPopupWindow.showAsDropDown(mHeadMenu);


    }


    private class OnlineFragAdaptger extends FragmentPagerAdapter
    {


        public OnlineFragAdaptger(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            return OnlineFragmentFactory.createFragment(position);
        }

        @Override
        public int getCount()
        {
            return mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return mTitles[position];
        }
    }
}
