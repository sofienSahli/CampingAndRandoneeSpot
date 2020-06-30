package soft.dot.com.campingandrandoneespot.com.dot.soft.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.fragments.AssocFragment;
import soft.dot.com.campingandrandoneespot.com.dot.soft.fragments.FloreFragment;
import soft.dot.com.campingandrandoneespot.com.dot.soft.fragments.ProfilFragment;

public class MainActivity extends AppCompatActivity implements  BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.main_activity_content);
        bottomNavigationView = findViewById(R.id.menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());
        commintFragment(new AssocFragment());
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
        FloreFragment floreFragment = new FloreFragment();
        Bundle bundle = new Bundle();
        switch (item.getItemId()) {
            case R.id.flore:
                bundle.putBoolean(FloreFragment.IS_FONE, false);
                floreFragment.setArguments(bundle);
                commintFragment(floreFragment);
                break;
            case R.id.faune:
                bundle.putBoolean(FloreFragment.IS_FONE, true);
                floreFragment.setArguments(bundle);
                commintFragment(floreFragment);
                break;
            case R.id.home:
                commintFragment(new AssocFragment());
                break;
            case R.id.profil:
                commintFragment(new ProfilFragment());
                break;
        }
        return true;
    }
}
