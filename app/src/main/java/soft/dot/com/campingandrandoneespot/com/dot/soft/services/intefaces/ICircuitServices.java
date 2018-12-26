package soft.dot.com.campingandrandoneespot.com.dot.soft.services.intefaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Circuit;

public interface ICircuitServices {

    @GET("circuit")
    Call<List<Circuit>> getAll();

    @POST("circuit")
    Call<Circuit> addNew( @Body Circuit circuit);

}
