package com.ptit.tmn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ptit.tmn.Controller.AvatarSelecter;
import com.ptit.tmn.Model.User;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    MaterialEditText username, password, new_pass, new_pass_re;
    Button btn_update;
    CircleImageView avatar;
    TextView uname;

    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username = findViewById(R.id.profile_username);
        password = findViewById(R.id.current_password);
        new_pass = findViewById(R.id.new_password);
        new_pass_re = findViewById(R.id.new_password_repeat);
        btn_update = findViewById(R.id.btn_update);
        avatar = findViewById(R.id.profile_avatar);
        uname = findViewById(R.id.profile_uname);

        username.setVisibility(View.GONE);

        password.setFocusable(false);
        password.setFocusableInTouchMode(false);
        password.setClickable(false);

        new_pass.setFocusable(false);
        new_pass.setFocusableInTouchMode(false);
        new_pass.setClickable(false);

        new_pass_re.setFocusable(false);
        new_pass_re.setFocusableInTouchMode(false);
        new_pass_re.setClickable(false);

        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                username.setText(user.getUsername());
                uname.setText(user.getUsername());

                AvatarSelecter avatarSelecter = new AvatarSelecter();
                if( avatarSelecter.has(Integer.parseInt(user.getImageURL())) ) {
                    avatar.setImageResource(avatarSelecter.getAvatar(Integer.parseInt(user.getImageURL())));
                } else {
                    Glide.with(ProfileActivity.this).load(user.getImageURL()).into(avatar);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname.setVisibility(View.GONE);

                username.setVisibility(View.VISIBLE);
                username.setFocusable(true);
                username.setFocusableInTouchMode(true);
                username.setClickable(true);

                password.setFocusable(false);
                password.setFocusableInTouchMode(false);
                password.setClickable(false);

                new_pass.setFocusable(false);
                new_pass.setFocusableInTouchMode(false);
                new_pass.setClickable(false);

                new_pass_re.setFocusable(false);
                new_pass_re.setFocusableInTouchMode(false);
                new_pass_re.setClickable(false);

                btn_update.setText("confirm");

                updateProfile();
            }
        });
    }

    private void updateProfile() {
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser firebaseUser = auth.getCurrentUser();

                if (username.getText().toString().length() > 12) {
                    Toast.makeText(ProfileActivity.this, "Username must be less than 12 characters.", Toast.LENGTH_SHORT).show();
                    return;
                }

                AvatarSelecter avatarSelecter = new AvatarSelecter();

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id", firebaseUser.getUid());
                hashMap.put("username", username.getText().toString());
                hashMap.put("imageURL", avatarSelecter.randomAvatar().toString());

                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

                uname.setVisibility(View.VISIBLE);
                uname.setText(username.getText().toString());

                username.setVisibility(View.GONE);
            }
        });
    }
}
