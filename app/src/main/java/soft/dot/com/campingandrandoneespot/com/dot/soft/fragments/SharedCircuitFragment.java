package soft.dot.com.campingandrandoneespot.com.dot.soft.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage.AppDatabase;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.services.CircuitService;
import soft.dot.com.campingandrandoneespot.com.dot.soft.adapters.CircuitListAdapters;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Circuit;

public class SharedCircuitFragment extends Fragment implements Callback<List<Circuit>> {
    RecyclerView list_parcourt;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.circuit_list, container, false);
        list_parcourt = view.findViewById(R.id.list_parcours);
        CircuitService circuitService = new CircuitService();
        circuitService.getAll(this);
        return view;
    }

    private void SetUpRecyclerView(ArrayList<Circuit> circuits) {
        CircuitListAdapters adapters = new CircuitListAdapters(circuits, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        list_parcourt.setLayoutManager(mLayoutManager);
        list_parcourt.setAdapter(adapters);
    }


    @Override
    public void onResponse(Call<List<Circuit>> call, Response<List<Circuit>> response) {
        if (response.code() == 200) {
            Log.e("Jawou béhi Service", response.body().size() + "");
            SetUpRecyclerView((ArrayList<Circuit>) response.body());
        } else {
            Snackbar.make(view, "Failed to acces remote server, please retry ", Snackbar.LENGTH_INDEFINITE).setAction("Retry", view -> {
                CircuitService circuitService = new CircuitService();
                circuitService.getAll(this);
            });

        }
    }

    @Override
    public void onFailure(Call<List<Circuit>> call, Throwable t) {
        Log.e("Erreur service", t.getMessage());
        SetUpRecyclerView((ArrayList<Circuit>) AppDatabase.getAppDatabase(getActivity()).circuitDAO().getAll());

    }

}
