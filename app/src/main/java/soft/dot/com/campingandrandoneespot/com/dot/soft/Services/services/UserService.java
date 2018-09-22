package soft.dot.com.campingandrandoneespot.com.dot.soft.Services.services;

import retrofit2.Call;
import retrofit2.Callback;
import soft.dot.com.campingandrandoneespot.com.dot.soft.Services.RetrofitClient;
import soft.dot.com.campingandrandoneespot.com.dot.soft.Services.intefaces.IUserServices;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.User;

/**
 * Created by sofien on 07/02/2018.
 */

public class UserService {

    public void SignUpUser(User user, Callback<User> callback) {
        RetrofitClient retrofitClient = new RetrofitClient();
        IUserServices iUserDAO = retrofitClient.getRetrofit().create(IUserServices.class);
        Call<User> call = iUserDAO.signUp (user);
        call.enqueue(callback);
    }

    public void logIn(User user, Callback<User> callback) {
      /*  RetrofitClient retrofitClient = new RetrofitClient();
        IUserServices iUserDAO = retrofitClient.getRetrofit().create(IUserServices.class);
        Call<User> call = iUserDAO.logIn(user);
        call.enqueue(callback);
*/
    }
}
