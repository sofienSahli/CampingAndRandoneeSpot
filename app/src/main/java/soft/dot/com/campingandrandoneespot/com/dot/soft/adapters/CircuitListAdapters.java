package soft.dot.com.campingandrandoneespot.com.dot.soft.adapters;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.activities.MapsActivity;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Circuit;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Spot;
import soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage.AppDatabase;

public class CircuitListAdapters extends RecyclerView.Adapter<CircuitListAdapters.AdapterViewHolder> {

    Context context;
    ArrayList<Circuit> circuits;

    public CircuitListAdapters(ArrayList<Circuit> circuits, Context context) {
        this.context = context;
        if (circuits != null)
            this.circuits = circuits;

    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.circuit_list_row, parent, false);

        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {

        holder.bindViewHolder(circuits.get(position));
    }


    @Override
    public int getItemCount() {
        return circuits.size();
    }


    class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView date, distance, vitess, duree;
        ImageButton imageButton4;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            distance = itemView.findViewById(R.id.distance);
            vitess = itemView.findViewById(R.id.vitess);
            duree = itemView.findViewById(R.id.duree);
            imageButton4 = itemView.findViewById(R.id.imageButton4);

        }

        public void bindViewHolder(Circuit circuit) {
            date.setText(circuit.getCreated_at());
            if (circuit.getSpots() != null && !circuit.getSpots().isEmpty()) {
                float distance = calculateDistance(circuit.getSpots().get(0), circuit.getSpots().get(circuit.getSpots().size() - 1));
                this.distance.setText(distance + " KM");
                this.vitess.setText(distance / circuit.getDuree() + "km/h");
            }
            String duree = circuit.getDuree() / 60 + ":" + circuit.getDuree() % 60;
            this.duree.setText(duree);
            imageButton4.setOnClickListener(v -> {

                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.inflate(R.menu.base_drawer_activity);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    if (menuItem.getItemId() == R.id.action_map) {
                        MapsActivity.circuit = circuit;
                        Intent intent = new Intent(context, MapsActivity.class);
                        context.startActivity(intent);
                        return true;
                    } else if (menuItem.getItemId() == R.id.action_delete) {
                        AppDatabase.getAppDatabase(context).spotDao().delete_by_circuit_id(circuit.getId());
                        AppDatabase.getAppDatabase(context).circuitDAO().deleteCircuit(circuit);
                        circuits.remove(circuit);
                        notifyDataSetChanged();
                        return true;
                    } else if (menuItem.getItemId() == R.id.action_partager) {
                        Toast.makeText(context, "Coming", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                });
            });

        }


        private float calculateDistance(Spot start, Spot end) {
            Location loc1 = new Location("");
            loc1.setLatitude(start.getLatitude());
            loc1.setLongitude(start.getLongitude());

            Location loc2 = new Location("");
            loc2.setLatitude(end.getLatitude());
            loc2.setLongitude(end.getLongitude());

            return loc1.distanceTo(loc2);
        }
    }
}
