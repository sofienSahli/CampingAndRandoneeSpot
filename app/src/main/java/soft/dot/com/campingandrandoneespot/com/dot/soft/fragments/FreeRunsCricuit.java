package soft.dot.com.campingandrandoneespot.com.dot.soft.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage.AppDatabase;
import soft.dot.com.campingandrandoneespot.com.dot.soft.adapters.CircuitListAdapters;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Circuit;

public class FreeRunsCricuit extends Fragment {


    private RecyclerView freeRuns;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.free_runs_fragment, container, false);
        freeRuns = view.findViewById(R.id.list_parcours);
        setUpRecyclerView();
        return view;
    }

    private void setUpRecyclerView() {
        ArrayList<Circuit> circuits = (ArrayList<Circuit>) AppDatabase.getAppDatabase(getActivity()).circuitDAO().getFreeRun();

        CircuitListAdapters adapters = new CircuitListAdapters(circuits, getActivity());
        freeRuns.setAdapter(adapters);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        freeRuns.setLayoutManager(mLayoutManager);
        adapters.notifyDataSetChanged();
    }



}
