package soft.dot.com.campingandrandoneespot.com.dot.soft.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

import soft.dot.com.campingandrandoneespot.MapsActivity;
import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Circuit;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Difficulty;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Spot;

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

        holder.imageView.setOnClickListener(v -> {
            MapsActivity.circuit = circuit;
            Intent intent = new Intent(context, MapsActivity.class);
            context.startActivity(intent);
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


        public AdapterViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tvCircuitName = itemView.findViewById(R.id.tvCircuitName);
            tvCircuitDescription = itemView.findViewById(R.id.tvCircuitDescription);
            tvDifficulty = itemView.findViewById(R.id.tvDifficulty);


        }
    }
}
