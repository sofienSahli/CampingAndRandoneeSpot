package soft.dot.com.campingandrandoneespot.com.dot.soft.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import soft.dot.com.campingandrandoneespot.R;

public class Faune_Flore_detail_Activity extends AppCompatActivity {

    public static final String ITEM_NAME = "na";
    public static final String ITEM_DESCRIPTION = "ds";
    public static final String ITEM_ID = "id";
    public static final String ITEM_SPECIES = "sp";
    public static final String ITEMS_SPECIES_ID = "spid";
    TextView tv_item_description, tv_item_name, tv_species;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faune__flore_detail_);
        Toolbar toolbar = findViewById(R.id.tb);
        setSupportActionBar(toolbar);
        tv_item_description = findViewById(R.id.tv_item_description);
        tv_item_name = findViewById(R.id.tv_item_name);
        tv_species = findViewById(R.id.tv_species);
        fetchDataIfAny();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    private void fetchDataIfAny() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            finish();
        } else {
            tv_item_description.setText(bundle.getString(ITEM_DESCRIPTION));
            tv_item_name.setText(bundle.getString(ITEM_NAME));
            tv_species.setText(bundle.getString(ITEM_SPECIES));
            getSupportActionBar().setTitle(bundle.getString(ITEM_NAME));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


    }


}