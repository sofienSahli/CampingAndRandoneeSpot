package soft.dot.com.campingandrandoneespot.com.dot.soft.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Media implements Parcelable {
    int id ;
    String type ;
    String filable_type;
    String filable_id;
    String file ;

    protected Media(Parcel in) {
        id = in.readInt();
        type = in.readString();
        filable_type = in.readString();
        filable_id = in.readString();
        file = in.readString();
    }

    public static final Creator<Media> CREATOR = new Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel in) {
            return new Media(in);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Media(int id, String type, String filable_type, String filable_id) {
        this.id = id;
        this.type = type;
        this.filable_type = filable_type;
        this.filable_id = filable_id;
    }

    public Media() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilable_type() {
        return filable_type;
    }

    public void setFilable_type(String filable_type) {
        this.filable_type = filable_type;
    }

    public String getFilable_id() {
        return filable_id;
    }

    public void setFilable_id(String filable_id) {
        this.filable_id = filable_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(type);
        parcel.writeString(filable_type);
        parcel.writeString(filable_id);
        parcel.writeString(file);
    }
}
