package com.codekiller.ehorizon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.codekiller.ehorizon.Utils.FireBase;
import com.codekiller.ehorizon.Utils.Toaster;
import com.codekiller.ehorizon.Utils.UserField;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = "REGISTER ACTIVITY";

    TextInputLayout nameLayout, deptLayout, rollNoLayout, yearLayout, genderLayout, sectionLayout, mailLayout, newPassLayout, confirmPassLayout;
    ImageView mainImage;
    MaterialButton registerBtn;
    FireBase fireBase;
    UserField userField;
    Toaster toaster;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameLayout = findViewById(R.id.name_text);
        deptLayout = findViewById(R.id.dept_text);
        rollNoLayout = findViewById(R.id.roll_no_text);
        yearLayout = findViewById(R.id.year_text);
        genderLayout = findViewById(R.id.gender_text);
        sectionLayout = findViewById(R.id.section_text);
        mailLayout = findViewById(R.id.mail_id_text);
        newPassLayout = findViewById(R.id.password_text);
        confirmPassLayout = findViewById(R.id.confirm_pass_text);
        registerBtn = findViewById(R.id.register_btn);
        mainImage = findViewById(R.id.main_anime);
        fireBase = new FireBase();
        toaster = new Toaster(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Registering");
        databaseReference = fireBase.getFireDB().getReference("Users");
        Glide.with(this).load(R.drawable.writing).into(mainImage);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameLayout.getEditText().getText().toString();
                String dept = deptLayout.getEditText().getText().toString();
                String rollNo = rollNoLayout.getEditText().getText().toString();
                String year = yearLayout.getEditText().getText().toString();
                String gender = genderLayout.getEditText().getText().toString();
                String section = sectionLayout.getEditText().getText().toString();
                String mailId = mailLayout.getEditText().getText().toString();
                String newPass = newPassLayout.getEditText().getText().toString();
                String confirmPass = confirmPassLayout.getEditText().getText().toString();
                if(len(name)&&len(dept)&&len(rollNo)&&len(year)&&len(gender)&&len(section)&&len(mailId)&&len(newPass)&&len(confirmPass)){
                    if(newPass.equals(confirmPass)){
                        if(newPass.length()>6){
                            if(mailId.endsWith("@gmail.com")||mailId.endsWith(".edu")){
                                progressDialog.show();
                                FirebaseAuth.getInstance().createUserWithEmailAndPassword(mailId, newPass)
                                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                            @Override
                                            public void onSuccess(AuthResult authResult) {
                                                String userid = authResult.getUser().getUid();
                                                Log.d(TAG, "onClick: user id - "+userid);
                                                userField = new UserField(name,dept,rollNo,year,gender,section,mailId,newPass, userid);
                                                databaseReference.child(userid).setValue(userField)
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.d(TAG, "onFailure: inner exception - "+e.getMessage());
                                                                toaster.toast("something went wrong");
                                                                progressDialog.dismiss();
                                                            }
                                                        })
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                toaster.toast("successfully register");
                                                                progressDialog.dismiss();
                                                                startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                                                            }
                                                        });
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "onFailure: exception - "+e.getMessage());
                                                toaster.toast("something went wrong");
                                                progressDialog.dismiss();
                                            }
                                        });
                            }else{
                                toaster.toast("invalid mail id");
                            }
                        }else{
                            toaster.toast("password length must be above 6");
                        }
                    }else{
                        toaster.toast("password mismatch");
                    }
                }else{
                    toaster.toast("fill all the fields");
                }
            }
        });
    }
    public static boolean len(String s){return s.length()!=0;}
}