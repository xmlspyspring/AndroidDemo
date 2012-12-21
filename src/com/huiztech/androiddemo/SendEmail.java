package com.huiztech.androiddemo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SendEmail extends Activity {
    private Button btnsend, btnFsend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendemail);
        btnsend = (Button) findViewById(R.id.btnSend);

        btnsend.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent data = new Intent(android.content.Intent.ACTION_SENDTO);
                data.setData(Uri.parse("mailto:303230815@qq.com"));
                data.putExtra(Intent.EXTRA_SUBJECT, "标题");
                data.putExtra(Intent.EXTRA_TEXT, "内容");
                startActivity(data);
            }
        });
        // 以上为发一条email
        // 一下为发多条email
        btnFsend = (Button) findViewById(R.id.btnFSend);
        btnFsend.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent data = new Intent(Intent.ACTION_SENDTO);// 建立Intent对象，设置对象动作
                data.setData(Uri.parse("mailto:qq10000@qq.com"));// 发件人地址
                data.putExtra(Intent.EXTRA_EMAIL, new String[] { "ls8707@163.com", "" });// 设置对方邮件地址
                data.putExtra(Intent.EXTRA_CC, new String[] { "" });// 抄送地址
                                                                    // 转发的地址
                data.putExtra(Intent.EXTRA_BCC, new String[] { "" });// 密送地址
                                                                     // 把邮件内容加密发出去
                data.putExtra(Intent.EXTRA_SUBJECT, "标题标题");// 邮件标题
                data.putExtra(Intent.EXTRA_TEXT, "内容");// 邮件内容
                startActivity(data);
            }
        });

    }

}
