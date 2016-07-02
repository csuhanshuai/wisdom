package com.cnst.wisdom.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cnst.wisdom.R;

/**
 * 自定义搜索框
 * 根据课名进行搜索
 *
 * @author meilianbing.
 * @see [相关类/方法]
 * @since [产品/模版版本]
 */
public class CustomSearchView extends RelativeLayout{


    private ExitListener exitListener;

    public CustomSearchView(Context context) {
        super(context);
        initView(context);
    }

    public CustomSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(final Context context){
        LayoutInflater.from(context).inflate(R.layout.searchview_layout,this);
        TextView tvCancel = (TextView) findViewById(R.id.btn_cancel);
        final ImageButton ibtnClear = (ImageButton) findViewById(R.id.ibtn_clear);
        final EditText etSearch = (EditText) findViewById(R.id.et_search);

        ibtnClear.setVisibility(INVISIBLE);
        ibtnClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setText("");
            }
        });

        etSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Window window = ((Activity) context).getWindow();
                WindowManager.LayoutParams params = window.getAttributes();
                if (params.softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                    etSearch.setCursorVisible(true);
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                    params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE;
                }
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString();
                if (TextUtils.isEmpty(query)) {
                    ibtnClear.setVisibility(GONE);
                } else {
                    ibtnClear.setVisibility(VISIBLE);
                }
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                    Toast.makeText(context,etSearch.getText().toString(),Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        tvCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exitListener != null) {
                    exitListener.exitSearch();
                }
            }
        });
    }

    public void setExitListener(ExitListener listener){
        exitListener = listener;
    }

    public interface ExitListener{
        void exitSearch();
    }
}
