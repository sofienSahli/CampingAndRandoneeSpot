package soft.dot.com.campingandrandoneespot.com.dot.soft.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.User;
import soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage.UserSharedPref;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.services.UserService;

public class AccountActivationActivity extends AppCompatActivity implements View.OnClickListener, Callback<User> {
    EditText char1;
    Button button8, button9, button10;
    User user;
    ConstraintLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_activation);
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle.getParcelable("user") != null) {
                user = bundle.getParcelable("user");
                char1 = findViewById(R.id.char1);
                button8 = findViewById(R.id.button8);
                button9 = findViewById(R.id.button9);
                button10 = findViewById(R.id.button10);
                button8.setOnClickListener(this);
                button9.setOnClickListener(this);
                button10.setOnClickListener(this);
                root = findViewById(R.id.root);
            }

        } else {
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        UserService userService = new UserService();

        if (view.getId() == R.id.button8) {
            userService.activate_account(user.getId() + "", user.getActivation_code(), this);
        } else if (view.getId() == R.id.button9) {
            userService.resend_activation_code(user.getId() + "", new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        } else if (view.getId() == R.id.button10) {
            send_sms_activation_code(user.getActivation_code());
        }
    }

    private void send_sms_activation_code(String activiation_code) {
        String s = "Merci pour votre inscription. Votre code d'activation est :" + activiation_code;
        SmsManager smgr = SmsManager.getDefault();
        smgr.sendTextMessage(user.getPhone() + "", null, s, null, null);
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if (response.code() == 200) {
            UserSharedPref userSharedPref = new UserSharedPref(getSharedPreferences(UserSharedPref.USER_FILE, Context.MODE_PRIVATE));
            userSharedPref.logIn(response.body());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } else {
            Snackbar.make(root, "Vérifier votre connexion puis réssayer", Snackbar.LENGTH_INDEFINITE).setAction("Retry", view -> {
                UserService userService = new UserService();
                userService.activate_account(user.getId() + "", user.getActivation_code(), this);
            });
        }
    }


    @Override
    public void onFailure(Call<User> call, Throwable t) {
        Snackbar.make(root, "Vérifier votre connexion puis réssayer", Snackbar.LENGTH_INDEFINITE).setAction("Retry", view -> {
            UserService userService = new UserService();
            userService.activate_account(user.getId() + "", user.getActivation_code(), this);
        });
    }
}