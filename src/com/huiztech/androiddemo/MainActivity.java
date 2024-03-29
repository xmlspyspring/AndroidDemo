package com.huiztech.androiddemo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.huiztech.androiddemo.player.MainActivity1;

public class MainActivity extends ListActivity {

    String[] str = { "发邮件", "打电话", "发短信", "浏览器", "音乐播放器", "视频播放器", "照相", "语音识别", "传感器", "重力感应器",
            "Google Map服务", "导航技术", "动画技术", "蓝牙技术", "手势操作", "界面特效", "录音", "GPS定位", "通知" };
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("activity", "xxxxx");
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, str);
        setListAdapter(aa);
        Log.i("activity", "yyyy");
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position) {
        case 0:
            intent = new Intent(MainActivity.this, SendEmail.class);
            startActivity(intent);
            break;
        case 1:
            intent = new Intent(MainActivity.this, CallActivity.class);
            startActivity(intent);
            break;
        case 2:
            intent = new Intent(MainActivity.this, SMSActivity.class);
            startActivity(intent);
            break;
        case 3:
            intent = new Intent(MainActivity.this, WebActivity.class);
            startActivity(intent);
            break;
        case 4:
            intent = new Intent(MainActivity.this, MainActivity1.class);
            startActivity(intent);
            break;
        case 5:
            intent = new Intent(MainActivity.this, VideoActivity.class);
            startActivity(intent);
            break;
        case 6:
            intent = new Intent(MainActivity.this, CameraActivity.class);
            startActivity(intent);
            break;
        case 7:
            intent = new Intent(MainActivity.this, VoiceRecognition.class);
            startActivity(intent);
            break;
        case 8:
            intent = new Intent(MainActivity.this, G_SensorActivity.class);
            startActivity(intent);
            break;
        case 14:
            intent = new Intent(MainActivity.this, Gesture.class);
            startActivity(intent);
            break;
        case 16:
            intent = new Intent(MainActivity.this, RecordActivity.class);
            startActivity(intent);
            break;
        case 18:
            intent = new Intent(MainActivity.this, NotificationActivity.class);
            startActivity(intent);
            break;

        //        case 17:
        //            intent = new Intent(MainActivity.this, GPSActivity.class);
        //            startActivity(intent);
        //            break;
        default:
            break;
        }
    }
}
