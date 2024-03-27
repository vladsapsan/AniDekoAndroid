package com.AniDeko.anidekoandroid.ui.home;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.AniDeko.anidekoandroid.R;
import com.AniDeko.anidekoandroid.databinding.FragmentHomeBinding;
import com.google.android.material.transition.MaterialFade;
import com.google.android.material.transition.MaterialFadeThrough;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private VideoView MainAnimeVideoView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // код для настройки представления фрагмента

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Видео трейлер
        MainAnimeVideoView = view.findViewById(R.id.MainAnimeVideoView);
        try {
            // Устанавливаем URL видео и начинаем его воспроизведение
            Uri videoUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/anidekoapp.appspot.com/o/SPY%20x%20FAMILY%20CODE_%20WHITE%20-%20Official%20Trailer%20(HD).mp4?alt=media&token=ca91b8fd-611f-435f-b605-0c207e96aff8");
            MainAnimeVideoView.setVideoURI(videoUri);
            MainAnimeVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    MainAnimeVideoView.requestFocus();
                    MainAnimeVideoView.start();
                    float videoRatio = mp.getVideoWidth() / (float) mp.getVideoHeight();
                    float screenRatio = MainAnimeVideoView.getWidth() / (float)
                            MainAnimeVideoView.getHeight();
                    float scaleX = videoRatio / screenRatio;
                    if (scaleX >= 1f) {
                        MainAnimeVideoView.setScaleX(scaleX);
                    } else {
                        MainAnimeVideoView.setScaleY(1f / scaleX);
                    }
                }
            });

        } catch (Exception e) {
            Toast.makeText(getContext(), "Ошибка при загрузке и воспроизведении видео", Toast.LENGTH_SHORT).show();
        }
        MainAnimeVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}