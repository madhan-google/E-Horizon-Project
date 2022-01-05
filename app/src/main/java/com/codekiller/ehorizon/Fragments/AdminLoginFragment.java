package com.codekiller.ehorizon.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codekiller.ehorizon.R;
import com.codekiller.ehorizon.Utils.RegisterUserAdapter;
import com.codekiller.ehorizon.Utils.UserField;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AdminLoginFragment extends Fragment {
    public static final String TAG = "ADMIN LOGIN FRAGMENT";
    Context context;
    RecyclerView recyclerView;
    List<UserField> userList;
    ArrayList<String> list;
    DatabaseReference databaseReference;
    RegisterUserAdapter registerUserAdapter;
    public AdminLoginFragment(Context context, ArrayList<String> list) {
        // Required empty public constructor
        this.userList = new ArrayList<>();
        this.list = list;
        this.context = context;
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        ArrayList<String> list = getActivity().getIntent().getStringArrayListExtra("participators");
        View v = inflater.inflate(R.layout.fragment_admin_login, container, false);
//        assert list != null;
        recyclerView = v.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        if(list!=null) {
            for (String key : list) {
                databaseReference.child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userList.add(snapshot.getValue(UserField.class));
//                        Log.d(TAG, "onDataChange: user list - "+userList);
                        registerUserAdapter = new RegisterUserAdapter(userList, context);
                        registerUserAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(registerUserAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
        Log.d(TAG, "onCreateView: userlist - "+userList);

        return v;
    }
}