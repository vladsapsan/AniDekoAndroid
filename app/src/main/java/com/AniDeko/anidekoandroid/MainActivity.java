package com.AniDeko.anidekoandroid;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.AniDeko.anidekoandroid.DataStructure.User;
import com.AniDeko.anidekoandroid.ui.Auth.AuthFragment;
import com.AniDeko.anidekoandroid.ui.home.HomeFragment;
import com.AniDeko.anidekoandroid.ui.profile.ProfileFragment;
import com.AniDeko.anidekoandroid.ui.trends.TrendsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.AniDeko.anidekoandroid.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {
    public FirebaseAuth auth;
    public  FirebaseUser currentUser;
    public StorageReference storageReference;
    public final static String Users_Child = "Users";
    public final static String Users_SubList="SubScribeList";
    public final static String Users_MySubsList="MySubScribeList";
    public DatabaseReference mDatabase;
    BottomNavigationView navView;
    final FragmentManager fm = getSupportFragmentManager();
    public NavController navController;
    Fragment active = null;
    public User cUserInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Инициализация FireAuth при создании


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();



        //Инициализация нижнего навигационного меню
        navView = findViewById(R.id.nav_view);

        if(savedInstanceState==null) {

        }

        NavigationUI.setupWithNavController(navView,navController);
    }

    //функция навигации между фрагментами по нижнему меню
    public void setFragment(Fragment fragment, String tag, int position) {
        if (fm.findFragmentByTag(tag)!=null) {
            if (fragment == active) {
            } else {
                fm.beginTransaction().hide(active).show(fragment).commit();
            }
        } else if (active != null) {
                fm.beginTransaction().hide(active).add(R.id.nav_host_fragment_activity_main, fragment, tag).commit();
        } else {
            fm.beginTransaction().add(R.id.nav_host_fragment_activity_main, fragment,tag).commit();
        }
        navView.getMenu().getItem(position).setChecked(true);
        active = fragment;
    }






    public void UpdateUserProfile(){
        ProfileFragment profileFragment = (ProfileFragment) getSupportFragmentManager().findFragmentById(R.id.ProfileUserFragment);
        if(profileFragment!=null) {
            profileFragment.LoadMyUserInfo();
        }
    }
    //Инициализируем бд
    public void DataBaseInit(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
    //Инициализируем storage
    public void StorageInit(){
        storageReference = FirebaseStorage.getInstance().getReference();;
    }




    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public FirebaseUser Auth(){
        //Пробуем авторизоваться
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null){
            currentUser = auth.getCurrentUser();
        }else {
            return null;
        }
        return currentUser;
    }

    @Override
    public void onStart() {
        super.onStart();
        Auth();
    }
}