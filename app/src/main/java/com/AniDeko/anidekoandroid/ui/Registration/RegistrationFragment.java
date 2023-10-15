package com.AniDeko.anidekoandroid.ui.Registration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.AniDeko.anidekoandroid.DataStructure.User;
import com.AniDeko.anidekoandroid.MainActivity;
import com.AniDeko.anidekoandroid.R;
import com.AniDeko.anidekoandroid.ui.Auth.AuthFragment;
import com.AniDeko.anidekoandroid.ui.profile.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;

import org.w3c.dom.Text;

import java.util.regex.Pattern;


public class RegistrationFragment extends Fragment {

    ImageView BackToAuthButton;
    CardView finishRegistrationButton;
    MainActivity mainActivity;
    EditText PasswordRegistrationEditText,EmailRegistrationEditText,NickNameRegistrationEditText;
    ProgressBar progressBarRegistration;

    //Запиьс данных
    public void writeNewUser(String name, String email) {
        //Анимация
        progressBarRegistration.setVisibility(View.VISIBLE);
        //Инициализация данных
        User user = new User(email, name,"","","",false,false);
        mainActivity.DataBaseInit();
        //Запрос создания данных о пользователе
        mainActivity.auth.createUserWithEmailAndPassword(EmailRegistrationEditText.getText().toString(), PasswordRegistrationEditText.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Добавляем данные о пользователе в базу
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(NickNameRegistrationEditText.getText().toString())
                                    .build();
                            mainActivity.auth.getCurrentUser().updateProfile(profileUpdates);
                            //Запрос на добавление данных в бд
                            mainActivity.mDatabase.child(MainActivity.Users_Child).child(mainActivity.auth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        //Все прошло успешно, загружаем профиль
                                        mainActivity.Auth();
                                    }else {
                                        Toast.makeText(getContext(), "Ошибка загрузки данных...", Toast.LENGTH_SHORT).show();
                                        progressBarRegistration.setVisibility(View.GONE);
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "Что-то пошло не так при создании аккаунта...", Toast.LENGTH_SHORT).show();
                            progressBarRegistration.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public RegistrationFragment() {
        // Required empty public constructor
    }


    public static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9\\!\\@\\#\\$]{8,24}");

        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }

    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.Z, true));
        setReturnTransition(new MaterialSharedAxis(MaterialSharedAxis.Z, true));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivity = (MainActivity) getActivity();
        PasswordRegistrationEditText = view.findViewById(R.id.PasswordRegistrationEditText);
        EmailRegistrationEditText = view.findViewById(R.id.EmailRegistrationEditText);
        NickNameRegistrationEditText = view.findViewById(R.id.NickNameRegistrationEditText);
        progressBarRegistration = view.findViewById(R.id.progressBarRegistration);



        //Кнопка регистрации
        finishRegistrationButton = view.findViewById(R.id.finishRegistrationButton);
        finishRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(EmailRegistrationEditText.getText().length()!=0) {
                    writeNewUser(EmailRegistrationEditText.getText().toString().toLowerCase(),NickNameRegistrationEditText.getText().toString());
                }
            }
        });

        //Кнопка закрытия фрагмента??? не работает
        BackToAuthButton = view.findViewById(R.id.BackToAuthButton);
        BackToAuthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.getSupportFragmentManager().popBackStack();
            }
        });
    }
}