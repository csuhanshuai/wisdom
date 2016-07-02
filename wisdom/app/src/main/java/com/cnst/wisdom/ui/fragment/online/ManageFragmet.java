package com.cnst.wisdom.ui.fragment.online;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cnst.wisdom.BaseApplication;
import com.cnst.wisdom.R;
import com.cnst.wisdom.model.Constants;
import com.cnst.wisdom.model.bean.bdmeizhi;
import com.cnst.wisdom.ui.activity.LearnDetail;
import com.cnst.wisdom.ui.adapter.BaseListAdapter;
import com.cnst.wisdom.ui.adapter.ViewHolderHelper;
import com.cnst.wisdom.ui.base.BaseNetFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <班务管理>
 * <功能详细描述>
 *
 * @author pengjingnag.
 * @see [相关类/方法]
 * @since [产品/模版版本]
 */
public class ManageFragmet extends BaseNetFragment
{

    private BaseListAdapter<bdmeizhi.DataEntity> mAdapter;
    private ArrayList<bdmeizhi.DataEntity> mRec_data;

    @Override
    protected String setHeadTitle()
    {
        return null;
    }

    @Override
    public boolean setHeadIsVisibility()
    {
        return false;
    }

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

    @Override
    public String setRequestURL()
    {
        return Constants.baidumeinv;
    }

    @Override
    public View createSucceedView()
    {
        /**
         * 模拟数据
         */
        View rootView = View.inflate(BaseApplication.getContext(), R.layout.lv_online_teach, null);
        ListView listView = (ListView)rootView.findViewById(R.id.lv_online_teach);
        mAdapter = new BaseListAdapter<bdmeizhi.DataEntity>(BaseApplication.getContext(), R.layout.item_online)
        {
            @Override
            protected void convert(final ViewHolderHelper helper, bdmeizhi.DataEntity item)
            {
                helper.setText(R.id.tv_item, item.getAbs());
                helper.setImageFromUrl2(R.id.iv_item, item.getImage_url());
            }


        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                // TODO
                Intent intent = new Intent(getActivity(), LearnDetail.class);
                startActivity(intent);
            }
        });
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
        List<bdmeizhi.DataEntity> data = bdmeizhi.getData();
        for(int i = 0; i < data.size()-1; i++)
        {
            mRec_data.add(data.get(i));
        }
        mAdapter.refreshData(mRec_data);

    }


}
