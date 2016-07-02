package com.cnst.wisdom.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cnst.wisdom.R;
import com.cnst.wisdom.model.cache.CacheData;

/**
 * 教学计划主界面
 * 通过此页面键入教学科目、教学进度、修改计划
 *
 * @author taoyuan.
 * @since 1.0
 */
public class TeachPlan extends AppCompatActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach_plan);
        CacheData.initSubject(this);
        findViewById(R.id.teach_plan_subject).setOnClickListener(this);
        findViewById(R.id.teach_plan_progress).setOnClickListener(this);
        findViewById(R.id.teach_plan_mof).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.teach_plan_subject:
                //进入教学科目，新建教学计划
                startActivity(new Intent(this, TeachPlanCourse.class));
                break;
            case R.id.teach_plan_progress:
                //进入教学进度，更新教学进度
                startActivity(new Intent(this, TeachPlanProgress.class));
                break;
            case R.id.teach_plan_mof:
                //进入修改计划，修改教学计划
                startActivity(new Intent(this, TeachPlanMod.class));
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }
}
