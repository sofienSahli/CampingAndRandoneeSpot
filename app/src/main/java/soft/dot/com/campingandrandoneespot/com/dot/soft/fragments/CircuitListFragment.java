package soft.dot.com.campingandrandoneespot.com.dot.soft.fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import soft.dot.com.campingandrandoneespot.com.dot.soft.activities.FreeRaceActivity;
import soft.dot.com.campingandrandoneespot.com.dot.soft.activities.Nouveau_Cricuit_Activity;
import soft.dot.com.campingandrandoneespot.R;

public class CircuitListFragment extends Fragment {

    private PagerTabStrip mIndicator;
    private ViewPager viewPager;
    Menu menu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.base_expandable_fragment, container, false);
        view.findViewById(R.id.material_design_floating_action_menu_item1).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Nouveau_Cricuit_Activity.class);
            getActivity().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());

        });
        view.findViewById(R.id.material_design_floating_action_menu_item2).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), FreeRaceActivity.class);
            getActivity().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());

        });

        viewPager = view.findViewById(R.id.viewPager);
        mIndicator = view.findViewById(R.id.page_indicator);
        mIndicator.setDrawFullUnderline(true);
        mIndicator.setTabIndicatorColor(getResources().getColor(R.color.colorAccent));
        mIndicator.setTextColor(getResources().getColor(R.color.colorWhite));
        ScreenSlidePagerAdapter screenSlidePagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(screenSlidePagerAdapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu, menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        Fragment[] fragments = {new FreeRunsCricuit(), new SharedCircuitFragment()};

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Historique personel";

                case 1:
                    return "Circuit partagées";


            }

            return null;
        }
    }


}
