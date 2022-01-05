package com.codekiller.ehorizon.Fragments;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codekiller.ehorizon.AddingActivity;
import com.codekiller.ehorizon.R;
import com.codekiller.ehorizon.Utils.EventRecyclerAdapter;
import com.codekiller.ehorizon.Utils.Events;
import com.codekiller.ehorizon.Utils.Toaster;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventFragment extends Fragment implements SensorEventListener {

    SensorManager sensorManager;
    Sensor sensor, sensorAcc;

    Context context;
    RecyclerView recyclerView;
    MaterialButton materialButton;
    String who;
    List<Events> list;

    DatabaseReference databaseReference;
    Toaster toaster;

    ProgressDialog progressDialog;
    EventRecyclerAdapter eventRecyclerAdapter;

    public EventFragment() {
    }

    public EventFragment(Context context, String who) {
        // Required empty public constructor
        this.who = who;
        this.context = context;
        list = new ArrayList<>();
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading");
        toaster = new Toaster(context);
        databaseReference = FirebaseDatabase.getInstance().getReference("Events");
        sensorManager = (SensorManager) context.getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorAcc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_event, container, false);
        materialButton = v.findViewById(R.id.add_btn);
        recyclerView = v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        if(who!=null&&who.equals("admin")) materialButton.setVisibility(View.VISIBLE);
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AddingActivity.class));
            }
        });
        progressDialog.show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                     for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                         list.add(dataSnapshot.getValue(Events.class));
                     }
                     Collections.reverse(list);
                     eventRecyclerAdapter = new EventRecyclerAdapter(list, context, who);
                     recyclerView.setAdapter(eventRecyclerAdapter);
                     progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                toaster.toast("something went wrong");
                progressDialog.dismiss();
            }
        });

//        recyclerView.setAdapter();

        return v;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_PROXIMITY){
            if(event.values[0]<5) getActivity().finish();
        }
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            if(event.values[1]<-2&&event.values[1]>-4){
//                recyclerView.scrollToPosition(eventRecyclerAdapter.getPosi()<0?0:eventRecyclerAdapter.getPosi()-1);
                recyclerView.smoothScrollToPosition(0);
//                recyclerView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        recyclerView.smoothScrollToPosition(eventRecyclerAdapter.getItemCount()+1);
//                    }
//                });
            }else if(event.values[1]>4&&event.values[1]<6){
//                recyclerView.scrollToPosition(eventRecyclerAdapter.getPosi()+1);
                recyclerView.smoothScrollToPosition(eventRecyclerAdapter.getItemCount()-1);
//                recyclerView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        recyclerView.smoothScrollToPosition(eventRecyclerAdapter.getItemCount()-1);
//                    }
//                });
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorAcc, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}