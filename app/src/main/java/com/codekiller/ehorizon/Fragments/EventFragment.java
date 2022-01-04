package com.codekiller.ehorizon.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codekiller.ehorizon.R;
import com.google.android.material.button.MaterialButton;

public class EventFragment extends Fragment {
    Context context;
    RecyclerView recyclerView;
    MaterialButton materialButton;
    String who;
    public EventFragment(Context context, String who) {
        // Required empty public constructor
        this.who = who;
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
        View v = inflater.inflate(R.layout.fragment_event, container, false);
        materialButton = v.findViewById(R.id.add_btn);
        recyclerView = v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        if(who.equals("admin")) materialButton.setVisibility(View.VISIBLE);
        return v;
    }
}