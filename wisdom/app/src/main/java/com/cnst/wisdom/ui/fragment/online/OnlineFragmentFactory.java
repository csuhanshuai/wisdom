package com.cnst.wisdom.ui.fragment.online;

import android.support.v4.app.Fragment;

import com.cnst.wisdom.ui.fragment.Material.MusicFragment;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author jiangzuyun.
 * @see [相关类/方法]
 * @since [产品/模版版本]
 */
public class OnlineFragmentFactory
{
    public static final int FRAGMENT_TEACH   = 0;
    public static final int FRAGMENT_MANAGE   = 1;
    public static final int FRAGMENT_COMMUNICATE   = 2;
    public static final int FRAGMENT_COURSE   = 3;


    /**
     *  对fragment做内存缓存，避免重复创建fragment
     */
//    private Map<Integer,BaseNetFragment> mCacheFragmentMaps = new HashMap<Integer,BaseNetFragment>();

    /**
     * 根据传入的position创建对应的fragment
     * @param position
     *        tabs对应的fragment的位置
     * @return
     *        position对应位置上的fragment
     */
    public static Fragment createFragment(int position)
    {
        Fragment fragment = null;
//        if(mCacheFragmentMaps.containsKey(position))
//        {
//            fragment = mCacheFragmentMaps.get(position);
//            return fragment;
//        }

        switch(position)
        {
            case FRAGMENT_TEACH:
                fragment = new TeachFragment();
                break;
            case FRAGMENT_MANAGE:
                fragment = new ManageFragmet();
                break;
            case FRAGMENT_COMMUNICATE:
                fragment = new TeachFragment();
                break;
            case FRAGMENT_COURSE:
                fragment = new TeachFragment();
                break;
            default:
                break;
        }
        return fragment;
    }

}
