package com.AniDeko.anidekoandroid.DataStructure;

import android.content.Context;
import android.text.Html;
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

public class UserListItemProfileAdapter  extends RecyclerView.Adapter<UserListItemProfileAdapter.ViewHolderSection>{


    private ArrayList<User> mListArticle;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public class ViewHolderSection extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        ImageView ImageProfile,VerifyedIcon;

        ViewHolderSection(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.UserName);
            ImageProfile = itemView.findViewById(R.id.ImageProfile);
            VerifyedIcon = itemView.findViewById(R.id.IsverifiedIcon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public UserListItemProfileAdapter(Context context, ArrayList<User> mListArticle){
        this.mInflater = LayoutInflater.from(context);
        this.mListArticle = mListArticle;
    }



    @NonNull
    @Override
    public ViewHolderSection onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_profile, parent, false);
        return new ViewHolderSection(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSection holder, int position) {
        holder.myTextView.setText((mListArticle.get(position).NickName).toString());
        //Загрузка картинок с помощью библиотеки
        if(mListArticle.get(position).PhotoUri!=null) {
            Glide.with(this.mInflater.getContext()).load(mListArticle.get(position).PhotoUri).into(holder.ImageProfile);
        }
        if(mListArticle.get(position).isVerifeid==true){
            holder.VerifyedIcon.setVisibility(View.VISIBLE);
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public int getItemCount() {
        return mListArticle.size();
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<User> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        mListArticle = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }
}
