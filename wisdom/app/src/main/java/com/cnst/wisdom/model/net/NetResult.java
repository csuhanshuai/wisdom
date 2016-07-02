package com.cnst.wisdom.model.net;

import com.android.volley.VolleyError;

/**
 *  网络回调接口
 * @author jiangzuyun.
 * @see [onFailure，onSucceed]
 * @since [产品/模版版本]
 */
public abstract class NetResult<T>
{
    public abstract void onFailure(VolleyError error);

    public abstract void onSucceed(T response);
}
