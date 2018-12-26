package soft.dot.com.campingandrandoneespot.com.dot.soft.services.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.RetrofitClient;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.intefaces.ICircuitServices;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Circuit;

public class CircuitService {

    public void getAll(Callback callback) {
        RetrofitClient retrofitClient = new RetrofitClient();
        ICircuitServices iCircuitServices = retrofitClient.getRetrofit().create(ICircuitServices.class);
        Call<List<Circuit>> call = iCircuitServices.getAll();
        call.enqueue(callback);
    }

    public void addCircuit(Callback callback, Circuit circuit) {
        RetrofitClient retrofitClient = new RetrofitClient();
        ICircuitServices iCircuitServices = retrofitClient.getRetrofit().create(ICircuitServices.class);
        Call<Circuit> call = iCircuitServices.addNew(circuit);
        call.enqueue(callback);

    }
}
