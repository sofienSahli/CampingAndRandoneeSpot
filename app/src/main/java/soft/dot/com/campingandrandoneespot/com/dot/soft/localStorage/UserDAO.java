package soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.User;

@Dao
public interface UserDAO {
    @Insert
    public void insertUser (User user);
    @Query("Select * from User where id= :id")
    User findById(long id);

}
