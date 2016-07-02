package com.cnst.wisdom.utills;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.UUID;


/**
 *  DeviceUtils工具类
 *  获取手机信息
 * @author jiangzuyun.
 * @see  [put，get]
 * @since [产品/模版版本]
 */
public class DeviceUtils {
  protected static final String TAG = DeviceUtils.class.getSimpleName();

  // 移动
  private static final int CHINA_MOBILE = 1;
  // 联通
  private static final int UNICOM = 2;
  // 电信
  private static final int TELECOMMUNICATIONS = 3;
  // 失败
  private static final int ERROR = 0;

  /**
   * 手机唯一标识
   */
  public static String getDeviceId(Context context) {
    final TelephonyManager tm = getTelphoneManager(context);
    final String tmDevice, tmSerial, androidId;
    tmDevice = "" + tm.getDeviceId();
    tmSerial = "" + tm.getSimSerialNumber();
    androidId = "" + android.provider.Settings.Secure
        .getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
    UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
    String uniqueId = deviceUuid.toString();
    return uniqueId;
  }

  /**
   * TelephonyManager对象
   */
  private static TelephonyManager getTelphoneManager(Context context) {
    return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
  }

  /**
   * DeviceId
   */
  public static String getDeviceID(Context context) {
    return getTelphoneManager(context).getDeviceId();
  }

  /**
   * 厂商信息
   */
  public static String getProductInfo() {
    return android.os.Build.MODEL;
  }

  /**
   * 系统 -版本
   */
  public static String getSystemVersion() {
    return android.os.Build.VERSION.RELEASE;
  }

  /**
   * SDK_INT 版本
   */
  public static int getSDKVersion() {
    return android.os.Build.VERSION.SDK_INT;
  }

  /**
   * Android的SDK中有TelephonyManager这个API：Provides access to information about the telephony services on the
   * device. Applications can use the methods in this class to determine telephony services and states, as
   * well as to access some types of subscriber information. Applications can also register a listener to
   * receive notification of telephony state changes. 它可以获取到手机信息、通话状态、SIM卡信息等等（我还发现了.getCellLocation()
   * 这个方法，这不就是基站定位吗？呵呵）， getLine1Number(); 不一定能够获取到用户的电话，因为这个数据是通过手工设定的，而且是可以更改的。 经过测试确实是不可以获取到手机号码的。
   *
   * 手机号码，这个号码不一定能获取到
   */
  public static String getPhoneNum(Context context) {
    return getTelphoneManager(context).getLine1Number();
  }

  /**
   * 当前运营商
   *
   * @return 返回0 表示失败 1表示为中国移动 2为中国联通 3为中国电信
   */
  public static int getProviderName(Context context) {
    String IMSI = getIMSI(context);
    if (IMSI == null) {
      return ERROR;
    }
    if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
      return CHINA_MOBILE;
    } else if (IMSI.startsWith("46001")) {
      return UNICOM;
    } else if (IMSI.startsWith("46003")) {
      return TELECOMMUNICATIONS;
    }
    return ERROR;
  }

  /**
   * 手机CPU名字
   */
  public static String getCpuName() {
    FileReader fileReader = null;
    BufferedReader bufferedReader = null;
    try {
      // 读取文件CPU信息
      fileReader = new FileReader("/pro/cpuinfo");
      bufferedReader = new BufferedReader(fileReader);
      String string = bufferedReader.readLine();
      String[] strings = string.split(":\\s+", 2);
      return strings[1];
    } catch (FileNotFoundException e) {
      Log.e(TAG, e.getLocalizedMessage());
    } catch (IOException e) {
      Log.e(TAG, e.getLocalizedMessage());
    } finally {
      if (bufferedReader != null) {
        try {
          bufferedReader.close();
        } catch (IOException e) {
          Log.e(TAG, e.getLocalizedMessage());
        }
      }
      if (fileReader != null) {
        try {
          fileReader.close();
        } catch (IOException e) {
          Log.e(TAG, e.getLocalizedMessage());
        }
      }
    }
    return null;
  }

  /**
   * 判断是否联网
   */
  public static boolean isNetworkConnected(Context context) {
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    return networkInfo != null && networkInfo.isAvailable();
  }

  /** 获取手机型号 */
  public static String getDeviceModel() {
    return android.os.Build.MODEL;
  }

  /** 获取设备的IMEI */
  public static String getIMEI(Context context) {
    if (null == context) {
      return null;
    }
    return getTelphoneManager(context).getDeviceId();
  }

  /** 检测手机是否已插入SIM卡 */
  public static boolean isCheckSimCardAvailable(Context context) {
    if (null == context) {
      return false;
    }
    TelephonyManager tm = getTelphoneManager(context);
    return tm.getSimState() == getTelphoneManager(context).SIM_STATE_READY;
  }

  /** sim卡是否可读 */
  public static boolean isCanUseSim(Context context) {
    if (null == context) {
      return false;
    }
    try {
      TelephonyManager mgr = getTelphoneManager(context);
      return TelephonyManager.SIM_STATE_READY == mgr.getSimState();
    } catch (Exception e) {
      Logger.e(e.getMessage());
    }
    return false;
  }

  /** 取得当前sim手机卡的imsi */
  public static String getIMSI(Context context) {
    if (null == context) {
      return null;
    }
    String imsi = null;
    try {
      TelephonyManager tm = getTelphoneManager(context);
      imsi = tm.getSubscriberId();
    } catch (Exception e) {
      Logger.e(e.getMessage());
    }
    return imsi;
  }

  /** 返回手机服务商名字 */
  public static String getProvidersName(Context context) {
    String ProvidersName = null;
    // 返回唯一的用户ID;就是这张卡的编号神马的
    String IMSI = getIMSI(context);
    if(null != IMSI)
    {
      // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
      if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
        ProvidersName = "中国移动";
      } else if (IMSI.startsWith("46001")) {
        ProvidersName = "中国联通";
      } else if (IMSI.startsWith("46003")) {
        ProvidersName = "中国电信";
      } else {
        ProvidersName = "其他服务商:" + IMSI;
      }
    }
    return ProvidersName;
  }

  /** 获取当前设备的SN */
  public static String getSimSN(Context context) {
    if (null == context) {
      return null;
    }
    String simSN = null;
    try {
      TelephonyManager tm = getTelphoneManager(context);
      simSN = tm.getSimSerialNumber();
    } catch (Exception e) {
      Logger.e(e.getMessage());
    }
    return simSN;
  }

  /**
   * 手机MAC地址
   */
  public static String getMacAddressInfo(Context context) {
    WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    WifiInfo info = manager.getConnectionInfo();
    return info.getMacAddress();
  }

  /** 获得设备ip地址 */
  public static String getLocalAddress() {
    try {
      Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
      while (en.hasMoreElements()) {
        NetworkInterface intf = en.nextElement();
        Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
        while (enumIpAddr.hasMoreElements()) {
          InetAddress inetAddress = enumIpAddr.nextElement();
          if (!inetAddress.isLoopbackAddress()) {
            return inetAddress.getHostAddress();
          }
        }
      }
    } catch (SocketException e) {
      Logger.e(e.getMessage());
    }
    return null;
  }
}
