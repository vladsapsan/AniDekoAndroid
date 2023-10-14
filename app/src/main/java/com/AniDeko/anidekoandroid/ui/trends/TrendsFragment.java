package com.AniDeko.anidekoandroid.ui.trends;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.AniDeko.anidekoandroid.R;
import com.google.android.material.transition.MaterialFadeThrough;


public class TrendsFragment extends Fragment {



    public TrendsFragment() {
        // Required empty public constructor
    }


    public static TrendsFragment newInstance(String param1, String param2) {
        TrendsFragment fragment = new TrendsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setExitTransition(new MaterialFadeThrough());
        setEnterTransition(new MaterialFadeThrough());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trends, container, false);
    }
}