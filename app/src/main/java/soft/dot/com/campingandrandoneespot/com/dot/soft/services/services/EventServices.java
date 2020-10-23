package soft.dot.com.campingandrandoneespot.com.dot.soft.services.services;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Event;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.RetrofitClient;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.intefaces.IEventService;

public class EventServices {
    IEventService iEventService;

    public EventServices() {
        RetrofitClient retrofitClient = new RetrofitClient();
        iEventService = retrofitClient.getRetrofit().create(IEventService.class);
    }

    public void store_event(Event event, Callback<Event> callback) {
        Call<Event> call = iEventService.store(event);
        call.enqueue(callback);
    }

    public void index(Callback<List<Event>> callback) {
        Call<List<Event>> call = iEventService.index();
        call.enqueue(callback);
    }

    public void get_by_proposer(int id, Callback<List<Event>> callback) {
        Call<List<Event>> call = iEventService.get_by_proposer(id);
        call.enqueue(callback);
    }

    public void get_unallowed_event(Callback<List<Event>> callback) {
        Call<List<Event>> call = iEventService.get_unallowed_event();
        call.enqueue(callback);
    }

    public void allow_event(int id, Callback<ResponseBody> responseBodyCallback) {
        Call<ResponseBody> call = iEventService.allow_event(id);
        call.enqueue(responseBodyCallback);
    }

    public void subscribe_event(long user_id, int event_id, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = iEventService.subscribe(user_id, event_id);
        call.enqueue(callback);
    }

}
