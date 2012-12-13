package com.huiztech.androiddemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class WebActivity extends Activity {
    EditText editText;
    WebView webView;
    Button btn_OK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        editText = (EditText) findViewById(R.id.EditText);
        webView = (WebView) findViewById(R.id.webView);
        btn_OK = (Button) findViewById(R.id.btn_OK);
        //        webView.getSettings().setJavaScriptEnabled(true);如果网页有JavaScript，则webview必须支持JavaScript
        btn_OK.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                String url = editText.getText().toString();
                webView.loadUrl(url);
                webView.setWebViewClient(new WebViewClientDemo());
            }
        });

    }

    private class WebViewClientDemo extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
