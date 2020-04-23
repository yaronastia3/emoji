package com.meemory.emoji;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

class SchedulerHelper {
    static final String TYPE_EXTRA = "type";

    void msg(Context context){
        Calendar calendar = Calendar.getInstance();

        int n = 0;
        for (int i = 0; i < 30; i++) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            if (i <= 7) {
                calendar.add(Calendar.DAY_OF_WEEK, i);
                calendar.set(Calendar.HOUR_OF_DAY, 20);
                calendar.set(Calendar.MINUTE, 0);
                msg(calendar, context, i);
            }
            if (i >= 9){
                calendar.add(Calendar.DAY_OF_WEEK, i + (2 * n));
                calendar.set(Calendar.HOUR_OF_DAY, 20);
                calendar.set(Calendar.MINUTE, 0);
                msg(calendar, context, i);
                n++;
            }
        }
    }

    private void msg(Calendar calendar, Context context, int type) {
        Intent i = new Intent(context, ReceiverBroadcaster.class);
        i.putExtra(TYPE_EXTRA, type);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, type, i, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManagerRTC = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManagerRTC.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
