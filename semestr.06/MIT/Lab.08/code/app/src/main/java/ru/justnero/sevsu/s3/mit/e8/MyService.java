package ru.justnero.sevsu.s3.mit.e8;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MyService extends Service {
    public static ArrayList<Alarm> list = new ArrayList<>();

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        thread.start();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    Thread thread = new Thread(new Runnable() {
        NotificationManager nm;

        @Override
        public void run() {
            nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (Alarm alarm : list) {
                    Calendar now = Calendar.getInstance();
                    long time = now.get(Calendar.HOUR) * 100 + now.get(Calendar.MINUTE);
                    if (alarm.time != 0) {
                        if (alarm.time <= time) {
                            makeNotification(alarm.message);
                            list.remove(alarm);
                        }
                    }

                    if (alarm.date != null) {
                        if (alarm.date.before(now) || alarm.date.compareTo(now) == 0) {
                            makeNotification(alarm.message);
                            list.remove(alarm);
                        }
                    }
                }
            }
        }


        public void makeNotification(String text) {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(getApplicationContext())
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("Notification")
                            .setContentText(text);
            nm.notify(mBuilder.mNumber, mBuilder.build());
        }
    });

}
