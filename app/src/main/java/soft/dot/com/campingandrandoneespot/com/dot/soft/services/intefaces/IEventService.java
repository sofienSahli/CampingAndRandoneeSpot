package soft.dot.com.campingandrandoneespot.com.dot.soft.services.intefaces;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Event;

public interface IEventService {

    @POST("event")
    Call<Event> store(@Body Event event);

    @GET("event")
    Call<List<Event>> index();

    @GET("event/get_by_proposer_id/{id}")
    Call<List<Event>> get_by_proposer(@Path("id") int id);
    @GET("m/u")
    Call<List<Event>> get_unallowed_event();
    @GET("event/allow/{id}")
    Call<ResponseBody> allow_event(@Path("id") int id);
    @GET("event/subscribe/{user_id}/{event_id}")
    Call<ResponseBody> subscribe (@Path("user_id") long user_id, @Path("event_id") int event_id );

}
