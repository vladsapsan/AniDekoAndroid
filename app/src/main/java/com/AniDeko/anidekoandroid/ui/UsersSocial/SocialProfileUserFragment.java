package com.AniDeko.anidekoandroid.ui.UsersSocial;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.AniDeko.anidekoandroid.DataStructure.User;
import com.AniDeko.anidekoandroid.MainActivity;
import com.AniDeko.anidekoandroid.R;
import com.AniDeko.anidekoandroid.ui.profile.ProfileFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;

public class SocialProfileUserFragment extends Fragment {



    MainActivity mainActivity;
    ImageView IsverifiedIcon,PhotoProfile,SeconsPhotoProfile;
    ProgressBar progressBarProfile,progressBarCoverPhoto;
    User cUserInfo;
    String UserID;
    CardView CardProfileNameInfo;
    Bundle UserIDBunlde;
    TextView UserNameTextView,UserStatusTextView;
    FloatingActionButton BackToButton;
    public SocialProfileUserFragment() {
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
        return inflater.inflate(R.layout.fragment_social_profile_user, container, false);


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
        }
    }

    //Получение данных пользователя
    private void LoadUserInfo(String UserID){
        progressBarProfile.setVisibility(View.VISIBLE);
        progressBarCoverPhoto.setVisibility(View.VISIBLE);
        //Инициализируем бд
        mainActivity.DataBaseInit();
        mainActivity.mDatabase.child(mainActivity.Users_Child).child(UserID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    //Данные аккаунта успешно получены
                    cUserInfo = task.getResult().getValue(User.class);
                    LoadProfile();
                }else {
                    Toast.makeText(getActivity(), "Ошибка загрузки профиля", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Получение информации о пользователе из фрагмента профиля
    private void GetBundleLoadInfoByID(){
        UserIDBunlde = getArguments();
        UserID = UserIDBunlde.getString(SeacrhUsersFragment.Bunlde_UserID_Tag);
        if(UserID.length()>0) {
            LoadUserInfo(UserID);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivity = (MainActivity) getActivity();
        UserNameTextView = view.findViewById(R.id.UserNameTextView);
        IsverifiedIcon = view.findViewById(R.id.IsverifiedIcon);
        UserStatusTextView = view.findViewById(R.id.UserStatusTextView);
        progressBarProfile = view.findViewById(R.id.progressBarProfile);
        progressBarCoverPhoto = view.findViewById(R.id.progressBarCoverPhoto);
        PhotoProfile = view.findViewById(R.id.PhotoProfile);
        SeconsPhotoProfile = view.findViewById(R.id.SeconsPhotoProfile);

        GetBundleLoadInfoByID();

        BackToButton = view.findViewById(R.id.BackToButton);
        BackToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}