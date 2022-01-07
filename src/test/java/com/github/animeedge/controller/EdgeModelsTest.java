package com.github.animeedge.controller;

import com.github.animeedge.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class EdgeModelsTest {

    @Test
    void animeCharacterConstructors() {
        AnimeCharacter animeCharacter = new AnimeCharacter("Testserie", "Test Name", 1, "69/69");

        assertEquals("Testserie", animeCharacter.getAnimeName());
        assertEquals("Test Name", animeCharacter.getName());
        assertEquals(1, animeCharacter.getGender());
        assertEquals("69/69", animeCharacter.getBirthday());


        AnimeCharacterDTO dtoCharacter = new AnimeCharacterDTO(animeCharacter);

        assertEquals("Testserie", dtoCharacter.getAnimeName());
        assertEquals("Test Name", dtoCharacter.getName());
        assertEquals(1, dtoCharacter.getGender());
        assertEquals("69/69", dtoCharacter.getBirthday());

    }

    @Test
    void AnimeSeriesConstructors() {
        AnimeSeries animeSeries1 = new AnimeSeries("studio", "name", false, 24, 2000);
        AnimeSeries animeSeries2 = new AnimeSeries("studio", "name", "genre", false, 24, 2000);
        AnimeSeries animeSeries3 = new AnimeSeries("studio", "name", false, 24, 1, 2000);
        AnimeSeries animeSeries4 = new AnimeSeries("studio", "name", "genre", false, 24, 1, 2000);

        assertEquals("studio", animeSeries1.getStudio());
        assertEquals("name", animeSeries1.getName());
        assertEquals(false, animeSeries1.getMovie());
        assertEquals(24, animeSeries1.getEpisodes());
        assertEquals(2000, animeSeries1.getYearAired());

        assertEquals("studio", animeSeries2.getStudio());
        assertEquals("name", animeSeries2.getName());
        assertEquals(false, animeSeries2.getMovie());
        assertEquals(24, animeSeries2.getEpisodes());
        assertEquals(2000, animeSeries2.getYearAired());
        assertEquals("genre", animeSeries2.getGenre());

        assertEquals("studio", animeSeries3.getStudio());
        assertEquals("name", animeSeries3.getName());
        assertEquals(false, animeSeries3.getMovie());
        assertEquals(24, animeSeries3.getEpisodes());
        assertEquals(2000, animeSeries3.getYearAired());
        assertEquals(1, animeSeries3.getSeason());

        assertEquals("studio", animeSeries4.getStudio());
        assertEquals("name", animeSeries4.getName());
        assertEquals(false, animeSeries4.getMovie());
        assertEquals(24, animeSeries4.getEpisodes());
        assertEquals(2000, animeSeries4.getYearAired());
        assertEquals("genre", animeSeries4.getGenre());
        assertEquals(1, animeSeries4.getSeason());


        AnimeSeriesDTO dtoSeries = new AnimeSeriesDTO(animeSeries4);

        assertEquals("studio", dtoSeries.getStudio());
        assertEquals("name", dtoSeries.getName());
        assertEquals(false, dtoSeries.getMovie());
        assertEquals(24, dtoSeries.getEpisodes());
        assertEquals(2000, dtoSeries.getYearAired());
        assertEquals("genre", dtoSeries.getGenre());
        assertEquals(1, dtoSeries.getSeason());
    }

    @Test
    void AnimeStudioConstructors() {
        AnimeStudio animeStudio1 = new AnimeStudio("Madhouse", 69);

        assertEquals("Madhouse", animeStudio1.getName());
        assertEquals(69, animeStudio1.getSeriesAmount());


        AnimeStudioDTO dtoStudio = new AnimeStudioDTO(animeStudio1);

        assertEquals("Madhouse", dtoStudio.getName());
        assertEquals(69, dtoStudio.getSeriesAmount());


        AnimeStudio animeStudio2 = new AnimeStudio(dtoStudio);
        assertEquals("Madhouse", animeStudio2.getName());
        assertEquals(69, animeStudio2.getSeriesAmount());
    }

    @Test
    void AnimeCharacterSets() {
        AnimeCharacter animeCharacter = new AnimeCharacter("Testserie", "Test Name", 1, "69/69");
        AnimeCharacterDTO animeCharacterDTO = new AnimeCharacterDTO(animeCharacter);

        animeCharacter.setId("0");
        animeCharacter.setAnimeName("name");
        animeCharacter.setName("name");
        animeCharacter.setGender(0);
        animeCharacter.setBirthday("0/0");

        assertEquals("0", animeCharacter.getId());
        assertEquals("name", animeCharacter.getAnimeName());
        assertEquals("name", animeCharacter.getName());
        assertEquals(0, animeCharacter.getGender());
        assertEquals("0/0", animeCharacter.getBirthday());


        animeCharacterDTO.setAnimeName("name");
        animeCharacterDTO.setName("name");
        animeCharacterDTO.setGender(0);
        animeCharacterDTO.setBirthday("0/0");


        assertEquals("name", animeCharacterDTO.getAnimeName());
        assertEquals("name", animeCharacterDTO.getName());
        assertEquals(0, animeCharacterDTO.getGender());
        assertEquals("0/0", animeCharacterDTO.getBirthday());
    }

    @Test
    void AnimeSeriesSets() {
        AnimeSeries animeSeries = new AnimeSeries("studio", "name", false, 24, 2000);
        AnimeSeriesDTO animeSeriesDTO = new AnimeSeriesDTO(animeSeries);

        List<AnimeCharacter> animeCharacters = new ArrayList<>();
        AnimeCharacter animeCharacter1 = new AnimeCharacter("Testserie", "Test Name", 1, "69/69");
        AnimeCharacter animeCharacter2 = new AnimeCharacter("Testserie", "Test Name", 1, "69/69");
        animeCharacters.add(animeCharacter1);
        animeCharacters.add(animeCharacter2);

        animeSeries.setId("0");
        animeSeries.setStudio("Studio");
        animeSeries.setName("Name");
        animeSeries.setGenre("genre");
        animeSeries.setMovie(true);
        animeSeries.setEpisodes(12);
        animeSeries.setSeason(1);
        animeSeries.setYearAired(2001);
        animeSeries.setCharacters(animeCharacters);

        assertEquals("0", animeSeries.getId());
        assertEquals("Studio", animeSeries.getStudio());
        assertEquals("Name", animeSeries.getName());
        assertEquals("genre", animeSeries.getGenre());
        assertEquals(true, animeSeries.getMovie());
        assertEquals(12, animeSeries.getEpisodes());
        assertEquals(1, animeSeries.getSeason());
        assertEquals(2001, animeSeries.getYearAired());
        assertEquals(animeCharacters, animeSeries.getCharacters());


        animeSeriesDTO.setStudio("Studio");
        animeSeriesDTO.setName("Name");
        animeSeriesDTO.setGenre("genre");
        animeSeriesDTO.setMovie(true);
        animeSeriesDTO.setEpisodes(12);
        animeSeriesDTO.setSeason(1);
        animeSeriesDTO.setYearAired(2001);

        assertEquals("Studio", animeSeriesDTO.getStudio());
        assertEquals("Name", animeSeriesDTO.getName());
        assertEquals("genre", animeSeriesDTO.getGenre());
        assertEquals(true, animeSeriesDTO.getMovie());
        assertEquals(12, animeSeriesDTO.getEpisodes());
        assertEquals(1, animeSeriesDTO.getSeason());
        assertEquals(2001, animeSeriesDTO.getYearAired());
    }

    @Test
    void AnimeStudioSets() {
        AnimeStudio animeStudio = new AnimeStudio("Madhouse", 69);
        AnimeStudioDTO animeStudioDTO = new AnimeStudioDTO(animeStudio);

        List<AnimeSeries> seriesList = new ArrayList<>();
        AnimeSeries animeSeries1 = new AnimeSeries("studio", "name", false, 24, 2000);
        AnimeSeries animeSeries2 = new AnimeSeries("studio", "name", "genre", false, 24, 2000);
        seriesList.add(animeSeries1);
        seriesList.add(animeSeries2);

        animeStudio.setName("MAPPA");
        animeStudio.setSeriesAmount(420);
        animeStudio.setSeries(seriesList);

        assertEquals("MAPPA", animeStudio.getName());
        assertEquals(420, animeStudio.getSeriesAmount());
        assertEquals(seriesList, animeStudio.getSeries());


        animeStudioDTO.setName("MAPPA");
        animeStudioDTO.setSeriesAmount(420);

        assertEquals("MAPPA", animeStudioDTO.getName());
        assertEquals(420, animeStudioDTO.getSeriesAmount());
    }

}
