package com.rishi.family.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.rishi.family.R;

public class GiftRecomendation extends AppCompatActivity {
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_recomendation);
        WebView mywebview = (WebView) findViewById(R.id.webview);
        mToolbar = findViewById(R.id.toolbar3);
        setToolbarProperties();
        WebSettings webSettings = mywebview.getSettings();
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAllowFileAccess(true);
        mywebview.loadUrl("https://www.uncommongoods.com/sunny/giftfinder/intro");
    }

    void setToolbarProperties() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle(null);
    }

    @Override
    public void onBackPressed() {
        gotoBirthdaysListActivity();
    }

    void gotoBirthdaysListActivity() {
        Intent intent = new Intent(GiftRecomendation.this, BirthdaysListActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    public void onClick(View view) {
        onBackPressed();
    }
}
