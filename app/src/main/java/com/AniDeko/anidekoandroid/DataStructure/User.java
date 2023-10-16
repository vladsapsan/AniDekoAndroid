package com.AniDeko.anidekoandroid.DataStructure;

import androidx.annotation.NonNull;

import com.AniDeko.anidekoandroid.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

//Типизация отправки данных о пользователях
public class User implements Serializable {
    //Для успешной обработки данных, обязательно модификатор public
    public String NickName;
    public String Email;
    public String userStatus;
    public Boolean isVerifeid;
    public Boolean isBanned;
    public String PhotoUri;
    public String SecondPhotoUri;

    public User(){}

    public User(String Nickname, String Email,Boolean isBanned){
        this.NickName = Nickname;
        this.Email = Email;
        this.isBanned = isBanned;
    }
    public User(String Nickname,String Email,String PhotoUri){
        this.NickName = Nickname;
        this.Email = Email;
        this.PhotoUri = PhotoUri;
    }

    public User(String Nickname,String Email,String PhotoUri,String SecondPhotoUri){
        this.NickName = Nickname;
        this.Email = Email;
        this.PhotoUri = PhotoUri;
        this.SecondPhotoUri = SecondPhotoUri;

    }

    //Полный конструктор
    public User(String Nickname,String Email,String PhotoUri,String userStatus,String SecondPhotoUri,Boolean isBanned,Boolean isVerifeid){
        this.NickName = Nickname;
        this.Email = Email;
        this.PhotoUri = PhotoUri;
        this.SecondPhotoUri = SecondPhotoUri;
        this.userStatus = userStatus;
        this.isBanned = isBanned;
        this.isVerifeid = isVerifeid;
    }


}
