package com.AniDeko.anidekoandroid.DataStructure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.AniDeko.anidekoandroid.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class LightAnimeItemListAdapter extends RecyclerView.Adapter<LightAnimeItemListAdapter.ViewHolder>{
    private ArrayList<LightAnime> mListArticle;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView NameAnime,Rating;
        ImageView ImagePoster;

        ViewHolder(View itemView) {
            super(itemView);
            NameAnime = itemView.findViewById(R.id.NameOfAnimeText);
            Rating = itemView.findViewById(R.id.NumberOfExtentionText);
            ImagePoster = itemView.findViewById(R.id.ImageOfAnime);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    public LightAnimeItemListAdapter(Context context, ArrayList<LightAnime> mListArticle){
        this.mInflater = LayoutInflater.from(context);
        this.mListArticle = mListArticle;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.animecard, parent, false);
        return new LightAnimeItemListAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LightAnime anime = mListArticle.get(position);
        holder.NameAnime.setText((anime.Name));
        holder.Rating.setText((anime.Rating));
        //Загрузка картинок с помощью библиотеки
        if(anime.PosterImgUrl!=null) {
            Glide.with(this.mInflater.getContext()).load(anime.PosterImgUrl).into(holder.ImagePoster);
        }
    }

    @Override
    public int getItemCount() {
        return mListArticle.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
