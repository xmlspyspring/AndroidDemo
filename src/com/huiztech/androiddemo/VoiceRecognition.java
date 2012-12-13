package com.huiztech.androiddemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class VoiceRecognition extends Activity implements OnClickListener {
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    private ListView showListView;
    private Button speakBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_recognition);

        speakBtn = (Button) findViewById(R.id.speakBtn);
        showListView = (ListView) findViewById(R.id.showListView);

        PackageManager pm = getPackageManager();
        List activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        //        speakBtn.setOnClickListener(this);
        if (activities.size() != 0) {
            speakBtn.setOnClickListener(this);
        } else {
            speakBtn.setEnabled(false);
            speakBtn.setText("Recognizer not present");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.speakBtn) {
            startVoiceRecognitionActivity();
        }
    }

    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);//通过Intent传递语音识别模式
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,//语言模式和自由形式的语音识别
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognitiion demo");//提示语音开始
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);//开始执行Intent语音识别
    }

    //当语音结束时的回调函数onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            //取得语音的字符
            ArrayList matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            showListView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                    matches));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
