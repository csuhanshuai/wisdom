package com.cnst.wisdom.ui.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cnst.wisdom.R;
import com.cnst.wisdom.model.bean.TeachPlanBean;
import com.cnst.wisdom.ui.activity.TeachPlanCourse;

import java.util.List;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author jiangzuyun.
 * @see [相关类/方法]
 * @since [产品/模版版本]
 */
public class TeachModAd extends BaseAdapter implements View.OnClickListener
{
    private Context mContext;
    private List<TeachPlanBean> mList;
    private LayoutInflater mLayoutInflater;
//    private View curClickView;//当前选择修改未完成还是未完成的控件

    public TeachModAd(Context context, List<TeachPlanBean> list)
    {
        this.mContext = context;
        this.mList = list;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return mList!=null?mList.size():0;
    }

    @Override
    public Object getItem(int position)
    {
        return mList.get(position);
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
            convertView = mLayoutInflater.inflate(R.layout.item_teach_mod,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder)convertView.getTag();
        holder.clazzTv.setText(mList.get(position).getClazz().getName());
        holder.subjectTv.setText(mList.get(position).getSubject().getName());
        holder.periodsTv.setText(mList.get(position).getTerm().getName());
        holder.stateBt.setText(R.string.mof);
        holder.stateBt.setTag(position);
        holder.stateBt.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.teach_progress_state:
                int position = (Integer)v.getTag();
                mContext.startActivity(new Intent(mContext, TeachPlanCourse.class)
                .putExtra(TeachPlanCourse.class.getName(),mList.get(position)));
                break;
            default:
                break;
        }


    }

    private static class ViewHolder
    {
        private TextView clazzTv;
        private TextView subjectTv;
        private TextView periodsTv;
        private TextView stateBt;
        public ViewHolder(View convertView)
        {
            clazzTv = (TextView)convertView.findViewById(R.id.teach_progress_clazz);
            subjectTv = (TextView)convertView.findViewById(R.id.teach_progress_subject);
            periodsTv = (TextView)convertView.findViewById(R.id.teach_progress_periods);
            stateBt = (TextView)convertView.findViewById(R.id.teach_progress_state);
        }
    }
}
