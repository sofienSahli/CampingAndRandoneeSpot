package soft.dot.com.campingandrandoneespot.com.dot.soft.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.ImageSwitcher;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import soft.dot.com.campingandrandoneespot.R;

public class FirstIntroductionFragment extends Fragment {
    ImageSwitcher imageSwitcher;
    Context parentActivity ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentActivity = getActivity() ;
        return inflater.inflate(R.layout.fragment_introduction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        imageSwitcher = view.findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(() -> {
            ImageView iv = new ImageView(getActivity());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            return iv;
        });
        Animation in = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in);
        imageSwitcher.setInAnimation(in);

        imageSwitcher.setImageResource(R.drawable.rimel_landscape_2);
        Timer t = new Timer();
        //Set the schedule function and rate


        t.scheduleAtFixedRate(new TimerTask() {
            int ids[] = {R.drawable.besla, R.drawable.rimel_landscape_2, R.drawable.rimel_landscapte, R.drawable.rimel_tree};
            int current_index = 0;

            public void run() {

                ((AppCompatActivity)parentActivity).runOnUiThread(() -> {
                    imageSwitcher.setImageResource(ids[current_index]);
                });
                current_index++;
                if (current_index == 4)
                    current_index = 0;
            }

        }, 0, 1000);


    }
}
