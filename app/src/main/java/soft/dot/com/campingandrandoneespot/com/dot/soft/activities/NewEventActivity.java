package soft.dot.com.campingandrandoneespot.com.dot.soft.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.adapters.EventImageGridAdapter;

public class NewEventActivity extends AppCompatActivity {

    Toolbar toolbar4;
    public final int IMAGE_INTENT = 101;
    Button button6;
    GridView events_images;
    EventImageGridAdapter eventImageGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        toolbar4 = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar4);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        button6 = findViewById(R.id.button6);
        events_images = findViewById(R.id.events_images);
        ArrayList<Uri> drawables = new ArrayList<>();

        eventImageGridAdapter = new EventImageGridAdapter(this, drawables);
        events_images.setAdapter(eventImageGridAdapter);
        button6.setOnClickListener(v -> {
            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, IMAGE_INTENT, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_INTENT && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            eventImageGridAdapter.getDrawables().add(selectedImage);
            eventImageGridAdapter.notifyDataSetChanged();

        }
    }
}