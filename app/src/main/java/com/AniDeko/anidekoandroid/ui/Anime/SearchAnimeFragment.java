package com.AniDeko.anidekoandroid.ui.Anime;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.AniDeko.anidekoandroid.R;
import com.google.android.material.tabs.TabLayout;


public class SearchAnimeFragment extends Fragment {


    TabLayout AnimeTab;
    ViewPager2 SearchAnimeViewPage;
    AnimeViewPagerAdapter animeViewPagerAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_anime, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AnimeTab = view.findViewById(R.id.AnimeTab);
        SearchAnimeViewPage = view.findViewById(R.id.SearchAnimeViewPage);
        animeViewPagerAdapter = new AnimeViewPagerAdapter(getActivity());
        SearchAnimeViewPage.setAdapter(animeViewPagerAdapter);
        AnimeTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                SearchAnimeViewPage.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        SearchAnimeViewPage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                AnimeTab.getTabAt(position).select();
            }
        });
    }
}