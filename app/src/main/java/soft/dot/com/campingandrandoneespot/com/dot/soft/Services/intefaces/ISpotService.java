package soft.dot.com.campingandrandoneespot.com.dot.soft.Services.intefaces;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Circuit;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Spot;

public interface ISpotService {

    @POST("spot")
    Call<Spot> addNew(@Body Spot spot);

    @Multipart
    @POST("spot")
    Call<List<Spot>> UploadsSpots(@Part List<Spot> spots, @Part List<MultipartBody.Part> files);

    @Multipart
    @POST("spot")
    Call<Spot> uploadSpot(@Part("spot") Spot spot, @Part MultipartBody.Part file);

}
