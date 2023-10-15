package com.AniDeko.anidekoandroid;

import android.os.Bundle;
import android.view.MenuItem;

import com.AniDeko.anidekoandroid.ui.Auth.AuthFragment;
import com.AniDeko.anidekoandroid.ui.home.HomeFragment;
import com.AniDeko.anidekoandroid.ui.profile.ProfileFragment;
import com.AniDeko.anidekoandroid.ui.trends.TrendsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.AniDeko.anidekoandroid.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public Boolean UserisSign;
    public FirebaseAuth auth;
    public  FirebaseUser currentUser;
    ProfileFragment ProfileFragment;
    HomeFragment homeFragment;
    AuthFragment AuthFragment;
    TrendsFragment TrendsFragment;
    BottomNavigationView navView;
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //инициализация фрагментов
        navView = findViewById(R.id.nav_view);
        homeFragment = new HomeFragment();
        TrendsFragment = new TrendsFragment();
        ProfileFragment = new ProfileFragment();
        setFragment(homeFragment, "1", 1);



        //Инициализация при создании
        auth = FirebaseAuth.getInstance();

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.home){
                    setFragment(homeFragment, "1", 1);
                } else if (item.getItemId()==R.id.trends) {
                    setFragment(TrendsFragment, "2", 0);
                } else if (item.getItemId()==R.id.profile) {
                    setFragment(ProfileFragment, "3", 2);
                }
                return false;
            }
        });


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


    //Вызов экземпляра фрагмента
    public Fragment getFragment(int Fragment_id){
        switch (Fragment_id){
            case 1:
                return homeFragment;
            case 2:
                return TrendsFragment;
            case 3:
                return ProfileFragment;
            default:
                return homeFragment;
        }
    }


    public void Auth(){
        //Пробуем авторизоваться
        currentUser = auth.getCurrentUser();
        if(currentUser != null){
            if(getSupportFragmentManager().findFragmentByTag("Auth")!=null) {
                getSupportFragmentManager().beginTransaction().remove(ProfileFragment).remove(AuthFragment).commit();
                ProfileFragment = new ProfileFragment();
                setFragment(homeFragment, "1", 1);
            }
        }else{
            if(getSupportFragmentManager().findFragmentByTag("3")!=null) {
                AuthFragment = new AuthFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.ProfileFragmentConteiner, AuthFragment, "Auth").commit();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Auth();
    }
}