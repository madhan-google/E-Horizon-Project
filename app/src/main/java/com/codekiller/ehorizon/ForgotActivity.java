package com.codekiller.ehorizon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.codekiller.ehorizon.Utils.Toaster;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotActivity extends AppCompatActivity {
    MaterialButton button;
    TextInputLayout mailText;
    Toaster toaster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        button = findViewById(R.id.ok_btn);
        mailText = findViewById(R.id.mail_id);
        toaster = new Toaster(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mailText.getEditText().getText().toString();
                if(mail.length()!=0){
                    FirebaseAuth.getInstance().sendPasswordResetEmail(mail)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    toaster.toast("check your mail");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    toaster.toast("something went wrong");
                                }
                            });
                }
            }
        });
    }
}