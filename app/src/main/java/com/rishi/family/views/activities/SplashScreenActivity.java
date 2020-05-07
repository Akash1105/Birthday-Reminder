package com.rishi.family.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.rishi.family.R;
import com.rishi.family.utilities.MyBounceInterpolator;

public class SplashScreenActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 4000;

    //Components
    private ImageView mImageViewAppLogo;
    private TextView mTextViewAppTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getComponents();

        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                Animation animation = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.scale_up);


                // Use bounce interpolator with amplitude 0.2 and frequency 20
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20.0);
                animation.setInterpolator(interpolator);

                mImageViewAppLogo.startAnimation(animation);
                mImageViewAppLogo.setVisibility(View.VISIBLE);

                final Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        Animation animation = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.scale_up);

                        // Use bounce interpolator with amplitude 0.2 and frequency 20
                        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20.0);
                        animation.setInterpolator(interpolator);

                        mTextViewAppTitle.startAnimation(animation);
                        mTextViewAppTitle.setVisibility(View.VISIBLE);
                    }
                }, 1000);
            }
        }, 1000);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreenActivity.this, BirthdaysListActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void getComponents() {

        mImageViewAppLogo = (ImageView) findViewById(R.id.imageview_app_logo);
        mTextViewAppTitle = (TextView) findViewById(R.id.textview_app_title);
    }
}
