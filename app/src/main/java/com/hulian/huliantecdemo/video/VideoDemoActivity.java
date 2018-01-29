package com.hulian.huliantecdemo.video;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.hulian.huliantecdemo.BaseActivity;
import com.hulian.huliantecdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class VideoDemoActivity extends BaseActivity {

    @Bind(R.id.player_list_video)
    JCVideoPlayerStandard playerListVideo;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_video_demo);
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);

        boolean setUp = playerListVideo.setUp("视频地址", JCVideoPlayer.SCREEN_LAYOUT_LIST, "视频名字");
        if (setUp) {
            Glide.with(this).load("图片地址").into(playerListVideo.thumbImageView);
        }
        playerListVideo.startFullscreen(this, JCVideoPlayerStandard.class, "视频地址", "视频名字");
        //模拟用户点击开始按钮，NORMAL状态下点击开始播放视频，播放中点击暂停视频
        playerListVideo.startButton.performClick();

    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()){
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }







}
