package soft.dot.com.campingandrandoneespot.com.dot.soft.services.services;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.RetrofitClient;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.intefaces.ICircuitServices;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.intefaces.ISpotService;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Circuit;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Spot;

public class SpotService {

    public void getAll(Callback callback) {
        RetrofitClient retrofitClient = new RetrofitClient();
        ICircuitServices iCircuitServices = retrofitClient.getRetrofit().create(ICircuitServices.class);
        Call<List<Circuit>> call = iCircuitServices.getAll();
        call.enqueue(callback);
    }

    public void addCircuit(Callback callback, Spot Spot) {
        RetrofitClient retrofitClient = new RetrofitClient();
        ISpotService iCircuitServices = retrofitClient.getRetrofit().create(ISpotService.class);
        Call<Spot> call = iCircuitServices.addNew(Spot);
        call.enqueue(callback);

    }

    public void addCircuit(Callback callback, Spot spot,RequestBody filePart) {
        RetrofitClient retrofitClient = new RetrofitClient();
        ISpotService iCircuitServices = retrofitClient.getRetrofit().create(ISpotService.class);
        String spotId = String.valueOf(spot.getId());
        MultipartBody.Part file = MultipartBody.Part.createFormData("file", "file", filePart);
        Call<Spot> call = iCircuitServices.uploadSpot(spot, file);
        call.enqueue(callback);

    }
}
