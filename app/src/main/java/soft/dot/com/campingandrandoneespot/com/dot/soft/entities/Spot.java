package soft.dot.com.campingandrandoneespot.com.dot.soft.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
