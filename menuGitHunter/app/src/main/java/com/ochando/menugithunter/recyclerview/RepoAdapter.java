package com.ochando.menugithunter.recyclerview;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ochando.menugithunter.R;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder>{

    private static final String TAG = RepoAdapter.class.getSimpleName();
    private int numberItems;

    public RepoAdapter(int numberItems) {
        this.numberItems = numberItems;
    }

    public int getItemCount(){
        return numberItems;
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttachToParentInmediatly = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentInmediatly);

        RepoViewHolder viewHolder = new RepoViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        Log.d(TAG, "Posición: " + position);
        holder.bind(position);
    }

    class RepoViewHolder extends RecyclerView.ViewHolder {
        TextView repoName;
        TextView repoComment;
        TextView repoStars;


        public RepoViewHolder(View itemView){
            super(itemView);
            repoName = (TextView)itemView.findViewById(R.id.repo_name);
            repoComment = (TextView) itemView.findViewById(R.id.repo_comment);
            repoStars = (TextView) itemView.findViewById(R.id.repo_starts);
        }

        void bind(int listIndex){
            repoName.setText(String.valueOf(listIndex));
        }
    }

}
