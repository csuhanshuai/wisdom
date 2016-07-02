package com.cnst.wisdom.ui.materialpager;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cnst.wisdom.R;
import com.cnst.wisdom.model.Constants;
import com.cnst.wisdom.model.bean.MaterialPagerBean;
import com.cnst.wisdom.model.net.NetResult;
import com.cnst.wisdom.model.net.VolleyManager;
import com.cnst.wisdom.ui.adapter.BaseListAdapter;
import com.cnst.wisdom.utills.SPUtills;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 资源页
 * <功能详细描述>
 *
 * @author tangbinfeng
 * @see [相关类/方法]
 * @since [产品/模板版本]
 */

public class MaterialPager
{
    private BaseListAdapter<MaterialPagerBean.MaterialPager> mAdapter;//适配器测试
    private Activity mActivity;
    private ListView mListView;
    public View mRootView;
    private String mCode;//素材分类code
    private VolleyManager volleyManager = VolleyManager.getInstance();
    private ArrayList<MaterialPagerBean.MaterialPager> mMaterialPagerList;

    public MaterialPager(Activity activity, String code)
    {
        this.mActivity = activity;
        mRootView =getRootView();
        this.mCode = code;
    }

    /**
     * 获取视图
     *
     * @return
     */
    public View getRootView()
    {
        View view = View.inflate(mActivity, R.layout.fragment_music, null);
        mListView = (ListView)view.findViewById(R.id.listView);
        return view;
    }

    /**
     * 初始化数据
     */
    public void initData()
    {
        initDataFromService();
    }

    /**
     * 从网络上获取数据
     */
    private void initDataFromService()
    {
        //网络请求参数
        HashMap map = new HashMap();
        map.put("dictCode", mCode);
        map.put("pageNum", "1");
        map.put("pageSize", "20");
        volleyManager.getString(SPUtills.get(mActivity, Constants.GET_SERVER, "").toString()+Constants
                .QUERY_MATERIAL, map, "Material", new NetResult<String>()
        {
            @Override
            public void onFailure(VolleyError error)
            {

            }

            @Override
            public void onSucceed(String response)
            {
                Gson gson = new Gson();
                MaterialPagerBean materialPagerBean = null;
                materialPagerBean = gson.fromJson(response, MaterialPagerBean.class);
                if(materialPagerBean != null)
                {
                    Log.e("Material", materialPagerBean.toString());
                    int code = materialPagerBean.code;
                    switch(code)
                    {
                        case Constants.STATUS_ARGUMENT_ERROR:
                            break;
                        case Constants.STATUS_DATA_NOTFOUND:
                            break;
                        case Constants.STATUS_ILLEGAL:
                            break;
                        case Constants.STATUS_SERVER_EXCEPTION:
                            break;
                        case Constants.STATUS_SUCCESS:

                            mMaterialPagerList = materialPagerBean.data;
                            if(mMaterialPagerList != null)
                            {
                                Log.e("Material", mMaterialPagerList.toString());
                                initServiceData();
                            }
                            break;
                        case Constants.STATUS_TIMEOUT:
                            break;
                    }

                }
            }
        });
    }

    /**
     * 初始化从网络获取的数据
     */
    private void initServiceData()
    {
        MaterialPagerAdapter adapter = new MaterialPagerAdapter();
        mListView.setAdapter(adapter);
    }

    /**
     * 适配器
     */
    class MaterialPagerAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            Log.e("Material", String.valueOf(mMaterialPagerList.size()));
            return mMaterialPagerList.size();
        }

        @Override
        public MaterialPagerBean.MaterialPager getItem(int position)
        {
            return mMaterialPagerList.get(position);
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
            if(convertView == null)
            {
                holder = new ViewHolder();
                convertView = View.inflate(mActivity, R.layout.item_music, null);
                holder.iv_item_music = (ImageView)convertView.findViewById(R.id.iv_item_music);
                holder.tv_item_music = (TextView)convertView.findViewById(R.id.tv_item_music);
                holder.btn_item_music = (Button)convertView.findViewById(R.id.btn_item_music);
                convertView.setTag(holder);
            }else
            {
                holder = (ViewHolder)convertView.getTag();
            }
            MaterialPagerBean.MaterialPager item = getItem(position);
            holder.tv_item_music.setText(item.title);
            if(Boolean.valueOf(item.isVideo)==true){
                Glide.with(mActivity).load(item.thumbnailPath).placeholder(R.drawable.ic_empty_page).error(R.drawable.ic_empty_page)
                        .centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .bitmapTransform(new CropCircleTransformation(mActivity)).into(holder.iv_item_music);
            }else {
                Glide.with(mActivity).load(Constants.SERVER+item.thumbnailPath).placeholder(R.drawable
                        .ic_empty_page).error(R
                        .drawable.ic_empty_page)
                        .centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .bitmapTransform(new CropCircleTransformation(mActivity)).into(holder.iv_item_music);
            }


            return convertView;
        }
    }

    public static class ViewHolder
    {
        public ImageView iv_item_music;
        public TextView tv_item_music;
        public Button btn_item_music;
    }
}
