package com.cnst.wisdom.ui.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cnst.wisdom.R;
import com.cnst.wisdom.ui.adapter.MenuAdapter;
import com.cnst.wisdom.ui.view.ViewBaseAction;

/**
 * <自定义课程指导页面弹出的筛选菜单>
 * <提供用户筛选课程的菜单，点击菜单列表项进行筛选>
 *
 * @author meilianbing.
 * @see [相关类/方法]
 * @since [产品/模版版本]
 */
public class MenuView extends RelativeLayout implements ViewBaseAction {

    private Context mContext;

    private ListView mListView;

    private MenuAdapter adapter;

    private String[] items;
    private String showText;

    private OnSelectListener mOnSelectListener;

    public MenuView(Context context, String[] item ,int itemId) {
        super(context);
        init(context, item, itemId);
    }

    public void setItems(String[] item){
        items = item;
    }

    private void init(Context context, String[] item ,int itemId){
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_menulist, this,true);
        //setBackgroundDrawable(getResources().getDrawable(R.drawable.choosearea_bg));
        mListView = (ListView) findViewById(R.id.listView);
        items = item;
        adapter = new MenuAdapter(context, items ,itemId);
        adapter.setTextSize(17);
        mListView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MenuAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                if (mOnSelectListener != null) {
                    showText = items[position];
                    mOnSelectListener.getValue(items[position]);
                }
            }
        });
    }

    public String getShowText(){
        return showText;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        void getValue(String showText);
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }
}
