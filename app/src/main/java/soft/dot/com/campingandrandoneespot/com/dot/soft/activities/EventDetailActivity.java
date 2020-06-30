package soft.dot.com.campingandrandoneespot.com.dot.soft.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Event;

public class EventDetailActivity extends AppCompatActivity {

    public final static String EVENT_NAME = "name";
    public final static String EVENT_DATE = "date";
    public final static String EVENT_LOCATION = "loc";
    public final static String EVENT_LOGITUDE = "long";
    public final static String EVENT_LOGITITUDE = "lat";
    public final static String EVENT_DESCRIPTION = "descr";
    TextView event_description, event_lieu, event_date;
    Button button5;
    ImageSwitcher imageSwitcher2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());
        setContentView(R.layout.activity_event_detail);
        Toolbar toolbar = findViewById(R.id.toolbar3);
        toolbar.setTitleTextColor(getResources().getColor(R.color.bpWhite));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        event_description = findViewById(R.id.event_description);
        event_lieu = findViewById(R.id.event_lieu);
        event_date = findViewById(R.id.event_date);
        button5 = findViewById(R.id.button5);
        imageSwitcher2 = findViewById(R.id.imageSwitcher2);
        establishSwitcher();
        Event event = populate_window();
        if (event != null) {

            getSupportActionBar().setTitle(event.getTitle());
            event_description.setText(event.getDescription());
            event_lieu.setText(event.getPlace());
            event_date.setText(event.getDate());

        }
    }

    private Event populate_window() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Event event = new Event();
            event.setDate(bundle.getString(EVENT_DATE));
            event.setPlace(bundle.getString(EVENT_LOCATION));
            event.setTitle(bundle.getString(EVENT_NAME));
            event.setDescription(bundle.getString(EVENT_DESCRIPTION));
            return event;
        }
        return null;
    }

    private void establishSwitcher() {
        imageSwitcher2.setFactory(() -> {
            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            return iv;
        });
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        imageSwitcher2.setInAnimation(in);

        imageSwitcher2.setImageResource(R.drawable.rimel_1);
        Timer t = new Timer();
        //Set the schedule function and rate


        t.scheduleAtFixedRate(new TimerTask() {
            int ids[] = {R.drawable.event_1, R.drawable.rimel_peoples_2, R.drawable.rimel_3};
            int current_index = 0;

            public void run() {

                ((AppCompatActivity) EventDetailActivity.this).runOnUiThread(() -> {
                    imageSwitcher2.setImageResource(ids[current_index]);
                });
                current_index++;
                if (current_index == 3)
                    current_index = 0;
            }

        }, 0, 2000);


    }
}