package soft.dot.com.campingandrandoneespot;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import soft.dot.com.campingandrandoneespot.com.dot.soft.fragments.CircuitListFragment;
import soft.dot.com.campingandrandoneespot.com.dot.soft.fragments.IntroFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemReselectedListener {

    BottomNavigationView bottomNavigationView;
    Menu menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.main_activity_content);
        bottomNavigationView = findViewById(R.id.menu);
        commintFragment(new IntroFragment());
        bottomNavigationView.setVisibility(View.GONE);

        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());

    }

    private void goToNewCircuitActivity() {

        Intent intent = new Intent(this, Nouveau_Cricuit_Activity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    private void cardeFedeOut() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.background_anim_fade_out);
        CardView cardView = findViewById(R.id.cardView);
        cardView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
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

    }

    public void commintFragment(Fragment fragment) {
        bottomNavigationView.setVisibility(View.VISIBLE);

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
