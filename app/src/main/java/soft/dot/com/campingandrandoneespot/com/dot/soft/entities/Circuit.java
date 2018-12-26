package soft.dot.com.campingandrandoneespot.com.dot.soft.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@Entity()
public class Circuit implements Parcelable {
    @Expose
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    long id;
    @Expose
    @SerializedName("difficulty")
    @ColumnInfo(name = "difficulty")
    String difficulty;
    @Expose
    @SerializedName("duree")
    @ColumnInfo(name = "duree")
    long duree;
    @Expose
    @SerializedName("spots")
    @Ignore
    ArrayList<Spot> spots;
    @Expose
    @SerializedName("description")
    @ColumnInfo(name = "description")
    String description;
    @Expose
    @SerializedName("title")
    @ColumnInfo(name = "title")
    String title;
    @Expose
    @SerializedName("updated_at")
    @Ignore
    String updated_at;
    @Expose
    @SerializedName("created_at")
    @Ignore
    String created_at;
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Circuit createFromParcel(Parcel in) {
            return new Circuit(in);
        }

        public Circuit[] newArray(int size) {
            return new Circuit[size];
        }
    };

    public Circuit(Parcel in) {
        this.id = in.readLong() ;
        this.difficulty = in.readString();
        this.duree = in.readLong();
        this.description = in.readString();
        this.title = in.readString();

    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
            parcel.writeLong(this.id);
            parcel.writeString(this.difficulty);
            parcel.writeLong(this.duree);
            parcel.writeString(this.description);
            parcel.writeString(this.title);
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Circuit() {
        this.spots = new ArrayList<>();

    }

    @Ignore
    public Circuit(String difficulty, long duree, String description, String title) {
        this.difficulty = difficulty;
        this.duree = duree;
        this.description = description;
        this.title = title;
    }

    public ArrayList<Spot> getSpots() {
        return spots;
    }

    public void setSpots(ArrayList<Spot> spots) {
        this.spots = spots;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Circuit{" +
                "id=" + id +
                ", difficulty=" + difficulty +
                ", duree=" + duree +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public long getDuree() {
        return duree;
    }

    public void setDuree(long duree) {
        this.duree = duree;
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
