package com.example.MovieCatalogue.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.MovieCatalogue.Activity.TvShowDetailActivity;
import com.example.MovieCatalogue.Model.TvShowItems;
import com.example.MovieCatalogue.R;

import java.util.ArrayList;

public class TvShowFavoriteListAdapter extends RecyclerView.Adapter<TvShowFavoriteListAdapter.ViewHolder> {
    private ArrayList<TvShowItems> listTvShow;

    public TvShowFavoriteListAdapter(ArrayList<TvShowItems> list) {
        this.listTvShow = list;
    }

    public void setData(ArrayList<TvShowItems> items) {
        listTvShow.clear();

        ArrayList<TvShowItems> tvShowItems = new ArrayList<>();

        for (int i=0; i<items.size(); i++){

            if (items.get(i).getFavorite()){

                tvShowItems.add(items.get(i));

            }
        }

        if (tvShowItems!=null){

            listTvShow.addAll(tvShowItems);

            notifyDataSetChanged();

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv_show, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final TvShowItems tvShowItems = listTvShow.get(position);

        //Binding title dan vote

        if (tvShowItems.getFavorite()){
            holder.imgFavorite.setVisibility(View.VISIBLE);
        } else {
            holder.imgFavorite.setVisibility(View.INVISIBLE);
        }

        holder.tvTitle.setText(tvShowItems.getTitle()+" ("+ tvShowItems.getRelease().substring(0,4)+")");
        holder.tvVote.setText(tvShowItems.getVote());
        final int id = tvShowItems.getId();

        //Load Image Posternya
        Glide.with(holder.itemView.getContext())
                .load("http://image.tmdb.org/t/p/w154"+ tvShowItems.getPoster())
                .apply(new RequestOptions().override(480, 720))
                .into(holder.imgTvShow);

        //Bikin Listener CLick di Image posternya
        holder.imgTvShow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putInt(TvShowDetailActivity.EXTRA_NAME, id);

                Intent intent = new Intent(holder.itemView.getContext(), TvShowDetailActivity.class);
                intent.putExtras(bundle);
                holder.itemView.getContext().startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return listTvShow.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgTvShow,imgFavorite;
        TextView tvTitle, tvVote;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTvShow = itemView.findViewById(R.id.img_tv_show);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvVote = itemView.findViewById(R.id.tv_vote);
            imgFavorite = itemView.findViewById(R.id.img_favorite);
        }
    }
}
