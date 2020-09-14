package soft.dot.com.campingandrandoneespot.com.dot.soft.activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage.UserSharedPref;

public class SplashScreenActivity extends AppCompatActivity {
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        logo = findViewById(R.id.logo);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.logo_splash_anim);
        logo.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if (!isLoggedIn()) {
                    Intent intent = new Intent(SplashScreenActivity.this, FirstActivity.class);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(SplashScreenActivity.this).toBundle());

                } else {
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(SplashScreenActivity.this).toBundle());

                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private boolean isLoggedIn() {
        UserSharedPref userSharedPref = new UserSharedPref(getSharedPreferences(UserSharedPref.USER_FILE, Context.MODE_PRIVATE));
        Log.e("User Nam " , userSharedPref.getString(UserSharedPref.EMAIL ));
        return userSharedPref.isUserLogged();

    }
}
