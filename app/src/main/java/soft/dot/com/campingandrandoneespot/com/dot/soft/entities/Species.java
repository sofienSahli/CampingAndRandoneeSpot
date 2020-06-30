package soft.dot.com.campingandrandoneespot.com.dot.soft.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Species {
    @PrimaryKey(autoGenerate = true)
    int id;
    String title;
    String description;
    ArrayList<Individual> individuals;


    public Species(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
