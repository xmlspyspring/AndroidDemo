package com.huiztech.androiddemo;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class RecordActivity extends Activity {
    private Button btnStart, btnStop;
    private MediaRecorder mediaRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);// 让界面横屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉界面标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 重新设置界面大小
        setContentView(R.layout.record);
        init();

    }

    private void init() {
        btnStart = (Button) findViewById(R.id.btn_start);
        btnStop = (Button) findViewById(R.id.btn_stop);
        btnStart.setOnClickListener(new recordListener());
        btnStop.setOnClickListener(new recordListener());
    }

    class recordListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            if (v == btnStart) {
                initializeAudio();
            }
            if (v == btnStop) {
                mediaRecorder.stop();// 停止刻录
                mediaRecorder.release();// 刻录完成一定要释放资源
            }

        }

        private void initializeAudio() {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            // 设置MediaRecorder的音频源为麦克风
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            // 设置MediaRecorder录制的音频格式
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            // 设置MediaRecorder录制音频的编码为amr
            mediaRecorder.setOutputFile("/mnt/sdcard2/peipei.amr");
            // 设置录制好的音频文件保存路径
            try {
                mediaRecorder.prepare();// 准备录制
                mediaRecorder.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
