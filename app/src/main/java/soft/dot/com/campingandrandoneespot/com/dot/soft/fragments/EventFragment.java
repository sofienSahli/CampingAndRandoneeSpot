package soft.dot.com.campingandrandoneespot.com.dot.soft.fragments;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.activities.EventDetailActivity;
import soft.dot.com.campingandrandoneespot.com.dot.soft.activities.NewEventActivity;
import soft.dot.com.campingandrandoneespot.com.dot.soft.adapters.EventAdapter;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Event;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Roles;
import soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage.UserSharedPref;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.services.EventServices;

public class EventFragment extends Fragment implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    RecyclerView list_event;
    Spinner categories;
    ImageButton button7;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        list_event = view.findViewById(R.id.list_event);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        list_event.setLayoutManager(layoutManager);
        categories = view.findViewById(R.id.categories);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.row_spinner, getResources().getStringArray(R.array.event_categories));
        categories.setAdapter(arrayAdapter);
        populateListEvent();
        button7 = view.findViewById(R.id.button7);
        button7.setOnClickListener(this);
        return view;
    }

    private void populateListEvent() {
        EventServices eventServices = new EventServices();

        eventServices.index(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.code() == 200) {
                    if (response.body() != null && !response.body().isEmpty()) {
                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
                        list_event.addItemDecoration(dividerItemDecoration);
                        EventAdapter eventAdapter = new EventAdapter((ArrayList<Event>) response.body(), getActivity(), EventFragment.this);
                        list_event.setAdapter(eventAdapter);

                    }
                } else {
                    Toast.makeText(getActivity(), "Erreur intern veuillier ressayer plus tard", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(getActivity(), "Erreur intern veuillier ressayer plus tard", Toast.LENGTH_LONG).show();
                Log.e("PopulateListEvent", t.getMessage());
            }
        });
    }

    public void launchDetailsActivity(Event event) {
        Bundle bundle = new Bundle();
        bundle.putString(EventDetailActivity.EVENT_DESCRIPTION, event.getDescription());
        bundle.putString(EventDetailActivity.EVENT_LOCATION, event.getPlace());
        bundle.putString(EventDetailActivity.EVENT_DATE, event.getDate());
        bundle.putString(EventDetailActivity.EVENT_NAME, event.getTitle());
        bundle.putParcelable(EventDetailActivity.EVENT_KEY, event);
        Intent intent = new Intent(getActivity(), EventDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button7) {
            UserSharedPref userSharedPref = new UserSharedPref(getActivity().getSharedPreferences(UserSharedPref.USER_FILE, Context.MODE_PRIVATE));
            String role = userSharedPref.getString(UserSharedPref.USER_ROLE);

            MenuInflater menuInflater = getActivity().getMenuInflater();
            PopupMenu popupMenu = new PopupMenu(getActivity(), button7);
            popupMenu.setOnMenuItemClickListener(this);
            if (!role.equals(Roles.SUPER_ADMIN)) {
                menuInflater.inflate(R.menu.event_menu, popupMenu.getMenu());
            } else {
                menuInflater.inflate(R.menu.super_admin_event_menu, popupMenu.getMenu());
            }
            popupMenu.show();

        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.proposer_event) {
            Intent intent = new Intent(getActivity(), NewEventActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
        } else if (menuItem.getItemId() == R.id.proposed_event) {
            get_unnalowed_event();
        }
        return true;
    }

    private void get_unnalowed_event() {
        EventServices eventServices = new EventServices();
        eventServices.get_unallowed_event(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.code() == 200) {
                    if (response.body() != null && !response.body().isEmpty()) {
                        EventAdapter eventAdapter = new EventAdapter((ArrayList<Event>) response.body(), getActivity(), EventFragment.this);

                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
                        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.item_separator));
                        list_event.addItemDecoration(dividerItemDecoration);
                        list_event.setAdapter(eventAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failled to reach server", Toast.LENGTH_SHORT).show();
                Log.e("get_unnalowed_event", "onFailure: " + t.getMessage());
            }
        });
    }
}
