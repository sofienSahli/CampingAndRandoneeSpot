package soft.dot.com.campingandrandoneespot.com.dot.soft.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.transition.Fade;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.adapters.UserAdapters;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.User;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.services.UserService;

public class NewUserAccountsActivity extends AppCompatActivity {

    RecyclerView user_list;
    Callback<List<User>> callback = new Callback<List<User>>() {
        @Override
        public void onResponse(Call<List<User>> call, Response<List<User>> response) {
            if (response.body() != null && !response.body().isEmpty())
                instantiate_adapter(response.body());
        }

        @Override
        public void onFailure(Call<List<User>> call, Throwable t) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_accounts);
        setUpView();

    }

    private void setUpView() {
        getNormalUser();

        Toolbar toolbar2 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());
        user_list = findViewById(R.id.user_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        user_list.setLayoutManager(layoutManager);
        //  if (getSupportActionBar() != null)
        //    getSupportActionBar().hide();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 101);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_management_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.submenu0) {
            getNormalUser();
        } else if (id == R.id.submenu1) {
            getAdminUser();
        } else if (id == R.id.submenu2) {
            getSuperAdminUser();
        }


        return super.onOptionsItemSelected(item);
    }

    private void getSuperAdminUser() {
        UserService userService = new UserService();
        userService.get_super_admin(callback);
    }

    private void getAdminUser() {
        UserService userService = new UserService();
        userService.get_admins_user(callback);

    }

    private void getNormalUser() {
        UserService userService = new UserService();
        userService.get_simple_user(callback);
    }

    public void instantiate_adapter(List<User> userList) {
        UserAdapters userAdapters = new UserAdapters(this, userList);
        user_list.setAdapter(userAdapters);
    }
}
