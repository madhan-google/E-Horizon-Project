package com.codekiller.ehorizon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class AddingActivity extends AppCompatActivity {

    public static final String TAG = "ADDING ACTIVITY";

    TextView mainView;
    TextInputLayout titleLayout, descriptionLayout, coordinateLayout, deptLayout, startDateLayout, endDateLayout, formLinkLayout, imageUrl;
    MaterialButton addBtn;
    RadioGroup radioGroup;
    boolean isRegistrationNeed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);
        mainView = findViewById(R.id.main_text);
        titleLayout = findViewById(R.id.title_text);
        descriptionLayout = findViewById(R.id.description_text);
        coordinateLayout = findViewById(R.id.coordinat_name);
        deptLayout = findViewById(R.id.dept_name);
        startDateLayout = findViewById(R.id.start_date_text);
        endDateLayout = findViewById(R.id.end_date_text);
        formLinkLayout = findViewById(R.id.form_link_text);
        addBtn = findViewById(R.id.add_btn);
        radioGroup = findViewById(R.id.radio_group);
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

            }
        });
    }
    public static boolean ok(String s){
        return s.length()!=0;
    }
}