package soft.dot.com.campingandrandoneespot.com.dot.soft.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.adapters.FauneFloreAdapter;
import soft.dot.com.campingandrandoneespot.com.dot.soft.adapters.SpeciesAdapters;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Item;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Species;

public class FloreFragment extends Fragment {
    RecyclerView horizontal, vertical;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.flore_fragment, container, false);

        horizontal = view.findViewById(R.id.species_list);
        vertical = view.findViewById(R.id.items_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        horizontal.setLayoutManager(mLayoutManager);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        vertical.setLayoutManager(layoutManager);
        populate_list_view();
        return view;
    }

    public void populate_list_view() {
        ArrayList<Item> list_items = new ArrayList<>();
        ArrayList<Species> list_species = new ArrayList<>();
        list_species.add(new Species(2, "Plante"));
        list_species.add(new Species(3, "Arbre"));
        list_species.add(new Species(1, "Chévre volante"));
        list_species.add(new Species(4, "Champinon"));
        list_species.add(new Species(5, "Arbusseau"));

        list_items.add(new Item(1, "Pins", "Aussi connu sous le nom de zgougou cette espéce recouvre plus de 50% de la forêt du Rimel", null));
        list_items.add(new Item(2, "Pins", "Aussi connu sous le nom de zgougou cette espéce recouvre plus de 50% de la forêt du Rimel", null));
        list_items.add(new Item(3, "Pins", "Aussi connu sous le nom de zgougou cette espéce recouvre plus de 50% de la forêt du Rimel", null));
        list_items.add(new Item(4, "Pins", "Aussi connu sous le nom de zgougou cette espéce recouvre plus de 50% de la forêt du Rimel", null));
        list_items.add(new Item(5, "Pins", "Aussi connu sous le nom de zgougou cette espéce recouvre plus de 50% de la forêt du Rimel", null));
        list_items.add(new Item(6, "Pins", "Aussi connu sous le nom de zgougou cette espéce recouvre plus de 50% de la forêt du Rimel", null));
        list_items.add(new Item(7, "Pins", "Aussi connu sous le nom de zgougou cette espéce recouvre plus de 50% de la forêt du Rimel", null));
        list_items.add(new Item(8, "Pins", "Aussi connu sous le nom de zgougou cette espéce recouvre plus de 50% de la forêt du Rimel", null));
        SpeciesAdapters speciesAdapters = new SpeciesAdapters(list_species, getActivity());
        FauneFloreAdapter fauneFloreAdapter = new FauneFloreAdapter(list_items, getContext());
        horizontal.setAdapter(speciesAdapters);
        vertical.setAdapter(fauneFloreAdapter);
        vertical.getAdapter().notifyDataSetChanged();
        horizontal.getAdapter().notifyDataSetChanged();

    }
}
