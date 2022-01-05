package com.codekiller.ehorizon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codekiller.ehorizon.Utils.FireBase;
import com.codekiller.ehorizon.Utils.Toaster;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LOGIN ACTIVITY";

    ImageView mainImage;
    TextInputLayout mailText, passText;
    MaterialButton loginBtn, cancelBtn;
    TextView forgotText, registerText;
    Toaster toaster;
    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;

    DatabaseReference databaseAdmin, databaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mailText = findViewById(R.id.mail_id);
        passText = findViewById(R.id.password_text);
        mainImage = findViewById(R.id.anime1);
        loginBtn = findViewById(R.id.ok_button);
        cancelBtn = findViewById(R.id.cancel_btn);
        forgotText = findViewById(R.id.forgot_text);
        registerText = findViewById(R.id.register_text);
        toaster = new Toaster(this);

        databaseAdmin = FirebaseDatabase.getInstance().getReference("Admin");
        sharedPreferences = getApplicationContext().getSharedPreferences("com.codekiller.ehorizon", Context.MODE_PRIVATE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging In");
        progressDialog.setCanceledOnTouchOutside(false);

        Glide.with(this).load(R.drawable.loading_gif).into(mainImage);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mailText.getEditText().getText().toString();
                String pass = passText.getEditText().getText().toString();
                if(mail.length()!=0&&pass.length()!=0){
                    progressDialog.show();
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(mail, pass)
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    toaster.toast("something went wrong");
                                    progressDialog.dismiss();
                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    String userId = authResult.getUser().getUid();
                                    Log.d(TAG, "onSuccess: user id - "+userId);
                                    String who = "";
                                    if(userId.equals("VBBk17vuFUYo2TYhfLpmYFmzei83")){
                                        who = "admin";
                                    }else{
                                        who = "user";
                                    }
//                                    toaster.toast(error.getMessage());
                                    Log.d(TAG, "onSuccess: who - user");
                                    progressDialog.dismiss();
                                    sharedPreferences.edit().putString("who", who).apply();
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class).putExtra("who", who));
                                    finish();
                                }
                            });
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mailText.getEditText().setText("");
                passText.getEditText().setText("");
            }
        });
        forgotText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotActivity.class));
            }
        });
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            String who = sharedPreferences.getString("who", null);
            startActivity(new Intent(LoginActivity.this, HomeActivity.class).putExtra("who", who));
            finish();
        }
    }
}