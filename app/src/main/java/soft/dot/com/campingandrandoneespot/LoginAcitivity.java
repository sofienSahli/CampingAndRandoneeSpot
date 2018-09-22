package soft.dot.com.campingandrandoneespot;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeScroll;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginAcitivity extends AppCompatActivity implements View.OnClickListener {
    private final int RC_SIGN_IN = 101;
    private final int FB_SIGN_IN = 202;
    private CallbackManager callbackManager;
    private LoginButton facebook_login;
    private Button sign_button, login_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        googleSignSetUp();
        setUpFacebook();

        setUpTransition();
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);


    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(this, account.getAccount().toString(), Toast.LENGTH_SHORT).show();
            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e(" Google Sign In error", "signInResult:failed code=" + e.getMessage());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {

        Toast.makeText(this, account.getAccount().name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {

    }

    private void setUpFacebook() {
        callbackManager = CallbackManager.Factory.create();
        facebook_login = findViewById(R.id.facebook_login);
        facebook_login.setReadPermissions("email");
        facebook_login.setHeight(50);
        facebook_login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(LoginAcitivity.this, loginResult.getAccessToken().getUserId(), Toast.LENGTH_SHORT).show();
                Log.e("Facebook", " ok ");
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                Intent i = new Intent(LoginAcitivity.this, MainActivity.class);
                startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

                break;
            case R.id.sign_button:
                ImageView background = findViewById(R.id.background);
                Intent intent = new Intent(LoginAcitivity.this, SignUp.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, background, "background").toBundle());

                break;
        }
    }
}
