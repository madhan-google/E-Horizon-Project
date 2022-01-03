package com.codekiller.ehorizon.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.codekiller.ehorizon.R;



public class HomeFragment extends Fragment {
    Context context;
    public HomeFragment(Context context) {
        this.context = context;
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
}