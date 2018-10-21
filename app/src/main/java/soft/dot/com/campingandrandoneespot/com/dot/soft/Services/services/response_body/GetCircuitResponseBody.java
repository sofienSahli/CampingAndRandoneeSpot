package soft.dot.com.campingandrandoneespot.com.dot.soft.Services.services.response_body;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Nullable;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Spot;

public class GetCircuitResponseBody {
    @SerializedName("circuits")
    @Expose
    List<SpotResponseBody> data;
    @SerializedName("current_page")
    @Expose
    int current_page;

    public List<SpotResponseBody> getData() {
        return data;
    }

    public void setData(List<SpotResponseBody> data) {
        this.data = data;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }
}
