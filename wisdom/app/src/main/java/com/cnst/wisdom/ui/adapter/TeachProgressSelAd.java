package com.cnst.wisdom.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cnst.wisdom.R;

import java.util.List;


/**
 * 教学科目创建时弹出对话框界面Adapter
 * 选择教学科目
 *
 * @author jiangzuyun.
 * @see [相关类/方法]
 * @since [产品/模版版本]
 */
public class TeachProgressSelAd extends BaseAdapter
{
    private Context mContext;
    private List<String> names;
    private LayoutInflater mLayoutInflater;
    private int[] mStateDrawable;
    private int curPosition;

    public TeachProgressSelAd(Context context, List<String> names)
    {
        this.mContext = context;
        this.names = names;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mStateDrawable = new int[]{android.R.drawable.radiobutton_on_background,
                               android.R.drawable.radiobutton_off_background};
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
        return names!=null?names.size():0;
    }

    @Override
    public Object getItem(int position)
    {
        return names.get(position);
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
        stateBt.setText(names.get(position));
        if(position == curPosition)
        {
            stateBt.setCompoundDrawablesWithIntrinsicBounds(0,0,mStateDrawable[0],0);
        }else {
            stateBt.setCompoundDrawablesWithIntrinsicBounds(0,0,mStateDrawable[1],0);
        }
//        if(position==0)
//        {
//            convertView.setVisibility(View.GONE);
//        }

        return convertView;
    }
}
