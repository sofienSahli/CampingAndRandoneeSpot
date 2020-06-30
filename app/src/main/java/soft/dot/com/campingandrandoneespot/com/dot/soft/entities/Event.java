package soft.dot.com.campingandrandoneespot.com.dot.soft.entities;

import android.arch.persistence.room.Entity;

import java.util.List;
@Entity
public class Event {
    int id;
    String title;
    String date;
    String description;
    List<User> participants;
    String place;
    double longitude;
    double latitude;
    List<String> images;

    public Event() {
    }

    public Event(String title, String date, String description, String place) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.place = place;
    }

    public Event(int id, String title, String date, String description, List<User> participants, String place, double longitude, double latitude, List<String> images) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.description = description;
        this.participants = participants;
        this.place = place;
        this.longitude = longitude;
        this.latitude = latitude;
        this.images = images;
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

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
