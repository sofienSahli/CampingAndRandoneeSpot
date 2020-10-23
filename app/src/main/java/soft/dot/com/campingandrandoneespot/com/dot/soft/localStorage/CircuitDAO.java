package soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Circuit;

@Dao
public interface CircuitDAO {
    @Insert
    void insertCircuit(Circuit circuit);

    @Delete
    void deleteCircuit(Circuit circuit);

    @Update
    void updateCircuit(Circuit circuit);

    @Query("Select * from circuit where id= :id")
    Circuit findById(long id);

    @Query("Select * from circuit")
    List<Circuit> getAll();

    @Query("Select * from circuit where description= 'Parcours libre'")
    List<Circuit> getFreeRun();


    @Query("Delete  FROM circuit")
    public void clearTable();
}
