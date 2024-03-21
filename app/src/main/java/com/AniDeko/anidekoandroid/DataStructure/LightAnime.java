package com.AniDeko.anidekoandroid.DataStructure;

import java.util.HashMap;
import java.util.Map;

public class LightAnime {
    public String Name;
    public String DateOfRealize;
    public String Director;
    public String Author;
    public String Country;
    public String Studio;
    public Integer Pegi;
    public String Rating;
    public String TypeOfAnime;
    public String PosterImgUrl;
    public Map<Integer, String> JanrList = new HashMap<>();
    public boolean isVisible;


    public LightAnime() {};
    public LightAnime(String Name,String DateOfRealize,String Director,Map<Integer, String> JanrList,String Author,String Country,
                 String Studio,Integer Pegi,String Rating,String TypeOfAnime,String PosterImgUrl,boolean isVisible){
        this.Name = Name;
        this.DateOfRealize = DateOfRealize;
        this.Director = Director;
        this.JanrList = JanrList;
        this.Author = Author;
        this.Country = Country;
        this.Studio = Studio;
        this.Pegi = Pegi;
        this.Rating = Rating;
        this.TypeOfAnime = TypeOfAnime;
        this.PosterImgUrl = PosterImgUrl;
        this.isVisible = isVisible;
    }
}
