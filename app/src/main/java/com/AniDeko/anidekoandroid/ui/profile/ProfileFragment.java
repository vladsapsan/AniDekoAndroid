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
import com.google.android.material.transition.MaterialFadeThrough;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.auth.FirebaseUser;


public class ProfileFragment extends Fragment {

    MainActivity mainActivity;
    Button ExitFromAccButton;
    TextView UserNameTextView,UserStatusTextView;
    FirebaseUser CurrentUser;

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
        //Проверка авторизации
        if(mainActivity.UserisSign==false){
            //Открытие окна авторизации если пользователь не авторизован
            AuthFragment AuthFragment = new AuthFragment();
             getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ProfileFragmentConteiner, AuthFragment, "Auth").commit();
        }else {
            setExitTransition(new MaterialFadeThrough());
            setEnterTransition(new MaterialFadeThrough());

        }

        //Кнопка выхода из профиля
        ExitFromAccButton = view.findViewById(R.id.ExitFromAccButton);
        ExitFromAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.auth.signOut();
                mainActivity.Auth();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mainActivity.currentUser!=null){
            CurrentUser = mainActivity.currentUser;
            if(CurrentUser.getDisplayName()!=null) {
                UserNameTextView.setText(mainActivity.currentUser.getDisplayName());
            }
        }
    }
}