package com.AniDeko.anidekoandroid.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.AniDeko.anidekoandroid.DataStructure.User;
import com.AniDeko.anidekoandroid.MainActivity;
import com.AniDeko.anidekoandroid.ui.Auth.AuthFragment;
import com.AniDeko.anidekoandroid.R;
import com.AniDeko.anidekoandroid.ui.Settings.SettingsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transition.MaterialFadeThrough;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;


import java.time.Duration;

import kotlin.Pair;


public class ProfileFragment extends Fragment {

    MainActivity mainActivity;
    ImageView IsverifiedIcon;
    CardView CardProfileNameInfo;
    TextView UserNameTextView,UserStatusTextView;
    public final static String Bunlde_UserInfo_Tag = "UserInfo";
    User cUserInfo;
    Bundle UserInfoBundle;
    ProgressBar progressBarProfile;
    FloatingActionButton SettingsButton;
    SettingsFragment settingsFragment;

    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    private void LoadProfile(){
        if(cUserInfo!=null) {
            if (cUserInfo.NickName != null) {
                UserNameTextView.setText(cUserInfo.NickName);
            }
            if (cUserInfo.isVerifeid == true) {
                IsverifiedIcon.setVisibility(View.VISIBLE);
            }
            if(cUserInfo.userStatus!=""){
                UserStatusTextView.setText(cUserInfo.userStatus);
            }
        }
    }

    //Получение данных пользователя
    public void LoadUserInfo(){
        progressBarProfile.setVisibility(View.VISIBLE);

        //Инициализируем бд
        mainActivity.DataBaseInit();
        mainActivity.mDatabase.child(mainActivity.Users_Child).child(mainActivity.currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    //Данные аккаунта успешно получены
                    cUserInfo = task.getResult().getValue(User.class);
                    LoadProfile();
                    progressBarProfile.setVisibility(View.GONE);
                }else {
                    Toast.makeText(getActivity(), "Ошибка загрузки профиля", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void SetIntelizationBundle(SettingsFragment fragment){
        UserInfoBundle = new Bundle();
        UserInfoBundle.putSerializable(Bunlde_UserInfo_Tag,cUserInfo);
        fragment.setArguments(UserInfoBundle);
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
        IsverifiedIcon = view.findViewById(R.id.IsverifiedIcon);
        UserStatusTextView = view.findViewById(R.id.UserStatusTextView);
        progressBarProfile = view.findViewById(R.id.progressBarProfile);


        if(mainActivity.currentUser==null){
            mainActivity.Auth();
        }else{
            setExitTransition(new MaterialFadeThrough());
            setEnterTransition(new MaterialFadeThrough());
            setReenterTransition(new MaterialSharedAxis(MaterialSharedAxis.Z, true));
            LoadUserInfo();
        }


        //Кнопка настроек
        SettingsButton = view.findViewById(R.id.SettingsButton);
        SettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsFragment = new SettingsFragment();
                SetIntelizationBundle(settingsFragment);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ProfileFragmentConteiner, settingsFragment, "Settings").addToBackStack("SettingsBack").commit();
            }
        });

        CardProfileNameInfo = view.findViewById(R.id.CardProfileNameInfo);
        CardProfileNameInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(IsverifiedIcon.getVisibility()==View.VISIBLE){
                    Toast.makeText(getContext(),"Данный аккаунт верифицирован",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
    }
}