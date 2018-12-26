package soft.dot.com.campingandrandoneespot.com.dot.soft.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage.AppDatabase;
import soft.dot.com.campingandrandoneespot.com.dot.soft.adapters.CustomInfoWindowGoogleMap;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Circuit;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Spot;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, GoogleMap.OnMapClickListener, LocationListener, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private final int FINE_LOCATION = 101;
    private final int COARSE_LOCATION = 202;
    Location location;
    public static Circuit circuit;
    ArrayList<Spot> spots;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (circuit.getSpots().isEmpty()) {
            spots = (ArrayList<Spot>) AppDatabase.getAppDatabase(this).spotDao().findSpotsForCircuit(circuit.getId());

        } else {
            spots = circuit.getSpots();
        }

    }

    private void getRuntimePermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == FINE_LOCATION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, COARSE_LOCATION);


        }
        if (requestCode == COARSE_LOCATION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                getRuntimePermission();
            }
            Criteria criteria = new Criteria();
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            readyMap();

        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getRuntimePermission();

        mMap.setOnMapClickListener(this);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


    }

    private void readyMap() {
        if (spots != null) {
            // Add a marker in Sydney and move the camera
            PolylineOptions polyline = new PolylineOptions()
                    .clickable(true);

            for (Spot spot : spots) {
                Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(spot.getLatitude(), spot.getLongitude())));
                marker.setTag(spot.getId());
                polyline.add(new LatLng(spot.getLatitude(), spot.getLongitude()));
            }

            Polyline polyline1 = mMap.addPolyline(polyline);
            polyline1.setColor(Color.GREEN);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(spots.get(0).getLatitude(), spots.get(0).getLongitude())));
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            mMap.setOnMyLocationButtonClickListener(this);
            mMap.setOnMyLocationClickListener(this);
            mMap.setOnInfoWindowClickListener(this);
            mMap.setInfoWindowAdapter(new CustomInfoWindowGoogleMap(this,spots));
        } else {
            Toast.makeText(this, "Erreur interne veuilliez ressayer plus tard", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);


        return true;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        //     LatLng pos = new LatLng(location.getLongitude(), location.getLatitude());
        //   mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 13));
        //  mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);


    }

    @Override
    public void onMapClick(LatLng latLng) {
      /*  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        */

    }

    @Override
    public void onLocationChanged(Location location) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
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
    public void onInfoWindowClick(Marker marker) {
        marker.showInfoWindow();
    }
}
