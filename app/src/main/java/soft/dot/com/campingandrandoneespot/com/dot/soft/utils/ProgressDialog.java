package soft.dot.com.campingandrandoneespot.com.dot.soft.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import soft.dot.com.campingandrandoneespot.R;

public class ProgressDialog {
    Context context;
    AlertDialog dialog;

    public ProgressDialog(Context context) {
        this.context = context ;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.dialog_progress, null, false);
        dialog = new androidx.appcompat.app.AlertDialog.Builder(context).setView(view).create();
    }
    public void showDissmissDialog (){
        if(dialog.isShowing())
            dialog.dismiss();
        else
            dialog.show();
    }
}
