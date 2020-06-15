package soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.telephony.SmsMessage;

import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Circuit;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Individual;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Spot;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.User;

@Database(entities = {Spot.class, Circuit.class, User.class, SpeciesDAO.class, Individual.class}, version = 9, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract CircuitDAO circuitDAO();

    public abstract SpotDAO spotDao();

    public abstract UserDAO userDAO();

    public abstract SpeciesDAO speciesDAO();

    public abstract IndividualsDAO individualsDAO();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "mydb")
                            .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }


}
