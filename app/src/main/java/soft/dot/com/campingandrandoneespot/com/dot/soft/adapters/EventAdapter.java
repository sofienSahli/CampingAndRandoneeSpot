package soft.dot.com.campingandrandoneespot.com.dot.soft.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Event;
import soft.dot.com.campingandrandoneespot.com.dot.soft.fragments.EventFragment;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.Holder> {
    ArrayList<Event> events;
    Context context;
    EventFragment eventFragment;

    public EventAdapter(ArrayList<Event> events, Context context, EventFragment eventFragment) {
        this.events = events;
        this.context = context;
        this.eventFragment = eventFragment;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.event_row, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        Event event = events.get(i);
        holder.setEvent(event);
        holder.event_detail.setOnClickListener(v -> {
            eventFragment.launchDetailsActivity(event);
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView imageView9;
        TextView event_title, event_date, event_point, event_desc;
        Button event_detail, event_inscription;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView9 = itemView.findViewById(R.id.imageView9);
            event_title = itemView.findViewById(R.id.event_title);
            event_date = itemView.findViewById(R.id.event_date);
            event_point = itemView.findViewById(R.id.event_point);
            event_desc = itemView.findViewById(R.id.event_desc);
            event_detail = itemView.findViewById(R.id.event_detail);
            event_inscription = itemView.findViewById(R.id.event_inscription);


        }

        public void setEvent(Event event) {
            event_title.setText(event.getTitle());
            event_date.setText(event.getDate());
            event_desc.setText(event.getDescription());
            event_point.setText(event.getPlace());
        }
    }

}
