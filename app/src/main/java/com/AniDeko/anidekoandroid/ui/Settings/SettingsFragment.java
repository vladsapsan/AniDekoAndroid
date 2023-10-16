package com.AniDeko.anidekoandroid.ui.Settings;

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
import android.widget.Toast;

import com.AniDeko.anidekoandroid.DataStructure.User;
import com.AniDeko.anidekoandroid.MainActivity;
import com.AniDeko.anidekoandroid.R;
import com.AniDeko.anidekoandroid.ui.profile.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.transition.MaterialSharedAxis;


public class SettingsFragment extends Fragment {


    Button ExitFromAccButton;
    MainActivity mainActivity;
    EditText EditTextPerson;
    Bundle UserInfoBunlde;
    User cUserInfo;
    BottomSheetDialog bottomSheetDialog;
    LinearLayout EditAvatarProfileButton,EditStatusProfileButton,EditCoverProfileButton,EditNickNameProfileButton;

    ImageView BackToProfileButton;

    public SettingsFragment() {
        // Required empty public constructor
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
                mainActivity.mDatabase.child(mainActivity.Users_Child).child(mainActivity.currentUser.getUid()).child("userStatus")
                        .setValue(EditTextPerson.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            bottomSheetDialog.dismiss();
                            Snackbar.make(view,"Статус обновлен", Snackbar.LENGTH_SHORT).show();
                        }else {
                            Snackbar.make(view,"Ошибка загрузки статуса", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        //Кнопка редактирования статуса
        EditStatusProfileButton = view.findViewById(R.id.EditStatusProfileButton);
        EditStatusProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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