package com.github.animeedge.model;


import java.util.List;

public class AnimeStudio {

    private int id;
    private String name;
    private int seriesAmount;
    private List<AnimeSeries> series;

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

    public List<AnimeSeries> getSeries() {
        return series;
    }

    public void setSeries(List<AnimeSeries> series) {
        this.series = series;
    }
}
