package soft.dot.com.campingandrandoneespot.com.dot.soft.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.fragments.FirstIntroductionFragment;
import soft.dot.com.campingandrandoneespot.com.dot.soft.fragments.SecondIntroductionFragment;
import soft.dot.com.campingandrandoneespot.com.dot.soft.fragments.ThirdIntroductionFragment;

public class FirstActivity extends AppCompatActivity {
    ViewPager viewPager;
    int currentIndex = 0;
    final int MAX_TAB = 2 ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_content);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            private static final float MIN_SCALE = 0.75f;

            public void transformPage(View view, float position) {
                int pageWidth = view.getWidth();

                if (position < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    view.setAlpha(0f);

                } else if (position <= 0) { // [-1,0]
                    // Use the default slide transition when moving to the left page
                    view.setAlpha(1f);
                    view.setTranslationX(0f);
                    view.setScaleX(1f);
                    view.setScaleY(1f);

                } else if (position <= 2) { // (0,1]
                    // Fade the page out.
                    view.setAlpha(1 - position);

                    // Counteract the default slide transition
                    view.setTranslationX(pageWidth * -position);

                    // Scale the page down (between MIN_SCALE and 1)
                    float scaleFactor = MIN_SCALE
                            + (1 - MIN_SCALE) * (1 - Math.abs(position));
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);

                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    view.setAlpha(0f);
                }
            }
        });
        TabLayout tabLayout = findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);
        findViewById(R.id.button3).setOnClickListener(v -> {
            Intent intent = new Intent(FirstActivity.this, MainActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(FirstActivity.this).toBundle());
        });

        findViewById(R.id.imageButton).setOnClickListener(v -> {
            currentIndex++ ;
            if(currentIndex >= MAX_TAB){
                currentIndex = MAX_TAB ;
            }
            viewPager.setCurrentItem(currentIndex, true);
        });
    }

    private class PagerAdapter extends FragmentPagerAdapter {
        final Fragment[] fragments = {new FirstIntroductionFragment(), new SecondIntroductionFragment(), new ThirdIntroductionFragment()};

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }
}