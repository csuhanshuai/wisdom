package com.cnst.wisdom.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.cnst.wisdom.BaseApplication;
import com.cnst.wisdom.R;
import com.cnst.wisdom.model.net.VolleyManager;
import com.cnst.wisdom.presenter.StatePresenter;
import com.cnst.wisdom.ui.view.MainStateView;
import com.cnst.wisdom.ui.view.PageState;

/**
 * 基类 fragment 自动发起网络请求 子类实现方法 setparams //请求参数 setRequestURL  请求地址 createSucceedView   预期加载页面 onNetSucceed
 * 网络返回的数据
 *
 * @author jiangzuyun.
 * @see [setparams，setRequestURL，createSucceedView，onNetSucceed]
 * @since [产品/模版版本]
 */
public abstract class BaseNetFragment extends BaseFragment implements MainStateView,View.OnClickListener {

  public Gson gson = new Gson();
  private RelativeLayout rlcontent;
  protected View mView;

  public RelativeLayout mLoadingView;//转圈的view
  private View mErrorView;//错误的view
  private View mEmptyView;//空的view
  private View mSucceedView;//成功的view

  private RelativeLayout rl_head_content;
  public StatePresenter mStatePresenter;
  private RelativeLayout mHead;

  @Override
  protected void initPresenter(){
    if(null == mStatePresenter) {
      mStatePresenter = new StatePresenter(BaseApplication.getContext(), this);
    }
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater,
                           @Nullable ViewGroup container, @Nullable Bundle savedInstanceState
  )
  {
    initPresenter();
    mView = inflater.inflate(R.layout.base_fm, null);
    rlcontent = (RelativeLayout)mView.findViewById(R.id.rl_content);
    rl_head_content = (RelativeLayout)mView.findViewById(R.id.rl_temp_content);
    mLoadingView = (RelativeLayout)mView.findViewById(R.id.rl_state_loading);

    addHeadContent(inflater, rl_head_content);
    mView.findViewById(R.id.head_back_action).setOnClickListener(this);

    //子类设置 的title
    String title = setHeadTitle();
    if(null != title)
    {
      ( (TextView)mView.findViewById(R.id.head_text) ).setText(title);
    }
    mHead = (RelativeLayout)mView.findViewById(R.id.in_head_title);

    if(!changeHead(mHead))
    {
      initHead();
    }
    if(setSuccViewShowFirse())
    {
      mSucceedView = createSucceedView();
      if(null != mSucceedView)
      {
        rlcontent.addView(mSucceedView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT));
      }
    }
    if(PageState.LOADING == mStatePresenter.CState)
    {
      mStatePresenter.VolleyShow();
    }
    return mView;
  }

  //修改title头部据
  protected boolean changeHead(RelativeLayout head){
    return false;
  }

  private void initHead()
  {
    //设置 title是否可见
    mHead.setVisibility(!setHeadIsVisibility() ? View.GONE : View.VISIBLE);
  }

  /**
   * 隐藏 head 复写该方法
   */
  public boolean setHeadIsVisibility() {
    return true;
  }

  /**
   * 设置 head的title
   */
  protected abstract String setHeadTitle();


  /**
   * 在head下 添加 自定义布局
   */
  protected void addHeadContent(LayoutInflater inflater, RelativeLayout rl_head_content) {
  }

  /**
   * 设置加载数据后的界面 优先显示  默认返回false 拿到数据后在显示
   */
  public boolean setSuccViewShowFirse() {
    return false;
  }


  protected View createEmptyView() {
    return BaseApplication.inflate(R.layout.loading_page_empty);
  }

  protected View createErrorView() {
    View view = BaseApplication.inflate(R.layout.loading_page_error);
    Button retry = (Button) view.findViewById(R.id.page_bt);
    retry.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mStatePresenter.CState = PageState.LOADING;
        mStatePresenter.VolleyShow();
      }
    });
    return view;
  }

  /**
   * @param isVisibleToUser
   */
  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if (isVisibleToUser && mStatePresenter.CState == PageState.UNLOADED) {
      mStatePresenter.CState = PageState.LOADING;
      if (null != rlcontent) {
        //开始加载数据
        mStatePresenter.VolleyShow();
      }
    }
  }


  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.head_back_action) {
      if (getActivity() != null) {
        getActivity().finish();
      }
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    //初始化 加载状态
    VolleyManager.getInstance().cancel(BaseApplication.getContext());
  }

  @Override
  public void showSucceedView(boolean show){
    if (setSuccViewShowFirse()) {//先加载布局
      mSucceedView
              .setVisibility(mStatePresenter.CState == PageState.LOADING || show ? View.VISIBLE : View.INVISIBLE);
    } else {//先加载数据
      if (null != mSucceedView) {
        mSucceedView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
      } else if (show) {
        mSucceedView = createSucceedView();
        if (null != mSucceedView) {
          rlcontent.addView(mSucceedView,
                  new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                          RelativeLayout.LayoutParams.MATCH_PARENT));
        }
      }
    }
  }

  @Override
  public void showEmptyView(boolean show){
    //数据为空界面
    if (null != mEmptyView) {
      mEmptyView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    } else if (show) {
      mEmptyView = createEmptyView();
      if (null != mEmptyView) {
        rlcontent.addView(mEmptyView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));
      }
    }
  }

  @Override
  public void showErrorView(boolean show){
    //网络错误界面
    if (null != mErrorView) {
      mErrorView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    } else if (show) {
      mErrorView = createErrorView();
      if (null != mErrorView) {
        rlcontent.addView(mErrorView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));
      }
    }
  }

  @Override
  public void showLoadingView(boolean show){
    //加载状态
    mLoadingView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
  }

  @Override
  public int setRequestMethod(){
    return Request.Method.GET;
  }
}
