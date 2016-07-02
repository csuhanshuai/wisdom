package com.cnst.wisdom.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cnst.wisdom.R;

/**
 * Created by Jonas on 2016/1/20.
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author meilianbing.
 * @see [相关类/方法]
 * @since [产品/模版版本]
 */
public class GuidanceAdapter extends BaseAdapter{

    private String[] mResultData;
    private Context mContext;
    private final LayoutInflater inflater;

    public GuidanceAdapter(String[] resultData, Context context){
        mResultData = resultData;
        mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = inflater.inflate(R.layout.item_listview_guidance,null);
        }
        TextView tvTitle1 = (TextView) convertView.findViewById(R.id.tvTitle1);
        return convertView;
    }
}
