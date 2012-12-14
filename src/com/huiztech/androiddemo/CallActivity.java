package com.huiztech.androiddemo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.Phones;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CallActivity extends Activity {
    Context mContext = null;
    private static final String[] PHONES_PROJECTION = new String[] { Phones.DISPLAY_NAME,
            Phones.NUMBER };//联系人信息数组
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;//联系人姓名索引常量
    private static final int PHONES_NUMBER_INDEX = 1;//联系人号索引常量
    private ArrayList<String> mContactsName = new ArrayList<String>();//联系人姓名
    private ArrayList<String> mContactsNumber = new ArrayList<String>();//联系人号码数组
    MyListAdapter myAdapter = null;//自定义Adapter
    ListView mListView = null;//listview列表视图

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_list);
        mContext = this;
        mListView = (ListView) findViewById(R.id.callListView);
        getPhoneContacts();
        getSIMContacts();
        myAdapter = new MyListAdapter(this);
        mListView.setAdapter(myAdapter);
        //监听listView每一条Item点击事件
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //跳转到系统打电话界面  直接给选中的联系人打电话  
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                        + mContactsNumber.get(arg2)));
                startActivity(callIntent);
            }
        });

    }

    //得到手机通讯录联系人信息
    private void getPhoneContacts() {
        //ContentResolver 负责获取ContentProvider 提供的数据
        ContentResolver resolver = mContext.getContentResolver();
        //返回Cursor对象 query方法查询（uri连接通讯录数据表,第二个参数是得到的数据存储位置，
        //第三个参数为返回需要得到的数据相当于SQL语句中的where，
        //第四个参数配合第三个使用，如果第三个有？，第四个参数则替换第三个参数，
        //第五个参数按照什么顺序进行排列，相当于SQL语句中的order by）
        Cursor phoneCursor = resolver
                .query(Phones.CONTENT_URI, PHONES_PROJECTION, null, null, null);
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                //得到联系人号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                if (TextUtils.isEmpty(phoneNumber))
                    continue;
                //得到联系人名称
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                mContactsName.add(contactName);//添加到通讯录姓名集合
                mContactsNumber.add(phoneNumber);//添加到通讯录电话集合
            }
            phoneCursor.close();
        }
    }

    private void getSIMContacts() {
        ContentResolver resolver = mContext.getContentResolver();
        //获取Sims卡联系人信息
        Uri uri = Uri.parse("content：//icc/adn");
        Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null, null);
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                //得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                //当手机号码为空的或者为空字段，跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;
                //得到联系人名称
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
            //返回列表显示长度
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.sms_list_item, null);
            name = (TextView) convertView.findViewById(R.id.SMSnameTextView);
            number = (TextView) convertView.findViewById(R.id.SMSnumberTextView);
            name.setText(mContactsName.get(position));//显示名称
            number.setText(mContactsNumber.get(position));//显示号码
            return convertView;
        }
    }
}
