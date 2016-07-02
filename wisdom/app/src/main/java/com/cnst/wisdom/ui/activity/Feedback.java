package com.cnst.wisdom.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.cnst.wisdom.R;
/**
 * <需求反馈>
 * <需求反馈页面展示，>
 *
 * @author pengjingang.
 * @see [相关类/方法]
 * @since [产品/模版版本]
 *
 */
public class Feedback extends AppCompatActivity implements View.OnClickListener
{

    private ImageButton mIbBack;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        mIbBack = (ImageButton)findViewById(R.id.ib_back);
        mIbBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId()){
            case R.id.ib_back:
                finish();
                break;
        }
    }
}
