package com.hackthon.devfinder.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hackthon.devfinder.Activities.RepositoryDetailsActivity;
import com.hackthon.devfinder.Models.CommitDetails;
import com.hackthon.devfinder.Models.RepositoryMod;
import com.hackthon.devfinder.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CommitItemAdapter extends RecyclerView.Adapter<CommitItemAdapter.CommitHolder> {

    private List<CommitDetails> list;
    private RecyclerView recyclerView;
    private Context context;

    public CommitItemAdapter(List<CommitDetails> list, RecyclerView recyclerView, Context context) {
        this.list = list;
        this.recyclerView = recyclerView;
        this.context = context;
    }

    @NonNull
    @Override
    public CommitItemAdapter.CommitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommitHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.commit_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommitItemAdapter.CommitHolder holder, int position) {

        holder.setDetails(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    static class CommitHolder extends RecyclerView.ViewHolder {

        TextView devName, message;

        public CommitHolder(@NonNull View itemView) {
            super(itemView);

            devName = itemView.findViewById(R.id.devNameCommit);
            message = itemView.findViewById(R.id.messageCommit);

        }

        protected void setDetails(CommitDetails model){

            devName.setText(model.getCommitter());
            message.setText(model.getCommitMessage());

        }

    }


}