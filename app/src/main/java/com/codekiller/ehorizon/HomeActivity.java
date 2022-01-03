package com.codekiller.ehorizon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;

import com.codekiller.ehorizon.Fragments.HomeFragment;
import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    SNavigationDrawer navigationDrawer;
    ArrayList<MenuItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        list = new ArrayList<>();
        navigationDrawer = findViewById(R.id.drawer_layout);
        list.add(new MenuItem("Home", R.drawable.bg1));
        list.add(new MenuItem("Events", R.drawable.bg4));
        list.add(new MenuItem("About", R.drawable.bg6));
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
    }
}