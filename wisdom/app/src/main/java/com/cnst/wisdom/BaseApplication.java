package com.cnst.wisdom;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.multidex.MultiDexApplication;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cnst.wisdom.model.Constants;
import com.cnst.wisdom.ui.activity.GuidanceDetailActivity;
import com.cnst.wisdom.utills.SPUtills;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.youku.player.YoukuPlayerBaseConfiguration;

/**
 * 提供 一些 获取资源的便捷方法
 * @author jiangzuyun.
 * @see
 * @since [产品/模版版本]
 */
public class BaseApplication extends MultiDexApplication
{
    //初始化 主线程中的上下文
    private static BaseApplication mContext = null;

    /**
     * 优酷播放器配置
     */
    private static YoukuPlayerBaseConfiguration configuration;

    /**
     * 屏幕宽
     */
    private static int screenwidth;
    /**
     * 屏幕高
     */
    private static int screenHeight;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Logger.init("WISDOM")                   // default PRETTYLOGGER or use just init()
                .methodCount(3)                 // default 2
                .logLevel("release".equals(BuildConfig.BUILD_TYPE) ? LogLevel.NONE : LogLevel.FULL);

        mContext = this;
        configuration = new YoukuPlayerBaseConfiguration(mContext) {

            /**
             * 覆写此方法，返回正在缓存视频信息的界面
             * @return
             */
            @Override
            public Class<? extends Activity> getCachingActivityClass() {
                //return null;
                return GuidanceDetailActivity.class;
            }

            /**
             * 覆写此方法，返回已经缓存视频信息的界面
             * @return
             */
            @Override
            public Class<? extends Activity> getCachedActivityClass() {
                return GuidanceDetailActivity.class;
            }

            /**
             * 覆写此方法，配置视频缓存路径，默认视频缓存路径：/应用程序包名/videocache/
             * @return
             */
            @Override
            public String configDownloadPath() {
                return null;
            }
        };
    }

    public static BaseApplication getApplication()
    {
        return mContext;
    }

    public static int getScreenHeight()
    {
        return screenHeight == 0 ? (int)SPUtills.get(getApplication(), Constants.screenh, 0) : screenHeight;
    }

    public static void setScreenHeight(int screenHeight)
    {
        if(BaseApplication.screenHeight == 0)
        {
            SPUtills.put(getApplication(), Constants.screenh, screenHeight);
            BaseApplication.screenHeight = screenHeight;
        }
    }

    public static int getScreenwidth()
    {
        return screenwidth == 0 ? (int)SPUtills.get(getApplication(), Constants.screenw, 0) : screenwidth;
    }

    public static void setScreenwidth(int screenwidth)
    {
        if(BaseApplication.screenwidth == 0)
        {
            SPUtills.put(getApplication(), Constants.screenw, screenHeight);
            BaseApplication.screenwidth = screenwidth;
        }
    }

    public static Context getContext()
    {
        return BaseApplication.getApplication();
    }

    public static View inflate(int resId, ViewGroup root)
    {
        return LayoutInflater.from(getContext()).inflate(resId, root, true);
    }

    public static View inflate(int resId)
    {
        return LayoutInflater.from(getContext()).inflate(resId, null);
    }

    /** 获取资源 */
    public static Resources findResources()
    {

        return getContext().getResources();
    }

    /** 获取文字 */
    public static String findString(int resId)
    {
        return findResources().getString(resId);
    }

    /** 获取文字数组 */
    public static String[] findStringArray(int resId)
    {
        return findResources().getStringArray(resId);
    }

    /** 获取dimen */
    public static int findDimens(int resId)
    {
        return findResources().getDimensionPixelSize(resId);
    }

    /** 获取drawable */
    public static Drawable findDrawable(int resId)
    {
        return findResources().getDrawable(resId);
    }

    /** 获取颜色 */
    public static int findColor(int resId)
    {
        return findResources().getColor(resId);
    }

    /** 获取颜色选择器 */
    public static ColorStateList findColorStateList(int resId)
    {
        return findResources().getColorStateList(resId);
    }


    /** 对toast的简易封装。线程安全，可以在非UI线程调用。 */
    public static void showToastSafe(final int resId)
    {
        showToastSafe(findString(resId));
    }

    /** 对toast的简易封装。线程安全，可以在非UI线程调用。 */
    public static void showToastSafe(final String str)
    {
        Looper.prepare();
        showToast(str);
        Looper.loop();
    }

    private static void showToast(String str)
    {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }
}
