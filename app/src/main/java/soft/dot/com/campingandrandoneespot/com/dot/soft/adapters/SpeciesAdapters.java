package soft.dot.com.campingandrandoneespot.com.dot.soft.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Species;

public class SpeciesAdapters extends RecyclerView.Adapter<SpeciesAdapters.Holder> {

    Context context;
    ArrayList<Species> list;

    public SpeciesAdapters(ArrayList<Species> list, Context context) {
        this.list = list;
        this.context = context;
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
