package com.codekiller.ehorizon.Utils;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.codekiller.ehorizon.R;
import com.codekiller.ehorizon.UserDetails;
import com.google.gson.Gson;

import java.util.List;

public class RegisterUserAdapter extends RecyclerView.Adapter<RegisterUserAdapter.ViewHolder> {
    List<UserField> list;
    Context context;
//    String who;

    public RegisterUserAdapter(List<UserField> list, Context context) {
        this.list = list;
        this.context = context;
//        this.who = who;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.register_items, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserField userField = list.get(position);
        holder.nameView.setText(userField.getName());
        holder.deptView.setText("- "+userField.getDepartment()+" '"+userField.getSection()+"'");
        holder.eventNameView.setText("Register in ");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, UserDetails.class).putExtra("user obj", new Gson().toJson(userField)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView nameView, deptView, eventNameView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.layout_view);
            nameView = itemView.findViewById(R.id.name_text);
            deptView = itemView.findViewById(R.id.dept_text);
            eventNameView = itemView.findViewById(R.id.event_name_text);
        }
    }
}
