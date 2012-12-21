package com.huiztech.androiddemo;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NotificationActivity extends Activity {

    // 声明通知消息管理器
    NotificationManager m_NotificationManager;
    Intent m_Intent;
    PendingIntent m_PendingIntent;
    // 声明Notification对象
    Notification m_Notification;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        // 初始化NotificationManager对象
        m_NotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public void click(View view) {
        switch (view.getId()) {
        case R.id.btn_notification_start:
            showNotification();
            break;
        case R.id.btn_notification_stop:
            cancelNotification();
            break;

        default:
            break;
        }
    }

    private void showNotification() {
        // 设置点击通知时显示内容的类
        m_PendingIntent = PendingIntent.getActivity(NotificationActivity.this, 0, getIntent(), 0);
        // 构造Notification对象
        m_Notification = new Notification();
        // 设置通知在状态拦显示的图标
        m_Notification.icon = R.drawable.ic_launcher;
        // 当我们点击时显示的内容
        m_Notification.tickerText = "开始登陆系统.........";
        // 通知时发出的默认的声音
        m_Notification.defaults = Notification.DEFAULT_SOUND;
        // 设置通知显示的参数
        m_Notification.setLatestEventInfo(NotificationActivity.this, "MYIMSYS", "登陆通知",
                m_PendingIntent);
        // 可以理解为执行这个通知
        m_NotificationManager.notify(0, m_Notification);
    }

    private void cancelNotification() {
        m_NotificationManager.cancelAll();

    }

}