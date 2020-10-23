package soft.dot.com.campingandrandoneespot.com.dot.soft.activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Event;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.EventUser;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Media;
import soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage.UserSharedPref;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.RetrofitClient;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.services.EventServices;

public class EventDetailActivity extends AppCompatActivity {

    public final static String EVENT_NAME = "name";
    public final static String EVENT_DATE = "date";
    public final static String EVENT_LOCATION = "loc";
    public final static String EVENT_LOGITUDE = "long";
    public final static String EVENT_LOGITITUDE = "lat";
    public final static String EVENT_DESCRIPTION = "descr";
    public final static String EVENT_KEY = "event";
    TextView event_description, event_lieu, event_date, event_title, textView34;
    Button button5;
    ImageSwitcher imageSwitcher2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());
        setContentView(R.layout.activity_event_detail);
        setUpVie();
    }

    private void setUpVie() {

        event_description = findViewById(R.id.event_description);
        event_title = findViewById(R.id.event_title);
        textView34 = findViewById(R.id.textView34);

        event_lieu = findViewById(R.id.event_lieu);
        event_date = findViewById(R.id.event_date);
        button5 = findViewById(R.id.button5);
        imageSwitcher2 = findViewById(R.id.imageSwitcher2);
        Event event = populate_window();
        if (event != null) {

            if (!event.isIs_allowed()) {
                button5.setText("Autoriser l'événement");
                button5.setOnClickListener(v -> allowEvent(event));
            } else {

                button5.setText("S'inscrire à l'évennement");
                button5.setOnClickListener(v -> registerToEvent(event));
                UserSharedPref userSharedPref = new UserSharedPref(getSharedPreferences(UserSharedPref.USER_FILE, Context.MODE_PRIVATE));
                long user_id = userSharedPref.getLong(UserSharedPref.USER_ID);
                if (event.getParticipants() != null && !event.getParticipants().isEmpty()) {
                    for (EventUser eventUser : event.getParticipants()) {
                        if (eventUser.getInscrit() == user_id) {
                            button5.setEnabled(false);
                            button5.setText("Vous êtes déja inscrit à cet évenement");
                        }
                    }
                }
            }


            event_description.setText(event.getDescription());
            event_lieu.setText(event.getPlace());
            event_date.setText(event.getStarting_date());
            event_title.setText(event.getTitle());
            if (event.getProposer() != null) {
                String proposer_name = event.getProposer().getFirstName() + "   " + event.getProposer().getLastName();
                textView34.setText(proposer_name);
                textView34.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(ProfilActivity.USER_KEY, event.getProposer());
                    Intent intent = new Intent(EventDetailActivity.this, ProfilActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(EventDetailActivity.this).toBundle());
                });
            }

            establishSwitcher(event.getMedias());

        }

    }

    private void registerToEvent(Event event) {
        UserSharedPref userSharedPref = new UserSharedPref(getSharedPreferences(UserSharedPref.USER_FILE, Context.MODE_PRIVATE));
        long user_id = userSharedPref.getLong(UserSharedPref.USER_ID);
        EventServices eventServices = new EventServices();
        eventServices.subscribe_event(user_id, event.getId(), new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 201) {
                    button5.setText("Vous êtes déja inscrit à cette événement");
                    button5.setEnabled(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("RegisterEvent", t.getMessage());
                Toast.makeText(EventDetailActivity.this, "Erreur essayer plus tard", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void allowEvent(Event event) {
        EventServices eventServices = new EventServices();
        eventServices.allow_event(event.getId(), new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Toast.makeText(EventDetailActivity.this, "Evennement autorisé et partagé", Toast.LENGTH_LONG).show();
                    button5.setText("S'inscrire à l'évennement");
                    button5.setOnClickListener(v -> registerToEvent(event));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("allowevet", t.getMessage());
                Toast.makeText(EventDetailActivity.this, "Erreur intern veuillier ressayer plus tard", Toast.LENGTH_LONG).show();
            }
        });
    }

    private Event populate_window() {
        Bundle bundle = getIntent().getExtras();

        if (bundle.containsKey(EVENT_KEY)) {
            Event event = bundle.getParcelable(EVENT_KEY);
            Log.e("DETAIL", event.toString());
            return event;
        }

        return null;
    }

    private void establishSwitcher(List<Media> medias) {
        if (medias != null && !medias.isEmpty()) {


            Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
            imageSwitcher2.setInAnimation(in);

            Timer t = new Timer();
            //Set the schedule function and rate
            imageSwitcher2.setFactory(() -> {

                ImageView iv = new ImageView(this);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);

                return iv;
            });

            t.scheduleAtFixedRate(new TimerTask() {
                int current_index = 0;

                public void run() {

                    ((AppCompatActivity) EventDetailActivity.this).runOnUiThread(() -> {
                        ImageSwitcherPicasso mImageSwitcherPicasso = new ImageSwitcherPicasso(EventDetailActivity.this, imageSwitcher2);

                        String url = RetrofitClient.BASE_URL + medias.get(current_index).getFile();
                        Picasso.with(EventDetailActivity.this).load(url).into(mImageSwitcherPicasso);

                    });
                    current_index++;
                    if (current_index == medias.size())
                        current_index = 0;
                }

            }, 0, 2000);
        }

    }

    public class ImageSwitcherPicasso implements Target {

        private ImageSwitcher mImageSwitcher;
        private Context mContext;

        public ImageSwitcherPicasso(Context context, ImageSwitcher imageSwitcher) {
            mImageSwitcher = imageSwitcher;
            mContext = context;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
            mImageSwitcher.setImageDrawable(new BitmapDrawable(mContext.getResources(), bitmap));
        }

        @Override
        public void onBitmapFailed(Drawable drawable) {

        }

        @Override
        public void onPrepareLoad(Drawable drawable) {

        }

    }
}