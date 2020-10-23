package soft.dot.com.campingandrandoneespot.com.dot.soft.adapters;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.activities.Faune_Flore_detail_Activity;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Item;


public class FauneFloreAdapter extends RecyclerView.Adapter<FauneFloreAdapter.ViewHolder> {

    ArrayList<Item> list;
    Context context;

    public FauneFloreAdapter(ArrayList<Item> items, Context context) {
        this.list = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.flore_vertical_list_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Item item = list.get(position);
        holder.textView3.setText(item.getName());
        holder.textView4.setText(item.getDescription());
        holder.button4.setOnClickListener(v -> {
            Intent intent = new Intent(context, Faune_Flore_detail_Activity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Faune_Flore_detail_Activity.ITEM_DESCRIPTION, item.getDescription());
            bundle.putInt(Faune_Flore_detail_Activity.ITEM_ID, item.getId());
            bundle.putInt(Faune_Flore_detail_Activity.ITEMS_SPECIES_ID, item.getSpecies().getId());
            bundle.putString(Faune_Flore_detail_Activity.ITEM_NAME, item.getName());
            bundle.putString(Faune_Flore_detail_Activity.ITEM_SPECIES, item.getSpecies().getTitle());
            intent.putExtras(bundle);
            context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((AppCompatActivity) context).toBundle());


        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView3, textView4;
        public Button button4;

        public ViewHolder(View itemView) {
            super(itemView);
            textView3 = itemView.findViewById(R.id.textView3);
            textView4 = itemView.findViewById(R.id.textView4);
            button4 = itemView.findViewById(R.id.button4);
        }


    }
}
