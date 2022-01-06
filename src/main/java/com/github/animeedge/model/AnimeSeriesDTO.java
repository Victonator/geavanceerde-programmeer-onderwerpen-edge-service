package com.github.animeedge.model;

public class AnimeSeriesDTO {
    private String studio;
    private String name;
    private String genre;
    private Boolean isMovie;
    private Integer episodes;
    private Integer season;
    private Integer yearAired;

    public AnimeSeriesDTO() {}

    public AnimeSeriesDTO(AnimeSeries series) {

        this.studio = series.getStudio();
        this.name = series.getName();
        this.genre = series.getGenre();
        this.isMovie = series.getMovie();
        this.episodes = series.getEpisodes();
        this.season = series.getSeason();
        this.yearAired = series.getYearAired();
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Boolean getMovie() {
        return isMovie;
    }

    public void setMovie(Boolean movie) {
        isMovie = movie;
    }

    public Integer getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public Integer getYearAired() {
        return yearAired;
    }

    public void setYearAired(Integer yearAired) {
        this.yearAired = yearAired;
    }
}
