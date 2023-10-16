package com.AniDeko.anidekoandroid.ui.Settings;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.AniDeko.anidekoandroid.DataStructure.User;
import com.AniDeko.anidekoandroid.MainActivity;
import com.AniDeko.anidekoandroid.R;
import com.AniDeko.anidekoandroid.ui.profile.ProfileFragment;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;


public class SettingsFragment extends Fragment {


    private static final int SELECT_PICTURE = 1;
    ProgressBar progressBar;
    Button ExitFromAccButton;
    MainActivity mainActivity;
    EditText EditTextPerson;
    Bundle UserInfoBunlde;
    ImageView UserPhoto;
    User cUserInfo;
    BottomSheetDialog bottomSheetDialog;
    LinearLayout EditAvatarProfileButton,EditStatusProfileButton,EditCoverProfileButton,EditNickNameProfileButton;

    ImageView BackToProfileButton;

    public SettingsFragment() {
        // Required empty public constructor
    }

    //загрузка фото
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                UserPhoto.setImageURI(selectedImageUri);
            }
        }
    }

    private void GetImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    //Получение информации о пользователе из фрагмента профиля
    private void GetBundle(){
        UserInfoBunlde = getArguments();
        cUserInfo = (User) UserInfoBunlde.getSerializable(ProfileFragment.Bunlde_UserInfo_Tag);
    }

    private void UpdateUserProfile(){
        ProfileFragment profileFragment = (ProfileFragment) mainActivity.getSupportFragmentManager().findFragmentByTag("3");
        profileFragment.LoadUserInfo();
    }

    private byte[] GetByteFromPhoto(){
        Drawable photo = UserPhoto.getDrawable();
        Bitmap PhotoPreviewBitMap = ((BitmapDrawable) photo).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PhotoPreviewBitMap.compress(Bitmap.CompressFormat.JPEG, 35, baos);
        return baos.toByteArray();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.Z, true));
        setReturnTransition(new MaterialSharedAxis(MaterialSharedAxis.Z, true));

        GetBundle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivity = (MainActivity) getActivity();



        //Диалог снизу
        bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.ModalBottomSheetDialog);


        //Редактирование статуса профиля
        View EditStatusProfileDialog = LayoutInflater.from(getContext())
                .inflate(
                        R.layout.bottom_sheet_dialog_edit_status_profile,
                        (FrameLayout) view.findViewById(R.id.SheetDialogEditStatusProfileContainer)
                );
        EditTextPerson = EditStatusProfileDialog.findViewById(R.id.ProfileStatusEditText);
        if(cUserInfo.userStatus.length()>0){
            EditTextPerson.setText(cUserInfo.userStatus);
        }
        EditStatusProfileDialog.findViewById(R.id.SaveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                mainActivity.mDatabase.child(mainActivity.Users_Child).child(mainActivity.currentUser.getUid()).child("userStatus")
                        .setValue(EditTextPerson.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            bottomSheetDialog.dismiss();
                            progressBar.setVisibility(View.GONE);
                            UpdateUserProfile();
                            Snackbar.make(getView(),"Статус обновлен", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.MainWhite)).setTextColor(getResources().getColor(R.color.MainBlack)).show();
                        }else {
                            Snackbar.make(view,"Ошибка обновления статуса", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.MainWhite)).setTextColor(getResources().getColor(R.color.MainBlack)).show();
                        }
                    }
                });
            }
        });


        //Редактирование фото профиля
        View EditPhotoProfileDialog = LayoutInflater.from(getContext())
                .inflate(
                        R.layout.bottom_sheet_dialog_edit_avatar_profile,
                        (FrameLayout) view.findViewById(R.id.SheetDialogEditAvatarProfileContainer)
                );
        UserPhoto = EditPhotoProfileDialog.findViewById(R.id.UserPhotoDownload);
        if(cUserInfo.PhotoUri!=null){
            Glide.with(getActivity()).load(cUserInfo.PhotoUri).into(UserPhoto);
        }
        EditPhotoProfileDialog.findViewById(R.id.CardUserAvatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImage();
            }
        });
        EditPhotoProfileDialog.findViewById(R.id.SaveAvatarButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserPhoto != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    if (mainActivity.storageReference == null) {
                        mainActivity.StorageInit();
                    }
                    UploadTask uploadTaskPhoto = mainActivity.storageReference.child(mainActivity.Users_Child).child(mainActivity.currentUser.getUid()).child("PhotoUri").putBytes(GetByteFromPhoto());
                    uploadTaskPhoto.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){

                            }else {
                                progressBar.setVisibility(View.GONE);
                                Snackbar.make(view,"Ошибка загрузки аватара", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.MainWhite)).setTextColor(getResources().getColor(R.color.MainBlack)).show();
                            }
                        }
                    });
                    Task<Uri> taskPhoto = uploadTaskPhoto.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            return mainActivity.storageReference.child(mainActivity.Users_Child).child(mainActivity.currentUser.getUid()).child("PhotoUri").getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()){
                                mainActivity.mDatabase.child(mainActivity.Users_Child).child(mainActivity.currentUser.getUid()).child("PhotoUri").setValue(task.getResult().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            //Аватар загружен
                                            bottomSheetDialog.dismiss();
                                            UpdateUserProfile();
                                            progressBar.setVisibility(View.GONE);
                                        }else {
                                            progressBar.setVisibility(View.GONE);
                                            Snackbar.make(view,"Ошибка #3 загрузки аватара ", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.MainWhite)).setTextColor(getResources().getColor(R.color.MainBlack)).show();
                                        }
                                    }
                                });
                            }else {
                                progressBar.setVisibility(View.GONE);
                                Snackbar.make(view,"Ошибка #2 загрузки аватара ", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.MainWhite)).setTextColor(getResources().getColor(R.color.MainBlack)).show();
                            }
                        }
                    });

                }else {
                    Snackbar.make(view,"Аватар не выбран", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.MainWhite)).setTextColor(getResources().getColor(R.color.MainBlack)).show();
                }
            }
        });

        //Кнопка редактирования аватара
        EditAvatarProfileButton = view.findViewById(R.id.EditAvatarProfileButton);
        EditAvatarProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar = EditPhotoProfileDialog.findViewById(R.id.progressBar);
                bottomSheetDialog.setContentView(EditPhotoProfileDialog);
                bottomSheetDialog.show();
            }
        });

        //Кнопка редактирования статуса
        EditStatusProfileButton = view.findViewById(R.id.EditStatusProfileButton);
        EditStatusProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar = EditStatusProfileDialog.findViewById(R.id.progressBar);
                bottomSheetDialog.setContentView(EditStatusProfileDialog);
                bottomSheetDialog.show();
            }
        });

        //Кнопка закрытия окна настроек
        BackToProfileButton = view.findViewById(R.id.BackToProfileButton);
        BackToProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.getSupportFragmentManager().popBackStack();
            }
        });

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
}