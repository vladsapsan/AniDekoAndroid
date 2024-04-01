package com.AniDeko.anidekoandroid.DataStructure;

public class Comments {
    public String UserID;
    public String DateOfComments;
    public int NumberOfLike;
    public String Comments;

    public Comments(){}
    public Comments(String UserID,String DateOfComments,int NumberOfLike,String Comments){
        this.UserID = UserID;
        this.DateOfComments = DateOfComments;
        this.NumberOfLike = NumberOfLike;
        this.Comments = Comments;
    }
}
