package com.cnst.wisdom.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.cnst.wisdom.R;
import com.cnst.wisdom.model.Constants;
import com.cnst.wisdom.model.bean.MaterialBean;
import com.cnst.wisdom.model.net.NetResult;
import com.cnst.wisdom.model.net.VolleyManager;
import com.cnst.wisdom.ui.materialpager.MaterialPager;
import com.cnst.wisdom.ui.widget.PagerSlidingTabStrip;
import com.cnst.wisdom.utills.SPUtills;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * 资源素材的activity
 * 为了体现程序的封装性,所以考虑在Material里面同样使用Fragment来完成网络请求和界面数据的展示
 *
 * @author HuangYingde
 * @time 16:48
 * @see
 */
public class Material extends AppCompatActivity implements View.OnClickListener
{
    private PagerSlidingTabStrip mMaterialTabs;
    private ViewPager mViewPager;
    private ImageButton mBack;
    private ImageView mMore;
    private PopupWindow searchBox;//搜索框
    private VolleyManager volleyManager = VolleyManager.getInstance();
    private String[] mMaterialTabsTitleArr;      // Tab数据
    private DisplayMetrics mDm;
    private View searchView;
    private ArrayList<MaterialBean.MaterialList> materialList; //从网络上获取的资源列表数据集合
    private MyOnPagerChangeListener mMyOnPagerChangeListener = new MyOnPagerChangeListener();
    private ArrayList<MaterialPager> mMaterialPagersList;//资源页面数据集合
    private FrameLayout mFl_more;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);
        mDm = getResources().getDisplayMetrics();
        init();
        initDataFormService();
        //        initData();


    }

    private void init()
    {
        mMaterialTabs = (PagerSlidingTabStrip)findViewById(R.id.material_tabs);
        mViewPager = (ViewPager)findViewById(R.id.material_viewpager);

        mBack = (ImageButton)findViewById(R.id.head_back_action);
        mMore = (ImageView)findViewById(R.id.head_more_action);
        mFl_more = (FrameLayout)findViewById(R.id.fl_head_more_action);

        //初始化搜索框
        searchView = View.inflate(this,R.layout.popup_guidance,null);
        ImageButton ibtn_search2 = (ImageButton) searchView.findViewById(R.id.ibtn_search);
        ibtn_search2.setOnClickListener(this);

        // 设置tabs
        setTabsProperty();
    }

    /**
     * 设置tabs标签的一些属性
     */
    private void setTabsProperty()
    {
        mMaterialTabs.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        mMaterialTabs.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
        mMaterialTabs.setUnderlineHeight((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, mDm));
        // 设置Tab Indicator的高度
        mMaterialTabs.setIndicatorHeight((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, mDm));
        // 设置Tab标题文字的大小
        mMaterialTabs.setTextSize((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, mDm));
        // 设置Tab Indicator的颜色
        mMaterialTabs.setIndicatorColor(Color.parseColor("#45c01a"));
        // 设置选中Tab文字的颜色
        mMaterialTabs.setSelectedTextColor(Color.parseColor("#45c01a"));
        // 取消点击Tab时的背景色
        mMaterialTabs.setTabBackground(0);
    }

    /**
     * 调用网络接口获取数据
     */
    private void initDataFormService()
    {

        //网络请求参数
        HashMap map = new HashMap();
        map.put("fcode", "zysc");
        volleyManager.getString(SPUtills.get(getApplicationContext(), Constants.GET_SERVER, "").toString()+Constants.MATERIAL_DICTLIST, map, "Material", new NetResult<String>()
        {


            @Override
            public void onFailure(VolleyError error)
            {

            }

            @Override
            public void onSucceed(String response)
            {
                Gson gson = new Gson();
                MaterialBean materialBean = null;
                materialBean = gson.fromJson(response, MaterialBean.class);
                if(materialBean != null)
                {
                    Log.e("Material", materialBean.toString());
                    int code = materialBean.code;
                    switch(code)
                    {
                        case Constants.STATUS_ARGUMENT_ERROR:
                            break;
                        case Constants.STATUS_DATA_NOTFOUND:
                            break;
                        case Constants.STATUS_ILLEGAL:
                            break;
                        case Constants.STATUS_SERVER_EXCEPTION:
                            break;
                        case Constants.STATUS_SUCCESS:

                            materialList = materialBean.data;
                            if(materialList != null)
                            {
                                initServiceData();
                            }
                            break;
                        case Constants.STATUS_TIMEOUT:
                            break;
                    }
                }


            }
        });


    }

    /**
     * 解析网络数据
     */
    private void initServiceData()
    {
        //初始化页面数据集合
        mMaterialPagersList = new ArrayList<>();
        //      初始化tabs标签
        mMaterialTabsTitleArr = new String[materialList.size()];
        for(int i = 0; i<materialList.size(); i++)
        {
            MaterialBean.MaterialList material = materialList.get(i);
            //根据资源分类集合动态生成资源页面
            MaterialPager materialPager = new MaterialPager(this,material.code);

            mMaterialTabsTitleArr[i] = material.name;

            mMaterialPagersList.add(materialPager);
        }

        // 给viewPager设置适配器
        MaterialPagerAdapter adapter = new MaterialPagerAdapter();

        mViewPager.setAdapter(adapter);
        //        mViewPager.setOffscreenPageLimit(adapter.getCount());
        // 将Tabs和viewPager绑定
        mMaterialTabs.setViewPager(mViewPager);
        initListener();
//      初始化第一页
        mMaterialPagersList.get(0).initData();

    }

    private void initListener()
    {
        mBack.setOnClickListener(this);
        mFl_more.setOnClickListener(this);
        mMaterialTabs.setOnPageChangeListener(mMyOnPagerChangeListener);

//        mViewPager.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener()
//        {
//            @Override
//            public void onGlobalLayout()
//            {
//                // 刚进入Material界面时，手动选中第一个页面
//                mMyOnPagerChangeListener.onPageSelected(0);
//                // mViewPager.getViewTreeObserver().removeOnGlobalFocusChangeListener(this);
//            }
//        });
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.head_back_action)
        {
            finish();
        }else if(v.getId() == R.id.fl_head_more_action)
        {
            // 弹出popWindow
            initPopView();
        }
    }

    /**
     * 初始化popWindow
     */
    private void initPopView()
    {
        View popView = View.inflate(this, R.layout.popup_material, null);
        final TextView tv_search1 = (TextView)popView.findViewById(R.id.pop_item_search);
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;

        PopupWindow popupWindow = new PopupWindow(popView, width, height);
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
        popView.findViewById(R.id.pop_item_search).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Material.this,GuidanceSearch.class);
                startActivity(intent);
