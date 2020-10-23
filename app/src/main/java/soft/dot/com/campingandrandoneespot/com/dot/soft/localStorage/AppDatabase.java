package soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Circuit;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Spot;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.User;

@Database(entities = {Spot.class, Circuit.class, User.class}, version = 11, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract CircuitDAO circuitDAO();

    public abstract SpotDAO spotDao();

    public abstract UserDAO userDAO();


    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "mydb")
                            .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }


}
