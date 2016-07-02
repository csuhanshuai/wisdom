package com.cnst.wisdom.ui.fragment.online;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.cnst.wisdom.R;
import com.cnst.wisdom.ui.activity.LearnDetail;

/**
 * <教学技术>
 * <展示教学技术相关详细信息>
 *
 * @author pengjingag.
 * @see [相关类/方法]
 * @since [产品/模版版本]
 */
public class TeachFragment extends Fragment
{

    private ListView mLvOnlineTeach;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.lv_online_teach,null);
        mLvOnlineTeach = (ListView)view.findViewById(R.id.lv_online_teach);
        mLvOnlineTeach.setAdapter(new MyAdapter());
        mLvOnlineTeach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getActivity(), LearnDetail.class);
                startActivity(intent);
            }
        });
        return mLvOnlineTeach;
    }


    private class MyAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return 20;
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            convertView = View.inflate(getActivity(), R.layout.item_online, null);
            return convertView;
        }
    }
}
