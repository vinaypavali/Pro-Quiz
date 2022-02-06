package com.quiz.proquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quiz.proquiz.Adapters.CategoryAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LeaderboardsAdapter extends RecyclerView.Adapter<LeaderboardsAdapter.LeaderboardViewHolder>{
    Context context;
    ArrayList<Users> users;
    public LeaderboardsAdapter(Context context, ArrayList<Users> users){
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_leaderboards,parent,false);
        return new LeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {
        Users user = users.get(position);
        holder.name.setText(user.getUserName());
        holder.coins.setText(String.valueOf(user.getCoins()));
        holder.rank.setText(String.valueOf(users.size()-position));
        Picasso.get().load(user.getProfilepic()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class LeaderboardViewHolder extends RecyclerView.ViewHolder{
        private TextView rank;
        private ImageView imageView;
        private TextView name;
        private TextView coins;

        public LeaderboardViewHolder(@NonNull View itemView) {
            super(itemView);

            rank = itemView.findViewById(R.id.index);
            imageView =  itemView.findViewById(R.id.imageView7);
            name = itemView.findViewById(R.id.name);
            coins = itemView.findViewById(R.id.coins);

        }
    }
}
