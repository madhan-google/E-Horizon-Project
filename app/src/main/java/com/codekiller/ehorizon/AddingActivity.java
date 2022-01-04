package com.codekiller.ehorizon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codekiller.ehorizon.Utils.Events;
import com.codekiller.ehorizon.Utils.Toaster;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddingActivity extends AppCompatActivity {

    public static final String TAG = "ADDING ACTIVITY";
    public static final int IMAGE_REQUEST = 1234;
    public static final int GET_IMAGE = 12356;

    TextView mainView;
    TextInputLayout titleLayout, descriptionLayout, coordinateLayout, deptLayout, startDateLayout, endDateLayout, formLinkLayout;
    Uri imageUrl;
    MaterialButton addBtn;
    RadioGroup radioGroup;
    ImageView imageView;
    boolean isRegistrationNeed = false;

    DatabaseReference databaseReference;
    StorageReference storageReference;

    Toaster toaster;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);
        mainView = findViewById(R.id.main_text);
        imageView = findViewById(R.id.image_view);
        titleLayout = findViewById(R.id.title_text);
        descriptionLayout = findViewById(R.id.description_text);
        coordinateLayout = findViewById(R.id.coordinat_name);
        deptLayout = findViewById(R.id.dept_name);
        startDateLayout = findViewById(R.id.start_date_text);
        endDateLayout = findViewById(R.id.end_date_text);
        formLinkLayout = findViewById(R.id.form_link_text);
        addBtn = findViewById(R.id.add_btn);
        radioGroup = findViewById(R.id.radio_group);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Adding Event ..");
        toaster = new Toaster(this);

        databaseReference = FirebaseDatabase.getInstance().getReference("Events");
        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("EventsPhotos");


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(AddingActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(AddingActivity.this, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, IMAGE_REQUEST);
                }else{
                    startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), GET_IMAGE);
                }
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleLayout.getEditText().getText().toString();
                String description = descriptionLayout.getEditText().getText().toString();
                String coordinator = coordinateLayout.getEditText().getText().toString();
                String dept = deptLayout.getEditText().getText().toString();
                String startDate = startDateLayout.getEditText().getText().toString();
                String endDate = endDateLayout.getEditText().getText().toString();
                String formlink = descriptionLayout.getEditText().getText().toString();
                formlink = ok(formlink)?formlink:"no link";
                if(ok(title)&&ok(description)&&ok(dept)&&ok(startDate)&&ok(coordinator)&&ok(endDate)){
                    if(imageUrl!=null){
                        progressDialog.show();
                        StorageReference storageReference1 = storageReference.child("IMG_"+System.currentTimeMillis()+".jpg");
                        String finalFormlink = formlink;
                        storageReference1.putFile(imageUrl)
                                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        storageReference1.getDownloadUrl()
                                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        String pushKey = databaseReference.push().getKey();
                                                        Events events = new Events(title,dept,startDate,endDate,description, finalFormlink,uri.toString(),coordinator,isRegistrationNeed,pushKey);
                                                        databaseReference.child(pushKey)
                                                                .setValue(events)
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        toaster.toast(e.getMessage());
                                                                        progressDialog.dismiss();
                                                                    }
                                                                })
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        toaster.toast("Event has been added successfully");
                                                                        progressDialog.dismiss();
                                                                    }
                                                                });
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        toaster.toast(e.getMessage());
                                                        progressDialog.dismiss();
                                                    }
                                                });
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        toaster.toast(e.getMessage());
                                        progressDialog.dismiss();
                                    }
                                });
                    }else{
                        toaster.toast("fill above fields");
                    }
                }
            }
        });
    }
    public static boolean ok(String s){
        return s.length()!=0;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==IMAGE_REQUEST&&permissions[0]==Manifest.permission.READ_EXTERNAL_STORAGE){
            startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), GET_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GET_IMAGE&&resultCode==RESULT_OK&&data!=null){
            imageUrl = data.getData();
            Glide.with(AddingActivity.this).load(imageUrl).into(imageView);
        }
    }
}