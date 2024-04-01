package com.AniDeko.anidekoandroid.ui.Anime;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.AniDeko.anidekoandroid.ui.Comments.CommentsFragment;

public class AnimeViewPagerAdapter  extends FragmentStateAdapter {


    public AnimeViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new AnimeFragment();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
