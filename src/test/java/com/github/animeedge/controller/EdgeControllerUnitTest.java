package com.github.animeedge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.animeedge.model.AnimeCharacter;
import com.github.animeedge.model.AnimeSeries;
import com.github.animeedge.model.AnimeStudio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class EdgeControllerUnitTest {

    @Value("${studioservice.baseurl}")
    private String studioServiceBaseUrl;

    @Value("${seriesservice.baseurl}")
    private String seriesServiceBaseUrl;

    @Value("${characterservice.baseurl}")
    private String characterServiceBaseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();


    private AnimeCharacter characterNarutoUzumaki = new AnimeCharacter("Naruto", "Naruto Uzumaki", 1, "04/01");
    private AnimeCharacter characterSakuraHaruno = new AnimeCharacter("Naruto", "Sakura Haruno", 0, "04/01");

    private AnimeSeries seriesStudioCWFVoices = new AnimeSeries("CoMix Wave Films", "The Voices of a Distant Star", "science-fiction", true, 1, 2002);
    private AnimeSeries seriesStudioCWFName = new AnimeSeries("CoMix Wave Films", "your name.", "drama", true, 1, 2016);
    private AnimeSeries seriesNaruto = new AnimeSeries("Pierrot", "Naruto", "adventure", false, 57, 1, 2002);

    private AnimeStudio studioCoMixWaveFilms = new AnimeStudio("CoMix Wave Films", 86);
    private AnimeStudio studioMAPPA = new AnimeStudio("MAPPA", 72);

    @BeforeEach
    public void initializeMockServer() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }


    @Test
    public void givenCharacterName_whenGetCharacterByName_thenReturnJsonCharacters() throws Exception {

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(characterServiceBaseUrl+"/characters/Naruto%20Uzumaki")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(characterNarutoUzumaki)));

        mockMvc.perform(get("/characters/{characterName}", "Naruto Uzumaki"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Naruto Uzumaki"));
    }

    @Test
    public void givenSeriesName_whenGetCharactersBySeriesName_thenReturnJsonCharacters() throws Exception {

        List<AnimeCharacter> responseList = new ArrayList<>();
        responseList.add(characterNarutoUzumaki);
        responseList.add(characterSakuraHaruno);

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(characterServiceBaseUrl+"/characters/anime/Naruto")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(responseList)));

        mockMvc.perform(get("/characters/series/{seriesName}", "Naruto"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].animeName").value("Naruto"))
                .andExpect(jsonPath("$[1].animeName").value("Naruto"));
    }

    @Test
    public void givenStudioName_whenGetStudioByNameWithSeries_thenReturnJsonStudio() throws Exception {

        List<AnimeSeries> responseList = new ArrayList<>();
        responseList.add(seriesStudioCWFName);
        responseList.add(seriesStudioCWFVoices);

        List<AnimeStudio> responseStudio = new ArrayList<>();
        responseStudio.add(studioCoMixWaveFilms);

        List<AnimeStudio> emptyStudio = new ArrayList<>();

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI(studioServiceBaseUrl+"/studios/CoMix%20Wave%20Films")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(responseStudio)));

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI(seriesServiceBaseUrl+"/series/studio/CoMix%20Wave%20Films")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(responseList)));

        mockMvc.perform(get("/studio/{studioName}/series", "CoMix Wave Films"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.series.length()").value(2))
                .andExpect(jsonPath("$.name").value("CoMix Wave Films"));
    }

    @Test
    public void givenSeriesName_whenGetSeriesByNameWithCharacters_thenReturnJsonSeries() throws Exception {

        List<AnimeCharacter> responseList = new ArrayList<>();
        responseList.add(characterNarutoUzumaki);
        responseList.add(characterSakuraHaruno);

        List<AnimeCharacter> emptyCharacters = new ArrayList<>();

        //Series with 2 characters
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI(seriesServiceBaseUrl+"/series/name/Naruto")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(seriesNaruto)));

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI(characterServiceBaseUrl+"/characters/anime/Naruto")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(responseList)));

        //series with empty character list
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI(seriesServiceBaseUrl+"/series/name/Naruto")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(seriesNaruto)));

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI(characterServiceBaseUrl+"/characters/anime/Naruto")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(emptyCharacters)));

        mockMvc.perform(get("/series/{seriesName}/characters", "Naruto"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.characters.length()").value(2))
                .andExpect(jsonPath("$.name").value("Naruto"))
                .andExpect(jsonPath("$.characters[0].animeName").value("Naruto"))
                .andExpect(jsonPath("$.characters[1].animeName").value("Naruto"));

        mockMvc.perform(get("/series/{seriesName}/characters", "Naruto"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.characters").doesNotExist());
    }

    @Test
    public void whenGetAllSeries_thenReturnJsonSeries() throws Exception {

        List<AnimeSeries> allSeriesList = new ArrayList<>();
        allSeriesList.add(seriesNaruto);
        allSeriesList.add(seriesStudioCWFName);
        allSeriesList.add(seriesStudioCWFVoices);

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(seriesServiceBaseUrl+"/series")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .body(mapper.writeValueAsString(allSeriesList))
                        .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/series/all"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    public void whenGetAllStudios_thenReturnJsonStudios() throws Exception {

        List<AnimeStudio> allStudiosList = new ArrayList<>();
        allStudiosList.add(studioCoMixWaveFilms);
        allStudiosList.add(studioMAPPA);

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI(studioServiceBaseUrl+"/studios")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .body(mapper.writeValueAsString(allStudiosList))
                        .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/studios/all"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void givenSeriesWithCharacters_whenPutSeriesWithCharacters_thenReturnJsonSeries() throws Exception {

        AnimeSeries modifiedSeries = new AnimeSeries("Pierrot", "Naruto", "ninja", false, 57, 1, 2002);
        modifiedSeries.setId("0000");
        AnimeSeries modifiedSeriesWithCharacters = new AnimeSeries("Pierrot", "Naruto", "ninja", false, 57, 1, 2002);
        modifiedSeriesWithCharacters.setId("0000");

        AnimeCharacter modifiedNaruto = new AnimeCharacter("Naruto", "Naruto Uzumaki", 0, "04/01");
        modifiedNaruto.setId("0000");
        AnimeCharacter modifiedSakura = new AnimeCharacter("Naruto", "Sakura Haruno", 1, "04/01");
        modifiedSakura.setId("0001");

        List<AnimeCharacter> modifiedCharacterList = new ArrayList<>();
        modifiedCharacterList.add(modifiedSakura);
        modifiedCharacterList.add(modifiedNaruto);

        List<AnimeCharacter> emptyCharacter = new ArrayList<>();

        modifiedSeriesWithCharacters.setCharacters(modifiedCharacterList);

        //default scenario: series with at least one character
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(seriesServiceBaseUrl+"/series/0000")))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(modifiedSeries)));

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(characterServiceBaseUrl+"/characters")))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(modifiedSakura)));

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI(characterServiceBaseUrl+"/characters")))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(modifiedNaruto)));

        //series with no characters
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI(seriesServiceBaseUrl+"/series/0000")))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(modifiedSeries)));

        //series with no empty character list
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI(seriesServiceBaseUrl+"/series/0000")))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(modifiedSeries)));

        mockMvc.perform(put("/series/characters")
                        .content(mapper.writeValueAsString(modifiedSeriesWithCharacters))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.characters.length()").value(2))
                .andExpect(jsonPath("$.genre").value("ninja"))
                .andExpect(jsonPath("$.id").value("0000"));

        mockMvc.perform(put("/series/characters")
                        .content(mapper.writeValueAsString(modifiedSeries))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.characters").doesNotExist())
                .andExpect(jsonPath("$.genre").value("ninja"))
                .andExpect(jsonPath("$.id").value("0000"));

        modifiedSeries.setCharacters(emptyCharacter);
        mockMvc.perform(put("/series/characters")
                        .content(mapper.writeValueAsString(modifiedSeries))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.characters").doesNotExist())
                .andExpect(jsonPath("$.genre").value("ninja"))
                .andExpect(jsonPath("$.id").value("0000"));
    }

    @Test
    public void givenCharacter_whenPostCharacter_thenReturnJsonCharacter() throws Exception {

        AnimeCharacter characterLain = new AnimeCharacter("Serial Experiments Lain", "Iwakura Lain", 0, "");
        AnimeCharacter characterLainWId = new AnimeCharacter("Serial Experiments Lain", "Iwakura Lain", 0, "");
        characterLainWId.setId("0000");

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI(characterServiceBaseUrl+"/characters")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(characterLainWId)));

        mockMvc.perform(post("/characters")
                        .content(mapper.writeValueAsString(characterLain))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Iwakura Lain"));
    }

    @Test
    public void givenCharacterId_whenPostCharacter_thenReturnVoid() throws Exception {

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(characterServiceBaseUrl+"/characters/0")))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK));

        mockMvc.perform(delete("/characters/0"))
                .andExpect(status().isOk());
    }
}