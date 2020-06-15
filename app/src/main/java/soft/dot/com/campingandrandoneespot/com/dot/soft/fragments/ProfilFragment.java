package soft.dot.com.campingandrandoneespot.com.dot.soft.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage.UserSharedPref;

public class ProfilFragment extends Fragment {
    ImageView user_profile_photo;
    TextView user_profile_name, user_profile_short_bio;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        user_profile_photo = view.findViewById(R.id.user_profile_photo);
        user_profile_name = view.findViewById(R.id.user_profile_name);
        user_profile_short_bio = view.findViewById(R.id.user_profile_short_bio);
        facebookProviderPicture();
        return view;
    }

    private ImageView getViewById(View view) {
        return view.findViewById(R.id.user_profile_photo);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserSharedPref userSharedPref = new UserSharedPref(getActivity().getSharedPreferences(UserSharedPref.USER_FILE, Context.MODE_PRIVATE));
        String text = userSharedPref.getString(UserSharedPref.USER_FIRST_NAME) + "  " + userSharedPref.getString(UserSharedPref.USER_LAST_NAME);
        user_profile_name.setText(text);
    }

    private void facebookProviderPicture() {
        Bundle params = new Bundle();
        params.putString("fields", "id,email,gender,cover,picture.type(large)");
        new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                response -> {
                    if (response != null) {
                        try {
                            JSONObject data = response.getJSONObject();
                            if (data.has("picture")) {
                                String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                // Bitmap profilePic= BitmapFactory.decodeStream(profilePicUrl);
                                Picasso.with(getActivity()).load(profilePicUrl).fit().into(user_profile_photo);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).executeAsync();
    }

}
