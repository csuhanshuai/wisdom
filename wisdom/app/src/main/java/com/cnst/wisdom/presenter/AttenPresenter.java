package com.cnst.wisdom.presenter;

import android.content.Context;

import com.android.volley.VolleyError;
import com.cnst.wisdom.model.net.NetInterface;
import com.cnst.wisdom.model.net.NetResult;
import com.cnst.wisdom.model.net.VolleyManager;
import com.cnst.wisdom.ui.view.AttenView;

import java.util.Date;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author jiangzuyun.
 * @see [相关类/方法]
 * @since [产品/模版版本]
 */
public class AttenPresenter extends BasePresenter<AttenView>
{
    public AttenPresenter(Context context, AttenView view)
    {
        super(context, view);
    }

    public void getData(Date query,Object tag){
        mView.showLoadingView(true);
        VolleyManager.getInstance().getString(NetInterface.baidumeinv, null, tag, new NetResult<String>()
        {
            @Override
            public void onFailure(VolleyError error)
            {
                mView.showLoadingView(false);
                mView.showErrorView(true);
            }

            @Override
            public void onSucceed(String response)
            {
                mView.showLoadingView(false);
                mView.showSucceedView(true);
                mView.refreshData(response);
            }
        });
    }

    public void cancelNet(Object tag)
    {
        mView.showLoadingView(false);
        VolleyManager.getInstance().cancel(tag);
    }
}
