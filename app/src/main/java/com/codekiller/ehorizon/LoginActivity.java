package com.codekiller.ehorizon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {
    ImageView mainImage;
    TextInputLayout mailText, passText;
    MaterialButton loginBtn, cancelBtn;
    TextView forgotText, registerText;
    Toaster toaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mailText = findViewById(R.id.mail_id);
        passText = findViewById(R.id.password_text);
        mainImage = findViewById(R.id.main_image);
        loginBtn = findViewById(R.id.ok_button);
        cancelBtn = findViewById(R.id.cancel_btn);
        forgotText = findViewById(R.id.forgot_text);
        registerText = findViewById(R.id.register_text);
        toaster = new Toaster(this);

        Glide.with(this).load(R.drawable.loading_gif).into(mainImage);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mailText.getEditText().getText().toString();
                String pass = passText.getEditText().getText().toString();
                if(mail.length()!=0&&pass.length()!=0){
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(mail, pass)
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    toaster.toast("something went wrong");
                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    toaster.toast("logged in");
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class).putExtra("who", "admin"));
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

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
    }
}