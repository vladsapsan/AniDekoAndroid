package com.AniDeko.anidekoandroid.DataStructure;

//Типизация отправки данных о пользователях
public class User {
    String NickName;
    String Email;
    String PhotoUri;
    String SecondPhotoUri;

    User(){}

    User(String Nickname,String Email){
        this.NickName = Nickname;
        this.Email = Email;
    }

    User(String Nickname,String Email,String PhotoUri,String SecondPhotoUri){
        this.NickName = Nickname;
        this.Email = Email;
        this.PhotoUri = PhotoUri;
        this.SecondPhotoUri = SecondPhotoUri;

    }
}
