package com.cnst.wisdom.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 *  listview grideview 通用的 适配器
 * @author jiangzuyun.
 * @see  [ convert，ViewHolderHelper有很多方法]
 * @since [产品/模版版本]
 */
public abstract class BaseListAdapter<T> extends BaseAdapter
{

    private Context mContext;
    protected List<T> mData = new ArrayList<>();
    /**
     * 很多时候ListView的cell样式不止一种
     * listview中 不同的布局id
     */
    protected int[] mLayoutResArrays;

    public BaseListAdapter(Context context, int... layoutResArrays)
    {
        mLayoutResArrays = layoutResArrays;
        mContext = context;
    }

      public BaseListAdapter(Context context, List data ,int... layoutResArrays)
    {
        mData = data;
        mLayoutResArrays = layoutResArrays;
        mContext = context;
    }

    public void setData(List<T> data)
    {
        this.mData = data;
        this.notifyDataSetChanged();
    }


    public void addDataAll(List<T> data)
    {
        if(data != null)
        {
            this.mData.addAll(data);
        }
        this.notifyDataSetChanged();
    }


    public void refreshData(List<T> data)
    {
        if(data != null)
        {
            mData.clear();
            this.mData.addAll(data);
        }else
        {
            mData = data;
        }
        this.notifyDataSetChanged();
    }

    public void addData(T data)
    {
        if(null != mData)
        {
            this.mData.add(data);
            this.notifyDataSetChanged();
        }
    }

    public ArrayList<T> getAllData()
    {
        return (ArrayList<T>)this.mData;
    }

    @Override
    public int getCount()
    {
        if(null == this.mData)
        {
            return 0;
        }
        return this.mData.size();
    }

    @Override
    public T getItem(int position)
    {
        if(position>this.mData.size())
        {
            return null;
        }
        return mData.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public int getViewTypeCount()
    {
        return mLayoutResArrays == null ? 1 : mLayoutResArrays.length;
    }

    /**
     * 布局类型有多种时 需要重写该方法
     */
    public int getItemViewType(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolderHelper helper = getAdapterHelper(position, convertView, parent);
        T item = getItem(position);
        convert(helper, item);
        return helper.getRootView();
    }

    protected ViewHolderHelper getAdapterHelper(int position, View convertView, ViewGroup parent)
    {
        return ViewHolderHelper.get(mContext, convertView, parent, mLayoutResArrays[0], position);
    }

    protected abstract void convert(ViewHolderHelper helper, T item);

}
