package com.huiztech.androiddemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Gesture extends Activity {

    private Button btn_gesture, btn_drag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gesturebtn);
        btn_gesture = (Button) findViewById(R.id.btn_gesture);
        btn_drag = (Button) findViewById(R.id.btn_drag);
        btn_gesture.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Gesture.this, GestureActivity.class);
                startActivity(intent);
            }
        });
        btn_drag.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Gesture.this, DragActivity.class);
                startActivity(intent);
            }
        });
    }
}
