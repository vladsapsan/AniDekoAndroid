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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.AniDeko.anidekoandroid.DataStructure.Subscribes;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.transition.MaterialFadeThrough;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class SocialProfileUserFragment extends Fragment {
    MainActivity mainActivity;
    DatabaseReference databaseReference;
    ImageView IsverifiedIcon,PhotoProfile,SeconsPhotoProfile;
    ProgressBar progressBarProfile,progressBarCoverPhoto;
    User cUserInfo;
    LinearLayout SubscribeIcon;
    String UserID;
    ProgressBar progressBar,progressBarSubscribe;
    CardView CardProfileNameInfo,SubscribeButton,UnSubscribeButton;
    Bundle UserIDBunlde;
    TextView UserNameTextView,UserStatusTextView,UserSubsNumber,UserYourSubsNumber;
    FloatingActionButton BackToButton,RateButton,MenuButton;
    public SocialProfileUserFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setExitTransition(new MaterialFadeThrough());
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
            if (cUserInfo.isVerifeid) {
                IsverifiedIcon.setVisibility(View.VISIBLE);
            }
            if(!Objects.equals(cUserInfo.userStatus, "")){
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
         //   if(cUserInfo.SubList!=null){
          //      UserSubsNumber.setText(String.valueOf(cUserInfo.SubList.size()));
         //   }
         //   if(cUserInfo.MySubsList!=null){
          //      UserYourSubsNumber.setText(String.valueOf(cUserInfo.MySubsList.size()));
         //   }
        }
    }

    //Получение данных пользователя
    private void LoadUserInfo(String UserID){
        progressBarProfile.setVisibility(View.VISIBLE);
        progressBarCoverPhoto.setVisibility(View.VISIBLE);
        //Инициализируем бд
        mainActivity.DataBaseInit();
        mainActivity.mDatabase.child(MainActivity.Users_Child).child(UserID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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


  //  private void isSubscribe(){
     //   if(mainActivity.cUserInfo.SubList!=null) {
     //       for (Subscribes sub : mainActivity.cUserInfo.SubList) {
      //          if (sub.SubsribeID.equals(UserID)) {
     //               SubscribeButton.setVisibility(View.GONE);
    //                UnSubscribeButton.setVisibility(View.VISIBLE);
    //                break;
    //            }
   //         }
    //    }
  //  }
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
        UserSubsNumber = view.findViewById(R.id.UserSubsNumber);
        SeconsPhotoProfile = view.findViewById(R.id.SeconsPhotoProfile);
        progressBarSubscribe = view.findViewById(R.id.progressBarSubscribe);
        SubscribeIcon = view.findViewById(R.id.SubscribeIcon);
        UserYourSubsNumber = view.findViewById(R.id.UserYourSubsNumber);
        GetBundleLoadInfoByID();



        //Диалог снизу
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.ModalBottomSheetDialog);
        //Оценка профиля
        View RateProfileDialog = LayoutInflater.from(getContext())
                .inflate(
                        R.layout.bottom_sheet_dialog_rate_profile,
                        (FrameLayout) view.findViewById(R.id.SheetDialogRateProfileContainer)
                );
        //Диалог отписки
        View UnSubscribeProfileDialog = LayoutInflater.from(getContext())
                .inflate(
                        R.layout.bottom_sheet_dialog_unsubscribe,
                        (FrameLayout) view.findViewById(R.id.SheetDialogUnsubscribeProfileContainer)
                );
        UnSubscribeProfileDialog.findViewById(R.id.ExitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        UnSubscribeProfileDialog.findViewById(R.id.UnsubscribeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        //Кнопка подписаться
        SubscribeButton = view.findViewById(R.id.SubscribeButton);
        SubscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubscribeButton.setEnabled(false);
                progressBarSubscribe.setVisibility(View.VISIBLE);
                SubscribeIcon.setVisibility(View.GONE);
                databaseReference = mainActivity.mDatabase;
                Date currentTime = Calendar.getInstance().getTime();
                if (mainActivity.cUserInfo != null) {
                    //Добавляем инфу  о пользователе в бд
                    databaseReference.child(MainActivity.Users_Child)
                            .child(mainActivity.currentUser.getUid()).child(MainActivity.Users_SubList).push().setValue(new Subscribes(UserID,currentTime.toString()))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //Добавляем в инфу в подписчики канала
                                        databaseReference.child(MainActivity.Users_Child).child(UserID)
                                                .child(MainActivity.Users_MySubsList)
                                                .push().setValue(new Subscribes(mainActivity.currentUser.getUid(),currentTime.toString())).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){

                                                        }else {
                                                            Toast.makeText(getContext(), "Ошибка загрузки", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                        Toast.makeText(getContext(), "Вы подписались", Toast.LENGTH_SHORT).show();
                                       // isSubscribe();
                                        mainActivity.UpdateUserProfile();
                                    } else {
                                        Toast.makeText(getContext(), "Что-то пошло не так, попробуйте еще раз", Toast.LENGTH_SHORT).show();
                                        SubscribeButton.setEnabled(true);
                                    }
                                }
                            });
                }else {

                }
            }
        });

        //Кнопка отписки
        UnSubscribeButton = view.findViewById(R.id.UnSubscribeButton);
        UnSubscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar = UnSubscribeProfileDialog.findViewById(R.id.progressBar);
                bottomSheetDialog.setContentView(UnSubscribeProfileDialog);
                bottomSheetDialog.show();
            }
        });

        //Кнопка оценки аккаунта
        RateButton = view.findViewById(R.id.RateButton);
        RateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.setContentView(RateProfileDialog);
                bottomSheetDialog.show();
            }
        });

        MenuButton = view.findViewById(R.id.MenuButton);
        MenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        BackToButton = view.findViewById(R.id.BackToButton);
        BackToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}