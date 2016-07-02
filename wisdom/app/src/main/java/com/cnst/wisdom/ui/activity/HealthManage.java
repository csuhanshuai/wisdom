package com.cnst.wisdom.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cnst.wisdom.BaseApplication;
import com.cnst.wisdom.R;
import com.cnst.wisdom.ui.widget.calendar.CalendarDialog;
import com.cnst.wisdom.model.bean.Recommend;
import com.cnst.wisdom.model.bean.bdmeizhi;
import com.cnst.wisdom.model.net.VolleyManager;
import com.cnst.wisdom.presenter.AttenPresenter;
import com.cnst.wisdom.ui.adapter.BaseListAdapter;
import com.cnst.wisdom.ui.adapter.ViewHolderHelper;
import com.cnst.wisdom.ui.view.AttenView;
import com.cnst.wisdom.utills.DisplayUtils;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 *  健康管理
 *
 *  @author jiangzuyun
 *  @see
 */
public class HealthManage extends AppCompatActivity implements AttenView<String>, RadioGroup.OnCheckedChangeListener, View.OnClickListener, CalendarDialog.SelectorListener {
    private RadioGroup mRgTime;
    private RadioButton mRbtnToday;
    private RadioButton mRbtnYestoday;
    private RadioButton mRbtnCalendar;
    private ListView mAttendance;
    private ProgressBar mLoadingView;
    private BaseListAdapter mAttendapter;
    private AttenPresenter mPresenter;
    //data 分别存放2种数据 TODAY  YESTODAY
    private SparseArray sdata = new SparseArray(2);
    WeakReference<SparseArray> weakSdata = new WeakReference<SparseArray>(sdata);

    private int TODAY = 0;//使用具体日期
    private int YESTODAY = 1;//使用具体日期--月日0119

    private int CALENDAR = 2;//比较特殊2表示 选中的日期 不管是哪天
    /**
     * 当前 的数据类型
     */
    private int CURRDATA = TODAY;

    /**
     * 图片 应该使用的宽度
     */
    private int mIgw;
    private SimpleDateFormat mDateFormat;
    private SimpleDateFormat mCacheDateFormat;
    private CalendarDialog mCalendarDialog;

    private void assignViews()
    {
        mRgTime = (RadioGroup)findViewById(R.id.rg_time);
        mRbtnToday = (RadioButton)findViewById(R.id.rbtn_today);
        mRbtnYestoday = (RadioButton)findViewById(R.id.rbtn_yestoday);
        mRbtnCalendar = (RadioButton)findViewById(R.id.rbtn_calendar);
        mAttendance = (ListView)findViewById(R.id.vp_attendance);
        mLoadingView = (ProgressBar)findViewById(R.id.pb_loading);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        mPresenter = new AttenPresenter(this, this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_manage);
        assignViews();
        mRgTime.setOnCheckedChangeListener(this);

        ImageButton back = (ImageButton)findViewById(R.id.head_back_action);
        findViewById(R.id.head_search_action).setVisibility(View.INVISIBLE);
        back.setOnClickListener(this);
        ( (TextView)findViewById(R.id.head_text) ).setText(BaseApplication.findString(R.string.health));

        mIgw = ( BaseApplication.getScreenwidth()/4-DisplayUtils.dip2px(20, this) );
        mDateFormat = new SimpleDateFormat("MM月dd日");
        mCacheDateFormat = new SimpleDateFormat("MMdd");

        String today = mDateFormat.format(new Date());
        mRbtnToday.setText("("+today+")");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        mRbtnYestoday.setText("("+mDateFormat.format(calendar.getTime())+")");
        mRbtnCalendar.setText("");

        //初始化 今天 昨天
        YESTODAY = Integer.valueOf(mCacheDateFormat.format(calendar.getTime()));
        calendar.add(Calendar.DATE, +1);
        CURRDATA = TODAY = Integer.valueOf(mCacheDateFormat.format(calendar.getTime()));

        //TODO 从数据库获取数据 初始化到 sdata中  判断数据库中的缓存是否过期，过期就清除
        //没有数据再拿网络数据
        mPresenter.getData(new Date(), TODAY);
    }

