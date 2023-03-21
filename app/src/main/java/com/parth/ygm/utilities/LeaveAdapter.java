package com.parth.ygm.utilities;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parth.ygm.R;
import com.parth.ygm.models.EmployeeData;

import java.util.List;
import java.util.Objects;

public class LeaveAdapter extends RecyclerView.Adapter<LeaveAdapter.holder> {

    List<EmployeeData> data;

    public LeaveAdapter(List<EmployeeData> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.leave_card_layout, parent, false);
        return new holder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.fullName.setText(data.get(position).getFullName());
        holder.date.setText(data.get(position).getCreatedAt());
        holder.fromDate.setText(data.get(position).getFromDate());
        holder.toDate.setText(data.get(position).getToDate());
        holder.leaveReason.setText(data.get(position).getLeaveReason());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class holder extends RecyclerView.ViewHolder {

        TextView fullName, date, fromDate, toDate, leaveReason;

        public holder(@NonNull View itemView) {
            super(itemView);
            fullName = (TextView) itemView.findViewById(R.id.name);
            date = (TextView) itemView.findViewById(R.id.date);
            fromDate = (TextView) itemView.findViewById(R.id.leaveFromDate);
            toDate = (TextView) itemView.findViewById(R.id.leaveToDate);
            leaveReason = (TextView) itemView.findViewById(R.id.leaveReason);
        }
    }
}
