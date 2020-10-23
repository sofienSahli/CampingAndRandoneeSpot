package soft.dot.com.campingandrandoneespot.com.dot.soft.adapters;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Species;
import soft.dot.com.campingandrandoneespot.com.dot.soft.fragments.FloreFragment;

public class SpeciesAdapters extends RecyclerView.Adapter<SpeciesAdapters.Holder> {

    Context context;
    ArrayList<Species> list;
    FloreFragment floreFragment;

    public SpeciesAdapters(ArrayList<Species> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public SpeciesAdapters(ArrayList<Species> list, Context context, FloreFragment floreFragment) {
        this.list = list;
        this.context = context;
        this.floreFragment = floreFragment;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.flore_horizontal_list_row, parent, false);

        return new Holder((view));

    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.textView.setText(list.get(position).getTitle());
        holder.imageView.setOnClickListener(v -> {
            if (floreFragment != null) {
                floreFragment.speciesClicked(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public Holder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView9);
            imageView = itemView.findViewById(R.id.imageView5);
        }
    }
}
