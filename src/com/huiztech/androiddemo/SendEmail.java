package com.huiztech.androiddemo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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

            @Override
            public void onClick(View v) {
                Intent data = new Intent(android.content.Intent.ACTION_SEND);
                data.setData(Uri.parse("mailto:519213104@.com"));
                data.putExtra(Intent.EXTRA_SUBJECT, "这是标题");
                data.putExtra(Intent.EXTRA_TEXT, "这是内容");
                startActivity(data);
            }
        });

    }

}
