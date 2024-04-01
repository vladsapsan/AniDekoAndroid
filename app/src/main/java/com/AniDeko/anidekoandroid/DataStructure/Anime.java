package com.AniDeko.anidekoandroid.DataStructure;

import java.util.HashMap;
import java.util.Map;

public class Anime {
    public String ID;
    public String Name;
    public String TextAbout;
    public String DateOfRealize;
    public String Director;
    public String Author;
    public String Country;
    public String Studio;
    public Integer Pegi;
    public String Rating;
    public String TypeOfAnime;
    public String PosterImgUrl;
    public String TrailerUrl;
    public String HelpingInformation;
    public boolean isVisible;
    public Map<String, Integer> RatinsgList = new HashMap<>();
    public Map<String, String> GenreList = new HashMap<>();
    public HashMap<String, Comments> Comments = new HashMap<>();
    public Anime() {};
    public Anime(String ID,String Name,String TextAbout,String DateOfRealize,String Director,Map<String, String> JanrList,String Author,String Country,
                 String Studio,Integer Pegi,String Rating,String TypeOfAnime,String PosterImgUrl,String HelpingInformation,boolean isVisible){
        this.Name = Name;
        this.TextAbout = TextAbout;
        this.DateOfRealize = DateOfRealize;
        this.Director = Director;
        this.GenreList = JanrList;
        this.Author = Author;
        this.Country = Country;
        this.Studio = Studio;
        this.Pegi = Pegi;
        this.Rating = Rating;
        this.TypeOfAnime = TypeOfAnime;
        this.PosterImgUrl = PosterImgUrl;
        this.HelpingInformation = HelpingInformation;
        this.isVisible = isVisible;
        this.ID = ID;
    }
}
