package soft.dot.com.campingandrandoneespot.com.dot.soft.activities;

import android.Manifest;
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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.codetroopers.betterpickers.timepicker.TimePickerBuilder;
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

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage.AppDatabase;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.services.CircuitService;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.services.SpotService;
import soft.dot.com.campingandrandoneespot.com.dot.soft.adapters.SpotListAdapter;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Circuit;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Difficulty;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Spot;

public class Nouveau_Cricuit_Activity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener, LocationListener, Callback, SeekBar.OnSeekBarChangeListener {
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
    //RadioGroup radioGroup;
    EditText etDescription, tvcircuitTitre, tvPostDescription;
    String lastSavedPath;
    RecyclerView spotsRecyclerView;
    Button select_time;
    SeekBar difficulty;
    String duree;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circuit_add_activity);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());
        showDialogChoice();
        difficulty = findViewById(R.id.tvDifficulty);
        difficulty.setOnSeekBarChangeListener(this);
        difficulty.setMax(2);
        select_time = findViewById(R.id.select_time);
        select_time.setOnClickListener(v -> {
            TimePickerBuilder tpb = new TimePickerBuilder()
                    .setFragmentManager(getSupportFragmentManager())
                    .setStyleResId(R.style.BetterPickersDialogFragment_Light);
            tpb.show();
            tpb.addTimePickerDialogHandler((reference, hourOfDay, minute) -> {
                select_time.setText(hourOfDay + " : " + minute);
                duree = hourOfDay + " : " + minute;
            });
        });

        tvcircuitTitre = findViewById(R.id.tvcircuitTitre);
        etDescription = findViewById(R.id.etdescription);
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
        findViewById(R.id.ibDone).setOnClickListener(v -> closeWindow());
    }

    // Check weitehr or not the circuit is complete
    private void closeWindow() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
        switch (difficulty.getProgress()) {
            case 0:
                circuit.setDifficulty(Difficulty.Easy.toString());
                break;
            case 1:
                circuit.setDifficulty(Difficulty.Medium.toString());
                break;
            case 2:
                circuit.setDifficulty(Difficulty.Hard.toString());
                break;


        }
            circuit.setDuree(0);
            circuit.setUpdated_at("undefined");
            circuit.setCreated_at("undefined");

        CircuitService circuitService = new CircuitService();
        circuitService.addCircuit(this, circuit);

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
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
        } else {
            getRuntimePermission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == FINE_LOCATION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
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
                    Log.e("Saving image", "erreur");
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageButton.setImageBitmap(thumbnail);
            saveImage(thumbnail);
        }
    }

    //Location Listener impl

    @Override
    public void onLocationChanged(Location location) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));

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
            spot.setLongitude(currentMarker.getPosition().longitude);
            spot.setLatitude(currentMarker.getPosition().latitude);
        }
        if (TextUtils.isEmpty(lastSavedPath)) {
            imageButton.setBackgroundResource(R.drawable.empty_text_field_background);
            return;
        } else {//description description

            spot.setImage_url(lastSavedPath);
        }
        if (!TextUtils.isEmpty(tvPostDescription.getText())) {
            spot.setDescription(tvPostDescription.getText().toString());
        }

        ((SpotListAdapter) spotsRecyclerView.getAdapter()).addSpot(spot);
        ((SpotListAdapter) spotsRecyclerView.getAdapter()).notifyDataSetChanged();
        spot.setCircuit(circuit);
        File f = new File(lastSavedPath);
        RequestBody filePart = RequestBody.create(MediaType.parse("file"), f);

        SpotService spotService = new SpotService();
        spotService.addCircuit(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.code() == 200) {
                    AppDatabase.getAppDatabase(Nouveau_Cricuit_Activity.this).spotDao().insertSpot(spot);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                AppDatabase.getAppDatabase(Nouveau_Cricuit_Activity.this).spotDao().insertSpot(spot);

            }
        }, spot, filePart);

    }

    //Add circuit callback
    @Override
    public void onResponse(Call call, Response response) {
        if (response.code() == 200) {
            Log.e("service here ", "200");
            AppDatabase.getAppDatabase(Nouveau_Cricuit_Activity.this).circuitDAO().insertCircuit(circuit);
            if (circuit.getSpots() == null)
                circuit.setSpots(new ArrayList<>());
            dialog.show();
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        AppDatabase.getAppDatabase(Nouveau_Cricuit_Activity.this).circuitDAO().insertCircuit(circuit);
        if (circuit.getSpots() == null)
            circuit.setSpots(new ArrayList<>());
        // dialog.show();
        Log.e("service here ", t.getMessage());

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        int max = 2;
        if (i > max) {
            seekBar.setProgress(max);
        } else {
            seekBar.setProgress(i);
        }


    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

        seekBar.setBackground(getDrawable(R.drawable.back_text_bleu));

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        seekBar.setBackground(getDrawable(android.R.drawable.screen_background_light_transparent));


    }
}
