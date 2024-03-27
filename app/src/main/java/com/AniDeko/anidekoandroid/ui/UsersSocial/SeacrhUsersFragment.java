package com.AniDeko.anidekoandroid.ui.UsersSocial;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.AniDeko.anidekoandroid.DataStructure.SimpleUser;
import com.AniDeko.anidekoandroid.DataStructure.User;
import com.AniDeko.anidekoandroid.DataStructure.UserListItemProfileAdapter;
import com.AniDeko.anidekoandroid.MainActivity;
import com.AniDeko.anidekoandroid.R;
import com.google.android.material.search.SearchBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.transition.MaterialFadeThrough;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class SeacrhUsersFragment extends Fragment implements UserListItemProfileAdapter.ItemClickListener  {



    ImageView BackToProfileButton;
    SearchBar SearchUserBar;
    RecyclerView UsersRycecleView;
    EditText SearchUsersEditText;
    MainActivity mainActivity;
    public static final String Bunlde_UserID_Tag = "IdUser";
    DatabaseReference Userdatabase;
    String cUserID;
    UserListItemProfileAdapter userListItemProfileAdapter;

    private ArrayList<SimpleUser> UserslistTemp = new ArrayList<>();

    public SeacrhUsersFragment() {
        // Required empty public constructor
    }


    private void init(){
        //Второй столбец данных
        userListItemProfileAdapter = new UserListItemProfileAdapter(getActivity(),UserslistTemp);
        userListItemProfileAdapter.setClickListener(this::onItemClick);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        UsersRycecleView.setLayoutManager(layoutManager);
        UsersRycecleView.setAdapter(userListItemProfileAdapter);
    }

    //Загрузка разделов из базы
    private void DownloadSectionFirebaseData()
    {
        Userdatabase = FirebaseDatabase.getInstance().getReference(MainActivity.Users_Child);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(UserslistTemp.size()>0) UserslistTemp.clear();
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    SimpleUser user = ds.getValue(SimpleUser.class);
                    //Проверка
                    assert user != null;
                    if(user.isBanned==false) {
                        if(!user.ID.equals(cUserID)) {
                            UserslistTemp.add(user);
                        }
                    }
                }
                userListItemProfileAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        Userdatabase.addValueEventListener(valueEventListener);
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
        return inflater.inflate(R.layout.fragment_seacrh_users, container, false);
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<SimpleUser> filteredlist = new ArrayList<SimpleUser>();
        // running a for loop to compare elements.
        for (SimpleUser item : UserslistTemp) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.NickName.toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            userListItemProfileAdapter.filterList(filteredlist);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivity = (MainActivity) getActivity();
        UsersRycecleView = view.findViewById(R.id.UsersRycecleView);
        SearchUsersEditText = view.findViewById(R.id.SearchUsersEditText);
        cUserID = mainActivity.currentUser.getUid();
        init();
        DownloadSectionFirebaseData();

        SearchUsersEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        SearchUsersEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    MainActivity.hideKeyboardFrom(getContext(),getView());
                }
                return false;
            }
        });

        BackToProfileButton = view.findViewById(R.id.BackToProfileButton);
        BackToProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).navController.popBackStack();
            }
        });
    }


    //Навигация в профиль пользователя
    @Override
    public void onItemClick(View view, int position) {
       // MainActivity.hideKeyboardFrom(getContext(),getView());
        ((MainActivity) getActivity()).navController.navigate(R.id.action_seacrhUsersFragment_to_socialProfileUserFragment,SetIntelizationBundle(position));

    }

    //Создаем банд с информацией профиля
    private Bundle SetIntelizationBundle(int Count){
        Bundle UserIDBundle = new Bundle();
        UserIDBundle.putString(Bunlde_UserID_Tag,userListItemProfileAdapter.getItem(Count).ID);
        return UserIDBundle;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}