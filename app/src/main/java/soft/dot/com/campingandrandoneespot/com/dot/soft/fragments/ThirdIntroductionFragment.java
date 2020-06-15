package soft.dot.com.campingandrandoneespot.com.dot.soft.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.activities.LoginAcitivity;
import soft.dot.com.campingandrandoneespot.com.dot.soft.activities.SignUp;

public class ThirdIntroductionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.third_fragment_introduction, container, false);
        view.findViewById(R.id.register).setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), SignUp.class);
            startActivity(intent );
        });
        view.findViewById(R.id.login).setOnClickListener(view12 -> {
            Intent intent = new Intent(getActivity(), LoginAcitivity.class);
            startActivity(intent );

        });
        return view;
    }
}
