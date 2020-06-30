package soft.dot.com.campingandrandoneespot.com.dot.soft.fragments;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage.UserSharedPref;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.RetrofitClient;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.services.UserService;

import static android.app.Activity.RESULT_OK;

public class ProfilFragment extends Fragment implements View.OnClickListener, Callback {
    ImageView imageView10;
    TextView user_profile_name, user_profile_short_bio;
    static final int IMAGE_GALERIE = 202;
    private int READ_STORAGE = 101;
    private int WRITE_STORAGE = 101;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        imageView10 = view.findViewById(R.id.imageView10);
        imageView10.setOnClickListener(this);
        user_profile_name = view.findViewById(R.id.name);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            getRuntimePermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE);
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            getRuntimePermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_STORAGE);
        }
        UserSharedPref userSharedPref = new UserSharedPref(getActivity().getSharedPreferences(UserSharedPref.USER_FILE, Context.MODE_PRIVATE));
        if (TextUtils.isEmpty(userSharedPref.getString(UserSharedPref.USER_PROFILE_PICTURE)))
            get_profile_picture();
        else
            Picasso.with(getActivity()).load(userSharedPref.getString(UserSharedPref.USER_PROFILE_PICTURE)).into(imageView10);

        return view;
    }

    private void get_profile_picture() {
        UserSharedPref userSharedPref = new UserSharedPref(getActivity().getSharedPreferences(UserSharedPref.USER_FILE, Context.MODE_PRIVATE));
        UserService userService = new UserService();
        userService.get_profile_picture(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            String s = RetrofitClient.BASE_URL + jsonObject.getString("file");
                            Picasso.with(getActivity()).load(s).into(imageView10);
                            userSharedPref.insertString(UserSharedPref.USER_PROFILE_PICTURE, s);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        }, userSharedPref.getLong(UserSharedPref.USER_ID));

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserSharedPref userSharedPref = new UserSharedPref(getActivity().getSharedPreferences(UserSharedPref.USER_FILE, Context.MODE_PRIVATE));
        String text = userSharedPref.getString(UserSharedPref.USER_FIRST_NAME) + "  " + userSharedPref.getString(UserSharedPref.USER_LAST_NAME);

        user_profile_name.setText(text);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imageView10) {
            createDialog().show();
        }
    }

    public Dialog createDialog() {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Voulez changer votre image de profile ?")
                .setPositiveButton("Selectioner image", (dialog, id) -> {
                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, IMAGE_GALERIE, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                })
                .setNegativeButton("Cancel", (dialog, id) -> {
                    dialog.dismiss();
                });
        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_GALERIE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            imageView10.setImageURI(selectedImage);
            upload_image(getPathFromURI(selectedImage));

        }
    }

    private void upload_image(String selectedImage) {
        File f = new File(selectedImage);
        RequestBody filePart = RequestBody.create(MediaType.parse("file"), f);

        UserService userService = new UserService();
        UserSharedPref userSharedPref = new UserSharedPref(getActivity().getSharedPreferences(UserSharedPref.USER_FILE, Context.MODE_PRIVATE));
        userService.set_profile_picture(this, "image", userSharedPref.getLong(UserSharedPref.USER_ID), filePart);

    }

    @Override
    public void onResponse(Call call, Response response) {
        if (response.code() == 200) {
            Toast.makeText(getActivity(), "Image Uploaded", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Log.e("Upload erreur", t.getMessage());
    }

    public String getPathFromURI(Uri ContentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver()
                .query(ContentUri, proj, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            res = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            cursor.close();
        }


        return res;
    }

    private void getRuntimePermission(String[] s1, int code) {
        ActivityCompat.requestPermissions(getActivity(), s1, code);
    }

}
