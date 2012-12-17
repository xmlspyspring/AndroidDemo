package com.huiztech.androiddemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.video);

        final VideoView videoView = (VideoView) findViewById(R.id.VideoView01);

        Button PauseButton = (Button) this.findViewById(R.id.PauseButton);
        Button LoadButton = (Button) this.findViewById(R.id.LoadButton);
        Button PlayButton = (Button) this.findViewById(R.id.PlayButton);

        // load
        PauseButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                videoView.setVideoPath("mnt/sdcard/game.mp4");// video的存放路径
                /*
                 * videoView.setVideoPath(
                 * "android.resource://com.huiztech.androiddemo/" + R.raw.game);
                 */
                videoView.setMediaController(new MediaController(VideoActivity.this));// 视频控制控件
                                                                                      // 如
                                                                                      // 暂停
                                                                                      // 快进
                videoView.requestFocus();// VideoView获取焦点
            }
        });
        // play
        PlayButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                videoView.start();// 开始播放视频
            }
        });
        // pause
        PauseButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                videoView.pause();// 暂停播放视频
            }
        });
    }

}
