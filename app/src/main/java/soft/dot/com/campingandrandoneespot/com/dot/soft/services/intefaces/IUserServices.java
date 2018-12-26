package soft.dot.com.campingandrandoneespot.com.dot.soft.services.intefaces;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.User;

/**
 * Created by sofien on 08/02/2018.
 */

public interface IUserServices {
    @POST ("/api/users")
    Call<User> signUp( @Body User user);

/*    @GET()
    Call<ResponseBody>  notifyCycleEnd(@Url String url);
    @GET()
    Call <ResponseBody> notifyCycleStart(@Url String url);
    @GET()
    Call<ResponseBody> getAverageLength(@Url String url);
*/
}
