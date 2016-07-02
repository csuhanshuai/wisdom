package com.cnst.wisdom.ui.adapter;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cnst.wisdom.R;
import com.cnst.wisdom.model.bean.TeachPlanBean;

import java.util.List;

/**
 * 教学计划的教学进度ListView Item界面
 * 通过此页面弹出框来修改教学进度
 *
 * @author taoyuan.
 * @since 1.0
 */
public class TeachProgressAd extends BaseAdapter implements View.OnClickListener
{
    private Context mContext;
    private List<TeachPlanBean> mList;
    private LayoutInflater mLayoutInflater;
    private Dialog mDialog;
    private TextView dialogTitle;
    private TextView curClickView;//当前选择修改未完成还是未完成的控件

    public TeachProgressAd(Context context, List<TeachPlanBean> list)
    {
        this.mContext = context;
        this.mList = list;
        this.mLayoutInflater = LayoutInflater.from(context);
        mDialog = new Dialog(context, R.style.no_title_dialog);
        mDialog.setContentView(R.layout.dialog_sel_subject_state);
        mDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog);
        dialogTitle = (TextView)mDialog.findViewById(R.id.dialog_title);
        mDialog.findViewById(R.id.cancel).setOnClickListener(this);
        mDialog.findViewById(R.id.confirm).setOnClickListener(this);
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
            convertView = mLayoutInflater.inflate(R.layout.item_teach_progress,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder)convertView.getTag();
        holder.clazzTv.setText(mList.get(position).getClazz().getName());
        holder.subjectTv.setText(mList.get(position).getSubject().getName());
        holder.periodsTv.setText(mList.get(position).getTerm().getName());
        holder.nameTv.setText(position+1+"."+mList.get(position).getCourse().getName());
        if(mList.get(position).getState()==0)
        {
            holder.stateBt.setText(R.string.un_complete);
            holder.stateBt.setBackgroundResource(R.mipmap.complete);
            holder.stateBt.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        }else {
            holder.stateBt.setText(R.string.complete);
            holder.stateBt.setBackgroundResource(R.drawable.bg_button);
            holder.stateBt.setTextColor(mContext.getResources().getColor(R.color.tip_text));
        }

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
                if(v.getTag() instanceof Integer)
                {
                    curClickView = (TextView)v;
                    int state = mList.get((int)curClickView.getTag()).getState();
                    if(state==0)
                    {
                        dialogTitle.setText(R.string.change_complete);
                    }else {
                        dialogTitle.setText(R.string.change_un_complete);
                    }
                    mDialog.show();
                }
                break;
            case R.id.cancel:
                mDialog.dismiss();
                break;
            case R.id.confirm:
                int position = (int)curClickView.getTag();
                int state = mList.get(position).getState();
                TeachPlanBean bean = mList.get(position);
                if(state==0)
                {
                    bean.setState(1);
                    curClickView.setText(R.string.complete);
                    curClickView.setBackgroundResource(R.drawable.bg_button);
                    curClickView.setTextColor(mContext.getResources().getColor(R.color.tip_text));
                }else {
                    bean.setState(0);
                    curClickView.setText(R.string.un_complete);
                    curClickView.setBackgroundResource(R.mipmap.complete);
                    curClickView.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                }
                mDialog.dismiss();
            default:
                break;
        }


    }

    private static class ViewHolder
    {
        private TextView clazzTv;
        private TextView subjectTv;
        private TextView periodsTv;
        private TextView nameTv;
        private TextView stateBt;
        public ViewHolder(View convertView)
        {
            clazzTv = (TextView)convertView.findViewById(R.id.teach_progress_clazz);
            subjectTv = (TextView)convertView.findViewById(R.id.teach_progress_subject);
            periodsTv = (TextView)convertView.findViewById(R.id.teach_progress_periods);
            nameTv = (TextView)convertView.findViewById(R.id.teach_progress_name);
            stateBt = (TextView)convertView.findViewById(R.id.teach_progress_state);
        }
    }
}
