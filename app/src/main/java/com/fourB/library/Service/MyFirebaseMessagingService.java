package com.fourB.library.Service;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;

import com.fourB.library.MainActivity;
import com.fourB.library.R;
import com.fourB.library.SharedPrefManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.core.app.NotificationCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                scheduleJob();
            } else {
                handleNow();
            }
        }

        if (remoteMessage.getNotification() != null) {

            try {
                if( new ForegroundCheckTask().execute(this).get() ) {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("title", remoteMessage.getNotification().getTitle());
                    intent.putExtra("body", remoteMessage.getNotification().getBody());
                    intent.putExtra("type", remoteMessage.getData().get("type"));
                    intent.putExtra("content", remoteMessage.getData().get("content"));
                    intent.putExtra("date", remoteMessage.getData().get("date"));

                    startActivity(intent);
                } else {
                    PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE );
                    PowerManager.WakeLock wakeLock = pm.newWakeLock( PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "myapp:WAKELOCK");
                    wakeLock.acquire(3000);
                    wakeLock.release();

                    sendNotification(remoteMessage.getNotification().getTitle(),
                            remoteMessage.getNotification().getBody(),
                            remoteMessage.getData().get("type"),
                            remoteMessage.getData().get("content"),
                            remoteMessage.getData().get("date"));
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ForegroundCheckTask extends AsyncTask<Context, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Context... contexts) {
            Context context = contexts[0].getApplicationContext();
            return isAppOnForeground(context);
        }

        private Boolean isAppOnForeground(Context context) {
            ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> processInfoList = activityManager.getRunningAppProcesses();
            if( processInfoList == null || processInfoList.size() == 0 ) { return false; }
            for(ActivityManager.RunningAppProcessInfo info : processInfoList) {
                if(info.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && info.processName.equals(getPackageName()) ) {
                    return true;
                }
            }
            return false;
        }
    }


    @Override
    public void onNewToken(String token) {
        sendRegistrationToServer(token);
        SharedPrefManager.writeToken(getApplicationContext(), token);
    }


    private void scheduleJob() {
        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(MyWorker.class)
                .build();
        WorkManager.getInstance().beginWith(work).enqueue();
    }

    private void handleNow() { }

    private void sendRegistrationToServer(String token) {
        SharedPrefManager.writeToken(getApplicationContext(), token);
    }

    private void sendNotification(String messageTitle, String messageBody, String messageType, String messageContent, String messageDate) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("title", messageTitle);
//        intent.putExtra("body", messageBody);
//        intent.putExtra("type", messageType);
//        intent.putExtra("content", messageContent);
//        intent.putExtra("date", messageDate);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_stat_ic_notification)
                        .setContentTitle(messageTitle)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setVibrate(new long[]{1000, 1000})
                        .setLights(Color.BLUE, 1,1)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());

        startActivity(intent);
    }
}
