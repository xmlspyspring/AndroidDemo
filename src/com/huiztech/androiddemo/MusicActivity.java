package com.huiztech.androiddemo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MusicActivity extends Activity {
    Context mContext = null;
    private static final String[] MUSIC_PROJECTION = new String[] { MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST };
    private static final int MUSIC_NAME_INDEX = 0;
    private static final int MUSIC_SINGER_INDEX = 1;
    private ArrayList<String> musicNameArray = new ArrayList<String>();
    private ArrayList<String> musicSingerArray = new ArrayList<String>();
    MyListAdapter myAdapter = null;
    ListView mListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.music_list);
        mListView = (ListView) findViewById(R.id.musicListView);
        getMusic();
        mListView.setAdapter(myAdapter);
    }

    private void getMusic() {
        ContentResolver resolver = mContext.getContentResolver();
        Cursor musicCursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                MUSIC_PROJECTION, null, null, null);
        if (musicCursor != null) {
            while (musicCursor.moveToNext()) {
                String musicName = musicCursor.getString(MUSIC_NAME_INDEX);
                if (TextUtils.isEmpty(musicName))
                    continue;
                String musicSinger = musicCursor.getString(MUSIC_SINGER_INDEX);
                musicNameArray.add(musicName);
                musicSingerArray.add(musicSinger);
            }
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
