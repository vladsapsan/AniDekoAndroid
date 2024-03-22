package com.AniDeko.anidekoandroid.DataStructure;

import java.io.Serializable;
import java.util.List;

public class SimpleUser implements Serializable {
    public String NickName;
    public String ID;
    public Boolean isVerifeid;
    public Boolean isBanned;
    public String PhotoUri;
    public SimpleUser(){}

    //Полный конструктор
    public SimpleUser(String Nickname,String PhotoUri,Boolean isBanned,Boolean isVerifeid,String ID){
        this.NickName = Nickname;
        this.PhotoUri = PhotoUri;
        this.isBanned = isBanned;
        this.isVerifeid = isVerifeid;
        this.ID = ID;
    }
}
