package com.huiztech.androiddemo.player;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.huiztech.androiddemo.R;
import com.huiztech.androiddemo.util.MusicList;
import com.huiztech.androiddemo.player.MusicAdapter;
import com.huiztech.androiddemo.player.Music;

public class ListActivity extends Activity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listmusic_1);

        listView = (ListView) this.findViewById(R.id.listAllMusic);
        List<Music> listMusic = MusicList.getMusicData(getApplicationContext());
        MusicAdapter adapter = new MusicAdapter(this, listMusic);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(ListActivity.this, MusicActivity.class);
                intent.putExtra("id", arg2);
                startActivity(intent);
            }
        });
    }
}
