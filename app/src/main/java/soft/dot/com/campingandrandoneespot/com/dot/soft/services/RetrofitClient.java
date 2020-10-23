package soft.dot.com.campingandrandoneespot.com.dot.soft.services;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sofien on 04/02/2018.
 */

public class RetrofitClient extends AppCompatActivity {


    //public static final String BASE_URL = "http://10.0.2.2:8000/";
   // public static final String BASE_URL = "http://54.38.188.166/";
    //public static final String BASE_URL = "http://192.168.1.2:8000/";
   // public static final String BASE_URL = "http://192.168.3.140:8000/";
    //public static final String BASE_URL = "http://192.168.3.136:8000/";
    public static final String BASE_URL = "http://192.168.1.2:8000/";
   // public static final String BASE_URL = "http://192.168.1.3:8000/";
   // public static final String BASE_URL = "http://192.168.1.4:8000/";

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
                .registerTypeAdapter(Boolean.class, booleanAsIntAdapter)
                .registerTypeAdapter(boolean.class, booleanAsIntAdapter)
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
    private static final TypeAdapter<Boolean> booleanAsIntAdapter = new TypeAdapter<Boolean>() {
        @Override
        public void write(JsonWriter out, Boolean value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value);
            }
        }
        @Override
        public Boolean read(JsonReader in) throws IOException {
            JsonToken peek = in.peek();
            switch (peek) {
                case BOOLEAN:
                    return in.nextBoolean();
                case NULL:
                    in.nextNull();
                    return null;
                case NUMBER:
                    return in.nextInt() != 0;
                case STRING:
                    return Boolean.parseBoolean(in.nextString());
                default:
                    throw new IllegalStateException("Expected BOOLEAN or NUMBER but was " + peek);
            }
        }
    };

}