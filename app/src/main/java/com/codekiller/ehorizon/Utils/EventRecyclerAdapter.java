package com.codekiller.ehorizon.Utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codekiller.ehorizon.AddingActivity;
import com.codekiller.ehorizon.Fragments.AdminLoginFragment;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.ViewHolder> {
    public static final String TAG = "EVENT RECYCLER ADAPTER";
    List<Events> list;
    Context context;
    DatabaseReference databaseReference, databaseReferenceUser, databaseReferenceEvents;
//    StorageReference storageReference;
    ProgressDialog progressDialog;
    Toaster toaster;
    String who;
    int posi;

    public EventRecyclerAdapter(List<Events> list, Context context, String who) {
        this.who = who;
        this.list = list;
        this.context = context;
//        this.databaseReference = databaseReference;
//        databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users");
        databaseReferenceUser = FirebaseDatabase.getInstance().getReference("Users");
        databaseReferenceEvents = FirebaseDatabase.getInstance().getReference("Events");
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
        setPosi(position);
        Events events = list.get(position);
        holder.titleView.setText(events.getTitle());
        holder.coordinateView.setText("- "+events.getCoordinatorName()+" from "+events.getDept());
        holder.descriptionView.setText(events.getDescription());
        holder.dateView.setText(events.getStartDate()+" - "+events.getEndDate());
        Glide.with(context).load(events.getPictureUrl().equals("default")?R.drawable.event_gif:events.getPictureUrl()).into(holder.eventImage);
        if(events.getFormLink().length()!=0){
            holder.linkView.setVisibility(View.VISIBLE);
            holder.linkView.setText(events.getFormLink());
        }
        if(events.isRegisterNeed()) holder.registerBtn.setVisibility(View.VISIBLE);
        if(who.equals("admin")) holder.registerBtn.setVisibility(View.GONE);
        if(who!=null&&who.equals("admin")) holder.editBtn.setVisibility(View.VISIBLE);
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String obj = new Gson().toJson(events);
                context.startActivity(new Intent(context, AddingActivity.class).putExtra("what", obj));
            }
        });
        holder.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("registering..");
                progressDialog.show();
//                String pushKey = databaseReference.push().getKey();
                final UserField[] userField = new UserField[1];
                if(events.getParticipators()==null) events.setParticipators(new ArrayList<>());
                events.getParticipators().add(HomeActivity.userId);
                databaseReferenceEvents.child(events.getPushKey())
                        .setValue(events)
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                toaster.toast(e.getMessage());
                            }
                        })
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog.dismiss();
                                toaster.toast("registered successfully");
                            }
                        });
                /*databaseReferenceUser.child(HomeActivity.userId)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                userField[0] = snapshot.getValue(UserField.class);
                                Log.d(TAG, "onDataChange: user- "+userField[0].getDepartment());
                                databaseReference.child(pushKey)
                                        .setValue(userField[0])
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
                                                refresh();
                                                toaster.toast("registered successfully");
                                                progressDialog.dismiss();
                                            }
                                        });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                toaster.toast("something went wrong");
                            }
                        });*/
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                context.startActivity(new Intent(context, AdminLoginFragment.class).putStringArrayListExtra("participators", (ArrayList<String>) events.getParticipators()));
//                new HomeActivity().loadFragment(new AdminLoginFragment(context, (ArrayList<String>) events.getParticipators()));
                if(who.equals("admin")){
                    FragmentTransaction mFragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                    mFragmentTransaction.replace(R.id.frame, new AdminLoginFragment(context, (ArrayList<String>) events.getParticipators()));
                    mFragmentTransaction.addToBackStack(null).commit();
                }
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(who.equals("admin")){
                    new AlertDialog.Builder(context)
                            .setIcon(android.R.drawable.ic_delete)
                            .setMessage("Are you sure")
                            .setTitle("Delete")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(!events.getPictureUrl().equals("default"))FirebaseStorage.getInstance().getReferenceFromUrl(events.getPictureUrl()).delete();
                                    Log.d(TAG, "onClick: pushkey - "+events.getPushKey());
                                    databaseReferenceEvents.child(events.getPushKey()).removeValue()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    toaster.toast("deleted successfully");
//                                                    refresh();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    toaster.toast(e.getMessage());
                                                }
                                            });
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
                return true;
            }
        });
    }

    public void refresh(){
        this.notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public int getPosi() {
        return posi;
    }

    public void setPosi(int posi) {
        this.posi = posi;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView titleView, coordinateView, descriptionView, dateView, linkView;
        MaterialButton registerBtn;
        ImageView editBtn, eventImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventImage = itemView.findViewById(R.id.image_view);
            titleView = itemView.findViewById(R.id.title_view);
            coordinateView = itemView.findViewById(R.id.coordinate_view);
            descriptionView = itemView.findViewById(R.id.description_view);
            dateView = itemView.findViewById(R.id.date_text);
            linkView = itemView.findViewById(R.id.link_text);
            registerBtn = itemView.findViewById(R.id.register_btn);
            editBtn = itemView.findViewById(R.id.edit_icon);
            cardView = itemView.findViewById(R.id.relative_layout);
        }
    }
}
