package com.cnst.wisdom.ui.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.cnst.wisdom.R;
import com.cnst.wisdom.model.Constants;
import com.cnst.wisdom.model.bean.Clazz;
import com.cnst.wisdom.model.bean.Subject;
import com.cnst.wisdom.model.bean.TeachBaseBean;
import com.cnst.wisdom.model.bean.TeachPlanBean;
import com.cnst.wisdom.model.bean.Term;
import com.cnst.wisdom.model.cache.CacheData;
import com.cnst.wisdom.model.net.NetResult;
import com.cnst.wisdom.model.net.VolleyManager;
import com.cnst.wisdom.ui.view.ListDialog;
import com.cnst.wisdom.ui.widget.calendar.CalendarDialog;
import com.cnst.wisdom.utills.GeoUtil;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 教学计划的教学科目主界面
 * 通过此页面创建教学科目
 *
 * @author taoyuan.
 * @since 1.0
 */
public class TeachPlanCourse extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnDismissListener, CalendarDialog.SelectorListener
{
    private TextView clazzTv;
    private TextView subjectTv;
    private TextView termTv;
    private TextView cycleTv;
    private int flag;
    private final int CLASS = 1;
    private final int SUBJECT = 2;
    private final int TERM = 3;
    private final int DATE = 4;
    private TextView confirmBt;//确认提交按钮
    private ListDialog mSubjectDialog;
    private ListDialog mTermDialog;
    private ListDialog mClazzDialog;
    private CalendarDialog mCalendarDialog;
    private List<Clazz> classList = new ArrayList<>();//班级种类
    private List<Term> termList = new ArrayList<>();//期数种类
    private List<Subject> subjectList = new ArrayList<>();//学科种类
    private TeachPlanBean mPlanBean;
    private SimpleDateFormat sdf = new SimpleDateFormat("yy-mm-dd");
    private String TAG = getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach_subject);
        clazzTv = (TextView)findViewById(R.id.teach_plan_clazz);
        clazzTv.setOnClickListener(this);
        subjectTv = (TextView)findViewById(R.id.teach_plan_subject);
        subjectTv.setOnClickListener(this);
        termTv = (TextView)findViewById(R.id.teach_plan_term);
        termTv.setOnClickListener(this);
        cycleTv = (TextView)findViewById(R.id.teach_plan_cycle);
        cycleTv.setOnClickListener(this);
        confirmBt = (TextView)findViewById(R.id.teach_plan_confirm);
        findViewById(R.id.back).setOnClickListener(this);
        /**
         * 判断此页面是否由修改计划界面跳转过来
         */
        if(getIntent().getSerializableExtra(TAG) != null)
        {
            TextView titleView = (TextView)findViewById(R.id.title);
            titleView.setText(R.string.mof_plan);
            mPlanBean = (TeachPlanBean)getIntent().getSerializableExtra(TAG);
            subjectTv.setText(mPlanBean.getSubject().getName());
            termTv.setText(mPlanBean.getTerm().getName());
            cycleTv.setText(mPlanBean.getBeginTime()+" 至 "+mPlanBean.getEndTime());
            setConfirmClickable();
        }else
        {
            mPlanBean = new TeachPlanBean();
        }
        /**
         * 将数据的第一个item即“全部”，移除掉
         */
        initData();
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.teach_plan_clazz:
                if(mClazzDialog == null)
                {
                    mClazzDialog = new ListDialog(this, classList);
                    mClazzDialog.setOnDismissListener(this);

                }
                mClazzDialog.show();
                flag = CLASS;//更改标记，设置当前显示了班级的对话框
                break;
            case R.id.teach_plan_subject:
                if(mSubjectDialog == null)
                {
                    mSubjectDialog = new ListDialog(this, subjectList);
                    mSubjectDialog.setOnDismissListener(this);
                }
                if(subjectList.size() == 0)
                {
                    subjectList.addAll(CacheData.sSubjectList);
                    subjectList.remove(0);
                    mSubjectDialog.getAdapter().notifyDataSetChanged();
                }
                mSubjectDialog.show();
                flag = SUBJECT;//更改标记，设置当前显示了科目的对话框
                break;
            case R.id.teach_plan_term:
                if(mTermDialog == null)
                {
                    mTermDialog = new ListDialog(this, termList);
                    mTermDialog.setOnDismissListener(this);
                }
                if(termList.size() == 0)
                {
                    termList.addAll(CacheData.sPeriodsList);
                    termList.remove(0);
                    mTermDialog.getAdapter().notifyDataSetChanged();
                }
                mTermDialog.show();
                flag = TERM;//更改标记，设置当前显示了教学周期的对话框
                break;
            case R.id.teach_plan_cycle:
                showDateView();
                break;
            case R.id.teach_plan_confirm:
                addSubject();
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }


    private void addSubject()
    {
//                mPlanBean.setClazz(clazzTv.getText().toString());
//                mPlanBean.setCycle(cycleTv.getText().toString());
//                mPlanBean.setPeriods(termTv.getText().toString());
//                mPlanBean.setSubject(subjectTv.getText().toString());
        finish();
    }

    private void showDateView()
    {
        if(mCalendarDialog == null)
        {
            mCalendarDialog = new CalendarDialog(this, this);
        }
        mCalendarDialog.calV.setMaxSel(2);
        mCalendarDialog.show();
        mCalendarDialog.setOnDismissListener(this);
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        switch(flag){
            case CLASS:
                Clazz clazz = classList.get(mClazzDialog.getSelIndex());
                clazzTv.setText(clazz.getName());
                mPlanBean.setClazz(clazz);
                break;
            case SUBJECT:
                Subject subject = subjectList.get(mSubjectDialog.getSelIndex());
                subjectTv.setText(subject.getName());
                mPlanBean.setSubject(subject);
                break;
            case TERM:
                Term term = termList.get(mTermDialog.getSelIndex());
                termTv.setText(term.getName());
                mPlanBean.setTerm(term);
                break;
        }

        if(!confirmBt.isClickable())
        {
            if(mCalendarDialog != null && mTermDialog != null && mSubjectDialog != null && mClazzDialog != null)
            {
                /**
                 * 当确定键为不可编辑而且四个对话框都显示过即所有属性都编辑过时，设置确认键可编辑
                 */
                setConfirmClickable();
            }
        }
    }

    private void setConfirmClickable() {
        confirmBt.setClickable(true);
        confirmBt.setOnClickListener(this);
        confirmBt.setTextColor(Color.WHITE);
        confirmBt.setBackgroundResource(R.drawable.bg_button_clickable);
    }

    private void initData()
    {
        //获取班级列表
        //        Map classMap = new HashMap();
        //        classMap.put("type","class"); //接口未知
        //        VolleyManager.getInstance().getString(Constants.getTeachCategorys, classMap, TAG, new NetResult<String>()
        //        {
        //
        //            @Override
        //            public void onFailure(VolleyError error)
        //            {
        ////                Toast.makeText(TeachPlanCourse.this, new String(error.networkResponse.data), Toast.LENGTH_LONG).show();
        //            }
        //
        //            @Override
        //            public void onSucceed(String response)
        //            {
        //                if(response != null && response.length()>0)
        //                {
        //                    TeachBaseBean<Clazz> temp = new GeoUtil()
        //                            .deserializer(response, new TypeToken<TeachBaseBean<Clazz>>()
        //                            {}.getType());
        //                    if(temp != null && temp.getData() != null && temp.getData().size()>0)
        //                    {
        //                        classList.addAll(temp.getData());
        //                        for(Clazz bean : classList)
        //                        {
        //                            classNameList.add("一班");
        //                        }
        //                        clazzAdapter.notifyDataSetChanged();
        //                    }
        //                }
        //            }
        //        });
        //获取学科列表
//        String str = SPUtills.get(getApplicationContext(), Constants.GET_SESSIONID, "no-data").toString();
        Map termMap = new HashMap();
        termMap.put("type", "subject");//当获取学科信息时值为subject 不可空
        //        classMap.put("fk","fk"); //预留，获取学科信息 当前直接传入空。后期可传入幼儿园id获取某个幼儿园下的学科 可空
        VolleyManager.getInstance().getString(Constants.getTeachCategorys, termMap, TAG, new NetResult<String>()
        {
            @Override
            public void onFailure(VolleyError error)
            {
                //                Toast.makeText(TeachPlanCourse.this, new String(error.networkResponse.data), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSucceed(String response)
            {
                if(response != null && response.length()>0)
                {
                    TeachBaseBean<Subject> temp = new GeoUtil()
                            .deserializer(response, new TypeToken<TeachBaseBean<Subject>>()
                            {}.getType());
                    if(temp != null && temp.getData() != null && temp.getData().size()>0)
                    {
                        subjectList.addAll(temp.getData());
                        if(mSubjectDialog != null)
                        {
                            mSubjectDialog.getAdapter().notifyDataSetChanged();
                        }
                    }
                }
            }
        });
        //获取学期列表
        Map subjectMap = new HashMap();
        subjectMap.put("type", "term");//当获取学期信息时值为term 不可空
        //        classMap.put("fk","fk"); //获取学期信息 传入所属学科id；若为空则查询所有学科下的学期信息 不可空
        VolleyManager.getInstance().getString(Constants.getTeachCategorys, subjectMap, TAG, new NetResult<String>()
        {

            @Override
            public void onFailure(VolleyError error)
            {
                //                Toast.makeText(TeachPlanCourse.this,new String(error.networkResponse.data),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSucceed(String response)
            {
                if(response != null && response.length()>0)
                {
                    TeachBaseBean<Term> temp = new GeoUtil()
                            .deserializer(response, new TypeToken<TeachBaseBean<Term>>()
                            {}.getType());
                    if(temp != null && temp.getData() != null && temp.getData().size()>0)
                    {
                        termList.addAll(temp.getData());
                        if(mTermDialog != null)
                        {
                            mTermDialog.getAdapter().notifyDataSetChanged();
                        }
                    }
                }
            }
        });
        if(classList.size() == 0)
        {
            classList.addAll(CacheData.sClassList);

        }
    }

    @Override
    public void onSelector(String date)
    {
        cycleTv.setText(date);
        String[] time = date.split(" 至 ");
        mPlanBean.setBeginTime(time[0]);
        mPlanBean.setEndTime(time[1]);
    }

    @Override
    protected void onDestroy()
    {
        VolleyManager.getInstance().mRequestQueue.cancelAll(TAG);
        super.onDestroy();
    }
}
