package com.cnst.wisdom.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.cnst.wisdom.R;
import com.cnst.wisdom.model.Constants;
import com.cnst.wisdom.model.bean.Login;
import com.cnst.wisdom.model.net.NetResult;
import com.cnst.wisdom.model.net.VolleyManager;
import com.cnst.wisdom.utills.SPUtills;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * <登录界面>
 * 加载本地登录信息。如果存在符合“自动登录”条件的登录信息，则进行“保存密码”/“自动登录”，跳转到<主界面>。
 * 首次启动，用户需要在2个输入框内按提示分别输入<用户名/手机号>以及<登录密码>，并且有可选项<自动登录>供勾选。点击<登录>按钮进行登录信息验证。
 * 如果用户忘记了密码，可以点击<忘记密码>来通过提供相关证明信息来重置登录密码。
 * 本地保存的登录密码采用？？算法
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{

    private EditText editText_user;
    private EditText editText_password;
    private CheckBox mCheckBox;
    private TextView textView_login;
    private TextView textView_forgetPassword;
    private TextView dialogTitle;
    private String string_user;
    private String string_password;
    private String autoLogin_time;
    private String autoLogin_checked;
    private Calendar mCalendar = Calendar.getInstance();
    private Dialog mDialog;
    private VolleyManager mVolleyManager = VolleyManager.getInstance();
    private String serverUrl;

    //测试用的ip地址输入框，正式版本里删除
    private EditText editText_url;
    private CheckBox checkBox_url;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_login);
        setViews();
        checkLoginInfo();
    }

    /**
     * <检查本地已保存的登录信息>-完成
     * 1)如果以前没有勾选过自动登录，则跳过本方法。
     * 2)如果上次自动登录的日期距离本次登录的日期超过30天，则清空本地记录，跳过本方法。
     * 3)解密本地保存的登录密码
     * 4)将本地的登录信息发送到服务器进行验证，如果验证失败，则清空本地记录，跳过本方法。
     */

    private void checkLoginInfo()
    {
        autoLogin_checked = SPUtills.get(getApplicationContext(), "autoLogin_checked", "false").toString();
        if(!autoLogin_checked.equals("true"))
        {
            clearInfo();
            return;
        }

        autoLogin_time = SPUtills.get(getApplicationContext(), "autoLogin_time", "").toString();

        try
        {
            BigDecimal bd_auto = new BigDecimal(autoLogin_time);
            BigDecimal bd_now = new BigDecimal(mCalendar.getTimeInMillis());
            BigDecimal bd_duration = BigDecimal.valueOf(604800000L);//7天的毫秒值，超过7天没登录就清除记录
            if(bd_now.subtract(bd_auto).compareTo(bd_duration) != -1)
            {
                clearInfo();
                return;
            }
        }catch(Exception e)
        {
            clearInfo();
            Log.e("sp_time_parse_error", "本地保存日期转化错误");
            return;
        }

        string_user = SPUtills.get(getApplicationContext(), "autoLogin_user", "").toString();
        string_password = decrypt(SPUtills.get(getApplicationContext(), "autoLogin_password", "").toString());

        checkInfo();
    }

    private void setViews()
    {
        editText_user = (EditText)findViewById(R.id.editText_user);
        editText_password = (EditText)findViewById(R.id.editText_password);
        mCheckBox = (CheckBox)findViewById(R.id.checkBox_autoLogin);
        textView_forgetPassword = (TextView)findViewById(R.id.textView_forgetPassword);
        textView_forgetPassword.setOnClickListener(this);
        textView_login = (TextView)findViewById(R.id.textView_login);
        textView_login.setOnClickListener(this);
        mDialog = new Dialog(getApplicationContext(), R.style.no_title_dialog);
        mDialog.setContentView(R.layout.dialog_sel_subject_state);
        dialogTitle = (TextView)mDialog.findViewById(R.id.dialog_title);
        mDialog.findViewById(R.id.cancel).setOnClickListener(this);
        mDialog.findViewById(R.id.confirm).setOnClickListener(this);

        //测试用，正式版本删除
        editText_url=(EditText)findViewById(R.id.editText_url);
        checkBox_url=(CheckBox)findViewById(R.id.checkBox_url);

    }


    /**
     * <登录方法>-完成
     * 1)判断用户名/手机号和密码输入框是否为空，如果任一输入框为空则方法结束。
     * 2)发送登录信息到服务器进行验证，显示验证结果。如果验证失败，则要求用户重新输入。
     * 3)判断checkbox自动登录是否勾选，如果已勾选，则保存登录信息，下次启动本应用时将自动完成登录。如果未勾选，则尝试删除已保存的登录信息。
     * 4)判断是否需要进入欢迎界面，如果不需要则进入主界面。
     */
    private void login()
    {
        textView_login.setClickable(false);
        string_user = editText_user.getText().toString().trim();
        if(string_user.equals(""))
        {
            Toast.makeText(this, "用户名/手机号不能为空", Toast.LENGTH_SHORT).show();
            textView_login.setClickable(true);

            return;
        }
        string_password = editText_password.getText().toString();
        if(string_password.equals(""))
        {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            textView_login.setClickable(true);
            return;
        }
        if(checkBox_url.isChecked()){
            serverUrl=Constants.SERVER;
        }else{
            serverUrl="http://"+editText_url.getText().toString().trim()+"/";
        }
        SPUtills.put(getApplicationContext(), Constants.GET_SERVER, serverUrl);
        checkInfo();
        //switchActivity();
    }


    /**
     * <忘记密码>-未完成
     */
    private void forgetPassword()
    {
        //TODO:跳转到修改密码页面
        Toast.makeText(this, "跳转修改密码页面", Toast.LENGTH_SHORT).show();

    }


    /**
     * <验证登录信息>-未完成
     * 将用户名/手机号和登录密码发送到服务器进行验证。
     *
     * @return 如果登录信息正确，则方法返回true，否则返回false
     */
    private void checkInfo()
    {
        Map<String,String> mMap = new HashMap<String,String>();
        mMap.put("username", string_user);
        mMap.put("password", string_password);
        mVolleyManager.getString(serverUrl+Constants.LOGIN, mMap, "Login", new NetResult<String>()
        {
            @Override
            public void onFailure(VolleyError error)
            {
                Toast.makeText(LoginActivity.this, "无法连接到服务器", Toast.LENGTH_SHORT).show();
                textView_login.setClickable(true);
            }

            @Override
            public void onSucceed(String response)
            {
                textView_login.setClickable(true);
                Gson gson = new Gson();
                Login login = gson.fromJson(response, Login.class);
                int code = login.getCode();
                switch(code)
                {
                    case Constants.STATUS_ARGUMENT_ERROR:
                        Toast.makeText(LoginActivity.this, "缺少参数，或参数不正确", Toast.LENGTH_SHORT).show();

                        break;
                    case Constants.STATUS_DATA_NOTFOUND:
                        Toast.makeText(LoginActivity.this, "数据查询不存在", Toast.LENGTH_SHORT).show();

                        break;
                    case Constants.STATUS_FAIL:
                        Toast.makeText(LoginActivity.this, "用户名/手机号或密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                        break;
                    case Constants.STATUS_ILLEGAL:
                        Toast.makeText(LoginActivity.this, "非法请求", Toast.LENGTH_SHORT).show();

                        break;
                    case Constants.STATUS_SERVER_EXCEPTION:
                        Toast.makeText(LoginActivity.this, "服务器内部异常", Toast.LENGTH_SHORT).show();

                        break;
                    case Constants.STATUS_SUCCESS:

                        VolleyManager.getInstance().setCoolie(login.getSessionId());
                        SPUtills.put(getApplicationContext(), Constants.GET_SESSIONID, login.getSessionId());
                        SPUtills.put(getApplicationContext(), Constants.GET_NAME, login.getData().getName());
                        SPUtills.put(getApplicationContext(), Constants.GET_SCHOOL, login.getData().getSchool());
                        SPUtills.put(getApplicationContext(), Constants.GET_STATIONNAME, login.getData().getStationName());

//                        Log.i("tag_login", SPUtills.get(getApplicationContext(), Constants.GET_SESSIONID, "no-data").toString());
//                        Log.i("tag_login", SPUtills.get(getApplicationContext(), Constants.GET_NAME, "no-data").toString());
//                        Log.i("tag_login", SPUtills.get(getApplicationContext(), Constants.GET_SCHOOL, "no-data").toString());
//                        Log.i("tag_login", SPUtills.get(getApplicationContext(), Constants.GET_STATIONNAME, "no-data").toString());

                        if(mCheckBox.isChecked())
                        {
                            //TODO:保存登录信息到SP
                            SPUtills.put(getApplicationContext(), "autoLogin_user", string_user);
                            SPUtills.put(getApplicationContext(), "autoLogin_time", String.valueOf(mCalendar.getTimeInMillis()));
                            SPUtills.put(getApplicationContext(), "autoLogin_checked", "true");
                            SPUtills.put(getApplicationContext(), "autoLogin_password", encrypt(string_password));


                        }else
                        {
                            //TODO:删除SP内的登录信息，可能不会执行（在别的操作里已经删除了登录信息，才会在此操作过程里填写用户名和密码）
                            clearInfo();

                        }
                        switchActivity();
                        break;
                    case Constants.STATUS_TIMEOUT:
                        Toast.makeText(LoginActivity.this, "连接超时", Toast.LENGTH_SHORT).show();

                        break;
                }

            }
        });
    }

    /**
     * <删除本地登录信息>-完成
     * 删除已保存的用户名/手机号，加密的登录密码，最后一次自动登录的日期，自动登录设置为false
     */
    private void clearInfo()
    {
        SPUtills.remove(getApplicationContext(), "autoLogin_user");
        SPUtills.remove(getApplicationContext(), "autoLogin_password");
        SPUtills.remove(getApplicationContext(), "autoLogin_time");
        SPUtills.put(getApplicationContext(), "autoLogin_checked", "false");

    }

    /**
     * <加密登录密码>-未完成
     * 将登录密码转为密文，用于保存在本地
     */

    private String encrypt(String str)
    {
        String pwd;
        pwd = str;
        return pwd;
    }

    /**
     * <解密本地保存的密文>-未完成
     * 将密文还原为登录密码，用于发送到服务器进行登录信息验证
     */
    private String decrypt(String str)
    {
        String pwd;
        pwd = str;
        return pwd;
    }


    /**
     * <跳转界面>-未完成
     * 1)跳转到欢迎界面，条件？首次登录，长时间未登录，客户端更新，特殊日期
     * 2)首次登录，进入绑定手机号界面
     * 3)如果不符合上述条件，则跳转到主界面
     */
    private void switchActivity()
    {
        Intent intent = new Intent();
        if(true)
        {
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();

            intent.setClass(this, MainActivity.class);
        }else
        {
            intent.setClass(this, RegisterActivity.class);

        }
        startActivity(intent);
        finish();
    }

    /**
     * <绑定窗口>
     * 1)初始化对话框
     * 2)点击“确定”进入绑定页面
     * 3)点击“取消”则关闭对话框，不再执行任何操作
     */
    private void register()
    {
        dialogTitle.setText("初次登录需绑定手机号");
        mDialog.show();

    }


    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.textView_forgetPassword:
                forgetPassword();
                break;
            case R.id.textView_login:
                login();
                break;

            case R.id.cancel:
                //                mDialog.dismiss();
                break;
            case R.id.confirm:
                //                mDialog.dismiss();
                //                switchActivity();
                break;
            default:
                break;
        }
    }
}
