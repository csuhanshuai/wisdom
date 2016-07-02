package com.cnst.wisdom.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.cnst.wisdom.R;
import com.cnst.wisdom.ui.widget.CustomSearchView;

public class GuidanceSearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance_search);
        initView();
    }

    private void initView(){
        CustomSearchView searchView = (CustomSearchView) findViewById(R.id.search_view);
        searchView.setExitListener(new CustomSearchView.ExitListener() {
            @Override
            public void exitSearch() {
                exitWithAnim();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            exitWithAnim();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exitWithAnim(){
        finish();
        //overridePendingTransition();
    }
}
