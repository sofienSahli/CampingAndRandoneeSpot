package soft.dot.com.campingandrandoneespot.com.dot.soft.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class EventImageGridAdapter extends BaseAdapter {
    Context context;
    ArrayList<Uri> drawables;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Uri> getDrawables() {
        return drawables;
    }

    public void setDrawables(ArrayList<Uri> drawables) {
        this.drawables = drawables;
    }

    public EventImageGridAdapter(Context context, ArrayList<Uri> drawables) {
        this.context = context;
        this.drawables = drawables;
    }

    @Override
    public int getCount() {
        return drawables.size();
    }

    @Override
    public Object getItem(int i) {
        return drawables.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(context);
        imageView.setImageURI(drawables.get(i));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(265, 265));
        imageView.setLayoutParams(lp);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }
}
