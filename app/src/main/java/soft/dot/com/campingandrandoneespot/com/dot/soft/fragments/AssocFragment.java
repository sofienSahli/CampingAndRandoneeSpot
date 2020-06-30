package soft.dot.com.campingandrandoneespot.com.dot.soft.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import soft.dot.com.campingandrandoneespot.R;

public class AssocFragment extends Fragment {
    ViewPager vp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assoc, container, false);
        vp = view.findViewById(R.id.vp);
        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager());
        vp.setAdapter(pagerAdapter);
        return view;
    }

    class PagerAdapter extends FragmentStatePagerAdapter {
        Fragment[] fragments = {new AssocDescriptionFragment(), new EventFragment()};

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments[i];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0)
                return "Presentation";
            else if (position == 1)
                return "Events";
            return "";
        }
    }
}
