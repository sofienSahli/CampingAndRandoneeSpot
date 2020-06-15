package soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Circuit;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Individual;

@Dao
interface IndividualsDAO {
    @Insert
    void insertCircuit(Individual individual);

    @Delete
    void deleteCircuit(Individual individual);

    @Update
    void updateCircuit(Individual individual);

    @Query("Select * from Individual where id= :id")
    Circuit findById(int id);

    @Query("Select * from Individual")
    List<Circuit> getAll();

   /* @Query("Select * from circuit where description= 'Parcours libre'")
    List<Circuit> getFone();*/


    @Query("DELETE FROM Individual")
    public void clearTable();


}


