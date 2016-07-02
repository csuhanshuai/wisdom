package com.cnst.wisdom.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.cnst.wisdom.R;
import com.cnst.wisdom.utills.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonas on 2016/1/20.
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author meilianbing.
 * @see [相关类/方法]
 * @since [产品/模版版本]
 */
public class ExpandTabView extends LinearLayout implements PopupWindow.OnDismissListener {

    private Context mContext;

    private int displayWidth;
    private int displayHeight;
    private int tabWidth;

    private ToggleButton selectedButton;
    private List<ToggleButton> mButtonList = new ArrayList<ToggleButton>();

    private List<String> mTabTitleList = new ArrayList<String>();
    private List<RelativeLayout> mTabViewList = new ArrayList<RelativeLayout>();

    private int selectedPosition;
    private PopupWindow popupWindow;

    public ExpandTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ExpandTabView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        displayWidth = ((Activity)context).getWindowManager()
                .getDefaultDisplay().getWidth();
        displayHeight = ((Activity)context).getWindowManager()
                .getDefaultDisplay().getHeight();
        setOrientation(LinearLayout.HORIZONTAL);
    }

    /**
     * 根据选择的位置设置tabmenu显示的值
     */
    public void setTitle(String showText, int position) {
        if (position < mButtonList.size()) {
            mButtonList.get(position).setText(showText);
        }
    }

    /**
     * 根据选择的位置获取tabmenu显示的值
     */
    public String getTitle(int position) {
        if (position < mButtonList.size() && mButtonList.get(position).getText() != null) {
            return mButtonList.get(position).getText().toString();
        }
        return "";
    }

    /**
     * 设置tabmenu的个数和初始值
     */
    public void inflateTab(List<String> tabTitle,List<View> tabView){
        if(mContext==null)
            return ;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mTabTitleList = tabTitle;
        tabWidth = (int) (displayWidth/(tabView.size()*1.0));

        //动态设定tab菜单右边的箭头大小
        Drawable rightDrawable = getResources().getDrawable(R.drawable.tab_arrow_down);
        rightDrawable.setBounds(0, 0, DisplayUtils.dip2px(15, mContext), DisplayUtils.dip2px(10,mContext));

        for(int i=0;i<tabView.size();i++){
            RelativeLayout rl = new RelativeLayout(mContext);

            //下拉菜单最大长度
            int maxHeight = (int) (displayHeight*0.7);
            RelativeLayout.LayoutParams params = new RelativeLayout
                    .LayoutParams(tabWidth,maxHeight);
            params.leftMargin = tabWidth*i;
            //params.rightMargin = 10;

            //将下拉菜单加入相对布局中
            rl.addView(tabView.get(i), params);
            mTabViewList.add(rl);

            //将菜单按钮加入顶部菜单栏
            ToggleButton tButton = (ToggleButton) inflater.inflate(R.layout.toggle_button,this,false);
            tButton.setCompoundDrawables(null,null,rightDrawable,null);
            addView(tButton);


            //添加toggle按钮之间的分割线
            View line = new TextView(mContext);
            line.setBackgroundResource(R.drawable.choosebar_line);
            if(i<tabView.size()-1){
                LayoutParams lp = new LayoutParams(5,LayoutParams.MATCH_PARENT);
                addView(line,lp);
            }
            mButtonList.add(tButton);
            tButton.setTag(i);
            tButton.setText(mTabTitleList.get(i));

            //菜单被点击，展开状态则收回
            rl.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPressBack();
                }
            });

            //下拉菜单背景设置
            rl.setBackgroundColor(mContext.getResources().getColor(R.color.popup_main_background));

            //菜单点击
            tButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToggleButton toggleButton = (ToggleButton) v;
                    if(selectedButton!=null&&selectedButton!=toggleButton)
                        selectedButton.setChecked(false);
                    selectedButton = toggleButton;
                    selectedPosition = (int) selectedButton.getTag();
                    startAnimation();
                    if(mOnButtonClickListener!=null&&toggleButton.isChecked()){
                        mOnButtonClickListener.onClick(selectedPosition);
                    }
                }
            });
        }
    }

    public boolean onPressBack(){
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            hideView();
            if (selectedButton != null) {
                selectedButton.setChecked(false);
            }
            return true;
        } else {
            return false;
        }
    }

    private void showPopup(int selectedPosition){
        if (popupWindow.getContentView() != mTabViewList.get(selectedPosition)) {
            popupWindow.setContentView(mTabViewList.get(selectedPosition));
        }
        popupWindow.showAsDropDown(this, mButtonList.get(selectedPosition).getLeft(), 0);
    }

    private void hideView(){

    }

    /**
     * 菜单栏弹出或者关闭
     */
    private void startAnimation(){
        if(popupWindow==null){

            //popupWindow宽高及内容设定
            popupWindow = new PopupWindow(mTabViewList.get(selectedPosition), displayWidth, displayHeight);
            // popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
            popupWindow.setFocusable(false);
            popupWindow.setOutsideTouchable(true);
        }

        //判断选中的toggleButton是否展开
        if(selectedButton.isChecked()){
            if(!popupWindow.isShowing()){
                showPopup(selectedPosition);
            }else{
                //如果有其他菜单栏展开，应先将其关闭，再将新选中的toggleButton展开
                popupWindow.setOnDismissListener(this);
                popupWindow.dismiss();
                hideView();
            }
        }else{
            if(popupWindow.isShowing()){
                popupWindow.dismiss();
                hideView();
            }
        }
    }

    @Override
    public void onDismiss() {
        showPopup(selectedPosition);

        //停止对popupWindow消失的监听
        popupWindow.setOnDismissListener(null);
    }

    /**
     * 设置窗口透明度
     * @param alpha
     */
    private void setBackground(float alpha){
        Activity aty = (Activity) mContext;
        WindowManager.LayoutParams lp = aty.getWindow().getAttributes();
        lp.alpha = alpha;
        aty.getWindow().setAttributes(lp);
    }

    private OnButtonClickListener mOnButtonClickListener;

    public interface OnButtonClickListener {
        void onClick(int selectPosition);
    }
}
