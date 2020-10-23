package soft.dot.com.campingandrandoneespot.com.dot.soft.fragments;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.activities.MainActivity;
import soft.dot.com.campingandrandoneespot.com.dot.soft.activities.NewUserAccountsActivity;
import soft.dot.com.campingandrandoneespot.com.dot.soft.activities.ProfilActivity;
import soft.dot.com.campingandrandoneespot.com.dot.soft.activities.SplashScreenActivity;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Roles;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.User;
import soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage.UserSharedPref;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.RetrofitClient;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.services.UserService;

import static android.app.Activity.RESULT_OK;

public class ProfilFragment extends Fragment implements View.OnClickListener, Callback<ResponseBody> {
    CircleImageView imageView10;
    TextView user_profile_name, phone, email;
    Button logout, users, circuit, events;
    ImageButton update;
    static final int IMAGE_GALERIE = 202;
    private int READ_STORAGE = 101;
    private int WRITE_STORAGE = 101;
    User user;

    Callback<ResponseBody> ban_account = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (response.code() == 200) {
                Toast.makeText(getActivity(), "Le compte a été suspendu", Toast.LENGTH_LONG).show();
                setUpRetablirButton();
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {

            Log.e("onFailure ban account", "onFailure: " + t.getMessage());
            Toast.makeText(getActivity(), "Erreur intene ", Toast.LENGTH_LONG).show();
        }
    };
    Callback<ResponseBody> retablir_account = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (response.code() == 200) {
                Toast.makeText(getActivity(), "Le compte a été rétablie", Toast.LENGTH_LONG).show();
                setUpBanButton();
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {

            Log.e("onFailure ban account", "onFailure: " + t.getMessage());
            Toast.makeText(getActivity(), "Erreur intene ", Toast.LENGTH_LONG).show();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        instatiateWidget(view);
        if (getActivity() instanceof MainActivity)
            setUpLocalUserView(view);
        else if (getActivity() instanceof ProfilActivity) {
            if (getActivity().getIntent().getExtras() != null) {
                Bundle bundle = getActivity().getIntent().getExtras();
                if (bundle.containsKey(ProfilActivity.USER_KEY)) {
                    this.user = bundle.getParcelable(ProfilActivity.USER_KEY);
                    setUpVisitedProfilUser();
                }
            }
        }

        return view;
    }

    private void instatiateWidget(View view) {
        imageView10 = view.findViewById(R.id.imageView10);
        imageView10.setOnClickListener(this);

        user_profile_name = view.findViewById(R.id.name);
        update = view.findViewById(R.id.update);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        logout = view.findViewById(R.id.logout);
        users = view.findViewById(R.id.users);
        circuit = view.findViewById(R.id.circuit);
        events = view.findViewById(R.id.events);
    }

    private void setUpVisitedProfilUser() {
        if (user.getMedias() != null && !user.getMedias().isEmpty()) {
            String image_url = RetrofitClient.BASE_URL + user.getMedias().get(user.getMedias().size() - 1).getFile();
            Picasso.with(getActivity()).load(image_url).into(imageView10);
            imageView10.setOnClickListener(null);
        }
        String user_name = user.getFirstName() + " " + user.getLastName();
        user_profile_name.setText(user_name);
        phone.setText(" " + user.getPhone());
        email.setText(user.getEmail());
        if (user.isActive())
            setUpBanButton();
        else
            setUpRetablirButton();
        update.setVisibility(View.GONE);

        users.setText("Modifer les priviléges du comptes");
        users.setOnClickListener(v -> {
            modifier_accounte_role();
        });
    }

    private void modifier_accounte_role() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_update_role, null, false);
        RadioButton simple_user = view.findViewById(R.id.radioButton3);
        RadioButton admin = view.findViewById(R.id.radioButton2);
        RadioButton super_admin = view.findViewById(R.id.radioButton);
        if (user.getRole().equals(Roles.ADMIN_USER)) {
            admin.setChecked(true);
        } else if (user.getRole().equals(Roles.SUPER_ADMIN)) {
            super_admin.setChecked(true);
        } else {
            simple_user.setChecked(true);
        }
        AlertDialog alertDial = new AlertDialog.Builder(getActivity()).setView(view).create();
        view.findViewById(R.id.button13).setOnClickListener(v -> alertDial.dismiss());
        view.findViewById(R.id.valider).setOnClickListener(v -> {
            String role = "";
            if (simple_user.isChecked())
                role = Roles.SIMPLE_USER;
            else if (admin.isChecked())
                role = Roles.ADMIN_USER;
            else
                role = Roles.SUPER_ADMIN;

            UserService userService = new UserService();
            userService.update_privilége(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 200)
                        Toast.makeText(getActivity(), "Privilége modifé", Toast.LENGTH_LONG).show();
                    alertDial.dismiss();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getActivity(), "Erreur interne veuillez ressayer plus tard", Toast.LENGTH_SHORT).show();
                    Log.e("Role update", "onFailure: " + t.getMessage());
                    alertDial.dismiss();
                }
            }, user.getId(), role);
        });
        alertDial.show();
    }

    private void setUpRetablirButton() {
        logout.setText("Retablir le compte");
        logout.setBackground(getActivity().getDrawable(R.drawable.radius_corners_8_green));
        logout.setOnClickListener(v -> {
            new AlertDialog.Builder(getActivity()).setMessage("Etes vous sur de vouloire retablir ce compte ? ")
                    .setPositiveButton("Confirmer", (dialog, id) -> {
                        UserService userService = new UserService();
                        userService.retablir_account(retablir_account, user.getId());
                        dialog.dismiss();
                    })
                    .setNegativeButton("Cancel", (dialog, id) -> {
                        dialog.dismiss();
                    }).show();


        });
    }

    private void setUpBanButton() {
        logout.setText("Bannir l'utilistateur");
        logout.setBackground(getActivity().getDrawable(R.drawable.corner_radius_8_red));
        logout.setOnClickListener(v -> {
            new AlertDialog.Builder(getActivity()).setMessage("Etes vous sur de vouloire bannir ce compte ? ")
                    .setPositiveButton("Confirmer", (dialog, id) -> {
                        UserService userService = new UserService();
                        userService.ban_account(ban_account, user.getId());
                        dialog.dismiss();
                    })
                    .setNegativeButton("Cancel", (dialog, id) -> {
                        dialog.dismiss();
                    }).show();


        });
    }

    private void setUpLocalUserView(View view) {

        Log.e("setUpLocalUserView", " Check Point ");
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            getRuntimePermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE);
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            getRuntimePermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_STORAGE);
        }
        UserSharedPref userSharedPref = new UserSharedPref(getActivity().getSharedPreferences(UserSharedPref.USER_FILE, Context.MODE_PRIVATE));

        get_profile_picture();
        String s = userSharedPref.getString(UserSharedPref.USER_FIRST_NAME) + " " + userSharedPref.getString(UserSharedPref.USER_LAST_NAME);
        user_profile_name.setText(s);
        s = userSharedPref.getLong(UserSharedPref.USER_PHONE) + " ";
        phone.setText(s);
        email.setText(userSharedPref.getString(UserSharedPref.EMAIL));
        view.findViewById(R.id.logout).setOnClickListener(v -> {
            userSharedPref.logOutUser();
            Intent intent = new Intent(getActivity(), SplashScreenActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
        });
        view.findViewById(R.id.users).setOnClickListener(V -> {
            Intent intent = new Intent(getActivity(), NewUserAccountsActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
        });
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
        userSharedPref.insertString(UserSharedPref.USER_PROFILE_PICTURE, selectedImage);
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

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.code() == 200) {
            try {
                if (response.body().string().equals("not"))
                    Toast.makeText(getActivity(), "Echec de l'action veuilliez ressayer", Toast.LENGTH_SHORT).show();
                else {
                    UserSharedPref userSharedPref = new UserSharedPref(getActivity().getSharedPreferences(UserSharedPref.USER_FILE, Context.MODE_PRIVATE));
                    String image = RetrofitClient.BASE_URL + response.body().string();
                    Log.e("xxxx", response.body().string());
                    userSharedPref.insertString(UserSharedPref.USER_PROFILE_PICTURE, image);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), "Echec de l'action veuilliez ressayer", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.e("Upload failed", t.getMessage());
    }
}
