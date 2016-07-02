package com.cnst.wisdom.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cnst.wisdom.R;
import com.cnst.wisdom.model.bean.Course;
import com.cnst.wisdom.model.bean.Subject;
import com.cnst.wisdom.model.bean.Term;

import java.util.List;

/**
 * 筛选课程时弹出列表Adapter
 * 选择课程
 *
 * @author meilianbing.
 * @see [相关类/方法]
 * @since [产品/模版版本]
 */
public class GuidancePopAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater inflater;
    private int resId;

    private List<Object> data;

    private int selectedPos;

    public GuidancePopAdapter(Context context,List<Object> data,int resId){
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.data = data;
        this.resId = resId;

    }

    public void setSlectedPos(int selectedPos){
        this.selectedPos = selectedPos;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = inflater.inflate(resId,parent,false);
        }
        Object obj = data.get(position);
        if(obj instanceof Subject)
            ((TextView)convertView).setText(((Subject) obj).getName());
        else if(obj instanceof Term)
            ((TextView)convertView).setText(((Term) obj).getName());
        else if(obj instanceof Course)
            ((TextView)convertView).setText(((Course) obj).getName());
        if(position==selectedPos)
            convertView.setBackgroundResource(R.color.popup_selected);
        else
            convertView.setBackgroundResource(R.color.white);
        return convertView;
    }
}
