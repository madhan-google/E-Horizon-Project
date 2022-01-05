package com.codekiller.ehorizon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
        Glide.with(this).load(R.drawable.monkey_gif).into(mainAnime);
        nameView.setText("Name : "+userField.getName());
        deptView.setText("Department : "+userField.getDepartment()+" '"+userField.getSection()+"'");
        rollNoView.setText("Roll No : "+userField.getRoll_no());
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userField.getPhNo()!=null) startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+userField.getPhNo())));
            }
        });
        mailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Event");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{userField.getMailid()});
                if( intent.resolveActivity(getPackageManager()) != null ){
                    startActivity(intent);
                }
            }
        });
    }
}