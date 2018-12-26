package soft.dot.com.campingandrandoneespot.com.dot.soft.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.widget.Chronometer;
import android.widget.RemoteViews;
import android.widget.Toast;

import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.activities.FreeRaceActivity;

public class NotificationReciever extends BroadcastReceiver {
    public NotificationReciever() {
        // chihemek
    }

    @Override
    public void onReceive(Context context, Intent intesnt) {
        Toast.makeText(context, "Toasted", Toast.LENGTH_SHORT).show();
showNotification(context);
    }


    private void showNotification(Context context ) {



        Intent intent = new Intent(context, FreeRaceActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intent1 = new Intent(context, NotificationReciever.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, 0, intent1, PendingIntent.FLAG_ONE_SHOT);
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.rimel_landscape_2);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, FreeRaceActivity.CHANNEL_ID)
                .setLargeIcon(largeIcon)
                .setContentTitle("Title")
                .setSmallIcon(R.mipmap.ic_icone_round)
                .addAction(R.drawable.ic_action_home, "Unpause", pendingIntent1)

                .setStyle(new android.support.v4.media.app.NotificationCompat.DecoratedMediaCustomViewStyle().setShowActionsInCompactView(0))
                //().bigLargeIcon(largeIcon).bigPicture(largeIcon).setBigContentTitle("Race's Tracking"))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManagerCompat.notify(101, mBuilder.build());
    }

}