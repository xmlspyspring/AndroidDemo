package com.huiztech.androiddemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SendEmail extends Activity {
    private Button btnsend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendemail);
        btnsend = (Button) findViewById(R.id.btnSend);
        btnsend.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                String[] recipients = { "接收方邮件地址", "接收方邮件地址" };
                String subject = "邮件标题";
                String text = "邮件内容";
                Intent intent = new Intent();
                intent.setType("text/plain");
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(intent, "Sending..."));
            }
        });

    }

}
