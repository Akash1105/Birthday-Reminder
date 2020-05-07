package com.manjurulhoque.scheduler.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.manjurulhoque.scheduler.R;
import com.manjurulhoque.scheduler.activity.sms.SmsSchedulerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AutoSmsScheduler extends AppCompatActivity {

    @BindView(R.id.cardSmsScheduler)
    public CardView cardSmsScheduler;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_sms_scheduler);
        ButterKnife.bind(this);

        cardSmsScheduler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AutoSmsScheduler.this, SmsSchedulerActivity.class));
            }
        });

        AutoSmsScheduler.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return AutoSmsScheduler.context;
    }
}
