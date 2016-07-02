package com.cnst.wisdom.presenter;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.cnst.wisdom.model.net.NetResult;
import com.cnst.wisdom.ui.view.MainStateView;
import com.cnst.wisdom.ui.view.PageState;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created on 2016/1/17.  by Jonas{https://github.com/mychoices}
 */

public class StatePresenter extends BasePresenter<MainStateView> {

    public PageState CState = PageState.UNLOADED;

    public StatePresenter(Context context, MainStateView view){
        super(context, view);
    }

    public void showSafePagerView(PageState state){
        CState = state;
        showPagerView(state);
    }

    /**
     * 加载数据前 调用showPagerView(STATE_LOADING); 当加载完数据之后 调用以显示结果界面 错3 空4 ok5
     */
    protected void showPagerView(PageState mState){

        mView.showLoadingView(mState == PageState.LOADING);
        mView.showEmptyView(mState == PageState.EMPTY);
        mView.showErrorView(mState == PageState.ERROR);
        mView.showSucceedView(mState == PageState.SUCCEED);
    }

    public void VolleyShow(){
        showSafePagerView(CState);
        volleyNetData();
    }

    protected void volleyNetData(){
        if(Request.Method.GET == mView.setRequestMethod()) {
            mVolleyManager.getString(mView.setRequestURL(), mView.setparams(), mContext, new StateResult() {

                @Override
                public void onSucceed(String modle){
                    if(afterNet(modle) == PageState.CONTINUE) {
                        CState = PageState.LOADING;
                    }else {
                        //更具返回的数据内容显示相应界面
                        CState = checkNetDataResult(modle);
                    }
                    showSafePagerView(CState);
                    if(checkNetDataResult(modle) == PageState.SUCCEED) {
                        mView.onNetSucceed(modle);
                    }
                }
            });
        }else {
            mVolleyManager.postString(mView.setRequestURL(), mView.setparams(), mContext, new StateResult() {

                @Override
                public void onSucceed(String modle){
                    if(afterNet(modle) == PageState.CONTINUE) {
                        CState = PageState.LOADING;
                    }else {
                        //更具返回的数据内容显示相应界面
                        CState = checkNetDataResult(modle);
                    }
                    showSafePagerView(CState);
                    if(checkNetDataResult(modle) == PageState.SUCCEED) {
                        mView.onNetSucceed(modle);
                    }
                }
            });
        }
        //        VolleyManager.getInstance().postJsonObject(setRequestURL(), setparams(), this, new StateResult() {
        //
        //            @Override
        //            public <T> void onSucceed(T modle){
        //                //更具返回的数据内容显示相应界面
        //                if(onNetSucceed(modle) != STATE_LOADING) {
        //                    showSafePagerView(checkNetDataResult(modle));
        //                }
        //            }
        //
        //        });
    }

    protected <T> PageState afterNet(T modle){
        return checkNetDataResult(modle);
    }

    /**
     * 检查服务器返回的数据情况 返回 STATE_ERROR STATE_EMPTY STATE_SUCCEED
     */
    public PageState checkNetDataResult(Object obj){
        if(obj == null) {
            return CState = PageState.ERROR;
        }else if(obj instanceof List) {
            List list = (List)obj;
            if(list.size() == 0) {
                return CState = PageState.EMPTY;
            }
        }
        return CState = PageState.SUCCEED;
    }

    public abstract class StateResult extends NetResult<String> {
        @Override
        public void onFailure(VolleyError error){
            Logger.e("获取网络数据失败。。");
            showSafePagerView(PageState.ERROR);
        }
    }

}
