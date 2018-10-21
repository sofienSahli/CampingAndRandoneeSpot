package soft.dot.com.campingandrandoneespot.com.dot.soft.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.LocalStorage.AppDatabase;
import soft.dot.com.campingandrandoneespot.com.dot.soft.Services.RetrofitClient;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Spot;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Context context;
    private ArrayList<Spot> spots;

    public CustomInfoWindowGoogleMap(Context ctx) {
        context = ctx;
    }

    public CustomInfoWindowGoogleMap(Context ctx, ArrayList<Spot> spots) {
        context = ctx;
        this.spots = spots;

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity) context).getLayoutInflater()
                .inflate(R.layout.marker_custom_info_view, null);
        if ((Long) marker.getTag() != null) {
            Spot spot = findSpot(marker);
            if (spot != null) {
                ImageView imageView = view.findViewById(R.id.spotImage);
                Glide.with(context).load(RetrofitClient.BASE_URL + spot.getImage_url()).placeholder(R.drawable.ic_downloading).into(imageView);

                Log.e("Spot id : ",RetrofitClient.BASE_URL + spot.getImage_url());

                TextView textView = view.findViewById(R.id.spotDescription);
                textView.setText(spot.getDescription());
            }
        }
        return view;

    }

    private Spot findSpot(Marker marker) {
        if (spots == null)
            return AppDatabase.getAppDatabase(context).spotDao().findById((Long) marker.getTag());
        else for (Spot s : spots) {
            if (s.getId() == (Long) marker.getTag())
                return s;
        }
        return null;
    }

}