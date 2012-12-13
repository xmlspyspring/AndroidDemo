package com.huiztech.androiddemo;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MusicActivity extends Activity {
    private Button startBtn = null;
    private Button stopBtn = null;
    private Button lastBtn = null;
    private Button nextBtn = null;
    private Button speedBtn = null;
    private Button backBtn = null;
    private TextView playtime = null;
    private TextView durationTime = null;
    private SeekBar seekbar = null;
    private Handler handler = null;
    private Handler speedHandler = null;
    private Runnable updateThread = null;
    private int currentPosition;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music);
        mp = new MediaPlayer();
        try {
            mp.setDataSource("mnt/sdcard2/Music/Hey Jude.mp3");
            mp.prepare();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        playtime = (TextView) findViewById(R.id.playtime);
        durationTime = (TextView) findViewById(R.id.durationTime);
        startBtn = (Button) findViewById(R.id.startBtn);
        stopBtn = (Button) findViewById(R.id.stopBtn);
        lastBtn = (Button) findViewById(R.id.lastBtn);
        nextBtn = (Button) findViewById(R.id.nextBtn);
        speedBtn = (Button) findViewById(R.id.speedBtn);
        backBtn = (Button) findViewById(R.id.backBtn);
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        seekbar.setMax(mp.getDuration());
        handler = new Handler();
        updateThread = new Runnable() {

            @Override
            public void run() {
                seekbar.setProgress(mp.getCurrentPosition());
                handler.postDelayed(updateThread, 100);
            }

        };
        startBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mp.start();//开始当前播放
            }
        });
        stopBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mp.pause();//取消当前播放
                handler.removeCallbacks(updateThread);
            }
        });
        seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.start();//停止拖动进度条时，音乐开始播放
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mp.pause();//开始拖动进度条时，音乐暂停播放
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mp.seekTo(progress);//当进度条的值改变时，音乐播放器从新的位置开始播放
                }
            }
        });
    }
}
