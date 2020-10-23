package soft.dot.com.campingandrandoneespot.com.dot.soft.services.intefaces;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.User;

/**
 * Created by sofien on 08/02/2018.
 */

public interface IUserServices {
    @POST("/user")
    Call<User> signUp(@Body User user);

    @GET("/user/resend_activation/{id}")
    Call<User> resend_activation_code(@Path("id") String id);

    @GET("/user/activate_account/{id}/{activation_code}")
    Call<User> activate_account(@Path("id") String id, @Path("activation_code") String activation_code);

    @Multipart
    @POST("/media")
    Call<ResponseBody> set_profile_picture(@Part MultipartBody.Part file, @Part("owner_id") long owner_id, @Part("type") String type);

    @GET("/user/login/{email}/{password}")
    Call<List<User>> login(@Path("email") String email, @Path("password") String password);

    @GET("/media/{id}")
    Call<ResponseBody> get_profile_picture(@Path("id") long ind);

    @POST("/user/admins")
    Call<List<User>> get_admins_user();

    @POST("/user/simple")
    Call<List<User>> get_simple_user();

    @POST("/user/super")
    Call<List<User>> get_super_admins();

    @GET("user/ban_account/{id}")
    Call<ResponseBody> ban_user_account(@Path("id") long id);

    @GET("user/activate/{id}")
    Call<ResponseBody> retablir_account(@Path("id") long id);

    @GET("user/{id}/{role}")
    Call<ResponseBody> update_privilége(@Path("id") long id, @Path("role") String role);

    /*    @GET()
    Call<ResponseBody>  notifyCycleEnd(@Url String url);
    @GET()
    Call <ResponseBody> notifyCycleStart(@Url String url);
    @GET()
    Call<ResponseBody> getAverageLength(@Url String url);
*/
}
