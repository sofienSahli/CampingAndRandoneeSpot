package soft.dot.com.campingandrandoneespot.com.dot.soft.activities;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage.AppDatabase;
import soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage.UserSharedPref;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Circuit;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Spot;
import soft.dot.com.campingandrandoneespot.com.dot.soft.utils.ExpandCollapsAnim;
import soft.dot.com.campingandrandoneespot.com.dot.soft.utils.FreeRaceJobService;

public class FreeRaceActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener, LocationListener, View.OnClickListener, Chronometer.OnChronometerTickListener {
    private static final int JOB_SERVICE_ID = 12;
    LocationManager locationManager;
    GoogleMap map;
    private final int FINE_LOCATION = 101;
    boolean isSarted = false;
    Button demarrer;
    Chronometer elpased_time;
    boolean isFirstTime = true;
    Circuit circuit;
    public static final String CHANNEL_ID = "101";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_race);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView2);
        supportMapFragment.getMapAsync(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getRuntimePermission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);

        }

        findViewById(R.id.save).setOnClickListener(this);
        demarrer = findViewById(R.id.demarrer);
        demarrer.setOnClickListener(this);
        elpased_time = findViewById(R.id.elpased_time);
        elpased_time.setOnChronometerTickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == FINE_LOCATION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);

        }

    }


    private void getRuntimePermission(String[] s1) {
        ActivityCompat.requestPermissions(this, s1, FINE_LOCATION);
    }

    @Override
    public void onLocationChanged(Location location) {

        if (isSarted) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
            MarkerOptions currentMarker = new MarkerOptions()
                    .title("Last Known Location")
                    .position(new LatLng(location.getLatitude(), location.getLongitude()))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            map.addMarker(currentMarker);
            Spot spot = new Spot();
            spot.setId(System.currentTimeMillis());
            spot.setCircuit_id(circuit.getId());
            spot.setCircuit(circuit);
            spot.setDescription("Free race point");
            spot.setImage_url("Not attriuable");
            spot.setLatitude(location.getLatitude());
            spot.setLongitude(location.getLongitude());
            circuit.getSpots().add(spot);
            updateSpeedDistanceUI(location);

        }


    }

    private void updateSpeedDistanceUI(Location location) {
        if (circuit.getSpots().size() > 2) {
            int i = circuit.getSpots().size() - 1;
            Location location1 = new Location("");
            location1.setLongitude(circuit.getSpots().get(i).getLongitude());
            location1.setLatitude(circuit.getSpots().get(i).getLatitude());
            String distance = location.distanceTo(location1) + "M";
            TextView tv = findViewById(R.id.tv_distance);
            tv.setText(distance);


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

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == demarrer.getId()) {
            view.setVisibility(View.GONE);
            ExpandCollapsAnim.expand(findViewById(R.id.elpased_time));
            isSarted = !isSarted;
            if (isSarted) {
                demarrer.setBackgroundColor(getResources().getColor(R.color.bleu));
                demarrer.setTextColor(getResources().getColor(R.color.colorWhite));
                demarrer.setText("Enregistrement du Circuit");
                if (isFirstTime) {
                    isFirstTime = false;
                    elpased_time.setBase(SystemClock.elapsedRealtime());
                    instantiateCircuit();
                    showNotification();
                    startRecording();
                }
                elpased_time.start();


            } else {
                demarrer.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                demarrer.setTextColor(getResources().getColor(R.color.bleu));
                demarrer.setText("Sauvegarde mise en pause");
                showElapsedTime();
                elpased_time.stop();
            }

        } else if (view.getId() == R.id.save) {
            long passedTime = getElpaseTime();
                /*
                    String time = String.format("%02d min, %02d sec",
                    TimeUnit.MILLISECONDS.toMinutes(passedTime),
                    TimeUnit.MILLISECONDS.toSeconds(passedTime) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(passedTime))
                    );
                    */
            circuit.setDuree(passedTime);
            if (AppDatabase.getAppDatabase(this).circuitDAO().findById(circuit.getId()) == null) {
                AppDatabase.getAppDatabase(this).circuitDAO().insertCircuit(circuit);
                AppDatabase.getAppDatabase(this).spotDao().insertAllSpot(circuit.getSpots());
            }
            Toast.makeText(this, "Parcours sauvegardée", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }
    }

    private void startRecording() {

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fad_in);
        LinearLayout linearLayout = findViewById(R.id.ll_circuit_details);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                linearLayout.setVisibility(View.VISIBLE);
                elpased_time.setVisibility(View.VISIBLE);
                findViewById(R.id.save).setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        linearLayout.startAnimation(animation);
        elpased_time.startAnimation(animation);
        findViewById(R.id.save).startAnimation(animation);
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
        demarrer.startAnimation(anim);

    }

    // TODO recheck
    private void showNotification() {
        createNotificationChannel();

        ComponentName name = new ComponentName(this, FreeRaceJobService.class);
        PersistableBundle bundle = new PersistableBundle();
        bundle.putLong(FreeRaceJobService.CIRCUI_KEY, circuit.getId());
        JobInfo jobInfo = new JobInfo.Builder(JOB_SERVICE_ID, name).setExtras(bundle).setRequiresCharging(true).build();
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.schedule(jobInfo);
    }

    private long getElpaseTime() {
        return SystemClock.elapsedRealtime() - elpased_time.getBase();

    }

    private void instantiateCircuit() {
        UserSharedPref userSharedPref = new UserSharedPref(getSharedPreferences(UserSharedPref.USER_FILE, Context.MODE_PRIVATE));
        String name = userSharedPref.getString(UserSharedPref.USER_FIRST_NAME) + " " + userSharedPref.getString(UserSharedPref.USER_LAST_NAME);
        circuit = new Circuit();
        circuit.setId(System.currentTimeMillis());
        circuit.setTitle(name);
        Date localDate = Calendar.getInstance().getTime();
        circuit.setCreated_at(localDate.toString());
        circuit.setUpdated_at("eza");
        circuit.setSpots(new ArrayList<>());
        circuit.setDescription("Parcours libre");
        circuit.setDifficulty(userSharedPref.getLong(UserSharedPref.USER_ID) + "");
        circuit.setDuree(SystemClock.currentThreadTimeMillis());
        AppDatabase.getAppDatabase(this).circuitDAO().insertCircuit(circuit);

    }

    private void showElapsedTime() {
        long elapsedMillis = SystemClock.elapsedRealtime() - elpased_time.getBase();
        Toast.makeText(this, "Elapsed milliseconds: " + elapsedMillis,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onChronometerTick(Chronometer chronometer) {
        chronometer.getBase();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
