package soft.dot.com.campingandrandoneespot.com.dot.soft.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import soft.dot.com.campingandrandoneespot.R;
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
            Toast.makeText(context, "To be implemented", Toast.LENGTH_SHORT).show();
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
