package soft.dot.com.campingandrandoneespot.com.dot.soft.activities;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.fragments.CircuitListFragment;
import soft.dot.com.campingandrandoneespot.com.dot.soft.fragments.EventFragment;
import soft.dot.com.campingandrandoneespot.com.dot.soft.fragments.ProfilFragment;

public class ProfilActivity extends AppCompatActivity {
    ViewPager pager;
    public final static String USER_KEY = "keyuser";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        pager = findViewById(R.id.pager);
        pager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
    }

    private class PagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.fragments.add(new ProfilFragment());
            this.fragments.add(new EventFragment());
            this.fragments.add(new CircuitListFragment());

        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

                switch (position) {
                    case 0:
                        return "Profil";
                    case 1:
                        return "Evenements";
                    case 2:
                        return "Circuits";

            }
            return null;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }


        @Override
        public int getCount() {
            return fragments.size();
        }

    }
}