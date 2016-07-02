package com.cnst.wisdom.ui.view;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author jiangzuyun.
 * @see [相关类/方法]
 * @since [产品/模版版本]
 */
public interface AttenView<T> extends StateView
{
    /**
     * 数据刷新
     * @param data
     */
    void refreshData(T data);
}
