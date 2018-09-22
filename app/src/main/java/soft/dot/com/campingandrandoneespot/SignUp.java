package soft.dot.com.campingandrandoneespot;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.dot.com.campingandrandoneespot.com.dot.soft.LocalStorage.UserSharedPref;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.User;

public class SignUp extends AppCompatActivity implements  Callback<User> {
    EditText email_field, name, password_field, confirm_password_field;
    Button sign_up;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setUpTransition();
        email_field = findViewById(R.id.email_field);
        name = findViewById(R.id.name);
        password_field = findViewById(R.id.password_field);
        confirm_password_field = findViewById(R.id.confirm_password_field);
        sign_up = findViewById(R.id.sign_up);
        progressBar = findViewById(R.id.progress_bar);
        sign_up.setOnClickListener(v -> {

            if (validateData()) {
                Toast.makeText(this, "hold on ", Toast.LENGTH_SHORT).show();
                animateButton();

            } else {
                Dialog dialog = new Dialog(this);
                dialog.setTitle("Caution");
                dialog.show();
            }
        });

    }


    private void setUpTransition() {
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Slide());
    }

    private boolean validateData() {
        if (TextUtils.isEmpty(email_field.getText())
                && TextUtils.isEmpty(name.getText())
                && TextUtils.isEmpty(password_field.getText())
                && TextUtils.isEmpty(confirm_password_field.getText()))
            return false;
        else {
            if (password_field.getText().equals(confirm_password_field.getText())) {
                return true;
            }
        }
        return false;
    }

    private void animateButton() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.background_anim_fade_out);
        sign_up.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(SignUp.this, "I'm done", Toast.LENGTH_SHORT).show();
                sign_up.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                Animation anim = AnimationUtils.loadAnimation(SignUp.this, R.anim.fad_in);

                progressBar.startAnimation(anim);

                progressBar.animate();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    // WebService space

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if (response.body() != null) {
            UserSharedPref userSharedPref = new UserSharedPref(this.getSharedPreferences(UserSharedPref.USER_FILE, Context.MODE_PRIVATE));
            userSharedPref.logIn(response.body());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            Toast.makeText(this, "No users matches those details", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        Log.e("Error", t.getMessage());
        Toast.makeText(this, "No users matches those details", Toast.LENGTH_SHORT).show();


    }
}
