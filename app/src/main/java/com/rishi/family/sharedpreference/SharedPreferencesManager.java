package com.rishi.family.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;


public class SharedPreferencesManager {

    private static final String SHARED_PREFERENCES_NAME = "mySharedPreferences";
    private static final String NOTIFICATION_DAYS_BEFORE = "mNotificationDaysBefore";
    private static final String NOTIFICATION_TIME_HOUR = "mNotificationTimeHour";
    private static final String NOTIFICATION_TIME_MINUTE = "mNotificationTimeMinute";
    private static final String IS_APP_ALREADY_OPENED = "mIsAppAlreadyOpened";


    public static void saveValues(Context context, int daysBefore, int hour, int minute) {

        SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putInt(NOTIFICATION_DAYS_BEFORE, daysBefore);
        editor.putInt(NOTIFICATION_TIME_HOUR, hour);
        editor.putInt(NOTIFICATION_TIME_MINUTE, minute);

        editor.apply();
    }

    public static void appAlreadyOpened(Context context, boolean appAlreadyOpened) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putBoolean(IS_APP_ALREADY_OPENED, appAlreadyOpened);

        editor.apply();
    }

    public static int getNotificationDaysBefore(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        return sp.getInt(NOTIFICATION_DAYS_BEFORE, 0);
    }

    public static int getNotificationTimeHour(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        return sp.getInt(NOTIFICATION_TIME_HOUR, 0);
    }

    public static int getNotificationTimeMinute(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        return sp.getInt(NOTIFICATION_TIME_MINUTE, 0);
    }

    public static boolean isAppAlreadyOpened(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        return sp.getBoolean(IS_APP_ALREADY_OPENED, false);
    }


}
