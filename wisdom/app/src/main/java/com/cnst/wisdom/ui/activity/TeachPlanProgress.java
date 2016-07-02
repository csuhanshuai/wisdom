package com.cnst.wisdom.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.cnst.wisdom.R;
import com.cnst.wisdom.model.bean.Clazz;
import com.cnst.wisdom.model.bean.Subject;
import com.cnst.wisdom.model.bean.TeachPlanBean;
import com.cnst.wisdom.model.bean.Term;
import com.cnst.wisdom.model.cache.CacheData;
import com.cnst.wisdom.ui.adapter.TeachPlanPopSelAd;
import com.cnst.wisdom.ui.adapter.TeachProgressAd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 教学计划的教学进度主界面
 * 通过此页面查看及修改教学进度
 *
 * @author taoyuan.
 * @since 1.0
 */
public class TeachPlanProgress extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, PopupWindow.OnDismissListener
{
    private ListView mListView;//显示课程的ListView
    private List<TeachPlanBean> mPlanBeans = new ArrayList<>();//需要显示出来的数据,随筛选条件而变化
    private TeachProgressAd mAd;//mListView
    private ListPopupWindow clazzWin;//班级筛选弹出框
    private ListPopupWindow subWin;//科目筛选弹出框
    private ListPopupWindow perWin;//期数筛选弹出框
    private Clazz clazzName = CacheData.sClassList.get(0);//默认选择第一项,即全部
    private Subject subjectName = CacheData.sSubjectList.get(0);//默认选择第一项,即全部
    private Term periodsName = CacheData.sPeriodsList.get(0);//默认选择第一项,即全部
    private View contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        contentView = getLayoutInflater().inflate(R.layout.activity_teach_progress, null);
        setContentView(contentView);

        findViewById(R.id.teach_progress_subject).setOnClickListener(this);
        findViewById(R.id.teach_progress_clazz).setOnClickListener(this);
        findViewById(R.id.teach_progress_periods).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        mListView = (ListView)findViewById(R.id.teach_progress_sel_list);
        mPlanBeans.clear();
        mPlanBeans.addAll(CacheData.sCourseList);//第一次将缓存里的所有数据加进去
        Collections.sort(mPlanBeans);
        mAd = new TeachProgressAd(this, mPlanBeans);
        mListView.setAdapter(mAd);
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.teach_progress_clazz:
                if(clazzWin == null)
                {
                    clazzWin = getPopupWindow(v, CacheData.sClassList);
                }else
                {
                    clazzWin.show();
                }
                break;
            case R.id.teach_progress_subject:
                if(subWin == null)
                {
                    subWin = getPopupWindow(v, CacheData.sSubjectList);
                }else
                {
                    subWin.show();
                }
                break;
            case R.id.teach_progress_periods:
                if(perWin == null)
                {
                    perWin = getPopupWindow(v, CacheData.sPeriodsList);
                }else
                {
                    perWin.show();
                }
                break;
            case R.id.back:
                finish();
                return;
            default:
                break;
        }
        contentView.setBackgroundColor(getResources().getColor(R.color.tip_text));
        contentView.setAlpha(0.5f);
    }

    private ListPopupWindow getPopupWindow(View v, List data)
    {
        ListPopupWindow popupWindow = new ListPopupWindow(this);
        TeachPlanPopSelAd adapter = new TeachPlanPopSelAd(this, data);
        popupWindow.setAdapter(adapter);
        popupWindow.setOnItemClickListener(this);

        //        popupWindow.getListView().setFastScrollAlwaysVisible(false);
        popupWindow.setListSelector(getResources().getDrawable(R.color.underline_gray));
        popupWindow.setForceIgnoreOutsideTouch(false);//设置在外部点击,弹出框消失
        popupWindow.setListSelector(getResources().getDrawable(R.color.onClick));//设置备选中的颜色
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.white));//设置弹出框背景
        //        popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        popupWindow.setVerticalOffset(2);//垂直偏移
        popupWindow.setAnchorView(v);//在哪个控件下方显示
        popupWindow.setOnDismissListener(this);
        popupWindow.show();
        ListView listView = popupWindow.getListView();
        listView.setVerticalScrollBarEnabled(false);
        listView.setDividerHeight(10);
        listView.setDivider(new ColorDrawable(Color.GRAY));
        return popupWindow;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        TeachPlanPopSelAd ad = null;
        //获取选中的文本信息
        if(clazzWin != null && clazzWin.isShowing())
        {
            ad = ( (TeachPlanPopSelAd)clazzWin.getListView().getAdapter() );
            clazzName = CacheData.sClassList.get(position);
            clazzWin.dismiss();
        }else if(subWin != null && subWin.isShowing())
        {
            ad = ( (TeachPlanPopSelAd)subWin.getListView().getAdapter() );
            subjectName = CacheData.sSubjectList.get(position);
            subWin.dismiss();
        }else if(perWin != null && perWin.isShowing())
        {
            ad = ( (TeachPlanPopSelAd)perWin.getListView().getAdapter() );
            periodsName = CacheData.sPeriodsList.get(position);
            perWin.dismiss();
        }
        ad.setCurPosition(position);
        ad.notifyDataSetChanged();
        mPlanBeans.clear();//将原有数据清空
        SelSubject();//挑选课程对象
        mAd.notifyDataSetChanged();
    }


    private void SelSubject()
    {
        for(int i = 0; i<CacheData.sCourseList.size(); i++)
        {
            TeachPlanBean course = CacheData.sCourseList.get(i);
            boolean isClazz = clazzName.equals(CacheData.sClassList.get(0)) || course.getClazz().equals(clazzName);
            boolean isSubject = subjectName.equals(CacheData.sSubjectList.get(0)) || course.getSubject()
                    .equals(subjectName);
            boolean isPeriods = periodsName.equals(CacheData.sPeriodsList.get(0)) || course.getTerm()
                    .equals(periodsName);
            if(isClazz && isSubject && isPeriods)
            {
                mPlanBeans.add(course);
            }
        }
    }

    @Override
    public void onDismiss()
    {
        contentView.setBackgroundColor(getResources().getColor(R.color.white));
        contentView.setAlpha(1f);
    }
}
