package soft.dot.com.campingandrandoneespot.com.dot.soft.fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;

import java.util.ArrayList;

import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.activities.EventDetailActivity;
import soft.dot.com.campingandrandoneespot.com.dot.soft.activities.NewEventActivity;
import soft.dot.com.campingandrandoneespot.com.dot.soft.adapters.EventAdapter;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Event;

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
        ArrayList<Event> events_list = new ArrayList<>();
        events_list.add(new Event("Séance de aquagym à la plage", "30/07/2020 à 08:00", "L'association à le " +
                "plaisir de vous inviter à une séance de Aqua Gym pour vous remettre ne forme. Aucun frais d'inscription n'est requis " +
                "et vous serez encadré par des spécialiste de conditionnement phisique.", "Remel plage prêt des épaves."));
        events_list.add(new Event("Séance de aquagym à la plage", "30/07/2020 à 08:00", "L'association à le " +
                "plaisir de vous inviter à une séance de Aqua Gym pour vous remettre ne forme. Aucun frais d'inscription n'est requis " +
                "et vous serez encadré par des spécialiste de conditionnement phisique.", "Remel plage prêt des épaves."));
        events_list.add(new Event("Séance de aquagym à la plage", "30/07/2020 à 08:00", "L'association à le " +
                "plaisir de vous inviter à une séance de Aqua Gym pour vous remettre ne forme. Aucun frais d'inscription n'est requis " +
                "et vous serez encadré par des spécialiste de conditionnement phisique.", "Remel plage prêt des épaves."));
        events_list.add(new Event("Séance de aquagym à la plage", "30/07/2020 à 08:00", "L'association à le " +
                "plaisir de vous inviter à une séance de Aqua Gym pour vous remettre ne forme. Aucun frais d'inscription n'est requis " +
                "et vous serez encadré par des spécialiste de conditionnement phisique.", "Remel plage prêt des épaves."));
        events_list.add(new Event("Séance de aquagym à la plage", "30/07/2020 à 08:00", "L'association à le " +
                "plaisir de vous inviter à une séance de Aqua Gym pour vous remettre ne forme. Aucun frais d'inscription n'est requis " +
                "et vous serez encadré par des spécialiste de conditionnement phisique.", "Remel plage prêt des épaves."));
        events_list.add(new Event("Séance de aquagym à la plage", "30/07/2020 à 08:00", "L'association à le " +
                "plaisir de vous inviter à une séance de Aqua Gym pour vous remettre ne forme. Aucun frais d'inscription n'est requis " +
                "et vous serez encadré par des spécialiste de conditionnement phisique.", "Remel plage prêt des épaves."));
        events_list.add(new Event("Séance de aquagym à la plage", "30/07/2020 à 08:00", "L'association à le " +
                "plaisir de vous inviter à une séance de Aqua Gym pour vous remettre ne forme. Aucun frais d'inscription n'est requis " +
                "et vous serez encadré par des spécialiste de conditionnement phisique.", "Remel plage prêt des épaves."));
        events_list.add(new Event("Séance de aquagym à la plage", "30/07/2020 à 08:00", "L'association à le " +
                "plaisir de vous inviter à une séance de Aqua Gym pour vous remettre ne forme. Aucun frais d'inscription n'est requis " +
                "et vous serez encadré par des spécialiste de conditionnement phisique.", "Remel plage prêt des épaves."));

        EventAdapter eventAdapter = new EventAdapter(events_list, getActivity(), this);
        list_event.setAdapter(eventAdapter);
    }

    public void launchDetailsActivity(Event event) {
        Bundle bundle = new Bundle();
        bundle.putString(EventDetailActivity.EVENT_DESCRIPTION, event.getDescription());
        bundle.putString(EventDetailActivity.EVENT_LOCATION, event.getPlace());
        bundle.putString(EventDetailActivity.EVENT_DATE, event.getDate());
        bundle.putString(EventDetailActivity.EVENT_NAME, event.getTitle());
        Intent intent = new Intent(getActivity(), EventDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button7) {
            MenuInflater menuInflater = getActivity().getMenuInflater();
            PopupMenu popupMenu = new PopupMenu(getActivity(), button7);
            menuInflater.inflate(R.menu.event_menu, popupMenu.getMenu());
            popupMenu.show();
            popupMenu.setOnMenuItemClickListener(this);

        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.proposer_event) {
            Intent intent = new Intent(getActivity(), NewEventActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
        }
        return true;
    }
}
