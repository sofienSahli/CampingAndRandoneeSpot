package soft.dot.com.campingandrandoneespot.com.dot.soft.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Event implements Parcelable {
    int id;
    String title;
    String date;
    String description;
    List<EventUser> participants;
    String place;
    double longitude;
    double latitude;
    List<Media> medias;
    long prposed_by;
    String starting_date;
    String theme;
    boolean is_allowed;
    User proposer;

    public List<EventUser> getParticipants() {
        return participants;
    }

    public void setParticipants(List<EventUser> participants) {
        this.participants = participants;
    }

    protected Event(Parcel in) {
        id = in.readInt();
        title = in.readString();
        date = in.readString();
        description = in.readString();
        participants = in.createTypedArrayList(EventUser.CREATOR);
        place = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
        medias = in.createTypedArrayList(Media.CREATOR);
        prposed_by = in.readLong();
        starting_date = in.readString();
        theme = in.readString();
        is_allowed = in.readByte() != 0;
        proposer = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public User getProposer() {
        return proposer;
    }

    public void setProposer(User proposer) {
        this.proposer = proposer;
    }

    public boolean isIs_allowed() {
        return is_allowed;
    }

    public void setIs_allowed(boolean is_allowed) {
        this.is_allowed = is_allowed;
    }



    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public long getPrposed_by() {
        return prposed_by;
    }

    public void setPrposed_by(long prposed_by) {
        this.prposed_by = prposed_by;
    }

    public String getStarting_date() {
        return starting_date;
    }

    public void setStarting_date(String starting_date) {
        this.starting_date = starting_date;
    }

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }

    public long getPrposed_by_id() {
        return prposed_by;
    }

    public void setPrposed_by_id(long prposed_by_id) {
        this.prposed_by = prposed_by_id;
    }

    public Event() {
    }

    public Event(String title, String date, String description, String place) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.place = place;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(date);
        parcel.writeString(description);
        parcel.writeTypedList(participants);
        parcel.writeString(place);
        parcel.writeDouble(longitude);
        parcel.writeDouble(latitude);
        parcel.writeTypedList(medias);
        parcel.writeLong(prposed_by);
        parcel.writeString(starting_date);
        parcel.writeString(theme);
        parcel.writeByte((byte) (is_allowed ? 1 : 0));
        parcel.writeParcelable(proposer, i);
    }
}
