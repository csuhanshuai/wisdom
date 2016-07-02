package com.cnst.wisdom.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cnst.wisdom.R;
import com.cnst.wisdom.model.bean.Clazz;
import com.cnst.wisdom.model.bean.Subject;
import com.cnst.wisdom.model.bean.Term;

import java.util.List;


/**
 * 教学科目创建时弹出对话框界面Adapter
 * 选择教学科目
 *
 * @author jiangzuyun.
 * @see [相关类/方法]
 * @since [产品/模版版本]
 */
public class TeachPlanPopSelAd extends BaseAdapter
{
    private Context mContext;
    private List<Object> mObjects;
    private LayoutInflater mLayoutInflater;
    private int curPosition;

    public TeachPlanPopSelAd(Context context, List<Object> mObjects)
    {
        this.mContext = context;
        this.mObjects = mObjects;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public int getCurPosition()
    {
        return curPosition;
    }

    public void setCurPosition(int curPosition)
    {
        this.curPosition = curPosition;
    }

    @Override
    public int getCount()
    {
        return mObjects!=null?mObjects.size():0;
    }

    @Override
    public Object getItem(int position)
    {
        return mObjects.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        convertView = mLayoutInflater.inflate(R.layout.pop_list_item,parent,false);
        TextView stateBt = (TextView)convertView.findViewById(R.id.text);
        String text = "";
        Object object = mObjects.get(position);
        if(object instanceof Clazz){
            text = ((Clazz)object).getName();
        }else if(object instanceof Subject){
            text = ((Subject)object).getName();
        }else if(object instanceof Term){
            text = ((Term)object).getName();
        }
        stateBt.setText(text);
        if(position==curPosition){
            convertView.setBackgroundResource(R.color.onClick);
        }
        return convertView;
    }
}
