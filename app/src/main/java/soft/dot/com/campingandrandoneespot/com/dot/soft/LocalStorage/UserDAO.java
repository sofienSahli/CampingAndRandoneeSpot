package soft.dot.com.campingandrandoneespot.com.dot.soft.LocalStorage;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.User;

@Dao
public interface UserDAO {
    @Insert
    public void insertUser (User user);
    @Query("Select * from User where id= :id")
    User findById(long id);

}
