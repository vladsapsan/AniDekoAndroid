package com.AniDeko.anidekoandroid.ui.Auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.AniDeko.anidekoandroid.MainActivity;
import com.AniDeko.anidekoandroid.R;
import com.AniDeko.anidekoandroid.ui.Registration.RegistrationFragment;
import com.AniDeko.anidekoandroid.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AuthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AuthFragment extends Fragment {


    FloatingActionButton SettingsButton;
    Button AuthButton;
    TextView SkipButton,LosePasswordButton,RegistrationButton;
    CardView AuthWithGoogleButton;
    public AuthFragment() {
        // Required empty public constructor
    }


    public static AuthFragment newInstance(String param1, String param2) {
        AuthFragment fragment = new AuthFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getActivity().getWindow().setStatusBarColor(getActivity().getResources().getColor(R.color.MainBlack));



        RegistrationButton = view.findViewById(R.id.RegistrationButton);
        RegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Переход по навигации в фграмент школа
                RegistrationFragment RegistrationFragment = new RegistrationFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.AuthFragmentContainer, RegistrationFragment, "registration").addToBackStack(null).commit();
            }
        });

        //Инициализация
        SkipButton = view.findViewById(R.id.SkipButton);
        SkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Кнопка пропуска авторизации и перехода в главное меню
                HomeFragment homeFragment;
                MainActivity mainActivity = (MainActivity) getActivity();
                if(getActivity().getSupportFragmentManager().findFragmentByTag("1")!=null) {
                    homeFragment = (HomeFragment) getActivity().getSupportFragmentManager().findFragmentByTag("1");
                }else {
                    homeFragment = (HomeFragment) mainActivity.getFragment(1);
                }
                mainActivity.setFragment(homeFragment,"1",1);
            }
        });
    }
}