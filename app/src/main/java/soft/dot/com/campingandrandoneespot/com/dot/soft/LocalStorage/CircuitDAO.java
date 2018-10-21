package soft.dot.com.campingandrandoneespot.com.dot.soft.LocalStorage;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
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

    @Query("Delete  from circuit where 1=1")
    void clearTable();
}
