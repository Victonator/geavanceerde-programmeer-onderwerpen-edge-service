package com.github.animeedge.model;

public class AnimeStudioDTO {
    private String name;
    private int seriesAmount;

    public AnimeStudioDTO(){}

    public AnimeStudioDTO(AnimeStudio studio) {
        this.name = studio.getName();
        this.seriesAmount = studio.getSeriesAmount();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeriesAmount() {
        return seriesAmount;
    }

    public void setSeriesAmount(int amount) {
        this.seriesAmount = amount;
    }
}