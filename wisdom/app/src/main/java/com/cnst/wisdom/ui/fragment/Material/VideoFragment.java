package com.cnst.wisdom.ui.fragment.Material;

import android.view.View;
import android.widget.ListView;

import com.cnst.wisdom.BaseApplication;
import com.cnst.wisdom.R;
import com.cnst.wisdom.model.bean.videod;
import com.cnst.wisdom.ui.adapter.BaseListAdapter;
import com.cnst.wisdom.ui.adapter.ViewHolderHelper;
import com.cnst.wisdom.ui.base.BaseNetFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简述
 * 详细功能描述
 *
 * @author lenovo
 * @time 10:33
 * @see
 */
public class VideoFragment extends BaseNetFragment
{


    private BaseListAdapter<videod.DataEntity> mAdapter;
    private List<videod.DataEntity> mRec_data = new ArrayList<>();

    /**
     * 设置 head的title
     */
    @Override
    protected String setHeadTitle()
    {
        return null;
    }

    /**
     * 设置头部不可见
     * @return
     */
    @Override
    public boolean setHeadIsVisibility()
    {
        return false;
    }

    /**
     * 网络请求参数
     */
    @Override
    public Map setparams()
    {
        HashMap map = new HashMap();
        map.put("rn", "6");
        map.put("tag1", "美女");
        map.put("tag2", "可爱");
        map.put("pn", "6");
        return null;
    }

    /**
     * 设置 请求 url地址 对象
     */
    @Override
    public String setRequestURL()
    {
        return "http://192.168.11.87:8080/school//app/queryDictList_app.kq?dictCode=zysc";
    }

    /**
     * 拿到数据之前 加载界面 setSuccViewShowFirse 返回true 则优先于 onNetSucceed触发
     */
    @Override
    public View createSucceedView()
    {
        /**
         * 模拟数据
         */
        View rootView = View.inflate(BaseApplication.getContext(), R.layout.fragment_music,null);
        ListView listView = (ListView)rootView.findViewById(R.id.listView);
        mAdapter = new BaseListAdapter<videod.DataEntity>(BaseApplication.getContext(),R.layout.item_music) {
            @Override
            protected void convert(ViewHolderHelper helper, videod.DataEntity item)
            {
                helper.setText(R.id.tv_item_music,item.getName());
//                helper.setImageFromUrl2(R.id.iv_item_music, item.getImage_url());
            }

        };

        listView.setAdapter(mAdapter);

        return rootView;
    }

    /**
     * 拿到数据之后 解析数据 填充界面
     *
     * @param response
     */
    @Override
    public void onNetSucceed(String response)
    {

        Gson gson = new Gson();
        videod bdmeizhi = null;
        bdmeizhi = gson.fromJson((String)response, videod.class);

        mAdapter.refreshData(mRec_data);

    }
}
