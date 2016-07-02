package com.cnst.wisdom.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cnst.wisdom.R;

/**
 * <绑定手机号>
 * 用户首次登录时，在本页面进行手机号的绑定操作。
 * 验证码输入框禁用
 * 在手机号输入框输入用户的手机号码，点击“获取验证码”进行手机号码格式验证。如果格式正确，那么通知服务端向该手机号发送验证码短信。
 * 验证码输入框启用，此时用户可以输入验证码
 * 用户点击“绑定”进行校验，如果验证码正确，则完成绑定操作，并跳转到欢迎界面。
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener
{
    private TextView textView_getCode;
    private TextView textView_submit;
    private EditText editText_phone;
    private EditText editText_code;
    private ImageButton back;
    private String phone;
    private String code;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setviews();
    }

    private void setviews()
    {
        textView_getCode=(TextView)findViewById(R.id.textView_getCode);
        textView_getCode.setOnClickListener(this);
        textView_submit=(TextView)findViewById(R.id.textView_submit);
        textView_submit.setOnClickListener(this);
        editText_phone=(EditText)findViewById(R.id.editText_phone);
        editText_code=(EditText)findViewById(R.id.editText_code);
        back = (ImageButton)findViewById(R.id.head_back_action);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.textView_getCode:
                getCode();
                break;
            case R.id.textView_submit:
                submit();
                break;
            case R.id.head_back_action:
                finish();
                break;
            default:break;
        }
    }



    /**
     * <获取验证码>
     * 1)校验用户所输入的手机号的格式，如果格式不正确则显示提示信息
     * 2)向服务器发送请求，服务器向指定的手机号发送验证码信息
     * 3)将验证码输入框的状态从“禁用”变为“启用”
     */
    private void getCode()
    {
        phone=editText_phone.getText().toString();
        editText_code.setEnabled(true);
    }

    /**
     * <绑定>
     * 1)校验字符长度？输入类型？如果不符合条件则显示提示信息
     * 2)向服务器发送验证码，等待服务器返回验证结果
     * 3)显示验证结果。如果验证结果正确，则完成绑定操作，并跳转到欢迎界面。如果不正确，则提示输入有误。
     */
    private void submit()
    {
        code=editText_code.getText().toString();

    }


}
