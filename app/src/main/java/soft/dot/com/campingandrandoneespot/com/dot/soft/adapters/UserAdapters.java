package soft.dot.com.campingandrandoneespot.com.dot.soft.adapters;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import soft.dot.com.campingandrandoneespot.R;
import soft.dot.com.campingandrandoneespot.com.dot.soft.activities.ProfilActivity;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.Roles;
import soft.dot.com.campingandrandoneespot.com.dot.soft.entities.User;
import soft.dot.com.campingandrandoneespot.com.dot.soft.localStorage.UserSharedPref;
import soft.dot.com.campingandrandoneespot.com.dot.soft.services.RetrofitClient;

public class UserAdapters extends RecyclerView.Adapter<UserAdapters.Holder> {

    List<User> userList;
    Context context;

    public UserAdapters(Context context, List<User> list) {
        this.userList = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.user_row, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bindItem(userList.get(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView name, role, nombre_event, isActive;
        ImageButton imageButton7;


        public Holder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.circleImageView);
            name = itemView.findViewById(R.id.name);
            role = itemView.findViewById(R.id.role);
            nombre_event = itemView.findViewById(R.id.nombre_event);
            imageButton7 = itemView.findViewById(R.id.imageButton7);
            isActive = itemView.findViewById(R.id.isActive);
        }

        public void bindItem(User user) {
            UserSharedPref userSharedPref = new UserSharedPref(context.getSharedPreferences(UserSharedPref.USER_FILE, Context.MODE_PRIVATE));
            String r = userSharedPref.getString(UserSharedPref.USER_ROLE);
            String s = user.getFirstName() + "  " + user.getLastName();
            name.setText(s);
            role.setText(user.getRole());
            if (user.isActive())
                isActive.setText("Compte Active");
            else {
                isActive.setText("Compte suspendu");
                isActive.setTextColor(context.getColor(R.color.bpRed));
            }
            // Picasso for picture
            if (user.getMedias() != null && !user.getMedias().isEmpty())
                Picasso.with(context).load(RetrofitClient.BASE_URL + user.getMedias().get(0).getFile()).into(circleImageView);
            Toast.makeText(context, r, Toast.LENGTH_LONG).show();
            // Pop up menu
            if (r.equals(Roles.SUPER_ADMIN)) {
                showSuperAdminPopup(user);
            } else if (r.equals(Roles.ADMIN_USER)) {
                imageButton7.setOnClickListener(v -> {
                    showAdminPopup(user);
                });
            }
        }

        private void showSuperAdminPopup(User user) {
            imageButton7.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.inflate(R.menu.super_admin_user_popup);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    if (menuItem.getItemId() == R.id.email) {
                        sendEmail(user.getEmail());
                        return true;
                    } else if (menuItem.getItemId() == R.id.call) {
                        call(user.getPhone());
                        return true;
                    } else if (menuItem.getItemId() == R.id.submenu0) {
                        Toast.makeText(context, "Promouvoir le compte", Toast.LENGTH_SHORT).show();
                        return true;
                    } else if (menuItem.getItemId() == R.id.submenu1) {
                        Toast.makeText(context, "Bannir account", Toast.LENGTH_SHORT).show();
                        return true;
                    } else if (menuItem.getItemId() == R.id.submenu2) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(ProfilActivity.USER_KEY, user);
                        Intent intent = new Intent(context, ProfilActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((AppCompatActivity) context).toBundle());
                        return true;
                    }
                    return false;
                });
            });

        }

        private void showAdminPopup(User user) {
            imageButton7.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.inflate(R.menu.admn_user_popup_menu);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    if (menuItem.getItemId() == R.id.email) {
                        sendEmail(user.getEmail());
                        return true;
                    } else if (menuItem.getItemId() == R.id.call) {
                        call(user.getPhone());
                        return true;
                    } else if (menuItem.getItemId() == R.id.events) {
                        Toast.makeText(context, "Events clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    } else if (menuItem.getItemId() == R.id.circuits) {
                        Toast.makeText(context, "Circuit clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    }

                    return false;
                });

            });

        }

        private void sendEmail(String e_mail) {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            final PackageManager pm = context.getPackageManager();
            final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
            String className = null;

            for (final ResolveInfo info : matches) {
                if (info.activityInfo.packageName.equals("com.google.android.gm")) {
                    className = info.activityInfo.name;

                    if (className != null && !className.isEmpty()) {
                        break;
                    }
                }
            }
            if (className != null)
                emailIntent.setClassName("com.google.android.gm", className);
            emailIntent.setType("message/rfc822");

            //emailIntent.setDataAndType(Uri.parse("mailto:" + e_mail),"message/rfc822" );
            emailIntent.putExtra(Intent.EXTRA_EMAIL, e_mail);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Demande d'encadrement pour PFE ");
            try {

                context.startActivity(emailIntent);

            } catch (Exception e) {
                Log.e("Emaio", e.getMessage());
            }
        }

        private void call(long phone) {

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phone));
            context.startActivity(callIntent);
        }

    }
}
