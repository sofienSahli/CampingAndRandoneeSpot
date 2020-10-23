package soft.dot.com.campingandrandoneespot.com.dot.soft.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Individual {
    @Expose
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    int id ;
    @Relation(
            parentColumn = "userId",
            entityColumn = "userCreatorId"
    )
    @Expose
    @SerializedName("speciesId")
    int speciesId;

    @Expose
    @SerializedName("name")
    String name;
    @Expose
    @SerializedName("description")
    String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(int speciesId) {
        this.speciesId = speciesId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
