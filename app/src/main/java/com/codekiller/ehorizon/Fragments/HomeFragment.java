package com.codekiller.ehorizon.Fragments;

import android.app.Service;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.codekiller.ehorizon.R;

import java.lang.reflect.Type;


public class HomeFragment extends Fragment implements SensorEventListener {
    SensorManager sensorManager;
    Sensor sensor;
    Context context;
    public HomeFragment(Context context) {
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
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ImageView anime1 = v.findViewById(R.id.anime1);
        ImageView anime2 = v.findViewById(R.id.anime2);
        ImageView anime3 = v.findViewById(R.id.anime3);
        ImageView anime4 = v.findViewById(R.id.anime4);
        ImageView anime5 = v.findViewById(R.id.anime5);
        Glide.with(context).load(R.drawable.chemistry_gif).into(anime1);
        Glide.with(context).load(R.drawable.biology_gif).into(anime2);
        Glide.with(context).load(R.drawable.physics_gif).into(anime3);
        Glide.with(context).load(R.drawable.nuclear_gif2).into(anime4);
        Glide.with(context).load(R.drawable.books_gif).into(anime5);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener((SensorEventListener) context, sensor, SensorManager.SENSOR_DELAY_NORMAL);
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
}