package soft.dot.com.campingandrandoneespot.com.dot.soft.services.intefaces;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Media;

public interface IMediaServices {

    @Multipart
    @POST("/m/eve")
    Call<ResponseBody> store_event_media(@Part MultipartBody.Part file,@Part("event_id") int event_id, @Part("type") String type);

}
