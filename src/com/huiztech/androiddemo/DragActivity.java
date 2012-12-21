package com.huiztech.androiddemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;

public class DragActivity extends Activity {
    private ImageButton imageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drag);
        imageBtn = (ImageButton) findViewById(R.id.imageBtn);
        imageBtn.setOnTouchListener(new OnTouchListener() {
            int[] position = new int[] { 0, 0 };

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                //                int left = v.getLeft();
                //                int top = v.getTop();
                switch (event.getAction()) {
                //手指初次触摸
                case MotionEvent.ACTION_DOWN:
                    position[0] = (int) event.getX();
                    position[1] = y - v.getTop();
                    break;
                //手指滑动
                case MotionEvent.ACTION_MOVE:
                    v.layout(x - position[0], y - position[1], x + 365 - position[1], y
                            - position[1] + 135);
                    v.postInvalidate();
                    break;
                // MotionEvent.ACTION_UP 手指抬起
                default:
                    break;
                }
                return false;
            }
        });
    }
}
