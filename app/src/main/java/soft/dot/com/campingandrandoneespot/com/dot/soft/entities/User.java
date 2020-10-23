package soft.dot.com.campingandrandoneespot.com.dot.soft.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sofien on 05/02/2018.
 */
@Entity
public class User implements Parcelable {
    @Expose
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    private long id;

    @Expose
    @SerializedName("password")
    @ColumnInfo(name = "password")
    private String password;
    @Expose
    @SerializedName("first_name")
    @ColumnInfo(name = "firstName")
    private String firstName;
    @Expose
    @SerializedName("last_name")
    @ColumnInfo(name = "lastName")
    private String lastName;
    @Expose
    @SerializedName("role")
    @ColumnInfo(name = "role")
    private String role;
    @Expose
    @SerializedName("birth_date")
    @ColumnInfo(name = "birth_date")
    private String birthDate;
    @Expose
    @SerializedName("email")
    @ColumnInfo(name = "email")
    String email;
    @Expose
    @SerializedName("phone")
    @ColumnInfo(name = "phone")
    private long phone ;
    @Expose
    @SerializedName("isActive")
    @ColumnInfo(name = "isActive")
    private boolean isActive;
    @Expose
    @SerializedName("activation_code")
    @ColumnInfo(name = "activation_code")
    private String activation_code ;
    @Ignore
    List<Media> medias ;
    @Ignore
    List<Event> events;

    protected User(Parcel in) {
        id = in.readLong();
        password = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        role = in.readString();
        birthDate = in.readString();
        email = in.readString();
        phone = in.readLong();
        isActive = in.readByte() != 0;
        activation_code = in.readString();
        medias = in.createTypedArrayList(Media.CREATOR);
        events = in.createTypedArrayList(Event.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }

    public User (){

    }
    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getActivation_code() {
        return activation_code;
    }

    public void setActivation_code(String activation_code) {
        this.activation_code = activation_code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(password);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(role);
        parcel.writeString(birthDate);
        parcel.writeString(email);
        parcel.writeLong(phone);
        parcel.writeByte((byte) (isActive ? 1 : 0));
        parcel.writeString(activation_code);
        parcel.writeTypedList(medias);
        parcel.writeTypedList(events);
    }
}
