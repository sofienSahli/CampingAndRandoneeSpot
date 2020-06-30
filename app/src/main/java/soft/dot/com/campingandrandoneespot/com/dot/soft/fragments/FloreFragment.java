package soft.dot.com.campingandrandoneespot.com.dot.soft.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import java.util.ArrayList;

import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.adapters.FauneFloreAdapter;
import soft.dot.com.campingandrandoneespot.com.dot.soft.adapters.SpeciesAdapters;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Item;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Species;

public class FloreFragment extends Fragment implements SearchView.OnCloseListener, SearchView.OnQueryTextListener, View.OnScrollChangeListener {
    RecyclerView horizontal, vertical;
    final ArrayList<Item> list_items = new ArrayList<>();
    final ArrayList<Species> list_species = new ArrayList<>();
    ScrollView horizontal_scroll_view;
    SearchView search_view;
    public final static String IS_FONE = "fone";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.flore_fragment, container, false);
        horizontal_scroll_view = view.findViewById(R.id.horizontal_scroll_view);
        horizontal = view.findViewById(R.id.species_list);
        vertical = view.findViewById(R.id.items_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        horizontal.setLayoutManager(mLayoutManager);
        horizontal.setOnScrollChangeListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        vertical.setLayoutManager(layoutManager);
        prepareData();
        search_view = view.findViewById(R.id.search_view);
        search_view.setOnCloseListener(this);
        search_view.setOnQueryTextListener(this);

        return view;

    }

    private void prepareData() {
        if (getArguments() != null) {
            if (getArguments().getBoolean(IS_FONE))
                populate_list_view_faune();
            else
                populate_list_view_flore();

        }
    }

    public void populate_list_view_flore() {

        list_species.add(new Species(2, "Plante"));
        list_species.add(new Species(3, "Arbre"));
        list_species.add(new Species(1, "Chévre volante"));

        list_items.add(new Item(1, "Pins", "Aussi connu sous le nom de zgougou cette espéce recouvre plus de 50% de la forêt du Rimel", list_species.get(0)));
        list_items.add(new Item(2, "Pins", "Aussi connu sous le nom de zgougou cette espéce recouvre plus de 50% de la forêt du Rimel", list_species.get(0)));
        list_items.add(new Item(3, "Pins", "Aussi connu sous le nom de zgougou cette espéce recouvre plus de 50% de la forêt du Rimel", list_species.get(0)));
        list_items.add(new Item(4, "Pins", "Aussi connu sous le nom de zgougou cette espéce recouvre plus de 50% de la forêt du Rimel", list_species.get(0)));
        list_items.add(new Item(5, "Pins", "Aussi connu sous le nom de zgougou cette espéce recouvre plus de 50% de la forêt du Rimel", list_species.get(1)));
        list_items.add(new Item(6, "Pins", "Aussi connu sous le nom de zgougou cette espéce recouvre plus de 50% de la forêt du Rimel", list_species.get(2)));
        list_items.add(new Item(7, "Pins", "Aussi connu sous le nom de zgougou cette espéce recouvre plus de 50% de la forêt du Rimel", list_species.get(2)));
        list_items.add(new Item(8, "Pins", "Aussi connu sous le nom de zgougou cette espéce recouvre plus de 50% de la forêt du Rimel", list_species.get(2)));

        instantiateAdapter(list_species, list_items);

    }

    public void populate_list_view_faune() {
        list_species.add(new Species(2, "Insecte"));
        list_species.add(new Species(3, "Mamifére"));
        list_species.add(new Species(1, "Chévre volante"));

        list_items.add(new Item(1, "Scarabé des sables", "Aussi connu sous le nom de zgougou cette espéce recouvre plus de 50% de la forêt du Rimel", list_species.get(0)));
        list_items.add(new Item(2, "Serpent", "Aussi connu sous le nom de zgougou cette espéce recouvre plus de 50% de la forêt du Rimel", list_species.get(0)));
        list_items.add(new Item(3, "Masmoudi", "Aussi connu sous le nom de zgougou cette espéce recouvre plus de 50% de la forêt du Rimel", list_species.get(0)));
        list_items.add(new Item(4, "Chat sauvage", "Aussi connu sous le nom de zgougou cette espéce recouvre plus de 50% de la forêt du Rimel", list_species.get(0)));
        list_items.add(new Item(5, "Chien érant", "Aussi connu sous le nom de zgougou cette espéce recouvre plus de 50% de la forêt du Rimel", list_species.get(1)));
        list_items.add(new Item(6, "Anomaly naturel", "Aussi connu sous le nom de zgougou cette espéce recouvre plus de 50% de la forêt du Rimel", list_species.get(2)));
        list_items.add(new Item(7, "Faucon", "Aussi connu sous le nom de zgougou cette espéce recouvre plus de 50% de la forêt du Rimel", list_species.get(2)));
        list_items.add(new Item(8, "Oiseaux des murs", "Aussi connu sous le nom de zgougou cette espéce recouvre plus de 50% de la forêt du Rimel", list_species.get(2)));

        instantiateAdapter(list_species, list_items);

    }

    private void instantiateAdapter(ArrayList<Species> species, ArrayList<Item> items) {
        SpeciesAdapters speciesAdapters = new SpeciesAdapters(species, getActivity(), this);
        FauneFloreAdapter fauneFloreAdapter = new FauneFloreAdapter(items, getContext());
        horizontal.setAdapter(speciesAdapters);
        vertical.setAdapter(fauneFloreAdapter);
        vertical.getAdapter().notifyDataSetChanged();
        horizontal.getAdapter().notifyDataSetChanged();
    }

    public void speciesClicked(Species species) {
        ArrayList<Item> items = new ArrayList<>();
        for (Item i : list_items) {
            if (i.getSpecies().getId() == species.getId()) {
                items.add(i);
            }
        }
        FauneFloreAdapter fauneFloreAdapter = new FauneFloreAdapter(items, getContext());
        vertical.setAdapter(fauneFloreAdapter);
        fauneFloreAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onClose() {
        instantiateAdapter(list_species, list_items);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        query = query.toLowerCase();

        ArrayList<Item> items = new ArrayList<>();
        ArrayList<Species> species = new ArrayList<>();

        for (Item i : list_items) {
            if (i.getName().toLowerCase().contains(query)) {
                items.add(i);
            }
        }
        for (Species i : list_species) {
            if (i.getTitle().toLowerCase().contains(query)) {
                species.add(i);
            }
        }
        instantiateAdapter(species, items);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();

        ArrayList<Item> items = new ArrayList<>();
        ArrayList<Species> species = new ArrayList<>();

        for (Item i : list_items) {
            if (i.getName().toLowerCase().contains(newText)) {
                items.add(i);
            }
        }
        for (Species i : list_species) {
            if (i.getTitle().toLowerCase().contains(newText)) {
                species.add(i);
            }
        }
        instantiateAdapter(species, items);


        return true;
    }

    @Override
    public void onScrollChange(View view, int i, int i1, int i2, int i3) {
        horizontal_scroll_view.scrollBy(i, i1);
    }
}
