package com.rishi.family.views.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.rishi.family.R;


public class FamilyTree extends AppCompatActivity {
    AlertDialog.Builder builder;
    WebView webView;
Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_tree);
        mToolbar = findViewById(R.id.toolbar3);
        setToolbarProperties();
        builder = new AlertDialog.Builder(this);
        webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.loadUrl("https://www.familyecho.com");

        //     loadAlert();
    }
    void setToolbarProperties() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle(null);
    }


    private void loadAlert() {
        builder.setMessage("").setTitle("Just A Tip");

        //Setting message manually and performing action on button click
        builder.setMessage("This website works best on pc/laptop. Do you still want to visit?")
                .setCancelable(false)
                .setPositiveButton("Visit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        Toast.makeText(getApplicationContext(), "you choose yes action for alertbox",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Its ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "you choose no action for alertbox",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        gotoBirthdaysListActivity();
    }

    void gotoBirthdaysListActivity() {
        Intent intent = new Intent(FamilyTree.this, BirthdaysListActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    public void onClick(View view) {
        onBackPressed();
    }
}


