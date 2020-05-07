package com.rishi.family.receivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.rishi.family.R;

public class MyBroadcastReceiver extends BroadcastReceiver {
    public static final int NOTIFICATION_ID = 1;
    String actionUriSMSSend = "com.scheduler.action.SMS_SEND";
    NotificationManager notificationManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (intent.getAction().equals(actionUriSMSSend)) {
            notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            Bundle bundle = intent.getExtras();
//            String message1 = bundle.getString("message");
//            System.out.println("mmm=" + message1);
//            String number = bundle.getString("number");
//            System.out.println("ppp" + number);

            String number = intent.getStringExtra("number");
            String message1 = intent.getStringExtra("message");

            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(number, null, message1, null, null);
                Toast.makeText(context, "SMS sent.", Toast.LENGTH_LONG).show();
                notificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                String channelId = "some_channel_id";
                CharSequence channelName = "Some Channel";
                int importance = NotificationManager.IMPORTANCE_LOW;
                NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notificationManager.createNotificationChannel(notificationChannel);

                Toast.makeText(context, "notification created", Toast.LENGTH_SHORT).show();
                int notifyId = 1;

                Notification notification = new Notification.Builder(context)
                        .setContentTitle("Birthday Reminder")
                        .setContentText("Sms sent to " + number + " successfully")
                        .setSmallIcon(R.drawable.ic_birthday_logo)
                        .setChannelId(channelId)
                        .setAutoCancel(true)
                        .setSound(alarmSound)
                        .setLights(Color.RED, 3000, 3000)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})

                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .build();

                notificationManager.notify(notifyId, notification);


            } catch (Exception e) {
                Toast.makeText(context, "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_sms)
                        .setContentTitle("Scheduler")
                        .setContentText("Sms sent to " + number + " failed");
                mBuilder.setAutoCancel(true);
                //Vibration
                mBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});

                // set sound
                mBuilder.setSound(alarmSound);

                //LED
                mBuilder.setLights(Color.RED, 3000, 3000);
                notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
            }
        }
    }
}