//                if(searchBox==null){
//
//                    searchBox = new PopupWindow(searchView,360, 100);
//                    // 需要给popWindow指定一个背景色,不然点击外面的时候不可以消失
//                    searchBox.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    searchBox.setFocusable(true);
//                    searchBox.setOutsideTouchable(true);
//                    searchBox.setAnimationStyle(R.style.SearchWindowAnimation);
//                    searchBox.showAsDropDown(mMore);
//                }
//                if(searchBox.isShowing())
//                    searchBox.dismiss();
//                else{
//                    int[] location = new int[2];
//                    tv_search1.getLocationOnScreen(location);
//                    searchBox.showAtLocation(tv_search1, Gravity.NO_GRAVITY, location[0] - 250, location[1] + tv_search1.getHeight() / 2 - searchBox.getHeight() / 2);
//                }
            }
        });

        // 跳转到下载管理界面
        popView.findViewById(R.id.pop_item_download).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // 跳转到下载管理界面
                Intent intent = new Intent(Material.this, DownloadManager.class);
                startActivity(intent);
            }
        });

    }

    class MyOnPagerChangeListener implements OnPageChangeListener
    {


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {

        }

        @Override
        public void onPageSelected(int position)
        {
            if(mMaterialPagersList!=null)
            {
                mMaterialPagersList.get(position).initData();
            }
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

    /**
     * viewPage用于显示数据的自定义适配器类
     */
    class MaterialPagerAdapter extends PagerAdapter
    {
        @Override
        public CharSequence getPageTitle(int position)
        {

            return mMaterialTabsTitleArr[position];
        }

        @Override
        public int getCount()
        {
            return mMaterialPagersList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            MaterialPager materialPager = mMaterialPagersList.get(position);
            container.addView(materialPager.mRootView);
            return materialPager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
//            super.destroyItem(container, position, object);
            container.removeView((View)object);
        }
    }

}
