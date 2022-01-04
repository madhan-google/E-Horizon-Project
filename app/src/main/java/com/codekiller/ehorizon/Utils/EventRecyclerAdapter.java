package com.codekiller.ehorizon.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codekiller.ehorizon.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.ViewHolder> {
    List<Events> list;
    Context context;
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
            holder.linkView.setText(events.getFormLink());
        }
        if(events.isRegisterNeed()) holder.registerBtn.setVisibility(View.VISIBLE);
        holder.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
