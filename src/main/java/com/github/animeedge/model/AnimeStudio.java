package com.github.animeedge.model;


public class AnimeStudio {

    private int id;

    private String name;

    private int seriesAmount;

    public AnimeStudio(){}

    public AnimeStudio(String name, int seriesAmount){
        setName(name);
        setSeriesAmount(seriesAmount);
    }

    public AnimeStudio(AnimeStudioDTO animeStudioDTO) {
        setName(animeStudioDTO.getName());
        setSeriesAmount(animeStudioDTO.getSeriesAmount());
    }

    public int getId() {
        return id;
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

    public void setSeriesAmount(int seriesAmount) {
        this.seriesAmount = seriesAmount;
    }
}
