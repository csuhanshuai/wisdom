package com.cnst.wisdom.ui.activity;

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
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.cnst.wisdom.R;
import com.cnst.wisdom.ui.base.BaseNetFragment;
import com.cnst.wisdom.ui.fragment.downloadManager.DownloadManagerFragmentFactory;
import com.cnst.wisdom.ui.widget.PagerSlidingTabStrip;

/**
 * 下载管理页面
 *
 *
 * @author Huangyingde
 * @time 15:50
 * @see
 */
public class DownloadManager extends AppCompatActivity implements View.OnClickListener
{
    private PagerSlidingTabStrip mDownloadManagerTabs;
    private ViewPager            mViewPager;
    private ImageButton          mBack;
    private ImageView            mMore;
    private String[]             mDownloadManagerArrs;      // 模拟数据
    private DisplayMetrics       mDm;

    private MyOnPagerChangeListener mMyOnPagerChangeListener = new MyOnPagerChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_manager);
        init();
        initData();
        initListener();
    }

    private void init()
    {
        mDm = getResources().getDisplayMetrics();
        mDownloadManagerTabs = (PagerSlidingTabStrip)findViewById(R.id.dm_tabs);
        mViewPager = (ViewPager)findViewById(R.id.dm_viewpager);

        mBack = (ImageButton)findViewById(R.id.head_back_dm);
        mMore = (ImageView)findViewById(R.id.head_more_dm);

        // 设置tabs
        setTabsProperty();
    }


    private void initData()
    {
        // 模拟数据
        mDownloadManagerArrs = new String[]{"全部","音乐","教案","视频","节目","ppt","绘本"};

        // 初始化适配器
        DownLoadManagerFragmentPagerAdapter adapter = new DownLoadManagerFragmentPagerAdapter(getSupportFragmentManager());
        // 给viewPager设置适配器
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(adapter.getCount());
        // 将Tabs和viewPager绑定
        mDownloadManagerTabs.setViewPager(mViewPager);

    }

    private void initListener()
    {
        mBack.setOnClickListener(this);
        mMore.setOnClickListener(this);
        mDownloadManagerTabs.setOnPageChangeListener(mMyOnPagerChangeListener);

        mViewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                // 刚进入Material界面时，手动选中第一个页面
                mMyOnPagerChangeListener.onPageSelected(0);
                // mViewPager.getViewTreeObserver().removeOnGlobalFocusChangeListener(this);
            }
        });
    }

    /**
     设置tabs标签的一些属性
     */
    private void setTabsProperty()
    {
        mDownloadManagerTabs.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        mDownloadManagerTabs.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
        mDownloadManagerTabs.setUnderlineHeight((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, mDm));
        // 设置Tab Indicator的高度
        mDownloadManagerTabs.setIndicatorHeight((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, mDm));
        // 设置Tab标题文字的大小
        mDownloadManagerTabs.setTextSize((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, mDm));
        // 设置Tab Indicator的颜色
        mDownloadManagerTabs.setIndicatorColor(Color.parseColor("#45c01a"));
        // 设置选中Tab文字的颜色
        mDownloadManagerTabs.setSelectedTextColor(Color.parseColor("#45c01a"));
        // 取消点击Tab时的背景色
        mDownloadManagerTabs.setTabBackground(0);
    }

    @Override
    public void onClick(View v)
    {
        if(v == mBack)
        {
            finish();
        }
        else if(v == mMore)
        {
            // 彈出popupWindow
            initPopupWindow();
        }
    }

    private void initPopupWindow()
    {
        View popView = View.inflate(this,R.layout.popup_downloadmanager,null);
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;

        PopupWindow popupWindow = new PopupWindow(popView,width,height);
        // 设置popupWindow
        popupWindow.setFocusable(true);          // 设置可以获得焦点
        popupWindow.setOutsideTouchable(true);   // 设置点击外面popWindow可以消失
        // 需要给popWindow指定一个背景色,不然点击外面的时候不可以消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 给popupWindow设置动画效果
        popupWindow.setAnimationStyle(android.support.v7.appcompat.R.style.Animation_AppCompat_Dialog);

        // 让popupWindow显示在mMore的下面
        popupWindow.showAsDropDown(mMore);

        // 搜索的点击事件
        popView.findViewById(R.id.pop_download_manager).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // TODO
            }
        });
    }

    /**
     *  viewPage用于显示数据的自定义适配器类
     */
    class DownLoadManagerFragmentPagerAdapter extends FragmentPagerAdapter
    {

        private final DownloadManagerFragmentFactory mFactory;

        public DownLoadManagerFragmentPagerAdapter(FragmentManager fm)
        {
            super(fm);
            mFactory = new DownloadManagerFragmentFactory();
        }

        /**
         * 返回每一个fragment对应在tabs上的位置
         * @param position
         */
        @Override
        public Fragment getItem(int position)
        {
            BaseNetFragment fragment = mFactory.createFragment(position);
            return fragment;
        }

        /**
         * 返回全部tabs的数量
         */
        @Override
        public int getCount()
        {
            if(mDownloadManagerArrs.length != 0)
            {
                return mDownloadManagerArrs.length;
            }
            return 0;
        }

        /**
         * 必须覆写PagerAdapter中定义的getPageTitle方法
         * @param position
         *         title对应的位置
         * @return 对应page的title
         */
        @Override
        public CharSequence getPageTitle(int position)
        {
            return mDownloadManagerArrs[position];
        }
    }

    class MyOnPagerChangeListener implements ViewPager.OnPageChangeListener
    {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {

        }

        @Override
        public void onPageSelected(int position)
        {
        /*BaseNetFragment fragment = MaterialFragmentFactory.createFragment(position);
            if(fragment != null)
            {
                *//**
         * 开始加载数据
         *//*
                fragment.showLoadingView(true);
            }*/
        }

        @Override
        public void onPageScrollStateChanged(int state)
        {

        }
    }
}
