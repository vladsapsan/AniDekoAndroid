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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.AniDeko.anidekoandroid.MainActivity;
import com.AniDeko.anidekoandroid.R;
import com.AniDeko.anidekoandroid.ui.Registration.RegistrationFragment;
import com.AniDeko.anidekoandroid.ui.home.HomeFragment;
import com.AniDeko.anidekoandroid.ui.profile.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AuthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AuthFragment extends Fragment {


    MainActivity mainActivity;
    FloatingActionButton SettingsButton;
    Button AuthButton;
    TextView SkipButton,LosePasswordButton,RegistrationButton;
    CardView AuthWithGoogleButton;
    EditText AuthPasswordEditText,AuthEmailEditText;
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
        setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.Z, true));
        setReenterTransition(new MaterialSharedAxis(MaterialSharedAxis.Z, true));
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


        //инициализация компонентов
        mainActivity = (MainActivity) getActivity();
        AuthPasswordEditText = view.findViewById(R.id.AuthPasswordEditText);
        AuthEmailEditText = view.findViewById(R.id.AuthEmailEditText);


        //Кнопка аутентификации
        AuthButton = view.findViewById(R.id.AuthButton);
        AuthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Запуск фукнкции аутентификации
                mainActivity.auth.signInWithEmailAndPassword(AuthEmailEditText.getText().toString().toLowerCase(),AuthPasswordEditText.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mainActivity.Auth();
                                } else {
                                    Toast.makeText(getContext(), "Ошибка аутентификации",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        //Кнопка перехода к регистрации
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