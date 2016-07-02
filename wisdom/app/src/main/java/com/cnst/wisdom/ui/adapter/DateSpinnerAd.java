package com.cnst.wisdom.ui.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cnst.wisdom.R;

import java.util.List;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author taoyuan.
 * @see [相关类/方法]
 * @since [产品/模版版本]
 */
public class DateSpinnerAd extends BaseAdapter
{
    private List<String> monthes;
    private LayoutInflater mInflater;
    private Context mContext;

    public DateSpinnerAd(List<String> monthes,Context context)
    {
        this.monthes = monthes;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @Override
    public int getCount()
    {
        return monthes.size();
    }

    @Override
    public Object getItem(int position)
    {
        return monthes.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if(convertView==null)
        {
            convertView = new TextView(mContext);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder)convertView.getTag();
        holder.mTextView.setText(monthes.get(position));
        return convertView;
    }
    private class ViewHolder
    {
        private TextView mTextView;

        public ViewHolder(View view)
        {
            mTextView = (TextView)view;
            mTextView.setTextSize(18);
            mTextView.setPadding(20, 20, 20, 10);
            mTextView.setGravity(Gravity.CENTER);
//            mTextView.setCompoundDrawablesWithIntrinsicBounds(0,0,android.R.drawable.arrow_down_float,0);
            mTextView.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        }
    }
}
