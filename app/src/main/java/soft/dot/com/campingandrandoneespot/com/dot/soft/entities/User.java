package soft.dot.com.campingandrandoneespot.com.dot.soft.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by sofien on 05/02/2018.
 */
@Entity
public class User {
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
    @SerializedName("birthDate")
    @ColumnInfo(name = "birthDate")

    private String birthDate;
    @Expose
    @SerializedName("email")
    @ColumnInfo(name = "email")
    String email;

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
}
