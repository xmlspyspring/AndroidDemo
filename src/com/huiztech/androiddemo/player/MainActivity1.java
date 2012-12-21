package com.huiztech.androiddemo.player;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;

import com.huiztech.androiddemo.R;

public class MainActivity1 extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置全屏 无标题
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main_1);

        Resources resources = getResources();//资源
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        intent = new Intent().setClass(this, ListActivity.class);
        spec = tabHost.newTabSpec("音乐").setIndicator("音乐", resources.getDrawable(R.drawable.item))
                .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, ArtistsActivity.class);
        spec = tabHost.newTabSpec("艺术家")
                .setIndicator("艺术家", resources.getDrawable(R.drawable.artist)).setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, AlbumsActivity.class);
        spec = tabHost.newTabSpec("专辑").setIndicator("专辑", resources.getDrawable(R.drawable.album))
                .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, SongsActivity.class);
        spec = tabHost.newTabSpec("最近播放")
                .setIndicator("最近播放", resources.getDrawable(R.drawable.album)).setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
    }
}
