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
import android.widget.Button;

import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.dot.com.campingandrandoneespot.FreeRaceActivity;
import soft.dot.com.campingandrandoneespot.Nouveau_Cricuit_Activity;
import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.LocalStorage.AppDatabase;
import soft.dot.com.campingandrandoneespot.com.dot.soft.Services.services.CircuitService;
import soft.dot.com.campingandrandoneespot.com.dot.soft.adapters.CircuitListAdapters;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Circuit;

public class CircuitListFragment extends Fragment implements View.OnClickListener, Callback<List<Circuit>> {
    RecyclerView list_parcourt;
    CardView cardView;
    RecyclerView freeRuns;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.base_expandable_fragment, container, false);
        freeRuns = view.findViewById(R.id.recyclerView_freeRun);
        setUpHorizontalRecyclerView();
        callForCircuit();
        view.findViewById(R.id.material_design_floating_action_menu_item1).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Nouveau_Cricuit_Activity.class);
            getActivity().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());

        });
        view.findViewById(R.id.material_design_floating_action_menu_item2).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), FreeRaceActivity.class);
            getActivity().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());

        });
        list_parcourt = view.findViewById(R.id.list_parcours);
        cardView = view.findViewById(R.id.cardView);
        CircuitService circuitService = new CircuitService();
        circuitService.getAll(this);
        ArrayList<Circuit> circuits = (ArrayList<Circuit>) AppDatabase.getAppDatabase(getActivity()).circuitDAO().getFreeRun();
        Log.e("free runs", circuits.toString());
        return view;
    }

    private void setUpHorizontalRecyclerView() {
        List<Circuit> circuits = AppDatabase.getAppDatabase(getActivity()).circuitDAO().getFreeRun();
        CircuitListAdapters adapters = new CircuitListAdapters((ArrayList<Circuit>) circuits, getActivity());
        freeRuns.setAdapter(adapters);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
        freeRuns.setLayoutManager(mLayoutManager);
    }


    private void SetUpRecyclerView(ArrayList<Circuit> circuits) {
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
        if (response.code() == 200) {
            Log.e("Jawou béhi Service", response.body().size() + "");
            SetUpRecyclerView((ArrayList<Circuit>) response.body());
        } else {
            Log.e("Erreur Service not 200", response.message());
            SetUpRecyclerView((ArrayList<Circuit>) AppDatabase.getAppDatabase(getActivity()).circuitDAO().getAll());
        }
    }

    @Override
    public void onFailure(Call<List<Circuit>> call, Throwable t) {
        Log.e("Erreur service", t.getMessage());
        SetUpRecyclerView((ArrayList<Circuit>) AppDatabase.getAppDatabase(getActivity()).circuitDAO().getAll());

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