    @Override
    public void showSucceedView(boolean show)
    {
        //        List<String> list = Arrays.asList(BaseApplication.findStringArray(R.array.attenList));
        //        mAttendapter = new BaseListAdapter<Recommend>(this,list, R.layout.item_list_recommend)
        mAttendapter = new BaseListAdapter<Recommend>(this, R.layout.item_list_recommend)
        {
            @Override
            public void convert(ViewHolderHelper helper, Recommend item)
            {
                helper.setText(R.id.tv_list_title, item.title);
                helper.setClickListener(R.id.tv_list_rec_more, new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                    }
                });
                GridView gridView = helper.findViewById(R.id.ingv_recommend);
                gridView.setNumColumns(2);
                helper.setAdapter(R.id.ingv_recommend,
                        new BaseListAdapter<bdmeizhi.DataEntity>(BaseApplication.getContext(), item.meizhi.getData(),
                                R.layout.item_gridview_atten)
                        {

                            @Override
                            public void convert(ViewHolderHelper helper, final bdmeizhi.DataEntity item)
                            {
                                helper.setText(R.id.tv_item_grid_atten, item.getAbs());
                                helper.setText(R.id.btn_item_grid_atten, "健康");
                                helper.setCircleImageFromUrl(R.id.im_item_grid_recommend, mIgw, item.getImage_url());

                                helper.setClickListener(R.id.tv_item_grid_atten, new View.OnClickListener()
                                {

                                    @Override
                                    public void onClick(View v)
                                    {
                                    }
                                });
                            }

                        });
            }
        };

        mAttendance.setAdapter(mAttendapter);
    }

    @Override
    public void showEmptyView(boolean show)
    {

    }

    @Override
    public void showErrorView(boolean show)
    {
    }

    @Override
    public void showLoadingView(boolean show)
    {
        mLoadingView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void refreshData(String response)
    {
        Gson gson = new Gson();
        bdmeizhi bdmeizhi = null;
        bdmeizhi = gson.fromJson((String)response, com.cnst.wisdom.model.bean.bdmeizhi.class);
        ArrayList<Recommend> mRec_data = new ArrayList<Recommend>();
        List<String> url_maps = new ArrayList<>();
        for(int i = 0; i<bdmeizhi.getData().size()-1; i++)
        {
            //            List<bdmeizhi.DataEntity> data = bdmeizhi.getData();
            //            bdmeizhi.setData(data);
            mRec_data.add(new Recommend("今日热点："+i, bdmeizhi));
            url_maps.add(bdmeizhi.getData().get(i).getImage_url());
        }

        SparseArray wsdata = weakSdata.get();
        if(null != wsdata && CURRDATA != CALENDAR)
        {
            //将 拿到的数据 缓存起来   CALENDAR不存
            wsdata.append(CURRDATA, mRec_data);
        }
        //数据库存一份

        mAttendapter.refreshData(mRec_data);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        VolleyManager.getInstance().cancel(CURRDATA);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId)
    {
        mPresenter.cancelNet(CURRDATA);//取消 之前的网络请求
        switch(checkedId)
        {
            //切换到 选定的日期 数据
            case R.id.rbtn_calendar:
                CURRDATA = CALENDAR;
                showDateView();
                break;
            //获取今天数据
            case R.id.rbtn_today:
                CURRDATA = TODAY;
                mAttendance.setTag(CURRDATA);
                getData(TODAY);

                break;
            //获取昨天数据
            case R.id.rbtn_yestoday:
                CURRDATA = YESTODAY;
                mAttendance.setTag(CURRDATA);
                //昨天的数据 有获取过 则不再获取
                getData(YESTODAY);
                break;
        }
    }

    private void getData(int today)
    {
        if(null != weakSdata.get() && null != sdata.get(CURRDATA))
        {
            //今天的数据 有获取过 则不再获取
            mAttendapter.refreshData((List)sdata.get(CURRDATA));

        }else
        {
            mPresenter.getData(new Date(), today);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.head_back_action:
                finish();
                break;

        }
    }

    private void showDateView()
    {
        if(mCalendarDialog == null)
        {
            mCalendarDialog = new CalendarDialog(this, this);
        }
        mCalendarDialog.show();
    }

    @Override
    public void onSelector(String date)
    {
        mAttendance.setTag(CURRDATA);
        mPresenter.getData(new Date(), CALENDAR);
    }
}
