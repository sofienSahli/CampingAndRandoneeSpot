package soft.dot.com.campingandrandoneespot.com.dot.soft.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import soft.dot.com.campingandrandoneespot.MainActivity;
import soft.dot.com.campingandrandoneespot.R;

public class IntroFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promotion, container, false);
        view.findViewById(R.id.button2).setOnClickListener(v -> ((MainActivity) getActivity()).commintFragment(new CircuitListFragment()));
        return view;
    }
}
