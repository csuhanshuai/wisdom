package com.cnst.wisdom.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * 点击时显示明暗变化(滤镜效果)的ImageView
 */
public class SelectorImageView extends ImageView
{

    public SelectorImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                setFilter();
                break;
            case MotionEvent.ACTION_MOVE:
                setFilter();
                break;
            case MotionEvent.ACTION_UP:
                removeFilter();
                break;
        }
        return super.dispatchTouchEvent(event);
    }


    /**
     * 设置滤镜
     */

    private void setFilter()
    {
        //先获取设置的src图片
        Drawable drawable = getDrawable();
        //当src图片为Null，获取背景图片
        if(drawable == null)
        {
            drawable = getBackground();
        }
        if(drawable != null)
        {
            //设置滤镜
            drawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            ;
        }
    }

    /**
     * 清除滤镜
     */
    private void removeFilter()
    {
        //先获取设置的src图片
        Drawable drawable = getDrawable();
        //当src图片为Null，获取背景图片
        if(drawable == null)
        {
            drawable = getBackground();
        }
        if(drawable != null)
        {
            //清除滤镜
            drawable.clearColorFilter();
        }
    }
}
