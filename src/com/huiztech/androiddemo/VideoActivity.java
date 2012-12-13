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
                videoView.setVideoPath("mnt/sdcard/game.mp4");
                /*
                 * videoView.setVideoPath(
                 * "android.resource://com.huiztech.androiddemo/" + R.raw.game);
                 */
                videoView.setMediaController(new MediaController(VideoActivity.this));
                videoView.requestFocus();
            }
        });
        // play
        PlayButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                videoView.start();
            }
        });
        // pause
        PauseButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                videoView.pause();
            }
        });
    }

}
