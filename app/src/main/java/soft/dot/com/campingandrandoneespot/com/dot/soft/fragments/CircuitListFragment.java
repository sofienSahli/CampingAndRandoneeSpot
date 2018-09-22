package soft.dot.com.campingandrandoneespot.com.dot.soft.fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.dot.com.campingandrandoneespot.Nouveau_Cricuit_Activity;
import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.LocalStorage.AppDatabase;
import soft.dot.com.campingandrandoneespot.com.dot.soft.Services.services.CircuitService;
import soft.dot.com.campingandrandoneespot.com.dot.soft.adapters.CircuitListAdapters;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Circuit;

public class CircuitListFragment extends Fragment implements View.OnClickListener, Callback<List<Circuit>> {
    RecyclerView list_parcourt;
    CardView cardView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.base_expandable_fragment, container, false);
        SetUpRecyclerView(view, (ArrayList<Circuit>) AppDatabase.getAppDatabase(getActivity()).circuitDAO().getAll());
        callForCircuit();
        view.findViewById(R.id.imageButton).setOnClickListener(v -> cardeFedeOut());
        view.findViewById(R.id.fab).setOnClickListener(v -> goToNewCircuitActivity());
        cardView = view.findViewById(R.id.cardView);

        return view;
    }

    private void SetUpRecyclerView(View view, ArrayList<Circuit> circuits) {
        list_parcourt = view.findViewById(R.id.list_parcours);
        CircuitListAdapters adapters = new CircuitListAdapters(circuits, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        list_parcourt.setLayoutManager(mLayoutManager);
        list_parcourt.setAdapter(adapters);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onResponse(Call<List<Circuit>> call, Response<List<Circuit>> response) {

    }

    @Override
    public void onFailure(Call<List<Circuit>> call, Throwable t) {
        Log.e("Erreur service", t.getMessage());
/*        Snackbar.make(getView(), "Unable to join distant server", Snackbar.LENGTH_SHORT).setAction("Retry", v -> {
            callForCircuit();
        }).show();*/
    }

    private void callForCircuit() {
        // CircuitService circuitService = new CircuitService();
        //circuitService.getAll(this);
    }

    private void goToNewCircuitActivity() {

        Intent intent = new Intent(getActivity(), Nouveau_Cricuit_Activity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
    }

    private void cardeFedeOut() {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.background_anim_fade_out);
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

}
