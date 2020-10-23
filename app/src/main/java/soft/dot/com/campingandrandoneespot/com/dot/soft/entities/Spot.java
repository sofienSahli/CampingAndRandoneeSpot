package soft.dot.com.campingandrandoneespot.com.dot.soft.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(indices = {@Index(value = {"circuit_id"})},foreignKeys = @ForeignKey(entity = Circuit.class,
        parentColumns = "id",
        childColumns = "circuit_id"))
public class Spot {
    @Expose
    @SerializedName("image")
    @ColumnInfo(name = "image_url")
    String image_url;
    @Expose
    @SerializedName("id")
    @PrimaryKey
    long id;
    @Expose
    @SerializedName("longitude")
    @ColumnInfo(name = "longitude")
    double longitude;
    @Expose
    @SerializedName("latitude")
    @ColumnInfo(name = "latitude")
    double latitude;
    @Expose
    @SerializedName("description")
    @ColumnInfo(name = "description")
    String description;

    @Expose
    @SerializedName("circuit")
    @Ignore
    Circuit circuit;
    @ColumnInfo(name = "circuit_id")
    long circuit_id;

    long saved_at;
    @Ignore
    String created_at;
    @Ignore
    String updated_at;

    @Override
    public String toString() {
        return "Spot{" +
                "image_url='" + image_url + '\'' +
                ", id=" + id +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", description='" + description + '\'' +
                ", circuit=" + circuit +
                ", circuit_id=" + circuit_id +
                ", saved_at=" + saved_at +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }

    public long getSaved_at() {
        return saved_at;
    }

    public void setSaved_at(long saved_at) {
        this.saved_at = saved_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public long getCircuit_id() {
        return circuit_id;
    }

    public void setCircuit_id(long circuit_id) {
        this.circuit_id = circuit_id;
    }

    public Circuit getCircuit() {
        return circuit;
    }

    public void setCircuit(Circuit circuit) {
        this.setCircuit_id(circuit.getId());
        this.circuit = circuit;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




}
