package com.cnst.wisdom.ui.adapter;


import android.content.Context;
import android.graphics.drawable.Drawable;
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
public class TeachPlanDialogSelAd extends BaseAdapter
{
    private Context mContext;
    private List<Object> mObjects;
    private LayoutInflater mLayoutInflater;
    private int curPosition = -1;
    private Drawable selDrawable;
    private Drawable unSelDrawable;

    public TeachPlanDialogSelAd(Context context, List<Object> mObjects, Drawable selDrawable, Drawable unSelDrawable)
    {
        this.mContext = context;
        this.mObjects = mObjects;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.selDrawable = selDrawable;
        this.unSelDrawable = unSelDrawable;
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

        convertView = mLayoutInflater.inflate(R.layout.item_subject_name,parent,false);
        TextView stateBt = (TextView)convertView;
        String text = "";
        Object object = mObjects.get(position);
        if(object instanceof Clazz){
            text = ((Clazz)object).getName();
        }else if(object instanceof Subject){
            text = ((Subject)object).getName();
        }else if(object instanceof Term){
            text = ((Term)object).getName();
        }
        if(position==curPosition){
            stateBt.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.radiobutton_on_background, 0);
        }
        stateBt.setText(text);
        return convertView;
    }
}
