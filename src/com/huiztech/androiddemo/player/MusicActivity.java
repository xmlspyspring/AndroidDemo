package com.huiztech.androiddemo.player;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.huiztech.androiddemo.R;
import com.huiztech.androiddemo.util.MusicList;

public class MusicActivity extends Activity implements SensorEventListener {

    private TextView textName, textSinger, textStartTime, textEndTime;
    private ImageButton imageBtnLast, imageBtnRewind, imageBtnForward, imageBtnNext, imageBtnLoop,
            imageBtnRandom;
    public static ImageButton imageBtnPlay;
    //    public static LrcView lrc_view;
    private ImageView icon;
    private SeekBar seekBar1;
    private AudioManager audioManager;//音量管理
    private int maxVolume;
    private int currentVolume;
    private SeekBar seekBarVolume;
    private List<Music> lists;
    private Boolean isPlaying = false;
    private static int id = 1;
    private static int currentId = 2;
    private static Boolean replaying = false;
    private MyProgressBroadCastReceiver receiver;
    private MyCompletionListner completionListner;
    public static Boolean isLoop = true;
    private SensorManager sensorManager;
    private boolean mRegisteredSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.music_1);

        textName = (TextView) this.findViewById(R.id.music_name);
        textSinger = (TextView) this.findViewById(R.id.music_singer);
        textStartTime = (TextView) this.findViewById(R.id.music_start_time);
        textEndTime = (TextView) this.findViewById(R.id.music_end_time);
        seekBar1 = (SeekBar) this.findViewById(R.id.music_seekBar);
        //icon = (ImageView) this.findViewById(R.id.image_icon);
        imageBtnLast = (ImageButton) this.findViewById(R.id.music_lasted);
        imageBtnRewind = (ImageButton) this.findViewById(R.id.music_rewind);
        imageBtnPlay = (ImageButton) this.findViewById(R.id.music_play);
        imageBtnForward = (ImageButton) this.findViewById(R.id.music_foward);
        imageBtnNext = (ImageButton) this.findViewById(R.id.music_next);
        imageBtnLoop = (ImageButton) this.findViewById(R.id.music_loop);
        seekBarVolume = (SeekBar) this.findViewById(R.id.music_volume);
        imageBtnRandom = (ImageButton) this.findViewById(R.id.music_random);
        //        lrc_view = (LrcView) findViewById(R.id.LyricShow);

        imageBtnLast.setOnClickListener(new MyListener());
        imageBtnRewind.setOnClickListener(new MyListener());
        imageBtnPlay.setOnClickListener(new MyListener());
        imageBtnForward.setOnClickListener(new MyListener());
        imageBtnNext.setOnClickListener(new MyListener());
        imageBtnLoop.setOnClickListener(new MyListener());
        imageBtnRandom.setOnClickListener(new MyListener());
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        lists = MusicList.getMusicData(this);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);// 获得最大音量
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);// 获得当前音量
        seekBarVolume.setMax(maxVolume);
        seekBarVolume.setProgress(currentVolume);
        seekBarVolume.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress,
                        AudioManager.FLAG_ALLOW_RINGER_MODES);
            }
        });
        //电话状态监听 未完成
    }

    private class MobliePhoneStateListener extends PhoneStateListener {

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
            case TelephonyManager.CALL_STATE_IDLE://无任何状态时
                Intent intent = new Intent(MusicActivity.this, MusicService.class);
                intent.putExtra("play", "playing");
                intent.putExtra("id", id);
                startService(intent);
                isPlaying = true;
                imageBtnPlay.setImageResource(R.drawable.play1);
                replaying = true;
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK://接起电话时
            case TelephonyManager.CALL_STATE_RINGING://电话进来时   
                Intent intent2 = new Intent(MusicActivity.this, MusicService.class);
                intent2.putExtra("play", "pause");
                startService(intent2);
                isPlaying = false;
                imageBtnPlay.setImageResource(R.drawable.play1);
                replaying = false;
                break;
            default:
                break;
            }
        }

    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(receiver);
        this.unregisterReceiver(completionListner);
        super.onDestroy();

    }

    public class MyProgressBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            int position = intent.getIntExtra("position", 0);
            int total = intent.getIntExtra("total", 0);
            int progress = position * 100 / total;
            textStartTime.setText(toTime(position));
            seekBar1.setProgress(progress);
            seekBar1.invalidate();
        }
    }

    private class MyCompletionListner extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            Music m = lists.get(MusicService._id);
            textName.setText(m.getTitle());
            textSinger.setText(m.getSinger());
            textEndTime.setText(toTime((int) m.getTime()));
            imageBtnPlay.setImageResource(R.drawable.pause1);
        }

    }

    @Override
    protected void onPause() {
        if (mRegisteredSensor) {
            sensorManager.unregisterListener(this);
            mRegisteredSensor = false;
        }
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensors.size() > 0) {
            Sensor sensor = sensors.get(0);
            mRegisteredSensor = sensorManager.registerListener(this, sensor,
                    SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        receiver = new MyProgressBroadCastReceiver();
        IntentFilter filter = new IntentFilter("cn.com.karl.progress");
        this.registerReceiver(receiver, filter);

        id = getIntent().getIntExtra("id", 1);
        if (id == currentId) {
            Music m = lists.get(id);
            textName.setText(m.getTitle());
            textSinger.setText(m.getSinger());
            textEndTime.setText(toTime((int) m.getTime()));
            Intent intent = new Intent(MusicActivity.this, MusicService.class);
            intent.putExtra("play", "replaying");
            intent.putExtra("id", id);
            startService(intent);
            if (replaying == true) {
                imageBtnPlay.setImageResource(R.drawable.pause1);
                ///replaying=false;
                isPlaying = true;
            } else {
                imageBtnPlay.setImageResource(R.drawable.play1);
                //replaying=true;
                isPlaying = false;
            }
        } else {
            Music m = lists.get(id);
            textName.setText(m.getTitle());
            textSinger.setText(m.getSinger());
            textEndTime.setText(toTime((int) m.getTime()));
            imageBtnPlay.setImageResource(R.drawable.pause1);
            Intent intent = new Intent(MusicActivity.this, MusicService.class);
            intent.putExtra("play", "play");
            intent.putExtra("id", id);
            startService(intent);
            isPlaying = true;
            replaying = true;
            currentId = id;
        }

    }

    private class MyListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (v == imageBtnLast) {
                // 第一首
                id = 0;
                Music m = lists.get(0);
                textName.setText(m.getTitle());
                textSinger.setText(m.getSinger());
                textEndTime.setText(toTime((int) m.getTime()));
                imageBtnPlay.setImageResource(R.drawable.pause1);
                Intent intent = new Intent(MusicActivity.this, MusicService.class);
                intent.putExtra("play", "first");
                intent.putExtra("id", id);
                startService(intent);
                isPlaying = true;
            } else if (v == imageBtnRewind) {
                // 前一首
                int id = MusicService._id - 1;
                if (id >= lists.size() - 1) {
                    id = lists.size() - 1;
                } else if (id <= 0) {
                    id = 0;
                }
                Music m = lists.get(id);
                textName.setText(m.getTitle());
                textSinger.setText(m.getSinger());
                textEndTime.setText(toTime((int) m.getTime()));
                imageBtnPlay.setImageResource(R.drawable.pause1);
                Intent intent = new Intent(MusicActivity.this, MusicService.class);
                intent.putExtra("play", "rewind");
                intent.putExtra("id", id);
                startService(intent);
                isPlaying = true;
            } else if (v == imageBtnPlay) {
                // 正在播放
                if (isPlaying == true) {
                    Intent intent = new Intent(MusicActivity.this, MusicService.class);
                    intent.putExtra("play", "pause");
                    startService(intent);
                    isPlaying = false;
                    imageBtnPlay.setImageResource(R.drawable.play1);
                    replaying = false;
                } else {
                    Intent intent = new Intent(MusicActivity.this, MusicService.class);
                    intent.putExtra("play", "playing");
                    intent.putExtra("id", id);
                    startService(intent);
                    isPlaying = true;
                    imageBtnPlay.setImageResource(R.drawable.pause1);
                    replaying = true;
                }
            } else if (v == imageBtnForward) {
                // 下一首
                //                int id=MusicService._id+1;
                if (id >= lists.size() - 1) {
                    id = lists.size() - 1;
                } else if (id <= 0) {
                    id = 0;
                }
                Music m = lists.get(id);
                textName.setText(m.getTitle());
                textSinger.setText(m.getSinger());
                textEndTime.setText(toTime((int) m.getTime()));
                imageBtnPlay.setImageResource(R.drawable.pause1);
                Intent intent = new Intent(MusicActivity.this, MusicService.class);
                intent.putExtra("play", "forward");
                intent.putExtra("id", id);
                startService(intent);
                isPlaying = true;
            } else if (v == imageBtnNext) {
                // 最后一首
                int id = lists.size() - 1;
                Music m = lists.get(id);
                textName.setText(m.getTitle());
                textSinger.setText(m.getSinger());
                textEndTime.setText(toTime((int) m.getTime()));
                imageBtnPlay.setImageResource(R.drawable.pause1);
                Intent intent = new Intent(MusicActivity.this, MusicService.class);
                intent.putExtra("play", "last");
                intent.putExtra("id", id);
                startService(intent);
                isPlaying = true;
            } else if (v == imageBtnLoop) {
                if (isLoop == true) {
                    // 顺序播放
                    imageBtnLoop.setBackgroundResource(R.drawable.play_loop_spec);
                    isLoop = false;
                } else {
                    // 单曲播放
                    imageBtnLoop.setBackgroundResource(R.drawable.play_loop_sel);
                    isLoop = true;
                }
            } else if (v == imageBtnRandom) {
                imageBtnRandom.setImageResource(R.drawable.play_random_sel);
            }

        }
    }

    //重力感应甩歌 未完成
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //处理精确度改变
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    public String toTime(int time) {

        time /= 1000;
        int minute = time / 60;
        int hour = minute / 60;
        int second = time % 60;
        minute %= 60;
        return String.format("%02d:%02d", minute, second);
    }
}
