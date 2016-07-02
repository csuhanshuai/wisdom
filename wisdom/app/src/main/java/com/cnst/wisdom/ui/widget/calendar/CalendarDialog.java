package com.cnst.wisdom.ui.widget.calendar;

import android.app.Activity;
import android.app.Dialog;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.cnst.wisdom.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;


/**
 * 日历显示Dialog
 * 点击Spinner选择
 *
 * @author taoyuan
 */
public class CalendarDialog extends Dialog implements View.OnClickListener, AdapterView.OnItemClickListener
{
    private GestureDetector gestureDetector = null;//手势识别，判断手指滑动方向
    public CalendarAdapter calV = null;
    private ViewFlipper flipper = null;
    private GridView gridView = null;
    /** 显示当前月份 */
    private TextView monthTv;
    /** 点击跳转到今天 */
    private Button todayBt;
    private Activity mContext;
    /** 选择日期监听器 */
    private SelectorListener mSelectorListener;
    private Calendar calendar;//更加此对象判断要显示的月份数据
    //    private ArrayList<Calendar> calendars = new ArrayList<>();


    public CalendarDialog(Activity context, SelectorListener mSelectorListener)
    {
        super(context, R.style.no_title_dialog);
        this.mContext = context;
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        calendar = Calendar.getInstance();
        setContentView(R.layout.calendar);
        init();
        this.mSelectorListener = mSelectorListener;
    }

    private void init()
    {
        monthTv = (TextView)findViewById(R.id.month_pop);
        todayBt = (Button)findViewById(R.id.today);
        gestureDetector = new GestureDetector(mContext, new MyGestureListener());
        flipper = (ViewFlipper)findViewById(R.id.flipper);
        setListener();

    }

    private void setListener()
    {
        findViewById(R.id.calendar_cancel).setOnClickListener(this);
        findViewById(R.id.calendar_confirm).setOnClickListener(this);
        todayBt.setOnClickListener(this);
        addGridView();
    }
    //
    //    public void showTwoDate(Calendar startCalendar,Calendar endCalendar)
    //    {
    //        calV.setMaxSel(2);
    //        calV.refreshSelDate(startCalendar);
    //        calV.refreshSelDate(endCalendar);
    //        show();
    //    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        if(calV.getItem(position) != null)
        {
            calV.refreshSelDate((Calendar)calV.getItem(position));
        }
    }

    private class MyGestureListener extends SimpleOnGestureListener
    {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            if(e1.getX()-e2.getX()>120)
            {
                // 向左滑动位移大于120时
                calendar.add(Calendar.MONTH, 1);
                enterSelMonth(false);
                return true;
            }else if(e1.getX()-e2.getX()<-120)
            {
                // 向右滑动位移大于120
                calendar.add(Calendar.MONTH, -1);
                enterSelMonth(true);
                return true;
            }else
            {
                return false;
            }
        }
    }

    /**
     * 移动到指定月
     *
     * @param
     */
    private void enterSelMonth(boolean isAfter)
    {
        // 添加一个gridView
        addGridView();
        //给前后翻月设置不同动画
        if(isAfter)
        {
            flipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_right_in));
            flipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_right_out));
            flipper.showNext();
        }else
        {
            flipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_left_in));
            flipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_left_out));
            flipper.showPrevious();
        }
        flipper.removeViewAt(0);
    }

    private void addGridView()
    {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        // 取得屏幕的宽度和高度
        gridView = new GridView(mContext);
        gridView.setNumColumns(7);
        gridView.setColumnWidth(40);
        gridView.setGravity(Gravity.CENTER_VERTICAL);
        // 去除gridView边框
        gridView.setVerticalSpacing(1);
        gridView.setHorizontalSpacing(1);
        gridView.setSelector(R.color.white);
        gridView.setOnTouchListener(new OnTouchListener()
        {
            // 将gridview中的触摸事件回传给gestureDetector
            public boolean onTouch(View v, MotionEvent event)
            {
                return CalendarDialog.this.gestureDetector.onTouchEvent(event);
            }
        });
        gridView.setOnItemClickListener(this);
        gridView.setLayoutParams(params);
        if(calV == null)
        {
            calV = new CalendarAdapter(mContext, calendar);
        }else
        {
            calV.refreshDate(calendar);//更换Adapter里的数据
        }
        gridView.setAdapter(calV);
        StringBuffer textDate = new StringBuffer();
        textDate.append(calendar.get(Calendar.YEAR)+"年"+( calendar.get(Calendar.MONTH)+1 )+"月").append("\t");
        monthTv.setText(textDate);
        flipper.addView(gridView);
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.today: // 今天
                if(!((calendar.get(Calendar.MONTH) ==Calendar.getInstance().get(Calendar.MONTH))&&
                        (calendar.get(Calendar.YEAR) ==Calendar.getInstance().get(Calendar.YEAR))))
                {
                    boolean isAfter = CalendarUtils.isAfter(Calendar.getInstance(), calendar);
                    calendar = Calendar.getInstance();
                    enterSelMonth(isAfter);
                }
                calV.getSelCalendars().add(Calendar.getInstance());
                calV.notifyDataSetChanged();
                break;
            case R.id.calendar_cancel:
                dismiss();
                break;
            case R.id.calendar_confirm:
                StringBuffer date = new StringBuffer();
                ArrayList<Calendar> calendars = calV.getSelCalendars();
                if(calendars.size() == 0)
                {
                    Toast.makeText(mContext, R.string.ple_sel_date, Toast.LENGTH_SHORT).show();//当用户直选了一个日期时
                }else if(calV.getMaxSel()>calendars.size())
                {
                    Toast.makeText(mContext, R.string.ple_sel_end_date, Toast.LENGTH_SHORT).show();//当用户未选完日期时
                    return;
                }else
                {
                    date.append(calendars.get(0).get(Calendar.YEAR)).append("-")
                            .append(calendars.get(0).get(Calendar.MONTH)+1).append("-")
                            .append(calendars.get(0).get(Calendar.DAY_OF_MONTH));
                    if(calendars.size()>1)
                    {
                        date.append(" 至 ");
                        date.append(calendars.get(1).get(Calendar.YEAR)).append("-")
                                .append(calendars.get(1).get(Calendar.MONTH)+1).append("-")
                                .append(calendars.get(1).get(Calendar.DAY_OF_MONTH));
                    }
                }
                mSelectorListener.onSelector(date.toString());
                dismiss();
                break;
            default:
                break;
        }
    }

    public interface SelectorListener
    {
        void onSelector(String date);
    }
}