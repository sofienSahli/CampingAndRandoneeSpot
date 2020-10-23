package soft.dot.com.campingandrandoneespot.com.dot.soft.services.services;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.RetrofitClient;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.intefaces.IMediaServices;

public class MediaServices {
    IMediaServices iMediaServices;

    public MediaServices() {
        RetrofitClient retrofitClient = new RetrofitClient();
        iMediaServices = retrofitClient.getRetrofit().create(IMediaServices.class);
    }

    public void add_event_image(int id, RequestBody filePart, Callback<ResponseBody> callback) {
        MultipartBody.Part file = MultipartBody.Part.createFormData("n", "n", filePart);
        Call<ResponseBody> call = iMediaServices.store_event_media(file,id ,"event_image");
        call.enqueue(callback);
    }
}
