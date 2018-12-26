package soft.dot.com.campingandrandoneespot.com.dot.soft.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import soft.dot.com.campingandrandoneespot.com.dot.soft.activities.Circuit_detail;
import soft.dot.com.campingandrandoneespot.com.dot.soft.activities.MapsActivity;
import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Circuit;
import soft.dot.com.campingandrandoneespot.com.dot.soft.utils.DateUtils;

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
        final Circuit circuit = circuits.get(position);
        holder.tvCircuitName.setText(circuit.getTitle());
        holder.tvDifficulty.setText(circuit.getDifficulty());
        holder.tvCircuitDescription.setText(circuit.getDescription());


        holder.tvDuree.setText(DateUtils.getTimeFromLong(circuit.getDuree()));
        holder.imageView.setOnClickListener(v -> {
            MapsActivity.circuit = circuit;
            Intent intent = new Intent(context, MapsActivity.class);
            context.startActivity(intent);
        });
        holder.item_body.setOnClickListener(v -> {
            Circuit_detail.circuit = circuit;
            Intent intent = new Intent(context, Circuit_detail.class);
            context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());
        });

    }


    @Override
    public int getItemCount() {
        return circuits.size();
    }


    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvCircuitName;
        TextView tvCircuitDescription;
        TextView tvDifficulty;
        TextView tvDuree;
        ConstraintLayout item_body;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            item_body = itemView.findViewById(R.id.item_body);
            imageView = itemView.findViewById(R.id.imageView);
            tvCircuitName = itemView.findViewById(R.id.tvCircuitName);
            tvCircuitDescription = itemView.findViewById(R.id.tvCircuitDescription);
            tvDifficulty = itemView.findViewById(R.id.tvDifficulty);
            tvDuree = itemView.findViewById(R.id.tvDuree);
        }
    }
}
