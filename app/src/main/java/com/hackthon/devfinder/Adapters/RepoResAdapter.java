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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.bumptech.glide.Glide;
import com.hackthon.devfinder.Activities.RepositoryDetailsActivity;
import com.hackthon.devfinder.Models.RepositoryMod;
import com.hackthon.devfinder.R;

public class RepoResAdapter extends RecyclerView.Adapter<RepoResAdapter.RepoResHolder> {

    private List<RepositoryMod> list;
    private RecyclerView recyclerView;
    private Context context;

    public RepoResAdapter(List<RepositoryMod> list, RecyclerView recyclerView, Context context) {
        this.list = list;
        this.recyclerView = recyclerView;
        this.context = context;
    }

    @NonNull
    @Override
    public RepoResHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RepoResHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RepoResHolder holder, int position) {

        holder.setDetails(list.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(context, RepositoryDetailsActivity.class);
                RepositoryMod model = list.get(holder.getAdapterPosition());
                i.putExtra("devName",model.getDevName());
                i.putExtra("devAvatar",model.getDevAvatar());
                i.putExtra("commitLink",model.getCommits_url());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class RepoResHolder extends RecyclerView.ViewHolder {

        ImageView devAvatar;
        TextView devName;
        TextView repoName;
        TextView description;
        TextView starred;
        TextView language;
        TextView watchers;

        public RepoResHolder(@NonNull View itemView) {
            super(itemView);

            devName = itemView.findViewById(R.id.devName);
            devAvatar = itemView.findViewById(R.id.profile_image);
            repoName = itemView.findViewById(R.id.repoName);
            starred = itemView.findViewById(R.id.starred);
            description = itemView.findViewById(R.id.projDescription);
            language = itemView.findViewById(R.id.language);
            watchers = itemView.findViewById(R.id.watchers);

        }

        protected void setDetails(RepositoryMod model){
            devName.setText(model.getDevName());
            if (model.getDevAvatar() != null) {
                try {
                    Glide.with(devAvatar.getContext())
                            .load(new URL(model.getDevAvatar()))
                            .into(devAvatar);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else {
                Glide.with(devAvatar.getContext()).load(R.drawable.user).into(devAvatar);
            }

            repoName.setText(model.getRepoName());
            starred.setText(model.getStarredCount()+"");
            description.setText(model.getDescription());
            language.setText(model.getLanguage());
            watchers.setText(model.getWatchersCount()+"");

        }

    }


}