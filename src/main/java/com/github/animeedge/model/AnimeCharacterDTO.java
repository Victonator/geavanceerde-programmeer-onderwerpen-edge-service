package com.github.animeedge.model;

public class AnimeCharacterDTO {
    private String animeName;
    private String name;
    private int gender;
    private String birthday;

    public AnimeCharacterDTO() {}

    public AnimeCharacterDTO(AnimeCharacter character) {
        this.animeName = character.getAnimeName();
        this.name = character.getName();
        this.gender = character.getGender();
        this.birthday = character.getBirthday();
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
