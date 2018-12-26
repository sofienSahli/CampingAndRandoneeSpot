package soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Spot;

@Dao
public interface SpotDAO {
    @Insert
    void insertSpot(Spot spot);

    @Insert
    void insertAllSpot(List<Spot> spots);

    @Delete
    void deleteCircuit(Spot circuit);

    @Update
    void updateCircuit(Spot circuit);

    @Query("Delete  from circuit ")
    void clearTable();

    @Query("Select * from spot where id= :id")
    Spot findById(long id);

    @Query("SELECT * FROM spot")
    List<Spot> getAllRepos();

    @Query("SELECT * FROM spot WHERE circuit_id=:circuitId")
    List<Spot> findSpotsForCircuit(final long circuitId);

}
