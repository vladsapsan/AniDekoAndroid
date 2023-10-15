package com.AniDeko.anidekoandroid.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.AniDeko.anidekoandroid.MainActivity;
import com.AniDeko.anidekoandroid.ui.Auth.AuthFragment;
import com.AniDeko.anidekoandroid.R;
import com.AniDeko.anidekoandroid.ui.Settings.SettingsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transition.MaterialFadeThrough;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.auth.FirebaseUser;


public class ProfileFragment extends Fragment {

    MainActivity mainActivity;
    TextView UserNameTextView,UserStatusTextView;
    FirebaseUser CurrentUser;
    FloatingActionButton SettingsButton;
    SettingsFragment settingsFragment;

    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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



        //Получаем данные из Mainactivity
        mainActivity = (MainActivity) getActivity();
        UserNameTextView = view.findViewById(R.id.UserNameTextView);


        if(mainActivity.currentUser==null){
            mainActivity.Auth();
        }else{
            CurrentUser = mainActivity.currentUser;
            setExitTransition(new MaterialFadeThrough());
            setEnterTransition(new MaterialFadeThrough());
            setReenterTransition(new MaterialSharedAxis(MaterialSharedAxis.Z, true));
            if(CurrentUser.getDisplayName()!=null) {
                UserNameTextView.setText(mainActivity.currentUser.getDisplayName());
            }
        }



        //Кнопка настроек
        SettingsButton = view.findViewById(R.id.SettingsButton);
        SettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsFragment = new SettingsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ProfileFragmentConteiner, settingsFragment, "Settings").addToBackStack("SettingsBack").commit();
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
    }
}