package com.huiztech.androiddemo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MusicActivity extends Activity {
    Context mContext = null;
    private static final String[] MUSIC_PROJECTION = new String[] { MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST };
    private static final int MUSIC_NAME_INDEX = 0;
    private static final int MUSIC_SINGER_INDEX = 1;
    private ArrayList<String> musicNameArray = new ArrayList<String>();//歌曲名字
    private ArrayList<String> musicSingerArray = new ArrayList<String>();//演唱者
    MyListAdapter myAdapter = null;//自定义Adapter
    ListView mListView = null;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_list);
        mContext = this;
        mListView = (ListView) findViewById(R.id.musicListView);
        intent = new Intent(MusicActivity.this, MusicPlayerActivity.class);
        getMusic();
        myAdapter = new MyListAdapter(this);
        mListView.setAdapter(myAdapter);
        //ListView 每一条Item 点击事件
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                intent.putExtra("position", arg2);
                startActivity(intent);
            }
        });
    }

    //获取手机本地音乐
    private void getMusic() {
        ContentResolver resolver = mContext.getContentResolver();
        Cursor musicCursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                MUSIC_PROJECTION, null, null, null);
        if (musicCursor != null) {
            while (musicCursor.moveToNext()) {
                String musicName = musicCursor.getString(MUSIC_NAME_INDEX);//获得音乐名
                if (TextUtils.isEmpty(musicName))
                    continue;
                String musicSinger = musicCursor.getString(MUSIC_SINGER_INDEX);//获得演唱者名
                Log.i("musicName", musicName);
                Log.i("musicSinger", musicSinger);
                musicNameArray.add(musicName);
                musicSingerArray.add(musicSinger);
            }
            intent.putExtra("musicNameArray", musicNameArray);
            intent.putExtra("musicSingerArray", musicSingerArray);
            musicCursor.close();
        }
    }

    class MyListAdapter extends BaseAdapter {
        public MyListAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return musicNameArray.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView musicName = null;
            TextView musicSinger = null;
            convertView = LayoutInflater.from(mContext).inflate(R.layout.music_list_item, null);
            musicName = (TextView) convertView.findViewById(R.id.musicTextView);
            musicSinger = (TextView) convertView.findViewById(R.id.singerTextView);
            musicName.setText(musicNameArray.get(position));
            musicSinger.setText(musicSingerArray.get(position));
            return convertView;
        }

    }
}
