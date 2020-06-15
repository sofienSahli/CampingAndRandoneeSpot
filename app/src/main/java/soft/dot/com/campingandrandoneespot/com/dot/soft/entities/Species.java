package soft.dot.com.campingandrandoneespot.com.dot.soft.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@Entity()
public class Species {
    @Expose
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    int id ;
    @Expose
    @SerializedName("name")
    String name;
    @Expose
    @SerializedName("isFone")
    boolean isFone ;
    @Relation(
            parentColumn = "id",
            entityColumn = "speciesId"
    )
    ArrayList<Individual> individuals;

    public Species() {
    }

    public Species(int id, String name, boolean isFone, ArrayList<Individual> individuals) {
        this.id = id;
        this.name = name;
        this.isFone = isFone;
        this.individuals = individuals;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFone() {
        return isFone;
    }

    public void setFone(boolean fone) {
        isFone = fone;
    }

    public ArrayList<Individual> getIndividuals() {
        return individuals;
    }

    public void setIndividuals(ArrayList<Individual> individuals) {
        this.individuals = individuals;
    }
}
