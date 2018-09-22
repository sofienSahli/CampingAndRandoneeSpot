package soft.dot.com.campingandrandoneespot.com.dot.soft.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.LocalStorage.AppDatabase;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Spot;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindowGoogleMap(Context ctx) {
        context = ctx;
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

            ImageView imageView = view.findViewById(R.id.spotImage);
            File file = new File(spot.getImage_url());
            Picasso.get().load(file).into(imageView);
            Log.e("TAAAAAG", spot.getImage_url());
            TextView textView = view.findViewById(R.id.spotDescription);
            textView.setText(spot.getDescription());
        }
        return view;

    }

    private Spot findSpot(Marker marker) {

        return AppDatabase.getAppDatabase(context).spotDao().findById((Long) marker.getTag());
    }

}