package soft.dot.com.campingandrandoneespot.com.dot.soft.services;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sofien on 04/02/2018.
 */

public class RetrofitClient extends AppCompatActivity {


    //public static final String BASE_URL = "http://10.0.2.2:8000/";
    public static final String BASE_URL = "http://54.38.188.166/";

    protected Retrofit retrofit;

    public Retrofit getRetrofit() {
        return retrofit;
    }


    public RetrofitClient() {
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okBuilder.addInterceptor(httpLoggingInterceptor);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}