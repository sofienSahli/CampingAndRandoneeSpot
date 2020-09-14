package soft.dot.com.campingandrandoneespot.com.dot.soft.utils;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage.AppDatabase;
import soft.dot.com.campingandrandoneespot.com.dot.soft.activities.FreeRaceActivity;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Circuit;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Spot;

public class FreeRaceJobService extends JobService implements LocationListener {
    private static final int NOTIFICATION_ID = 202 ;
    Circuit circuit;
    static public final String CIRCUI_KEY = "circuit_key";
    NotificationCompat.Builder mBuilder;

    public FreeRaceJobService(){
    }
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.e("JOBSERVICE" , " 1");

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
        }
        long id = jobParameters.getExtras().getLong(CIRCUI_KEY);
        Toast.makeText(this, "id : " + id  , Toast.LENGTH_SHORT).show();
        circuit = AppDatabase.getAppDatabase(this).circuitDAO().findById(id);
        showNotification();
        return true;
    }

    private void showNotification() {
        createNotificationChannel();

        Intent intent = new Intent(this, FreeRaceActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_layout_expanded);
        remoteViews.setChronometer(R.id.chronometer2, SystemClock.elapsedRealtime(), null, true);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.rimel_landscape_2);
        mBuilder = new NotificationCompat.Builder(this, FreeRaceActivity.CHANNEL_ID)
                .setLargeIcon(largeIcon)
                .setContentTitle("Enregistrement Du circuit")
                .setSmallIcon(R.mipmap.ic_icone_round)
                .setCustomContentView(remoteViews)
                .setStyle(new android.support.v4.media.app.NotificationCompat.DecoratedMediaCustomViewStyle())
                //().bigLargeIcon(largeIcon).bigPicture(largeIcon).setBigContentTitle("Race's Tracking"))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManagerCompat = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManagerCompat.notify(NOTIFICATION_ID, mBuilder.build());
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.removeUpdates(this);
        Log.e("Service ", "Service Stopped");
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (circuit != null) {
            Spot spot = new Spot();
            spot.setId(System.currentTimeMillis());
            spot.setCircuit_id(circuit.getId());
            spot.setCircuit(circuit);
            spot.setDescription("Free race point");
            spot.setImage_url("Not attriuable");
            spot.setLatitude(location.getLatitude());
            spot.setLongitude(location.getLongitude());
            circuit.getSpots().add(spot);
            Log.e("Circuit STATUS : ", "Spot Aded ");
            AppDatabase.getAppDatabase(this).spotDao().insertSpot(spot);

        } else {
            Log.e("Circuit STATUS : ", "Circuit is null ");
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          /*  CharSequence name = getString(R.string.channel_name);
            //String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(FreeRaceActivity.CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);*/
        }
    }



}
