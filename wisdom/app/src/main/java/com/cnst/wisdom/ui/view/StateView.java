package com.cnst.wisdom.ui.view;

/**
 * Created on 2016/1/17.  by Jonas{https://github.com/mychoices}
 */

public  interface StateView extends BaseView{

    /**
     * 数据加载成功显示的界面
     * @param show
     */
    void showSucceedView(boolean show);

    /**
     * 数据为空 显示的界面
     * @param show
     */
    void showEmptyView(boolean show);

    /**
     * 网络错误显示的界面
     * @param show
     */
    void showErrorView(boolean show);

    /**
     * 正在加载。
     * @param show
     */
    void showLoadingView(boolean show);
}
