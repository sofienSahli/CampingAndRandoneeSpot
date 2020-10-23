package soft.dot.com.campingandrandoneespot.com.dot.soft.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class EventUser implements Parcelable {
    long inscrit;
    int event;
    boolean is_confirmed;

    protected EventUser(Parcel in) {
        inscrit = in.readLong();
        event = in.readInt();
        is_confirmed = in.readByte() != 0;
    }

    public static final Creator<EventUser> CREATOR = new Creator<EventUser>() {
        @Override
        public EventUser createFromParcel(Parcel in) {
            return new EventUser(in);
        }

        @Override
        public EventUser[] newArray(int size) {
            return new EventUser[size];
        }
    };

    public long getInscrit() {
        return inscrit;
    }

    public void setInscrit(long inscrit) {
        this.inscrit = inscrit;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public boolean isIs_confirmed() {
        return is_confirmed;
    }

    public void setIs_confirmed(boolean is_confirmed) {
        this.is_confirmed = is_confirmed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(inscrit);
        parcel.writeInt(event);
        parcel.writeByte((byte) (is_confirmed ? 1 : 0));
    }
}
