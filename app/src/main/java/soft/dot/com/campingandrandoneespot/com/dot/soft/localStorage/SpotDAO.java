package soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Spot;

@Dao
public interface SpotDAO {
    @Insert
    void insertSpot(Spot spot);

    @Insert
    void insertAllSpot(List<Spot> spots);

    @Delete
    void deleteSpot(Spot spot);


    @Update
    void updateCircuit(Spot circuit);

    @Query("Delete  from spot where circuit_id = :circuit_id ")
    void delete_by_circuit_id(long circuit_id);

    @Query("Select * from spot where id= :id")
    Spot findById(long id);

    @Query("SELECT * FROM spot")
    List<Spot> getAllRepos();

    @Query("SELECT * FROM spot WHERE circuit_id=:circuitId")
    List<Spot> findSpotsForCircuit(final long circuitId);

}
