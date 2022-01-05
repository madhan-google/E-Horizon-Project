package com.codekiller.ehorizon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.codekiller.ehorizon.Utils.UserField;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class UserDetails extends AppCompatActivity {

    ImageView mainAnime, callBtn, mailBtn;
    TextView nameView, deptView, rollNoView;

    DatabaseReference databaseReference;
    String obj;
    UserField userField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        mailBtn = findViewById(R.id.mail_btn);
        callBtn = findViewById(R.id.call_btn);
        mainAnime = findViewById(R.id.main_anime);
        nameView = findViewById(R.id.name_view);
        deptView = findViewById(R.id.dept_view);
        rollNoView = findViewById(R.id.roll_no_view);
//        databaseReference = FirebaseDatabase.getInstance().getReference("");
        obj = getIntent().getStringExtra("user obj");
        userField = new Gson().fromJson(obj, UserField.class);

    }
}