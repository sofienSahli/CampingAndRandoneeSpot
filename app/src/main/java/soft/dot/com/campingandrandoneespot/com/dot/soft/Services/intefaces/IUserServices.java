package soft.dot.com.campingandrandoneespot.com.dot.soft.Services.intefaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
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
