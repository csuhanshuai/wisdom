package com.cnst.wisdom.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.cnst.wisdom.R;
import com.cnst.wisdom.ui.base.BaseFragment;
import com.cnst.wisdom.ui.fragment.FmFactory;

/**
 * 主界面 viewpager依赖的activity
 * @author jiangzuyun.
 * @see  [put，get]
 * @since [产品/模版版本]
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    //分别为  消息 教学 动态  我
    private int[] pagers = {0, 1, 2, 3};
    //分别为  消息 教学 动态  我
    private int[] checkedId = {R.id.radio0, R.id.radio1, R.id.radio2, R.id.radio3};
    private RadioGroup mRadioGroup;
    private MyFragmentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager)findViewById(R.id.vp);
        mRadioGroup = (RadioGroup)findViewById(R.id.radioGroup1);
        mAdapter = new MyFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        MyOnpagerChangerListener listener = new MyOnpagerChangerListener();
        mViewPager.setOnPageChangeListener(listener);
        mViewPager.setOffscreenPageLimit(5);
        setOnClickeners();
        mViewPager.setCurrentItem(2);
    }

    private void setOnClickeners(){
        findViewById(R.id.radio0).setOnClickListener(this);
        findViewById(R.id.radio1).setOnClickListener(this);
        findViewById(R.id.radio2).setOnClickListener(this);
        findViewById(R.id.radio3).setOnClickListener(this);

    }


    class MyFragmentAdapter extends FragmentStatePagerAdapter {

        private final FmFactory mFactory;

        public MyFragmentAdapter(FragmentManager fm){
            super(fm);
            mFactory = new FmFactory();
        }

        @Override
        public BaseFragment getItem(int position){

            return mFactory.createFragment(pagers[position]);
        }

        @Override
        public int getCount(){
            return pagers.length;
        }
    }

    class MyOnpagerChangerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0){

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
        }

        @Override
        public void onPageSelected(int position){
            int id = 0;
            switch(position) {
                case 0:
                    id = R.id.radio0;
                    break;
                case 1:
                    id = R.id.radio1;
                    break;
                case 2:
                    id = R.id.radio2;
                    break;
                case 3:
                    id = R.id.radio3;
                    break;

            }
            mRadioGroup.check(id);
        }
    }

    @Override
    public void onClick(View v){
        int index = -1;
        switch(v.getId()) {
            case R.id.radio0:
                index = 0;
                break;
            case R.id.radio1:
                index = 1;
                break;
            case R.id.radio2:
                index = 2;
                break;
            case R.id.radio3:
                index = 3;
                break;

            default:
                break;
        }
        mViewPager.setCurrentItem(index);
    }

//    教学计划
    public void teackplan(View v){
        jumpTo(TeachPlan.class);
    }
    //课前指导
    public void guidance(View v){
        jumpTo(Guidance1.class);
    }
//    在线进修
    public void onlinelearning(View v){
        jumpTo(OnlineLearn.class);
    }
//    资源素材
    public void material(View v){
        jumpTo(Material.class);
    }
//    考勤管理
    public void attendance(View v){
        jumpTo(Attendance.class);
    }
//    健康管理
    public void health(View v){
        jumpTo(HealthManage.class);
    }

    public void jumpTo(Class clazz){
        Intent starter = new Intent(this, clazz);
        startActivity(starter);
    }

}
