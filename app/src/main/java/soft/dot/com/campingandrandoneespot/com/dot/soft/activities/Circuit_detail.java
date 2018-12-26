package soft.dot.com.campingandrandoneespot.com.dot.soft.activities;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage.AppDatabase;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Circuit;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Spot;
import soft.dot.com.campingandrandoneespot.com.dot.soft.utils.DateUtils;
import soft.dot.com.campingandrandoneespot.com.dot.soft.utils.ExpandCollapsAnim;

public class Circuit_detail extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnMapLongClickListener {
    public final static String CIRCUIT_ID = "CIRCUIT_ID";
    public static Circuit circuit;
    private ArrayList<Spot> spots;
    private GoogleMap mMap;
    TextView tvDuree, tvVitess, tvDistanceParcourus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circuit_detail);
        if (circuit.getSpots().isEmpty()) {
            spots = (ArrayList<Spot>) AppDatabase.getAppDatabase(this).spotDao().findSpotsForCircuit(circuit.getId());

        } else {
            spots = circuit.getSpots();
        }
        findViewById(R.id.imageButton2).setOnClickListener(v->{
            ExpandCollapsAnim.collapse(findViewById(R.id.details_layout));
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        setUpUI();
    }

    private void setUpUI() {
        tvDistanceParcourus = findViewById(R.id.tvElpasedTime);
        tvDuree = findViewById(R.id.tvDuree);
        tvVitess = findViewById(R.id.tvVitess);
        tvDuree.setText(DateUtils.getTimeFromLong(circuit.getDuree()));
        float distance = calculateDistance(spots.get(0), spots.get(spots.size() - 1));
        tvDistanceParcourus.setText(distance + "Metres");

        Toolbar toolbar = findViewById(R.id.toolbar);
        tvVitess.setText(distance/circuit.getDuree() + "km/h");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Detail du circuit");
        getSupportActionBar().setWindowTitle("Detail du circuit");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        PolylineOptions polyline = new PolylineOptions()
                .clickable(true);
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        int i=0 ;
        for (Spot spot : spots) {
            Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(spot.getLatitude(), spot.getLongitude())));
            marker.setTag(spot.getId());
            polyline.add(new LatLng(spot.getLatitude(), spot.getLongitude()));
            marker.setTitle(""+i);
            i++;
        }
        mMap.setOnMapLongClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(spots.get(0).getLatitude(), spots.get(0).getLongitude())));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);

    }

    private float calculateDistance(Spot start, Spot end) {
        Location loc1 = new Location("");
        loc1.setLatitude(start.getLatitude());
        loc1.setLongitude(start.getLongitude());

        Location loc2 = new Location("");
        loc2.setLatitude(end.getLatitude());
        loc2.setLongitude(end.getLongitude());

        return loc1.distanceTo(loc2);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        ExpandCollapsAnim.expand(findViewById(R.id.details_layout));

    }
}
