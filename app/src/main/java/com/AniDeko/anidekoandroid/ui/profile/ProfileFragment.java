package com.AniDeko.anidekoandroid.ui.profile;

import android.content.ContentResolver;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
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
import com.AniDeko.anidekoandroid.ui.UsersSocial.SeacrhUsersFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transition.MaterialContainerTransform;
import com.google.android.material.transition.MaterialFadeThrough;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;


import java.time.Duration;

import kotlin.Pair;


public class ProfileFragment extends Fragment {

    MainActivity mainActivity;
    ImageView IsverifiedIcon,PhotoProfile,SeconsPhotoProfile;
    CardView CardProfileNameInfo;
    TextView UserNameTextView,UserStatusTextView,UserSubsNumber;
    public final static String Bunlde_UserInfo_Tag = "UserInfo";
    public User cUserInfo;
    Bundle UserInfoBundle;
    ProgressBar progressBarProfile,progressBarCoverPhoto;
    FloatingActionButton SettingsButton,SearchButton;
    SettingsFragment settingsFragment;
    SeacrhUsersFragment searchUsersFragment;

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
            if(cUserInfo.PhotoUri.length()>0){
                Glide.with(getActivity()).load(cUserInfo.PhotoUri).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(@NonNull Drawable resource, @NonNull Object model, Target<Drawable> target, @NonNull DataSource dataSource, boolean isFirstResource) {
                        progressBarProfile.setVisibility(View.GONE);
                        return false;
                    }
                }).into(PhotoProfile);
            }else {
                progressBarProfile.setVisibility(View.GONE);
            }
            if(cUserInfo.SecondPhotoUri.length()>0){
                Glide.with(getActivity()).load(cUserInfo.SecondPhotoUri).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(@NonNull Drawable resource, @NonNull Object model, Target<Drawable> target, @NonNull DataSource dataSource, boolean isFirstResource) {
                        progressBarCoverPhoto.setVisibility(View.GONE);
                        return false;
                    }
                }).into(SeconsPhotoProfile);
            }else {
                progressBarCoverPhoto.setVisibility(View.GONE);
            }
            if(cUserInfo.SubList!=null){
                UserSubsNumber.setText(String.valueOf(cUserInfo.SubList.size()));
            }
            SettingsButton.show();
            SearchButton.show();
        }
    }




    //Получение данных пользователя
    public void LoadMyUserInfo(){
        progressBarProfile.setVisibility(View.VISIBLE);
        progressBarCoverPhoto.setVisibility(View.VISIBLE);
        //Инициализируем бд
        mainActivity.DataBaseInit();
        mainActivity.mDatabase.child(mainActivity.Users_Child).child(mainActivity.currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    //Данные аккаунта успешно получены
                    cUserInfo = task.getResult().getValue(User.class);
                    mainActivity.cUserInfo = cUserInfo;
                    LoadProfile();
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
    private void SearchFragmentInit(){
        searchUsersFragment = new SeacrhUsersFragment();
        searchUsersFragment.setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.Z, true));
        searchUsersFragment.setReturnTransition(new MaterialSharedAxis(MaterialSharedAxis.Z, true));
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
        progressBarCoverPhoto = view.findViewById(R.id.progressBarCoverPhoto);
        PhotoProfile = view.findViewById(R.id.PhotoProfile);
        UserSubsNumber = view.findViewById(R.id.UserSubsNumber);
        SeconsPhotoProfile = view.findViewById(R.id.SeconsPhotoProfile);
        SearchButton = view.findViewById(R.id.SearchButton);

        if(mainActivity.currentUser==null){
            mainActivity.Auth();
        }else{
            setExitTransition(new MaterialFadeThrough());
            setEnterTransition(new MaterialFadeThrough());
            setReenterTransition(new MaterialSharedAxis(MaterialSharedAxis.Z, true));
            LoadMyUserInfo();
            SearchFragmentInit();
        }


        //Кнопка настроек
        SettingsButton = view.findViewById(R.id.SettingsButton);
        SettingsButton.hide();
        SettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cUserInfo!=null) {
                    settingsFragment = new SettingsFragment();
                    SetIntelizationBundle(settingsFragment);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ProfileFragmentConteiner, settingsFragment, "Settings").addToBackStack(null).commit();
                }
            }
        });

        SearchButton.hide();
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ProfileFragmentConteiner, searchUsersFragment, "SearchUsers").addToBackStack(null).commit();
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