package soft.dot.com.campingandrandoneespot.com.dot.soft.Services.intefaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Circuit;

public interface ICircuitServices {

    @GET("circuit/circuits")
    Call<List<Circuit>> getAll();

    @POST("circuit/circuits")
    Call<Circuit> addNew(@Body Circuit circuit);

}
