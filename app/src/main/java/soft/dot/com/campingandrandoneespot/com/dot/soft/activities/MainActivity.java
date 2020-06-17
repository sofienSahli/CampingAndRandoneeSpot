package soft.dot.com.campingandrandoneespot.com.dot.soft.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.fragments.CircuitListFragment;
import soft.dot.com.campingandrandoneespot.com.dot.soft.fragments.FloreFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemReselectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    Menu menu;
    Toolbar toolbar2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.main_activity_content);
        bottomNavigationView = findViewById(R.id.menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        commintFragment(new FloreFragment());
        setupActionBar(R.layout.circuit_list);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());
        toolbar2 = findViewById(R.id.toolbar2);
        toolbar2.setTitleTextColor(getResources().getColor(R.color.bpWhite));
    }

    private void setupActionBar(int fragment) {
        switch (fragment) {
            case R.layout.circuit_list:

                break;
        }

    }

    private void goToNewCircuitActivity() {

        Intent intent = new Intent(this, Nouveau_Cricuit_Activity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                goToNewCircuitActivity();

        }
        return true;

    }


    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {
        Toast.makeText(this, "aa", Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.flore:
                commintFragment(new FloreFragment());
                break;
        }
    }

    public void commintFragment(Fragment fragment) {
        bottomNavigationView.setVisibility(View.VISIBLE);

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, "aa", Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.flore:
                commintFragment(new FloreFragment());
                toolbar2.setTitle("Flore Du Rimel");

                break;
        }
        return true;
    }
}
