package soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Circuit;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Individual;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Species;

@Dao
interface SpeciesDAO {
    @Insert
    void insertCircuit(Species species);

    @Delete
    void deleteCircuit(Species species);

    @Update
    void updateCircuit(Species species);

    @Query("Select * from Species where id= :id")
    Circuit findById(int id);

    @Query("Select * from Species")
    List<Circuit> getAll();

    @Query("Select * from Species where isFone= 'isFon'")
    List<Circuit> getFone(boolean isFon);


    @Query("DELETE FROM Species")
    public void clearTable();


}
