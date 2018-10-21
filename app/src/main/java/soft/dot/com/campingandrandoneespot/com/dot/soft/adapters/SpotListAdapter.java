package soft.dot.com.campingandrandoneespot.com.dot.soft.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.io.File;
import java.util.ArrayList;

import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Spot;

public class SpotListAdapter extends RecyclerView.Adapter<SpotListAdapter.Holder> {
    Context context;
    ArrayList<Spot> spots;

    public SpotListAdapter(Context context, ArrayList<Spot> spots) {
        this.spots = spots;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new Holder(inflater.inflate(R.layout.spot_row, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Spot spot = spots.get(position);
        holder.tvDescription.setText(spot.getDescription());
        //  holder.tvLatitude.setText(spot.getLatitude() + "");
        // holder.tvLongitude.setText(spot.getLongitude() + "");
        File file = new File(spot.getImage_url());
        Glide.with(context).load(file).into(holder.imageView);
    }

    public void addSpot(Spot spot) {
        spots.add(spot);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return spots.size();
    }


    class Holder extends RecyclerView.ViewHolder implements OnMapReadyCallback {


        ImageView imageView;
        TextView tvLongitude, tvLatitude, tvDescription;
        GoogleMap miniMap;

        public Holder(View itemView) {
            super(itemView);

            //     SupportMapFragment mapFragment = (SupportMapFragment) ((AppCompatActivity) context).getSupportFragmentManager()
            //      .findFragmentById(R.id.mini_map);

            //          mapFragment.getMapAsync(this);

            imageView = itemView.findViewById(R.id.spotImage);
            // tvLongitude = itemView.findViewById(R.id.tvLongitude);
            // tvLatitude = itemView.findViewById(R.id.tvLatitue);
            tvDescription = itemView.findViewById(R.id.tvDescription);

        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            miniMap = googleMap;

          /*  MarkerOptions currentMarker = new MarkerOptions()
                    .title("Selected Location")
                    .position(new LatLng(spot.getLatitude(), spot.getLatitude()))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));*/
            //miniMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(spot.getLatitude(), spot.getLatitude()), 15));
            // miniMap.addMarker(currentMarker);
        }
    }
}
