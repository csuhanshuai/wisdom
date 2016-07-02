package com.cnst.wisdom.ui.fragment.Material;

import com.cnst.wisdom.ui.base.BaseNetFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Material界面中创建fragment的工厂类
 * 主要创建每一个tabs所对应的fragment
 *
 * @author Huangyingde
 * @time 10:11
 * @see
 */
public class MaterialFragmentFactory
{
    public static final int FRAGMENT_MUSIC   = 0;
    public static final int FRAGMENT_PLAN    = 1;
    public static final int FRAGMENT_VIDEO   = 2;
    public static final int FRAGMENT_PROGRAM = 3;
    public static final int FRAGMENT_PPT     = 4;
    public static final int FRAGMENT_DRAW    = 5;

    /**
     *  对fragment做内存缓存，避免重复创建fragment
     */
    private Map<Integer,BaseNetFragment> mCacheFragmentMaps = new HashMap<Integer,BaseNetFragment>();

    /**
     * 根据传入的position创建对应的fragment
     * @param position
     *        tabs对应的fragment的位置
     * @return
     *        position对应位置上的fragment
     */
    public BaseNetFragment createFragment(int position)
    {
        BaseNetFragment fragment = null;
        if(mCacheFragmentMaps.containsKey(position))
        {
            fragment = mCacheFragmentMaps.get(position);
            return fragment;
        }

        switch(position)
        {
            case FRAGMENT_MUSIC:
                fragment = new MusicFragment();
                break;
            case FRAGMENT_PLAN:
                fragment = new PlanFragment();
                break;
            case FRAGMENT_VIDEO:
                fragment = new VideoFragment();
                break;
            case FRAGMENT_PROGRAM:
                fragment = new ProgramFragment();
                break;
            case FRAGMENT_PPT:
                fragment = new PPTFragment();
                break;
            case FRAGMENT_DRAW:
                fragment = new DrawFragment();
                break;
            default:
                break;
        }
        // 保存对应的引用
        mCacheFragmentMaps.put(position,fragment);
        return fragment;
    }



}
