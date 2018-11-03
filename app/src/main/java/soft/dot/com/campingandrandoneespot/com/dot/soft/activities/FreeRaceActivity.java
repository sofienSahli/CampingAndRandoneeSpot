package soft.dot.com.campingandrandoneespot.com.dot.soft.activities;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.LocalStorage.AppDatabase;
import soft.dot.com.campingandrandoneespot.com.dot.soft.LocalStorage.UserSharedPref;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Circuit;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Spot;

public class FreeRaceActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener, LocationListener, View.OnClickListener, Chronometer.OnChronometerTickListener {
    LocationManager locationManager;
    GoogleMap map;
    private final int FINE_LOCATION = 101;
    boolean isSarted = false;
    Button demarrer;
    Chronometer elpased_time;
    boolean isFirstTime = true;
    Circuit circuit;

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
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);

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
            isSarted = !isSarted;
            if (isSarted) {
                demarrer.setBackgroundColor(getResources().getColor(R.color.bleu));
                demarrer.setTextColor(getResources().getColor(R.color.colorWhite));
                demarrer.setText("Enregistrement du Circuit");
                if (isFirstTime) {
                    isFirstTime = false;
                    elpased_time.setBase(SystemClock.elapsedRealtime());
                    instantiateCircuit();
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
            String time = String.format("%02d min, %02d sec",
                    TimeUnit.MILLISECONDS.toMinutes(passedTime),
                    TimeUnit.MILLISECONDS.toSeconds(passedTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(passedTime))
            );

            circuit.setDuree(time);
            AppDatabase.getAppDatabase(this).circuitDAO().insertCircuit(circuit);
            AppDatabase.getAppDatabase(this).spotDao().insertAllSpot(circuit.getSpots());
            Toast.makeText(this, "Parcours sauvegardée", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }
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
        LocalDate localDate = LocalDate.now();
        circuit.setCreated_at(localDate.toString());
        circuit.setUpdated_at("eza");
        circuit.setSpots(new ArrayList<>());
        circuit.setDescription("Parcours libre");
        circuit.setDifficulty(userSharedPref.getLong(UserSharedPref.USER_ID) + "");
        circuit.setDuree("000");

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

}
