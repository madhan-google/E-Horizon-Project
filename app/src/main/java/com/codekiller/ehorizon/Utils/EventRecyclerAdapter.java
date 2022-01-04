package com.codekiller.ehorizon.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codekiller.ehorizon.HomeActivity;
import com.codekiller.ehorizon.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.ViewHolder> {
    public static final String TAG = "EVENT RECYCLER ADAPTER";
    List<Events> list;
    Context context;
    DatabaseReference databaseReference, databaseReferenceUser;
    ProgressDialog progressDialog;
    Toaster toaster;

    public EventRecyclerAdapter(List<Events> list, Context context) {
        this.list = list;
        this.context = context;
//        this.databaseReference = databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users");
        databaseReferenceUser = FirebaseDatabase.getInstance().getReference("Users");
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        toaster = new Toaster(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.events_items, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Events events = list.get(position);
        holder.titleView.setText(events.getTitle());
        holder.coordinateView.setText("- "+events.getCoordinatorName()+" from "+events.getDept());
        holder.descriptionView.setText(events.getDescription());
        holder.dateView.setText(events.getStartDate()+" - "+events.getEndDate());
        if(events.getFormLink().length()!=0){
            holder.linkView.setVisibility(View.VISIBLE);
            holder.linkView.setText(events.getFormLink());
        }
        if(events.isRegisterNeed()) holder.registerBtn.setVisibility(View.VISIBLE);
        holder.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("registering..");
                progressDialog.show();
                String pushKey = databaseReference.push().getKey();
                final UserField[] userField = new UserField[1];
                databaseReferenceUser.child(HomeActivity.userId)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                userField[0] = (UserField) snapshot.getValue(UserField.class);
                                Log.d(TAG, "onDataChange: user- "+userField[0].getDepartment());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                toaster.toast("something went wrong");
                            }
                        });
                databaseReference.child(pushKey)
                        .setValue(userField)
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
                                toaster.toast("registered successfully");
                                progressDialog.dismiss();
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleView, coordinateView, descriptionView, dateView, linkView;
        MaterialButton registerBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.title_view);
            coordinateView = itemView.findViewById(R.id.coordinate_view);
            descriptionView = itemView.findViewById(R.id.description_view);
            dateView = itemView.findViewById(R.id.date_text);
            linkView = itemView.findViewById(R.id.link_text);
            registerBtn = itemView.findViewById(R.id.register_btn);
        }
    }
}
