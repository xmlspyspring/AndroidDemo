package com.huiztech.androiddemo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

    String[] str = { "发邮件", "打电话", "发短信", "浏览器", "音乐播放器", "视频播放器", "照相", "语音识别", "传感器", "重力感应器",
            "Google Map服务", "导航技术", "动画技术", "蓝牙技术", "手势操作", "界面特效", "录音", "GPS定位" };
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
        default:
            break;
        }
    }
}
