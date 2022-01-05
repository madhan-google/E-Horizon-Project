package com.codekiller.ehorizon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import com.codekiller.ehorizon.Fragments.AdminLoginFragment;
import com.codekiller.ehorizon.Fragments.EventFragment;
import com.codekiller.ehorizon.Fragments.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements SensorEventListener {
    public static String userId;
    public static final String TAG = "HOME ACTIVITY";
    SNavigationDrawer navigationDrawer;
    ArrayList<MenuItem> list;
    String who;
    SensorManager sensorManager;
    Sensor sensor, sensorAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        list = new ArrayList<>();
        navigationDrawer = findViewById(R.id.drawer_layout);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        who = getIntent().getStringExtra("who");
        Log.d(TAG, "onCreate: who - "+who);
        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorAcc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        list.add(new MenuItem("Home", R.drawable.bg1));
        list.add(new MenuItem("Events", R.drawable.bg4));
        list.add(new MenuItem("About", R.drawable.bg6));
        list.add(new MenuItem("Logout", R.drawable.bg3));
        if(who!=null&&who.equals("admin")) list.add(new MenuItem("Admin", R.drawable.bg5));
        navigationDrawer.setMenuItemList(list);
        navigationDrawer.setAppbarTitleTV("Home");
        navigationDrawer.setAppbarColor(android.R.color.holo_blue_light);
        loadFragment(new HomeFragment(this));
        navigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClicked(int position) {
                switch (position){
                    case 0:
                        loadFragment(new HomeFragment(HomeActivity.this));
                        break;
                    case 1:
                        loadFragment(new EventFragment(HomeActivity.this, who));
                        break;
                    case 2:
//                        loadFragment(new AdminLoginFragment(HomeActivity.this));
                        break;
                    case 3:
                        FirebaseAuth.getInstance().signOut();
//                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        finish();
                        break;
                    case 4:
                        loadFragment(new AdminLoginFragment(HomeActivity.this));
                }
            }
        });
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
//        transaction.attach(fragment);
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        loadFragment(new HomeFragment(this));
        sensorManager.registerListener(this, sensorAcc, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener((SensorEventListener) this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_PROXIMITY){
            if(event.values[0]<5) finish();
        }
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            Log.d(TAG, "onSensorChanged: x = "+event.values[0]+" y = "+event.values[1]+" z = "+event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}