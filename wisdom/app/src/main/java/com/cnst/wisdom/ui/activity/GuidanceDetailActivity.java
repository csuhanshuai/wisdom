package com.cnst.wisdom.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cnst.wisdom.R;
import com.youku.player.base.YoukuBasePlayerManager;
import com.youku.player.base.YoukuPlayer;
import com.youku.player.base.YoukuPlayerView;
import com.youku.service.download.DownloadInfo;
import com.youku.service.download.OnCreateDownloadListener;
import com.youku.service.download.DownloadManager;

import java.util.ArrayList;

public class GuidanceDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView lvRelated;

    /**
     * 视频播放控件
     */
    private YoukuPlayerView playerView;

    private YoukuPlayer player;
    private YoukuBasePlayerManager playerManager;

    /**
     * 标识要播放视频是否来自本地
     */
    private boolean isFromlocal;

    /**
     * 标识视频，用于视频播放
     */
    private String vid;

    /**
     * 正在下载视频信息
     */
    private DownloadInfo info;

    private static final int MSG_REFRESH_PROGRESS = 1;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_REFRESH_PROGRESS:
                    info = downloadManager.getDownloadInfo(vid);
                    if(hasDownloaded(vid)){
                        Toast.makeText(GuidanceDetailActivity.this,"info null",Toast.LENGTH_SHORT).show();
                        btnDownload.setText("已下载");
                        return ;
                    }
                    if(info.getProgress()==100){
                        btnDownload.setText("已下载");
                        return ;
                    }
                    btnDownload.setText(((int)info.getProgress())+"%");
                    handler.sendEmptyMessageDelayed(MSG_REFRESH_PROGRESS,1000);
                    break;
            }
        }
    };

    private Button btnDownload;
    private DownloadManager downloadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance_detail);
        initView();
        initData();
        playerManager = new YoukuBasePlayerManager(this) {
            @Override
            public void setPadHorizontalLayout() {

            }

            @Override
            public void onInitializationSuccess(YoukuPlayer player) {
                addPlugins();
                GuidanceDetailActivity.this.player = player;
                play();
            }

            @Override
            public void onFullscreenListener() {
                Toast.makeText(GuidanceDetailActivity.this,"full screen",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSmallscreenListener() {
                Toast.makeText(GuidanceDetailActivity.this,"small screen",Toast.LENGTH_SHORT).show();
            }
        };
        playerManager.onCreate();
        getIntentData(getIntent());
        if (TextUtils.isEmpty(vid))
            vid = "XOTI4ODEwMTYw";
        playerView.initialize(playerManager);
    }

    private void initView() {
        lvRelated = (ListView) findViewById(R.id.related);
        ImageButton ibtnBack = (ImageButton) findViewById(R.id.ibtn_back);
        ibtnBack.setOnClickListener(this);
        playerView = (YoukuPlayerView) findViewById(R.id.player_view);
        playerView
                .setSmallScreenLayoutParams(new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT));
        playerView
                .setFullScreenLayoutParams(new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT));
        btnDownload = (Button) findViewById(R.id.btn_download);
        btnDownload.setOnClickListener(this);
    }

    private void initData() {
        GuidanceDetailAdapter adapter = new GuidanceDetailAdapter(this);
        lvRelated.setAdapter(adapter);
    }

    /**
     * 通过上个页面传来的参数获取视频id
     *
     * @param intent
     */
    private void getIntentData(Intent intent) {
        if (intent != null) {
            isFromlocal = intent.getBooleanExtra("isFromLocal", false);
            if (isFromlocal) {
                vid = intent.getStringExtra("video_id");
            } else {
                vid = intent.getStringExtra("vid");
            }
        }
    }

    /**
     * 播放视频
     */
    private void play() {
        if (isFromlocal) {
            player.playLocalVideo(vid);
            btnDownload.setText("已下载");
        } else {
            if(hasDownloaded(vid)){
                btnDownload.setText("已下载");
                Toast.makeText(this,"local" +vid,Toast.LENGTH_SHORT).show();
                player.playLocalVideo(vid);
            }else {
                player.playVideo(vid);
            }
        }
    }

    /**
     * 根据视频id判断该视频是否下载
     * @param vid
     * @return
     */
    private boolean hasDownloaded(String vid){
        ArrayList<DownloadInfo> downloadedList = DownloadManager.getInstance().getDownloadedList();
        for(DownloadInfo info:downloadedList){
            if(info.videoid.equals(vid))
                return true;
        }
        return false;
    }

    /**
     * 判断是否在wifi状态下
     * @return
     */
    private boolean isWifiConn(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return networkInfo.isAvailable();
    }

    /**
     * 视频下载
     * @param vid
     * @param title
     */
    private void download(String vid, final String title){
        downloadManager = DownloadManager.getInstance();

        if(hasDownloaded(vid)){
            Toast.makeText(this,"视频已下载",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isWifiConn()){
            Toast.makeText(this,"请在wifi状态下下载",Toast.LENGTH_SHORT).show();
            return;
        }
        info = downloadManager.getDownloadInfo(vid);

        if(info!=null){
            if(info.getState()==DownloadInfo.STATE_DOWNLOADING
                    ||info.getState()==DownloadInfo.STATE_INIT
                    ||info.getState()==DownloadInfo.STATE_EXCEPTION
                    ||info.getState()==DownloadInfo.STATE_WAITING){
                handler.removeCallbacksAndMessages(null);
                downloadManager.pauseDownload(info.taskId);
                btnDownload.setText("暂停");
            }else if(info.getState()==DownloadInfo.STATE_PAUSE){
                downloadManager.startDownload(info.taskId);
                handler.sendEmptyMessageDelayed(MSG_REFRESH_PROGRESS,1000);
            }
            return ;
        }
        downloadManager.createDownload(vid, title, new OnCreateDownloadListener() {
            @Override
            public void onfinish(boolean isNeedRefresh) {
                Toast.makeText(GuidanceDetailActivity.this, title+" 开始下载", Toast.LENGTH_SHORT).show();
            }
        });
        handler.sendEmptyMessageDelayed(MSG_REFRESH_PROGRESS,2000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibtn_back:
                finish();
                break;
            case R.id.btn_download:
                download("XOTI4ODEwMTYw", "魔女范冰冰扑倒黄晓明");
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        playerManager.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        playerManager.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        playerManager.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playerManager.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        playerManager.onBackPressed();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        playerManager.onLowMemory();
    }

    /**
     * 配置发生变化
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        playerManager.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onSearchRequested() {
        return playerManager.onSearchRequested();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean managerKeyDown = playerManager.onKeyDown(keyCode, event);
        if (playerManager.shouldCallSuperKeyDown()) {
            return super.onKeyDown(keyCode, event);
        } else {
            return managerKeyDown;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    /**
     * 相关素材listview适配器
     */
    private class GuidanceDetailAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public GuidanceDetailAdapter(Context context) {
            inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_guidance_detail, null);
            }
            ImageView iv_item = (ImageView) convertView.findViewById(R.id.iv_item);
            TextView tv_videotype = (TextView) convertView.findViewById(R.id.tv_videotype);
            TextView tv_videoname = (TextView) convertView.findViewById(R.id.tv_videoname);

            return convertView;
        }
    }
}
