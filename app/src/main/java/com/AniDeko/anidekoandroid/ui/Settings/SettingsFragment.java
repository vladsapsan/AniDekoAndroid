package com.AniDeko.anidekoandroid.ui.Settings;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    boolean isAvatar = false;
    Button ExitFromAccButton;
    MainActivity mainActivity;
    EditText EditTextPerson,EditTextNickName;
    Bundle UserInfoBunlde;
    ImageView UserPhoto,UserCover;
    User cUserInfo;
    BottomSheetDialog bottomSheetDialog;
    LinearLayout EditAvatarProfileButton,EditStatusProfileButton,EditCoverProfileButton,EditNickNameProfileButton,AddAnimeButton;

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
                if(getMimeType(selectedImageUri).toLowerCase().contains("jpeg")||getMimeType(selectedImageUri).toLowerCase().contains("png")||getMimeType(selectedImageUri).toLowerCase().contains("webp")){
                    if(isAvatar==true) {
                        UserPhoto.setImageURI(selectedImageUri);
                    }else {
                        UserCover.setImageURI(selectedImageUri);
                    }
                }
                else{
                    Toast.makeText(getContext(),"Выбран файл неподдерживаемого формата",Toast.LENGTH_SHORT).show();
                }
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


    //Получение расширения файла
    public String getMimeType(Uri uri) {
        String mimeType;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = getActivity().getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
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


    private byte[] GetByteFromPhoto(){
        Drawable photo;
        if(isAvatar==true){
             photo = UserPhoto.getDrawable();
        }else {
             photo = UserCover.getDrawable();
        }
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
                            mainActivity.UpdateUserProfile();
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
                isAvatar=true;
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
                                            mainActivity.UpdateUserProfile();
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

        //Редактирование обложки
        View EditCoverProfileDialog = LayoutInflater.from(getContext())
                .inflate(
                        R.layout.bottom_sheet_dialog_edit_cover_profile,
                        (FrameLayout) view.findViewById(R.id.SheetDialogEditCoverProfileContainer)
                );
        UserCover = EditCoverProfileDialog.findViewById(R.id.UserCoverDownload);
        if(cUserInfo.SecondPhotoUri!=null){
            Glide.with(getActivity()).load(cUserInfo.SecondPhotoUri).into(UserCover);
        }
        EditCoverProfileDialog.findViewById(R.id.CardUserCover).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAvatar=false;
                GetImage();
            }
        });
        EditCoverProfileDialog.findViewById(R.id.SaveAvatarButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserCover != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    if (mainActivity.storageReference == null) {
                        mainActivity.StorageInit();
                    }
                    UploadTask uploadTaskPhoto = mainActivity.storageReference.child(mainActivity.Users_Child).child(mainActivity.currentUser.getUid()).child("SecondPhotoUri").putBytes(GetByteFromPhoto());
                    uploadTaskPhoto.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){

                            }else {
                                progressBar.setVisibility(View.GONE);
                                Snackbar.make(view,"Ошибка загрузки обложки", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.MainWhite)).setTextColor(getResources().getColor(R.color.MainBlack)).show();
                            }
                        }
                    });
                    Task<Uri> taskPhoto = uploadTaskPhoto.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            return mainActivity.storageReference.child(mainActivity.Users_Child).child(mainActivity.currentUser.getUid()).child("SecondPhotoUri").getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()){
                                mainActivity.mDatabase.child(mainActivity.Users_Child).child(mainActivity.currentUser.getUid()).child("SecondPhotoUri").setValue(task.getResult().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            //Обложка загружена
                                            bottomSheetDialog.dismiss();
                                            mainActivity.UpdateUserProfile();
                                            progressBar.setVisibility(View.GONE);
                                        }else {
                                            progressBar.setVisibility(View.GONE);
                                            Snackbar.make(view,"Ошибка #3 загрузки обложки ", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.MainWhite)).setTextColor(getResources().getColor(R.color.MainBlack)).show();
                                        }
                                    }
                                });
                            }else {
                                progressBar.setVisibility(View.GONE);
                                Snackbar.make(view,"Ошибка #2 загрузки обложки ", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.MainWhite)).setTextColor(getResources().getColor(R.color.MainBlack)).show();
                            }
                        }
                    });

                }else {
                    Snackbar.make(view,"Обложка не выбрана", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.MainWhite)).setTextColor(getResources().getColor(R.color.MainBlack)).show();
                }
            }
        });

        //Редактирование статуса профиля
        View EditNickNameProfileDialog = LayoutInflater.from(getContext())
                .inflate(
                        R.layout.bottom_sheet_dialog_edit_nickname_profile,
                        (FrameLayout) view.findViewById(R.id.SheetDialogEditANicknNameProfileContainer)
                );
        EditTextNickName = EditNickNameProfileDialog.findViewById(R.id.ProfileNickNameEditText);
        EditTextNickName.setText(cUserInfo.NickName);
        EditNickNameProfileDialog.findViewById(R.id.SaveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                mainActivity.mDatabase.child(mainActivity.Users_Child).child(mainActivity.currentUser.getUid()).child("NickName")
                        .setValue(EditTextNickName.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    bottomSheetDialog.dismiss();
                                    progressBar.setVisibility(View.GONE);
                                    mainActivity.UpdateUserProfile();
                                    Snackbar.make(getView(),"Никнейм обновлен", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.MainWhite)).setTextColor(getResources().getColor(R.color.MainBlack)).show();
                                }else {
                                    Snackbar.make(view,"Ошибка обновления никнейма", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.MainWhite)).setTextColor(getResources().getColor(R.color.MainBlack)).show();
                                }
                            }
                        });
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

        //Кнопка редактирования обложки
        EditCoverProfileButton = view.findViewById(R.id.EditCoverProfileButton);
        EditCoverProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar = EditCoverProfileDialog.findViewById(R.id.progressBar);
                bottomSheetDialog.setContentView(EditCoverProfileDialog);
                bottomSheetDialog.show();
            }
        });

        //Кнопка редактирования ника
        EditNickNameProfileButton = view.findViewById(R.id.EditNickNameProfileButton);
        EditNickNameProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar = EditNickNameProfileDialog.findViewById(R.id.progressBar);
                bottomSheetDialog.setContentView(EditNickNameProfileDialog);
                bottomSheetDialog.show();
            }
        });
        //Кнопка закрытия окна настроек
        BackToProfileButton = view.findViewById(R.id.BackToProfileButton);
        BackToProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).navController.popBackStack();
            }
        });

        //Кнопка добавления аниме
        AddAnimeButton = view.findViewById(R.id.AddAnimeButton);
        AddAnimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).navController.navigate(R.id.action_settingsFragment_to_addAnimeFragment);
            }
        });

        //Кнопка выхода из профиля
        ExitFromAccButton = view.findViewById(R.id.ExitFromAccButton);
        ExitFromAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.auth.signOut();
                if(mainActivity.Auth()==null){
                    mainActivity.navController.navigate(R.id.action_settingsFragment_to_authFragment

                    );
                }
            }
        });
    }
}