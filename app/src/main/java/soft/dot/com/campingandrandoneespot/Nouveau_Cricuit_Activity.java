package soft.dot.com.campingandrandoneespot;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.dot.com.campingandrandoneespot.com.dot.soft.LocalStorage.AppDatabase;
import soft.dot.com.campingandrandoneespot.com.dot.soft.adapters.SpotListAdapter;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Circuit;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Difficulty;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Spot;

public class Nouveau_Cricuit_Activity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, LocationListener, Callback {
    private static final int CAMERA = 100;
    private static final int IMAGE_GALLERY = 205;
    private static final String IMAGE_DIRECTORY = "/location_images";
    GoogleMap map;
    AlertDialog dialog;
    MarkerOptions currentMarker;
    LocationManager locationManager;
    private final int FINE_LOCATION = 101;
    private final int COARSE_LOCATION = 202;
    ImageButton imageButton;
    Circuit circuit;
    RadioGroup radioGroup;
    EditText etDescription, tvcircuitTitre, tvPostDescription;
    String lastSavedPath;
    RecyclerView spotsRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circuit_add_activity);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());
        showDialogChoice();
        tvcircuitTitre = findViewById(R.id.tvcircuitTitre);
        etDescription = findViewById(R.id.etdescription);
        radioGroup = findViewById(R.id.rgdifficulty);

        findViewById(R.id.addSpot).setOnClickListener(v -> {
            if (circuit == null)
                instatiateCircuit();
            else
                dialog.show();

        });
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        spotsRecyclerView = findViewById(R.id.spotsRecyclerView);
        spotsRecyclerView.setAdapter(new SpotListAdapter(this, new ArrayList<>()));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        spotsRecyclerView.setLayoutManager(mLayoutManager);
        findViewById(R.id.ibDone).setOnClickListener(v -> checkCircuitIntegrity());
    }

    // Check weitehr or not the circuit is complete
    private void checkCircuitIntegrity() {
        if (circuit.getSpots().isEmpty()) {
            Toast.makeText(this, "Il n'est pas possible d'ajouter un circuit sans spots", Toast.LENGTH_SHORT).show();
        } else {
         /*   for (Spot spot : circuit.getSpots()) {
                spot.encodeImage();
            }*/
            //TODO reuse service once backend ready and change return to previous activity from callback
            //CircuitService circuitService = new CircuitService();
            //circuitService.addCircuit(this, circuit);
            AppDatabase.getAppDatabase(this).circuitDAO().insertCircuit(circuit);
            AppDatabase.getAppDatabase(this).spotDao().insertAllSpot(circuit.getSpots());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }

    }

    private void instatiateCircuit() {
        circuit = new Circuit();
        circuit.setId(System.currentTimeMillis());
        if (TextUtils.isEmpty(tvcircuitTitre.getText())) {
            tvcircuitTitre.setBackgroundResource(R.drawable.empty_text_field_background);
            tvcircuitTitre.setOnClickListener(v -> v.setBackground(null));
            return;
        } else {
            circuit.setTitle(tvcircuitTitre.getText().toString());
        }
        if (TextUtils.isEmpty(etDescription.getText())) {
            etDescription.setBackgroundResource(R.drawable.empty_text_field_background);
            etDescription.setOnClickListener(v -> v.setBackground(null));
            return;
        } else {
            circuit.setDescription(etDescription.getText().toString());
        }
        if (radioGroup.getCheckedRadioButtonId() == -1) {

            radioGroup.setBackgroundResource(R.drawable.empty_text_field_background);
            radioGroup.setOnClickListener(v -> v.setBackground(null));
            return;
        } else {
            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.rbeasy:
                    circuit.setDifficulty(Difficulty.Easy.toString());
                    break;
                case R.id.rbmedium:
                    circuit.setDifficulty(Difficulty.Medium.toString());
                    break;
                case R.id.rbhard:
                    circuit.setDifficulty(Difficulty.Hard.toString());
                    break;
            }
        }
        if (circuit.getSpots() == null)
            circuit.setSpots(new ArrayList<>());
        dialog.show();
    }

    //Prepare new Spot dialog
    private void showDialogChoice() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.new_spot_dialog, null, false);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

        alertDialog.setView(view);
        dialog = alertDialog.create();
        tvPostDescription = view.findViewById(R.id.etDescription);
        view.findViewById(R.id.cancel).setOnClickListener(v -> dialog.dismiss());
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getRuntimePermission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
        }
        view.findViewById(R.id.confirm).setOnClickListener(v -> {
            addSpot();
            dialog.dismiss();
        });
        imageButton = view.findViewById(R.id.ibChoseImage);
        imageButton.setOnClickListener(v -> showImageDialog());

    }


    //Choose weither to use locale storage or camera
    private void showImageDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Camera ou storage ? ");
        alertDialog.setNeutralButton("From Library", (dialogInterface, i) -> openStorage());
        alertDialog.setPositiveButton("Camera", (dialogInterface, i) -> openCamera());
        alertDialog.create().show();
    }

    private void openStorage() {
        if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE) && checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, IMAGE_GALLERY);
        } else {
            getRuntimePermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE});
        }
    }

    private void openCamera() {
        if (checkPermission(Manifest.permission.CAMERA) && checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA);
        } else {
            getRuntimePermission(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE});

        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMapLongClickListener(this);

        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) && checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 600000, 0, this);
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        map.clear();
        currentMarker = new MarkerOptions()
                .title("Selected Location")
                .position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        map.addMarker(currentMarker);
    }

    private void getRuntimePermission(String[] s1) {
        ActivityCompat.requestPermissions(this, s1, FINE_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == FINE_LOCATION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, COARSE_LOCATION);
        }
        if (requestCode == COARSE_LOCATION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                getRuntimePermission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION});

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == IMAGE_GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    saveImage(bitmap);
                    imageButton.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageButton.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    //Location Listener impl

    @Override
    public void onLocationChanged(Location location) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
        currentMarker = new MarkerOptions()
                .title("Your last know location")
                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

        map.addMarker(currentMarker);
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
    //Check weither or not permission is givin during runtime

    public boolean checkPermission(String s1) {
        return ActivityCompat.checkSelfPermission(this, s1) == PackageManager.PERMISSION_GRANTED;
    }

    //Save bitmap to file
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, System.currentTimeMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            lastSavedPath = f.getAbsolutePath();
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public void addSpot() {
        Spot spot = new Spot();
        spot.setId(System.currentTimeMillis());
        if (currentMarker == null) {
            Toast.makeText(this, "Veuillez placer un marker sur la carte", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(this, " latitude = " + currentMarker.getPosition().latitude + "    " + " longitude = " + currentMarker.getPosition().longitude, Toast.LENGTH_SHORT).show();
            spot.setLongitude(currentMarker.getPosition().longitude);
            spot.setLatitude(currentMarker.getPosition().latitude);
        }
        if (TextUtils.isEmpty(lastSavedPath)) {
            imageButton.setBackgroundResource(R.drawable.empty_text_field_background);
            return;
        } else {

            spot.setImage_url(lastSavedPath);
        }
        if (!TextUtils.isEmpty(tvPostDescription.getText())) {
            spot.setDescription(tvPostDescription.getText().toString());
        }
        ((SpotListAdapter) spotsRecyclerView.getAdapter()).addSpot(spot);
        spot.setCircuit(circuit);
        circuit.getSpots().add(spot);

        Toast.makeText(this, "" + circuit.getSpots().size(), Toast.LENGTH_SHORT).show();
    }

    //Add circuit callback
    @Override
    public void onResponse(Call call, Response response) {
        if (response.body() != null) {
            Toast.makeText(this, "Nouveau circuit ajouté avec succés", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Toast.makeText(this, "Echeck de l'ajout", Toast.LENGTH_SHORT).show();
        Log.e("Failed  to add ", t.getMessage());

    }

}
