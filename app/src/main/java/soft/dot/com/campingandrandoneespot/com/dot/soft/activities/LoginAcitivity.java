package soft.dot.com.campingandrandoneespot.com.dot.soft.activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Roles;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.User;
import soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage.UserSharedPref;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.services.UserService;

public class LoginAcitivity extends AppCompatActivity implements View.OnClickListener, Callback<List<User>> {
    private final int RC_SIGN_IN = 101;
    private final int FB_SIGN_IN = 202;
    private CallbackManager callbackManager;
    LoginButton facebook_login;
    Button sign_button, login_button;
    EditText email_field, password_field;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        UserSharedPref userSharedPref = new UserSharedPref(getSharedPreferences(UserSharedPref.USER_FILE, Context.MODE_PRIVATE));

        if (!TextUtils.isEmpty(userSharedPref.getString(UserSharedPref.USER_FIRST_NAME))) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }
        googleSignSetUp();
        setUpFacebook();
        setUpTransition();

        email_field = findViewById(R.id.email_field);
        password_field = findViewById(R.id.password_field);

        sign_button = findViewById(R.id.sign_button);
        login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(this);
        sign_button.setOnClickListener(this);


    }

    private void setUpTransition() {
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new ChangeBounds());
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        // Toast.makeText(this, "on start", Toast.LENGTH_SHORT).show();
    }


    private void googleSignSetUp() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(v -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {

            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            Log.e(" Google Sign In error", "signInResult:failed code=" + e.getMessage());
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        User user = new User();
        user.setLastName(account.getFamilyName());
        user.setFirstName(account.getGivenName());
        user.setEmail(account.getEmail());
        user.setRole(Roles.SIMPLE_USER);
        Log.e("user", account.toJson());
        Bundle bundle = new Bundle();
        bundle.putParcelable(SignUp.USER_KEY, user);
        Intent intent = new Intent(this, SignUp.class);
        intent.putExtras(bundle);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @Override
    public void onBackPressed() {

    }

    private void setUpFacebook() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());
        callbackManager = CallbackManager.Factory.create();
        facebook_login = findViewById(R.id.facebook_login);
        facebook_login.setReadPermissions(Arrays.asList("email"));
        facebook_login.setHeight(50);
        facebook_login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        (object, response) -> {

                            if (object != null) {

                                //Log.e("Graph Response " , object.toString());
                                userFromFacebook(object);
                            } else {
                                Toast.makeText(LoginAcitivity.this, "Echec de l'utilisation de votre compte facebook, veuilliez utiliser une autre méthode.", Toast.LENGTH_LONG).show();
                            }
                        }

                );
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,first_name,last_name,birthday,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginAcitivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Facebook", error.getMessage());

            }
        });


    }

    //Handle user information from facebook
    private void userFromFacebook(JSONObject object) {
        try {

            User user = new User();
            user.setFirstName(object.getString("first_name"));
            user.setLastName(object.getString("last_name"));
            user.setRole(Roles.SIMPLE_USER);
            user.setEmail(object.getString("email"));
            Bundle bundle = new Bundle();
            bundle.putParcelable(SignUp.USER_KEY, user);
            Intent intent = new Intent(this, SignUp.class);
            intent.putExtras(bundle);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

        } catch (JSONException e) {
            Log.e("FacebookGraph", e.getMessage());
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                if (!TextUtils.isEmpty(email_field.getText()) && !TextUtils.isEmpty(password_field.getText())) {
                    sign_in(email_field.getText().toString(), password_field.getText().toString());
                }
                break;
            case R.id.sign_button:
                ImageView background = findViewById(R.id.background);
                Intent intent = new Intent(LoginAcitivity.this, SignUp.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, background, "background").toBundle());
                break;
        }
    }

    private void sign_in(String toString, String toString1) {
        UserService userService = new UserService();
        userService.logIn(toString1, toString, this);
    }


    @Override
    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
        if (response.code() == 200 && response.body().size() > 0) {


            if (response.body().get(0) != null) {
                if (response.body().get(0).isActive() ) {
                    UserSharedPref userSharedPref = new UserSharedPref(getSharedPreferences(UserSharedPref.USER_FILE, Context.MODE_PRIVATE));
                    userSharedPref.logIn(response.body().get(0));
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                } else {
                    Toast.makeText(this, "Account not activated yet", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "Veuilliez vérifier vos identifiant", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onFailure(Call<List<User>> call, Throwable t) {
        Log.e("Login failed", t.getMessage());
        Toast.makeText(this, "Veuilliez vérifier vos identifiant", Toast.LENGTH_SHORT).show();

    }
}
