package com.huiztech.androiddemo;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People.Phones;
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

public class CallActivity extends ListActivity {
    Context mContext = null;
    private static final String[] PHONES_PROJECTION = new String[] { Phones.DISPLAY_NAME, Phones.NUMBER };
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;// 联系人显示名称
    private static final int PHONES_NUMBER_INDEX = 1;
    private ArrayList<String> mContactsName = new ArrayList<String>();
    private ArrayList<String> mContactsNumber = new ArrayList<String>();
    ListView mListView = null;
    MyListAdapter myAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mContext = this;
        mListView = this.getListView();
        // 得到手机通讯录联系人信息
        getPhoneContacts();
        Log.i("xxx", mContactsNumber.get(1));
        myAdapter = new MyListAdapter(this);
        setListAdapter(myAdapter);
        mListView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // 调用系统方法拨打电话
                Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mContactsNumber.get(arg2)));
                startActivity(dialIntent);
            }
        });
        super.onCreate(savedInstanceState);
    }

    private void getPhoneContacts() {
        ContentResolver resolver = mContext.getContentResolver();
        Cursor phoneCursor = resolver.query(null, PHONES_PROJECTION, null, null, null);
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                // 得到联系人号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                if (TextUtils.isEmpty(phoneNumber))
                    continue;
                // 得到联系人名称
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                // 得到联系人ID
                // Long cantactid =
                // phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);

                mContactsName.add(contactName);
                mContactsNumber.add(phoneNumber);
            }
            phoneCursor.close();
        }
    }

    private void getSIMContacts() {
        ContentResolver resolver = mContext.getContentResolver();
        // 获取Sims卡联系人信息
        Uri uri = Uri.parse("content：//icc/adn");
        Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null, null);
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                // 得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                // 当手机号码为空的或者为空字段，跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;
                // 得到联系人名称
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);

                mContactsName.add(contactName);
                mContactsNumber.add(phoneNumber);
            }
            phoneCursor.close();
        }

    }

    class MyListAdapter extends BaseAdapter {
        public MyListAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return mContactsName.size();
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
            TextView name = null;
            TextView number = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.call_list_item, null);
                name = (TextView) convertView.findViewById(R.id.nameTextView);
                number = (TextView) convertView.findViewById(R.id.numberTextView);
            }
            name.setText(mContactsName.get(position));
            number.setText(mContactsNumber.get(position));
            return convertView;
        }

    }
}
