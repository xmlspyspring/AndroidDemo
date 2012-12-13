package com.huiztech.androiddemo;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CameraActivity extends Activity {
    private Button btnPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);

        btnPhoto = (Button) findViewById(R.id.btn_photo);
        btnPhoto.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            }
        });
    }

    protected void onActivityResult1(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Activity.RESULT_OK) {

            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED))
                ;// 检测sd是否可用
            Log.v("TestFile", "SD card is not avaiable/writeable right now");
            return;
        }

        Bundle bundle = data.getExtras();
        Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式

        FileOutputStream b = null;
        File file = new File("mnt/sdcard2/myImage");
        file.mkdirs();// 创建文件夹
        String fileName = "mnt/sdcard2/myImage/111.jpg";

        try {
            b = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                b.flush();
                b.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode != 0) {
                return;
            }
            super.onActivityResult(requestCode, resultCode, data);
            Bundle extras = data.getExtras();
            Bitmap b = (Bitmap) extras.get("data");

            // 得到图片对图片处理...

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
