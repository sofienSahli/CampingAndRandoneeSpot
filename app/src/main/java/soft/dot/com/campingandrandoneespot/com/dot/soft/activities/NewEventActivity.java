package soft.dot.com.campingandrandoneespot.com.dot.soft.activities;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.adapters.EventImageGridAdapter;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Event;
import soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage.UserSharedPref;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.services.EventServices;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.services.MediaServices;
import soft.dot.com.campingandrandoneespot.com.dot.soft.utils.FileUtil;
import soft.dot.com.campingandrandoneespot.com.dot.soft.utils.ProgressDialog;
import timber.log.Timber;

public class NewEventActivity extends AppCompatActivity {

    Toolbar toolbar4;
    public final int IMAGE_INTENT = 101;
    Button button6;
    GridView events_images;
    EventImageGridAdapter eventImageGridAdapter;
    EditText event_desciption, event_name, editTextDate, event_place;
    LatLng point;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        setUpView();
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

    }

    private void setUpView() {
        getWindow().setEnterTransition(new AutoTransition());
        getWindow().setExitTransition(new AutoTransition());
        toolbar4 = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar4);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        button6 = findViewById(R.id.button6);
        events_images = findViewById(R.id.events_images);
        ArrayList<Uri> drawables = new ArrayList<>();
        event_desciption = findViewById(R.id.event_desciption);
        event_name = findViewById(R.id.event_name);
        editTextDate = findViewById(R.id.editTextDate);
        eventImageGridAdapter = new EventImageGridAdapter(this, drawables);
        editTextDate.setOnFocusChangeListener((view, b) -> {
            if (b)
                show_date_dialog();
        });
        findViewById(R.id.imageButton5).setOnClickListener(v -> {
            Intent intent = new Intent(NewEventActivity.this, MapActvity.class);
            startActivityForResult(intent, 202, ActivityOptions.makeSceneTransitionAnimation(NewEventActivity.this).toBundle());
        });
        events_images.setAdapter(eventImageGridAdapter);
        button6.setOnClickListener(v -> {
            Intent i = new Intent(
                    Intent.ACTION_GET_CONTENT,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, IMAGE_INTENT, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        });
        event_place = findViewById(R.id.event_place);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.row_spinner, getResources().getStringArray(R.array.event_categories));
        spinner.setAdapter(arrayAdapter);
        findViewById(R.id.button12).setOnClickListener(v -> upload_event_suggestion());
    }

    private void show_date_dialog() {
        DatePickerDialog dialog = new DatePickerDialog(this);
        dialog.setOnDateSetListener((datePicker, i, i1, i2) -> {
            String date = i + "-" + i1 + "-" + i2;
            editTextDate.setText(date);
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_INTENT && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            eventImageGridAdapter.getDrawables().add(selectedImage);
            eventImageGridAdapter.notifyDataSetChanged();

        }
        if (resultCode == RESULT_OK && requestCode == 202 && null != data) {
            Bundle bundle = data.getExtras();
            if (bundle.containsKey("longi")) {
                point = new LatLng();
                point.setLatitude(bundle.getDouble("lati"));
                point.setLongitude(bundle.getDouble("longi"));
                Geocoder geocoder = new Geocoder(this);
                try {
                    List<Address> address = geocoder.getFromLocation(point.getLatitude(), point.getLongitude(), 1);
                    event_place.setText(address.get(0).getFeatureName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.check_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.done) {
            if (eventImageGridAdapter.getDrawables() != null && eventImageGridAdapter.getDrawables().isEmpty()) {
                Toast.makeText(this, "Aucune image n'est séléctioné ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Ok with images", Toast.LENGTH_SHORT).show();
            }
            upload_event_suggestion();
        }
        return true;
    }

    private void upload_event_suggestion() {
        Event event = new Event();
        long proposed_by_id;
        UserSharedPref userSharedPref = new UserSharedPref(getSharedPreferences(UserSharedPref.USER_FILE, Context.MODE_PRIVATE));
        proposed_by_id = userSharedPref.getLong(UserSharedPref.USER_ID);
        event.setPrposed_by_id(proposed_by_id);
        event.setDescription(event_desciption.getText().toString());
        event.setTitle(event_name.getText().toString());
        EventServices eventServices = new EventServices();
        ProgressDialog progressDialog = new ProgressDialog(this);
        if (point == null) {
            Toast.makeText(this, "Veuillez spécifier une location pour l'évenement", Toast.LENGTH_LONG).show();
            return;
        }
        event.setStarting_date(editTextDate.getText().toString());
        event.setLatitude(point.getLatitude());
        event.setLongitude(point.getLongitude());
        event.setPlace(event_place.getText().toString());
        event.setTheme(spinner.getSelectedItem().toString());
        progressDialog.showDissmissDialog();

        eventServices.store_event(event, new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                progressDialog.showDissmissDialog();
                if (response.code() == 201) {
                    if (eventImageGridAdapter.getDrawables() == null && !eventImageGridAdapter.getDrawables().isEmpty()) {
                        Intent intent = new Intent(NewEventActivity.this, MainActivity.class);
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(NewEventActivity.this).toBundle());

                        Toast.makeText(NewEventActivity.this, "Merci pour votre suggestion, vous serez" +
                                " notifé par e-mail quand votre demande sera validé", Toast.LENGTH_LONG).show();

                    } else {
                        upload_event_picture(response.body());

                    }
                }
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                progressDialog.showDissmissDialog();
                Timber.e("Message" + t.getMessage());
                Toast.makeText(NewEventActivity.this, "Erreur interne veuilliez ressayer dans quelques instant", Toast.LENGTH_LONG).show();
            }
        });


    }

    private void upload_event_picture(Event event) {

        for (Uri uri : eventImageGridAdapter.getDrawables()) {

            File file = new File(FileUtil.getPath(this, uri));
            RequestBody filePart = RequestBody.create(MediaType.parse("file"), file);
            MediaServices mediaServices = new MediaServices();
            mediaServices.add_event_image(event.getId(), filePart, new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 200) {
                        //  Toast.makeText(NewEventActivity.this, "Uploading image numéro " + finalI + "/" + eventImageGridAdapter.getDrawables().size(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("Media upload erreur", t.getMessage());
                }
            });

            this.finish();
        }
    }


}
