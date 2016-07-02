package com.cnst.wisdom.ui.fragment.downloadManager;

import android.view.View;
import android.widget.ListView;

import com.cnst.wisdom.BaseApplication;
import com.cnst.wisdom.R;
import com.cnst.wisdom.model.Constants;
import com.cnst.wisdom.model.bean.bdmeizhi;
import com.cnst.wisdom.ui.adapter.BaseListAdapter;
import com.cnst.wisdom.ui.adapter.ViewHolderHelper;
import com.cnst.wisdom.ui.base.BaseNetFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DownloadManager界面里的全部tab标签对应的fragment
 * 详细功能描述
 *
 * @author Huangyingde
 * @time 10:33
 * @see
 */
public class AllFragment extends BaseNetFragment
{
    private BaseListAdapter<bdmeizhi.DataEntity> mAdapter;
    private List<bdmeizhi.DataEntity> mRec_data          = new ArrayList<>();

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
        return map;
    }

    /**
     * 设置 请求 url地址 对象
     */
    @Override
    public String setRequestURL()
    {
        return Constants.baidumeinv;
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
        mAdapter = new BaseListAdapter<bdmeizhi.DataEntity>(BaseApplication.getContext(),R.layout.item_music) {
            @Override
            protected void convert(final ViewHolderHelper helper, bdmeizhi.DataEntity item)
            {
                helper.setText(R.id.tv_item_music, item.getAbs());
                helper.setImageFromUrl2(R.id.iv_item_music, item.getImage_url());
                helper.setClickListener(R.id.btn_item_music, new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {

                    }
                });
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
        bdmeizhi bdmeizhi = null;
        bdmeizhi = gson.fromJson(response, com.cnst.wisdom.model.bean.bdmeizhi.class);
        mRec_data = new ArrayList<bdmeizhi.DataEntity>();
        List<String> url_maps = new ArrayList<>();
        List<bdmeizhi.DataEntity> data = bdmeizhi.getData();
        for(int i = 0; i < data.size()-1; i++)
        {
            mRec_data.add(data.get(i));
        }
        mAdapter.refreshData(mRec_data);

    }


}
