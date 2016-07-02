package com.cnst.wisdom.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.cnst.wisdom.R;
import com.cnst.wisdom.presenter.AttenPresenter;

/**
 * <播放页详情>
 * <优酷播放器和评论展示>
 *
 * @author pengjingang.
 * @see [相关类/方法]
 * @since [产品/模版版本]
 */
public class LearnDetail extends AppCompatActivity implements View.OnClickListener
{
    private ImageButton mViewById;
    private ImageButton mIbBack;
    private TextView mTvCoursename;
    private TextView mTvIntroduce;
    private Button mBtnDownload;
    private EditText mEtComment;
    private Button mBtnDeliver;
    private ListView mLvComment;
    private AttenPresenter mAttenPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learndetail);
        mLvComment = (ListView)findViewById(R.id.lv_comment);
        View view = View.inflate(this, R.layout.top_player,null);
        mLvComment.addHeaderView(view);
        mIbBack = (ImageButton)view.findViewById(R.id.ib_back);
        mTvCoursename = (TextView)view.findViewById(R.id.tv_coursename);
        mTvIntroduce = (TextView)view.findViewById(R.id.tv_introduce);
        mBtnDownload = (Button)view.findViewById(R.id.btn_download);
        mEtComment = (EditText)view.findViewById(R.id.et_comment);
        mBtnDeliver = (Button)view.findViewById(R.id.btn_deliver);

        mLvComment.setAdapter(new MyAdapter());


        mIbBack.setOnClickListener(this);


    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId()){
            case R.id.ib_back:
                finish();
                break;

//            未完待续…………
            default:
                break;

        }

    }

    private class MyAdapter extends BaseAdapter
    {
        @Override
        public int getCount()
        {
            return 20;
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            convertView = View.inflate(LearnDetail.this, R.layout.item_online_detail, null);
            return convertView;
        }

        @Override
        public boolean isEnabled(int position)
        {
            return false;
        }
    }
}
