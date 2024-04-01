package com.AniDeko.anidekoandroid.ui.Anime;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.AniDeko.anidekoandroid.MainActivity;
import com.AniDeko.anidekoandroid.R;
import com.facebook.shimmer.ShimmerFrameLayout;


public class AnimeFragment extends Fragment {


    CardView CommentsCard,FullAnimeTextCard;
    ImageView ArrowImageFullAnimeTextCard;
    TextView AnimeTextAbout;
    ShimmerFrameLayout container;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anime, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        container = view.findViewById(R.id.shimmer_view_container);
        container.stopShimmer();

        //Описание аниме и кнопка его раскрытия
        AnimeTextAbout = view.findViewById(R.id.AnimeTextAbout);
        ArrowImageFullAnimeTextCard = view.findViewById(R.id.ArrowImageFullAnimeTextCard);
        FullAnimeTextCard = view.findViewById(R.id.FullAnimeTextCard);
        FullAnimeTextCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AnimeTextAbout.getMaxLines()==3) {
                    container.stopShimmer();
                    AnimeTextAbout.setMaxLines(100);
                    ArrowImageFullAnimeTextCard.setRotationX(180);
                }else {
                    AnimeTextAbout.setMaxLines(3);
                    ArrowImageFullAnimeTextCard.setRotationX(0);
                }
            }
        });

        //Кнопка комментариев
        CommentsCard = view.findViewById(R.id.CommentsCard);
        CommentsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).navController.navigate(R.id.action_animeFragment_to_commentsFragment);
            }
        });

    }
}