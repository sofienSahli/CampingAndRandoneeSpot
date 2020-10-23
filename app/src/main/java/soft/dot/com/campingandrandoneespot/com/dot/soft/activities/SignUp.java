package soft.dot.com.campingandrandoneespot.com.dot.soft.activities;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Roles;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.User;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.services.UserService;
import soft.dot.com.campingandrandoneespot.com.dot.soft.utils.ProgressDialog;

public class SignUp extends AppCompatActivity implements Callback<User> {
    EditText email_field, name, password_field, confirm_password_field, phone, prenom;
    Button birth_date;
    ImageButton sign_up;
    ImageButton back;
    private final int ACCESS_SIM_CARD = 101;
    private final int SEND_SMS = 102;
    public final static String USER_KEY = "user";
    ScrollView scroll_view;
    User user;
     ProgressDialog progressDialog;
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
        phone = findViewById(R.id.phone);
        prenom = findViewById(R.id.prenom);
        birth_date = findViewById(R.id.birth_date);
        back = findViewById(R.id.back);
        scroll_view = findViewById(R.id.scroll_view);
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Explode());
        if (getIntent().getExtras() != null) {
            this.user = getIntent().getExtras().getParcelable(USER_KEY);
            email_field.setText(user.getEmail());
            name.setText(user.getLastName());
            prenom.setText(user.getFirstName());
        }
        back.setOnClickListener(v -> {
            finish();
        });
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            getRuntimePermission(new String[]{Manifest.permission.READ_PHONE_STATE} , ACCESS_SIM_CARD);
        } else {

            TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String mPhoneNumber = tMgr.getLine1Number();
            phone.setText(mPhoneNumber);
        }
        birth_date.setOnClickListener(v -> {
            pickDate(birth_date);
        });
        sign_up.setOnClickListener(v -> {
            check_and_register_user();
        });
        progressDialog = new ProgressDialog(this);

    }

    private void check_and_register_user() {

        if (TextUtils.isEmpty(name.getText())) {
            name.setBackground(getResources().getDrawable(R.drawable.empty_text_field_background, getTheme()));
        } else if (TextUtils.isEmpty(email_field.getText())) {
            email_field.setBackground(getResources().getDrawable(R.drawable.empty_text_field_background, getTheme()));
        } else if (TextUtils.isEmpty(password_field.getText())) {
            password_field.setBackground(getResources().getDrawable(R.drawable.empty_text_field_background, getTheme()));
        } else if (TextUtils.isEmpty(confirm_password_field.getText())) {
            confirm_password_field.setBackground(getResources().getDrawable(R.drawable.empty_text_field_background, getTheme()));
        } else if (!isEmailValid(email_field.getText().toString())) {
            Toast.makeText(this, "E-mail non valide", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(birth_date.getText())) {
            birth_date.setBackground(getResources().getDrawable(R.drawable.empty_text_field_background, getTheme()));
        } else if (TextUtils.isEmpty(prenom.getText())) {
            prenom.setBackground(getResources().getDrawable(R.drawable.empty_text_field_background, getTheme()));

        } else if (TextUtils.isEmpty(phone.getText())) {
            phone.setBackground(getResources().getDrawable(R.drawable.empty_text_field_background, getTheme()));
        } else {
            progressDialog.showDissmissDialog();
            User user = new User();
            user.setId(System.currentTimeMillis());
            user.setEmail(email_field.getText().toString());
            user.setPassword(password_field.getText().toString());
            user.setFirstName(prenom.getText().toString());
            user.setLastName(name.getText().toString());
            user.setActive(false);
            user.setBirthDate(birth_date.getText().toString());
            int i = Integer.parseInt(phone.getText().toString());
            user.setPhone(i);
            user.setActivation_code(activiation_code());
            user.setRole(Roles.SIMPLE_USER);
            UserService us = new UserService();
            us.SignUpUser(user, this);
        }

    }

    private String activiation_code() {
        StringBuilder s = new StringBuilder();
        String alphabet = "abcedfghijklmnopqrstuvwxyz123456789";
        Random r = new Random();

        for (int i = 0; i < 6; i++) {
            s.append(alphabet.charAt(r.nextInt(alphabet.length())));
        }
        return s.toString();
    }


    private void setUpTransition() {
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Slide());

    }


    public static boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // WebService space

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        progressDialog.showDissmissDialog();
        if (response.body() != null && response.code() == 200) {
            this.user = response.body();
            Intent intent = new Intent(this, AccountActivationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("user", response.body());
            intent.putExtras(bundle);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                getRuntimePermission(new String[]{Manifest.permission.SEND_SMS},SEND_SMS);
            } else {
                send_sms_activation_code(response.body().getActivation_code());
            }

        } else {
            progressDialog.showDissmissDialog();

            Snackbar.make(scroll_view, "Veuilliez vérifier votre conexion internet puis ressayer", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Ressayer", view -> {
                        check_and_register_user();
                    });
        }
    }

    private void send_sms_activation_code(String activiation_code) {
        String s = "Merci pour votre inscription. Votre code d'activation est :" + activiation_code;
        SmsManager smgr = SmsManager.getDefault();
        smgr.sendTextMessage(phone.getText().toString(), null, s, null, null);
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        Log.e("Error", t.getMessage());
        Snackbar.make(scroll_view, "Veuilliez vérifier votre conexion internet puis ressayer", Snackbar.LENGTH_INDEFINITE)
                .setAction("Ressayer", view -> {
                    check_and_register_user();
                });
    }

    private void getRuntimePermission(String[] s1,int code) {
        ActivityCompat.requestPermissions(this, s1, code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == ACCESS_SIM_CARD && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String mPhoneNumber = tMgr.getLine1Number();
            phone.setText(mPhoneNumber);

        } else if (requestCode == SEND_SMS && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            send_sms_activation_code(user.getActivation_code());

        }

    }


    void pickDate(Button b) {

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (DatePickerDialog.OnDateSetListener) (view, year, monthOfYear, dayOfMonth) -> b.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth), mYear, mMonth, mDay);
        datePickerDialog.show();
    }


}
