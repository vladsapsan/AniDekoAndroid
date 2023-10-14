package com.AniDeko.anidekoandroid.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.AniDeko.anidekoandroid.ui.Auth.AuthFragment;
import com.AniDeko.anidekoandroid.R;
import com.google.android.material.transition.MaterialFadeThrough;


public class ProfileFragment extends Fragment {


    Boolean isSign = false;
    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(isSign==false){
            //Открытие окна авторизации если пользователь не авторизован
            AuthFragment AuthFragment = new AuthFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            if(fragmentManager.findFragmentByTag("Auth")==null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.ProfileFragmentConteiner, AuthFragment, "Auth").commit();
            }
        }
    }
}