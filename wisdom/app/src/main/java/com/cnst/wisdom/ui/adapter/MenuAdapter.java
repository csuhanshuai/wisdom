package com.cnst.wisdom.ui.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cnst.wisdom.R;

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
public class MenuAdapter extends ArrayAdapter<String>{

    private Context mContext;

    private List<String> mListData;
    private String[] mArrayData;

    private int selectedPosition = -1;
    private String selectedText = "";

    private float textSize;
    private View.OnClickListener onClickListener;
    private OnItemClickListener mOnItemClickListener;

    private int tvLayoutId;

    public MenuAdapter(Context context, List<String> listData, int itemId) {
        super(context, R.string.no_data,listData);
        mContext = context;
        mListData = listData;
        tvLayoutId = itemId;
        init();
    }

    public MenuAdapter(Context context, String[] arrayData, int itemId) {
        super(context, R.string.no_data,arrayData);
        mContext = context;
        mArrayData = arrayData;
        tvLayoutId = itemId;
        init();
    }

    /**
     * 菜单项点击监听
     */
    private void init(){
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = (Integer) v.getTag();
                setSelectedPosition(selectedPosition);
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, selectedPosition);
                }
            }
        };
    }

    /**
     * 设置选中的position
     */
    public void setSelectedPosition(int pos) {
        if (mListData != null && pos < mListData.size()) {
            selectedPosition = pos;
            selectedText = mListData.get(pos);
            //notifyDataSetChanged();
        } else if (mArrayData != null && pos < mArrayData.length) {
            selectedPosition = pos;
            selectedText = mArrayData[pos];
            //notifyDataSetChanged();
        }
    }

    /**
     * 获取选中的position
     */
    public int getSelectedPosition() {
        if (mArrayData != null && selectedPosition < mArrayData.length) {
            return selectedPosition;
        }
        if (mListData != null && selectedPosition < mListData.size()) {
            return selectedPosition;
        }
        return -1;
    }

    /**
     * 设置列表字体大小
     */
    public void setTextSize(float tSize) {
        textSize = tSize;
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;
    }

    /**
     * 重新定义菜单选项单击接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            textView = (TextView) LayoutInflater.from(mContext).inflate(tvLayoutId, parent, false);
        } else {
            textView = (TextView) convertView;
        }
        textView.setTag(position);
        String mString = "";
        if (mListData != null) {
            if (position < mListData.size()) {
                mString = mListData.get(position);
            }
        } else if (mArrayData != null) {
            if (position < mArrayData.length) {
                mString = mArrayData[position];
            }
        }
        if (mString.contains("不限"))
            textView.setText("不限");
        else
            textView.setText(mString);
        //textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        textView.setTextSize(textSize);
        textView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.choose_eara_item_selector));
        textView.setOnClickListener(onClickListener);
        return textView;
    }
}
