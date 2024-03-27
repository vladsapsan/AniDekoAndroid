package com.AniDeko.anidekoandroid.DataStructure;

import androidx.annotation.NonNull;

import com.AniDeko.anidekoandroid.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//Типизация отправки данных о пользователях
public class User implements Serializable {
    //Для успешной обработки данных, обязательно модификатор public
    public String NickName;
    public String Email;
    public String userStatus;
    public String ID;
    public Boolean isVerifeid;
    public Boolean isBanned;
    public String PhotoUri;
    public String SecondPhotoUri;
    //Лист с теми кто подписан на пользователя
    public HashMap<String, String> SubScribeList = new HashMap<>();
    //Лист с теми на кого подписан пользователь
    public HashMap<String, String> MySubScribeList = new HashMap<>();
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
    public User(String Nickname,String Email,String PhotoUri,String userStatus,String SecondPhotoUri,Boolean isBanned,Boolean isVerifeid,String ID){
        this.NickName = Nickname;
        this.Email = Email;
        this.PhotoUri = PhotoUri;
        this.SecondPhotoUri = SecondPhotoUri;
        this.userStatus = userStatus;
        this.isBanned = isBanned;
        this.isVerifeid = isVerifeid;
        this.ID = ID;
    }

    //Полный конструктор
    public User(String Nickname,String Email,String PhotoUri,String userStatus,String SecondPhotoUri,Boolean isBanned,Boolean isVerifeid,String ID,HashMap<String, String> MySubScribeList,HashMap<String, String> SubScribeList){
        this.NickName = Nickname;
        this.Email = Email;
        this.PhotoUri = PhotoUri;
        this.SecondPhotoUri = SecondPhotoUri;
        this.userStatus = userStatus;
        this.isBanned = isBanned;
        this.isVerifeid = isVerifeid;
        this.ID = ID;
        this.MySubScribeList = MySubScribeList;
        this.SubScribeList = SubScribeList;
    }

    public final void AddSubsribe(String IDProfile){
        SubScribeList.put((IDProfile), String.valueOf((Calendar.getInstance().getTime())));
    }
    public final Map<String, String> GetSubsribe(){
        return SubScribeList;
    }
    public final void DeleteSubsribe(String IDProfile){
        SubScribeList.remove((IDProfile), String.valueOf((Calendar.getInstance().getTime())));
    }
}
