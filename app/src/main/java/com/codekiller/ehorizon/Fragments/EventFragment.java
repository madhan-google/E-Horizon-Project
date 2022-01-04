package com.codekiller.ehorizon.Fragments;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codekiller.ehorizon.AddingActivity;
import com.codekiller.ehorizon.R;
import com.codekiller.ehorizon.Utils.EventRecyclerAdapter;
import com.google.android.material.button.MaterialButton;

public class EventFragment extends Fragment implements SensorEventListener {

    SensorManager sensorManager;
    Sensor sensor;

    Context context;
    RecyclerView recyclerView;
    MaterialButton materialButton;
    String who;

    public EventFragment() {
    }

    public EventFragment(Context context, String who) {
        // Required empty public constructor
        this.who = who;
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
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
//        recyclerView.setAdapter();
        if(who!=null&&who.equals("admin")) materialButton.setVisibility(View.VISIBLE);
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AddingActivity.class));
            }
        });
        return v;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_PROXIMITY){
            if(event.values[0]<5) getActivity().finish();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener((SensorEventListener) context, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
}