package soft.dot.com.campingandrandoneespot.com.dot.soft.services.services;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.User;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.RetrofitClient;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.intefaces.IUserServices;

/**
 * Created by sofien on 07/02/2018.
 */

public class UserService {

    public void SignUpUser(User user, Callback<User> callback) {
        RetrofitClient retrofitClient = new RetrofitClient();
        IUserServices iUserDAO = retrofitClient.getRetrofit().create(IUserServices.class);
        Call<User> call = iUserDAO.signUp(user);
        call.enqueue(callback);
    }

    public void logIn(String password, String email,  Callback<List<User>> callback) {
        RetrofitClient retrofitClient = new RetrofitClient();
        IUserServices iUserDAO = retrofitClient.getRetrofit().create(IUserServices.class);
        Call<List<User>> call = iUserDAO.login(email, password);
        call.enqueue(callback);
    }


    public void resend_activation_code(String id, Callback<User> callback) {
        RetrofitClient retrofitClient = new RetrofitClient();
        IUserServices iUserDAO = retrofitClient.getRetrofit().create(IUserServices.class);
        Call<User> call = iUserDAO.resend_activation_code(id);
        call.enqueue(callback);
    }

    public void activate_account(String id, String activation_code, Callback<User> callback) {
        RetrofitClient retrofitClient = new RetrofitClient();
        IUserServices iUserDAO = retrofitClient.getRetrofit().create(IUserServices.class);
        Call<User> call = iUserDAO.activate_account(id, activation_code);
        call.enqueue(callback);
    }

    public void set_profile_picture(Callback callback, String type, long owner_id, RequestBody filePart) {
        RetrofitClient retrofitClient = new RetrofitClient();
        IUserServices iCircuitServices = retrofitClient.getRetrofit().create(IUserServices.class);
        MultipartBody.Part file = MultipartBody.Part.createFormData("n", "n", filePart);
        Call<ResponseBody> call = iCircuitServices.set_profile_picture(file, owner_id, type);
        call.enqueue(callback);
    }
    public void get_profile_picture( Callback<ResponseBody> callback, long id  ){
        RetrofitClient retrofitClient = new RetrofitClient();
        IUserServices iCircuitServices = retrofitClient.getRetrofit().create(IUserServices.class);
        Call<ResponseBody> call = iCircuitServices.get_profile_picture(id);
        call.enqueue(callback);
    }

}
