package com.rishi.family.views.activities;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.rishi.family.R;
import com.rishi.family.sharedpreference.SharedPreferencesManager;
import com.rishi.family.views.fragments.TimePickerFragment;

public class NotificationsSettingsActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    //Components
    private ImageButton mImageButtonNotificationTIme, mImageButtonBackArrow;
    private TextView mTextViewNotificationTime;
    private Spinner mSpinnerReminderDays;
    private Button mButtonApply;

    //Variables
    private int mNotificationReminderDays = 0;
    private int mNotificationHour = 0;
    private int mNotificationMinute = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_settings);
        //Getting the components
        getComponents();
        //Setting the listeners
        setListeners();
        //Getting and setting the array data to the spinner
        setSpinnerData();
        //Getting the shared prefernces and setting the values
        getSharedpreferencesValues();


    }

    private void getSharedpreferencesValues() {
        mNotificationHour = SharedPreferencesManager.getNotificationTimeHour(this);
        mNotificationMinute = SharedPreferencesManager.getNotificationTimeMinute(this);
        mNotificationReminderDays = SharedPreferencesManager.getNotificationDaysBefore(this);

        String notificationTime = mNotificationHour + ":" + mNotificationMinute;
        mTextViewNotificationTime.setText(notificationTime);
        mSpinnerReminderDays.setSelection(mNotificationReminderDays);
    }

    private void setSpinnerData() {

        String[] arrayReminderDaya = getResources().getStringArray(R.array.array_reminder_days);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arrayReminderDaya);

        mSpinnerReminderDays.setAdapter(adapter);
    }

    private void getComponents() {
        mImageButtonNotificationTIme = (ImageButton) findViewById(R.id.imagebutton_notificationtime);
        mTextViewNotificationTime = (TextView) findViewById(R.id.textview_notificationtime);
        mImageButtonBackArrow = (ImageButton) findViewById(R.id.imagebutton_backarrow);
        mSpinnerReminderDays = (Spinner) findViewById(R.id.spinner_reminderdays);
        mButtonApply = (Button) findViewById(R.id.button_apply);


    }

    private void setListeners() {
        mImageButtonNotificationTIme.setOnClickListener(this);
        mImageButtonBackArrow.setOnClickListener(this);
        mButtonApply.setOnClickListener(this);

    }

    void gotoBirthdaysListActivity() {
        Intent intent = new Intent(NotificationsSettingsActivity.this, BirthdaysListActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v == mImageButtonNotificationTIme) {
            DialogFragment newFragment = new TimePickerFragment();
            newFragment.show(getFragmentManager(), "TimePicker");
        }

        if (v == mImageButtonBackArrow) {

            onBackPressed();

        }

        if (v == mButtonApply) {

            mNotificationReminderDays = mSpinnerReminderDays.getSelectedItemPosition();

            SharedPreferencesManager.saveValues(this, mNotificationReminderDays, mNotificationHour, mNotificationMinute);

            gotoBirthdaysListActivity();
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        mNotificationHour = hourOfDay;
        mNotificationMinute = minute;

        String notificationTime = hourOfDay + ":" + minute;
        mTextViewNotificationTime.setText(notificationTime);

    }

    @Override
    public void onBackPressed() {

        gotoBirthdaysListActivity();
    }
}
