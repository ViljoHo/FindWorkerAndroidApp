package com.findworker.findworker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecyleViewAdapter extends RecyclerView.Adapter<RecyleViewAdapter.MyViewHolder> {

    List<JSONObject> resultsList;
    Context context;


    public RecyleViewAdapter(List<JSONObject> resultsList, Context context) {
        this.resultsList = resultsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            holder.tv_profName.setText(resultsList.get(position).getString("by_user"));
            holder.tv_info.setText(resultsList.get(position).getString("additionalInfo"));
            holder.tv_rating.setText(resultsList.get(position).getString("askPrice"));
            //Glide
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profPic;
        TextView tv_profName;
        TextView tv_info;
        TextView tv_rating;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_profPic = itemView.findViewById(R.id.iv_profilePicture);
            tv_profName = itemView.findViewById(R.id.tv_profileName);
            tv_info = itemView.findViewById(R.id.tv_infoText);
            tv_rating = itemView.findViewById(R.id.tv_rating);

        }
    }
}
