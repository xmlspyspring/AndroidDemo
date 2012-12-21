package com.huiztech.androiddemo.util;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.huiztech.androiddemo.player.Music;

public class MusicList {

    public static List<Music> getMusicData(Context context) {
        List<Music> musicList = new ArrayList<Music>();
        ContentResolver cr = context.getContentResolver();
        if (cr != null) {
            //获取全部歌曲
            Cursor cursor = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                    MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
            if (null == cursor) {
                return null;
            }
            //遍历游标 常用do-while
            if (cursor.moveToFirst()) {
                do {
                    Music music = new Music();
                    String title = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.TITLE));
                    String singer = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    if ("<unknown>".equals(singer)) {
                        singer = "未知艺术家";
                    }
                    String album = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                    long time = cursor.getLong(cursor
                            .getColumnIndex(MediaStore.Audio.Media.DURATION));
                    String url = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.DATA));
                    String name = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    String sbr = name.substring(name.length() - 3, name.length());

                    if (sbr.equals("mp3")) {
                        music.setTitle(title);
                        music.setSinger(singer);
                        music.setAlbum(album);
                        music.setSize(size);
                        music.setTime(time);
                        music.setUrl(url);
                        music.setName(name);
                        musicList.add(music);
                    }
                } while (cursor.moveToNext());
            }
        }
        return musicList;

    }
}
