package com.github.animeedge.model;


public class AnimeCharacter {

    private String id;

    private String animeName;

    private String name;

    private int gender;

    private String birthday;

    public AnimeCharacter() {
    }

    public AnimeCharacter(String animeName, String name, int gender, String birthday) {
        this.animeName = animeName;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnimeName() {
        return animeName;
    }

    public void setAnimeName(String animeName) {
        this.animeName = animeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}