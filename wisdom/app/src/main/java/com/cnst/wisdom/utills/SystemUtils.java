package com.cnst.wisdom.utills;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Debug;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.cnst.wisdom.BuildConfig;
import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jonas on 2014/6/19.
 */
public class SystemUtils
{

    /** 获取android系统版本号 */
    public static String getOSVersion()
    {
        String release = android.os.Build.VERSION.RELEASE; // android系统版本号
        release = "android"+release;
        return release;
    }

    /** 获得android系统sdk版本号 */
    public static int getOSVersionSDKINT()
    {
        return android.os.Build.VERSION.SDK_INT;
    }


    /** 获取屏幕的分辨率 */
    @SuppressWarnings("deprecation")
    public static int[] getResolution(Context context)
    {
        if(null == context)
        {
            return null;
        }
        WindowManager windowMgr = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        int[] res = new int[2];
        res[0] = windowMgr.getDefaultDisplay().getWidth();
        res[1] = windowMgr.getDefaultDisplay().getHeight();
        return res;
    }

    /** 获得设备的横向dpi */
    public static float getWidthDpi(Context context)
    {
        if(null == context)
        {
            return 0;
        }
        DisplayMetrics dm = null;
        try
        {
            if(context != null)
            {
                dm = new DisplayMetrics();
                dm = context.getApplicationContext().getResources().getDisplayMetrics();
            }

            return dm.densityDpi;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    /** 获得设备的纵向dpi */
    public static float getHeightDpi(Context context)
    {
        if(null == context)
        {
            return 0;
        }
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.ydpi;
    }

    /** 获取设备信息 */
    public static String[] getDivceInfo()
    {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String[] cpuInfo = {"", ""};
        String[] arrayOfString;
        try
        {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for(int i = 2; i<arrayOfString.length; i++)
            {
                cpuInfo[0] = cpuInfo[0]+arrayOfString[i]+" ";
            }
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            cpuInfo[1] += arrayOfString[2];
            localBufferedReader.close();
        }catch(IOException e)
        {
            Logger.e(e.getMessage());
        }
        return cpuInfo;
    }

    /** 判断手机CPU是否支持NEON指令集 */
    public static boolean isNEON()
    {
        boolean isNEON = false;
        String cupinfo = getCPUInfos();
        if(cupinfo != null)
        {
            cupinfo = cupinfo.toLowerCase();
            isNEON = cupinfo != null && cupinfo.contains("neon");
        }
        return isNEON;
    }

    /** 读取CPU信息文件，获取CPU信息 */
    @SuppressWarnings("resource")
    private static String getCPUInfos()
    {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        StringBuilder resusl = new StringBuilder();
        String resualStr = null;
        try
        {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            while(( str2 = localBufferedReader.readLine() ) != null)
            {
                resusl.append(str2);
                // String cup = str2;
            }
            if(resusl != null)
            {
                resualStr = resusl.toString();
                return resualStr;
            }
        }catch(IOException e)
        {
            Logger.e(e.getMessage());
        }
        return resualStr;
    }

    /** 获取当前设备cpu的型号 */
    public static int getCPUModel()
    {
        return matchABI(getSystemProperty("ro.product.cpu.abi"))|matchABI(getSystemProperty("ro.product.cpu.abi2"));
    }

    /** 匹配当前设备的cpu型号 */
    private static int matchABI(String abiString)
    {
        if(TextUtils.isEmpty(abiString))
        {
            return 0;
        }
        if("armeabi".equals(abiString))
        {
            return 1;
        }else if("armeabi-v7a".equals(abiString))
        {
            return 2;
        }else if("x86".equals(abiString))
        {
            return 4;
        }else if("mips".equals(abiString))
        {
            return 8;
        }
        return 0;
    }

    /** 获取CPU核心数 */
    public static int getCpuCount()
    {
        return Runtime.getRuntime().availableProcessors();
    }

    /** 获取Rom版本 */
    public static String getRomversion()
    {
        String rom = "";
        try
        {
            String modversion = getSystemProperty("ro.modversion");
            String displayId = getSystemProperty("ro.build.display.id");
            if(modversion != null && !modversion.equals(""))
            {
                rom = modversion;
            }
            if(displayId != null && !displayId.equals(""))
            {
                rom = displayId;
            }
        }catch(Exception e)
        {
            Logger.e(e.getMessage());
        }
        return rom;
    }

    /** 获取系统配置参数 */
    public static String getSystemProperty(String key)
    {
        String pValue = null;
        try
        {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method m = c.getMethod("get", String.class);
            pValue = m.invoke(null, key).toString();
        }catch(Exception e)
        {
            Logger.e(e.getMessage());
        }
        return pValue;
    }

    /** 获取系统中的Library包 */
    public static List<String> getSystemLibs(Context context)
    {
        if(null == context)
        {
            return null;
        }
        PackageManager pm = context.getPackageManager();
        String[] libNames = pm.getSystemSharedLibraryNames();
        List<String> listLibNames = Arrays.asList(libNames);
        Logger.d("SystemLibs: "+listLibNames);
        return listLibNames;
    }

    /** 获取手机外部可用空间大小，单位为byte */
    @SuppressWarnings("deprecation")
    public static long getExternalTotalSpace()
    {
        long totalSpace = -1L;
            try
            {
                String path = Environment.getExternalStorageDirectory().getPath();// 获取外部存储目录即 SDCard
                StatFs stat = new StatFs(path);
                long blockSize = stat.getBlockSize();
                long totalBlocks = stat.getBlockCount();
                totalSpace = totalBlocks*blockSize;
            }catch(Exception e)
            {
                Logger.e(e.getMessage());
            }
        return totalSpace;
    }

    /** 获取外部存储可用空间，单位为byte */
    @SuppressWarnings("deprecation")
    public static long getExternalSpace()
    {
        long availableSpace = -1L;
        try
        {
            String path = Environment.getExternalStorageDirectory().getPath();
            StatFs stat = new StatFs(path);
            availableSpace = stat.getAvailableBlocks()*(long)stat.getBlockSize();
        }catch(Exception e)
        {
            Logger.e(e.getMessage());
        }
        return availableSpace;
    }

    /** 获取手机内部空间大小，单位为byte */
    @SuppressWarnings("deprecation")
    public static long getTotalInternalSpace()
    {
        long totalSpace = -1L;
        try
        {
            String path = Environment.getDataDirectory().getPath();
            StatFs stat = new StatFs(path);
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();// 获取该区域可用的文件系统数
            totalSpace = totalBlocks*blockSize;
        }catch(Exception e)
        {
            Logger.e(e.getMessage());
        }
        return totalSpace;
    }

    /** 获取手机内部可用空间大小，单位为byte */
    @SuppressWarnings("deprecation")
    public static long getAvailableInternalMemorySize()
    {
        long availableSpace = -1l;
        try
        {
            String path = Environment.getDataDirectory().getPath();// 获取 Android 数据目录
            StatFs stat = new StatFs(path);// 一个模拟linux的df命令的一个类,获得SD卡和手机内存的使用情况
            long blockSize = stat.getBlockSize();// 返回 Int ，大小，以字节为单位，一个文件系统
            long availableBlocks = stat.getAvailableBlocks();// 返回 Int ，获取当前可用的存储空间
            availableSpace = availableBlocks*blockSize;
        }catch(Exception e)
        {
            Logger.e(e.getMessage());
        }
        return availableSpace;
    }

    /** 获取单个应用最大分配内存，单位为byte */
    public static long getOneAppMaxMemory(Context context)
    {
        if(context == null)
        {
            return -1;
        }
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        return activityManager.getMemoryClass()*1024*1024;
    }

    /** 获取指定本应用占用的内存，单位为byte */
    public static long getUsedMemory(Context context)
    {
        return getUsedMemory(context, null);
    }

    /** 获取指定包名应用占用的内存，单位为byte */
    public static long getUsedMemory(Context context, String packageName)
    {
        if(context == null)
        {
            return -1;
        }
        if(TextUtils.isEmpty(packageName))
        {
            packageName = context.getPackageName();
        }
        long size = 0;
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runapps = activityManager.getRunningAppProcesses();
        for(ActivityManager.RunningAppProcessInfo runapp : runapps)
        { // 遍历运行中的程序
            if(packageName.equals(runapp.processName))
            {// 得到程序进程名，进程名一般就是包名，但有些程序的进程名并不对应一个包名
                // 返回指定PID程序的内存信息，可以传递多个PID，返回的也是数组型的信息
                Debug.MemoryInfo[] processMemoryInfo = activityManager.getProcessMemoryInfo(new int[]{runapp.pid});
                // 得到内存信息中已使用的内存，单位是K
                size = processMemoryInfo[0].getTotalPrivateDirty()*1024;
            }
        }
        return size;
    }

    /** 获取手机剩余内存，单位为byte */
    public static long getAvailableMemory(Context context)
    {
        if(context == null)
        {
            return -1;
        }
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(info);
        return info.availMem;
    }


    /** 手机低内存运行阀值，单位为byte */
    public static long getThresholdMemory(Context context)
    {
        if(context == null)
        {
            return -1;
        }
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(info);
        return info.threshold;
    }

    /** 手机是否处于低内存运行 */
    public static boolean isLowMemory(Context context)
    {
        if(context == null)
        {
            return false;
        }
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(info);
        return info.lowMemory;
    }

    /**
     * 是否在最前面
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isTopActivity(Context context, String packageName)
    {
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if(tasksInfo.size()>0)
        {
            if(BuildConfig.DEBUG)
            {
                Log.d("jonas", ( "---------------包名-----------"+tasksInfo.get(0).topActivity.getPackageName() ));
            }
            // 应用程序位于堆栈的顶层
            if(packageName.equals(tasksInfo.get(0).topActivity.getPackageName()))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断Android应用是否在前台
     */
    public static boolean isAppOnForeground(Context context)
    {
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getPackageName();
        List<ActivityManager.RecentTaskInfo> appTask = activityManager.getRecentTasks(Integer.MAX_VALUE, 1);

        if(appTask == null)
        {
            return false;
        }

        if(appTask.get(0).baseIntent.toString().contains(packageName))
        {
            return true;
        }
        return false;
    }

    /**
     * 判断activity是否在系统中存在
     *
     * @param context
     * @param packName
     * @param className
     * @return
     */
    public static boolean isActivityExits(Context context, String packName, String className)
    {
        //已知包名和类名，如何判断这个activity是否在系统中存在呢？很简单，通过intent就行。
        Intent intent = new Intent();
        intent.setClassName(packName, className);
        if(context.getPackageManager().resolveActivity(intent, 0) == null)
        {
            //说明系统中不存在这个activity
            return false;
        }
        return true;
    }

    /**
     * Android 判断程序前后台状态
     *
     * @param context
     * @return
     */
    public static boolean isLauncherRunnig(Context context)
    {
        boolean result = false;
        List<String> names = getAllTheLauncher(context);
        ActivityManager mActivityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appList = mActivityManager.getRunningAppProcesses();
        for(ActivityManager.RunningAppProcessInfo running : appList)
        {
            if(running.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
            {
                for(int i = 0; i<names.size(); i++)
                {
                    if(names.get(i).equals(running.processName))
                    {
                        result = true;
                        break;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 获取Android手机内安装的所有桌面
     *
     * @param context
     * @return
     */
    private static List<String> getAllTheLauncher(Context context)
    {
        List<String> names = null;
        PackageManager pkgMgt = context.getPackageManager();
        Intent it = new Intent(Intent.ACTION_MAIN);
        it.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> ra = pkgMgt.queryIntentActivities(it, 0);
        if(ra.size() != 0)
        {
            names = new ArrayList<String>();
        }
        for(int i = 0; i<ra.size(); i++)
        {
            String packageName = ra.get(i).activityInfo.packageName;
            names.add(packageName);
        }
        return names;
    }

    /**
     * 检查程序是否运行
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppRunning(Context context, String packageName)
    {
        boolean isAppRunning = false;
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for(ActivityManager.RunningTaskInfo info : list)
        {
            if(info.topActivity.getPackageName().equals(packageName) && info.baseActivity.getPackageName()
                    .equals(packageName))
            {
                isAppRunning = true;
                // find it, break
                break;
            }
        }
        list.clear();
        return isAppRunning;
    }

    /**
     * 用来判断服务是否运行.
     *
     * @param className
     *         判断的服务名字
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRunning(Context mContext, String className)
    {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(30);
        if(!( serviceList.size()>0 ))
        {
            return false;
        }
        for(int i = 0; i<serviceList.size(); i++)
        {
            if(serviceList.get(i).service.getClassName().equals(className) == true)
            {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

}
