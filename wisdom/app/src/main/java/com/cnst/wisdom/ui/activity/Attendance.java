package com.cnst.wisdom.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cnst.wisdom.BaseApplication;
import com.cnst.wisdom.R;
import com.cnst.wisdom.model.bean.Recommend;
import com.cnst.wisdom.model.bean.bdmeizhi;
import com.cnst.wisdom.presenter.AttenPresenter;
import com.cnst.wisdom.ui.adapter.BaseListAdapter;
import com.cnst.wisdom.ui.adapter.ViewHolderHelper;
import com.cnst.wisdom.ui.view.AttenView;
import com.cnst.wisdom.ui.widget.calendar.CalendarDialog;
import com.cnst.wisdom.utills.DisplayUtils;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 考勤管理
 *
 * @author jiangzuyun.
 * @since [产品/模版版本]
 */
public class Attendance extends AppCompatActivity implements AttenView<String>, View.OnClickListener, CalendarDialog.SelectorListener, AdapterView.OnItemClickListener
{

    private RadioButton mRbtnToday;
    private TextView mtv_sw_class;
    private TextView mRbtnCalendar;
    private ListView mAttendance;
    private ProgressBar mLoadingView;
    private BaseListAdapter mAttendapter;
    private AttenPresenter mPresenter;

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
    private CalendarDialog mCalendarDialog;
    private ImageView mIv_toggle;
    private RotateAnimation down = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f);
    private RotateAnimation up = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f);
    private boolean popShow;
    private ListPopupWindow mListPopup;

    {
        up.setFillAfter(true);
        up.setDuration(300);
        down.setFillAfter(true);
        down.setDuration(300);
    }

    private void assignViews()
    {
        mtv_sw_class = (TextView)findViewById(R.id.tv_sw_class);
        mIv_toggle = (ImageView)findViewById(R.id.iv_toggle);
        mRbtnCalendar = (TextView)findViewById(R.id.tv_chose_time);
        mAttendance = (ListView)findViewById(R.id.vp_attendance);
        mLoadingView = (ProgressBar)findViewById(R.id.pb_loading);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        mPresenter = new AttenPresenter(this, this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        assignViews();

        ImageButton back = (ImageButton)findViewById(R.id.head_back_action);
        back.setOnClickListener(this);
        findViewById(R.id.head_search_action).setVisibility(View.GONE);
        ( (TextView)findViewById(R.id.head_text) ).setText(BaseApplication.findString(R.string.atten));

        mDateFormat = new SimpleDateFormat("MM月dd日");

        String today = mDateFormat.format(new Date());
        mRbtnCalendar.setText("("+today+")");

        //没有数据再拿网络数据
        mPresenter.getData(new Date(), TODAY);
        initPopwin();
        if("phone".equals(BaseApplication.findString(R.string.screen_type)))
        {
            mIgw = ( BaseApplication.getScreenwidth()/4-DisplayUtils.dip2px(20, this) );
        }else
        {
            mIgw = ( BaseApplication.getScreenwidth()/6-DisplayUtils.dip2px(20, this) );
        }
    }

    @Override
    public void showSucceedView(boolean show)
    {
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
                if("phone".equals(BaseApplication.findString(R.string.screen_type)))
                {
                    gridView.setNumColumns(2);
                }else
                {
                    gridView.setNumColumns(3);
                }
                helper.setAdapter(R.id.ingv_recommend,
                        new BaseListAdapter<bdmeizhi.DataEntity>(BaseApplication.getContext(), item.meizhi.getData(),
                                R.layout.item_gridview_atten)
                        {

                            @Override
                            public void convert(ViewHolderHelper helper, final bdmeizhi.DataEntity item)
                            {
                                helper.setText(R.id.tv_item_grid_atten, item.getAbs());
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

        mAttendapter.refreshData(mRec_data);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mPresenter.cancelNet(CURRDATA);
    }

    /**
     * 弹出日历对话框
     */
    private void showDateView()
    {
        if(mCalendarDialog == null)
        {
            mCalendarDialog = new CalendarDialog(this, this);
        }
        mCalendarDialog.show();
    }

    private void getData(int cuday)
    {
        mPresenter.cancelNet(CURRDATA);
        CURRDATA = cuday;
        //今天的数据 有获取过 则不再获取
        mPresenter.getData(new Date(), cuday);
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

    @Override
    public void onSelector(String date)
    {
        mRbtnCalendar.setText("("+date.substring(date.indexOf("-")+1).replace("-", "月")+")");

        getData(CALENDAR);
    }

    public void choseDate(View v)
    {
        showDateView();
    }

    public void classChose(View v)
    {
        toggleAni();
        togglePop();
    }

    private void toggleAni()
    {
        mIv_toggle.startAnimation(popShow ? up : down);
        popShow = !popShow;
    }

    private void togglePop()
    {
        if(mListPopup.isShowing())
        {
            mListPopup.dismiss();
        }else
        {
            mListPopup.show();
        }

    }

    private void initPopwin()
    {
        mListPopup = new ListPopupWindow(this);
        mListPopup.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{
                "list", "pop", "name"
        }));
        mListPopup.setModal(true);
        mListPopup.setWidth(AbsListView.LayoutParams.WRAP_CONTENT);
        mListPopup.setHeight(AbsListView.LayoutParams.WRAP_CONTENT);
        mListPopup.setAnchorView(findViewById(R.id.rl_pop));
        mListPopup.setOnDismissListener(new PopupWindow.OnDismissListener()
        {
            @Override
            public void onDismiss()
            {
                toggleAni();
            }
        });
        mListPopup.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        mListPopup.dismiss();
        screenOutClass();
        mtv_sw_class.setText("("+position+")");
    }

    private void screenOutClass()
    {
        //TODO
    }
}
