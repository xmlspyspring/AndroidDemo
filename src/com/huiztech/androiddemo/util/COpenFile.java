package com.huiztech.androiddemo.util;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class COpenFile {

    //通过文件的扩展名 去打开对于的系统的应用程序
    public static void openApp(Context context, String filePath) {
        //从filePath 获得扩展名  /sdcard/test.txt
        String fileExt = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
        if ("txt".contains(fileExt)) {
            Intent intent = getTextFileIntent(filePath, false);
            // startActivity 是 Activity extends Context.startActivity
            context.startActivity(intent);
        }

        if ("png,gif,jpg".contains(fileExt)) {
            Intent intent = getImageFileIntent(filePath);
            context.startActivity(intent);
        }
        if ("mp3".contains(fileExt)) {
            Intent intent = getAudioFileIntent(filePath);
            context.startActivity(intent);
        }

        if ("pdf".contains(fileExt)) {
            Intent intent = getPdfFileIntent(filePath);
            context.startActivity(intent);
        }

        if ("mp4,3gp".contains(fileExt)) {
            Intent intent = getVideoFileIntent(filePath);
            context.startActivity(intent);
        }
    }

    //android获取一个用于打开HTML文件的intent
    public static Intent getHtmlFileIntent(String param) {
        Uri uri = Uri.parse(param).buildUpon().encodedAuthority("com.android.htmlfileprovider")
                .scheme("content").encodedPath(param).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        //设置打开 文件的类型
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    //android获取一个用于打开图片文件的intent
    public static Intent getImageFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        // image/* 表示所有的图片
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    //android获取一个用于打开PDF文件的intent
    public static Intent getPdfFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    //android获取一个用于打开文本文件的intent
    public static Intent getTextFileIntent(String param, boolean paramBoolean) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //如果是 true 从网络打开文件
        if (paramBoolean) {
            Uri uri1 = Uri.parse(param);
            intent.setDataAndType(uri1, "text/plain");
        } else //如果是 false 从 sdcard 打开文件
        {
            Uri uri2 = Uri.fromFile(new File(param));
            intent.setDataAndType(uri2, "text/plain");
        }
        return intent;
    }

    //android获取一个用于打开音频文件的intent
    public static Intent getAudioFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    //android获取一个用于打开视频文件的intent
    public static Intent getVideoFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

}
