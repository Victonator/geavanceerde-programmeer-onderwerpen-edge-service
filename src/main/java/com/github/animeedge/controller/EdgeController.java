package com.github.animeedge.controller;

import com.github.animeedge.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EdgeController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${studioservice.baseurl}")
    private String studioServiceBaseUrl;

    @Value("${seriesservice.baseurl}")
    private String seriesServiceBaseUrl;

    @Value("${characterservice.baseurl}")
    private String characterServiceBaseUrl;

    @GetMapping("/characters/{characterName}")
    public AnimeCharacter getAnimeCharacterByName(@PathVariable String characterName) {
        return restTemplate.getForObject(characterServiceBaseUrl+"/characters/{characterName}",
                AnimeCharacter.class, characterName);
    }

    @GetMapping("/characters/series/{seriesName}")
    public List<AnimeCharacter> getAnimeCharactersBySeriesName(@PathVariable String seriesName) {

        ResponseEntity<List<AnimeCharacter>> responseEntityCharacters =
                restTemplate.exchange(characterServiceBaseUrl + "/characters/anime/{seriesName}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<AnimeCharacter>>() {
                        }, seriesName);

        return responseEntityCharacters.getBody();
    }

    @GetMapping("/studio/{studioName}/series")
    public AnimeStudio getStudioByNameWithSeries(@PathVariable String studioName) {
        ResponseEntity<List<AnimeStudio>> responseEntityStudios =
                restTemplate.exchange(studioServiceBaseUrl + "/studios/{studioName}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<AnimeStudio>>() {
                        }, studioName);

        AnimeStudio studio = responseEntityStudios.getBody().get(0);

        ResponseEntity<List<AnimeSeries>> responseEntitySeries =
                restTemplate.exchange(seriesServiceBaseUrl + "/series/studio/{studioName}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<AnimeSeries>>() {
                        }, studioName);

        studio.setSeries(responseEntitySeries.getBody());

        return studio;
    }

    @GetMapping("/series/{seriesName}/characters")
    public AnimeSeries getSeriesByNameWithCharacters(@PathVariable String seriesName) {
        AnimeSeries series = restTemplate.getForObject(seriesServiceBaseUrl+"/series/name/{seriesName}",
                AnimeSeries.class, seriesName);

        ResponseEntity<List<AnimeCharacter>> responseEntityCharacters =
                restTemplate.exchange(characterServiceBaseUrl + "/characters/anime/{seriesName}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<AnimeCharacter>>() {
                        }, seriesName);

        if(responseEntityCharacters.getBody() == null || responseEntityCharacters.getBody().size() == 0) return series;

        series.setCharacters(responseEntityCharacters.getBody());
        return series;
    }

    @PutMapping("/series/characters")
    public AnimeSeries putSeriesWithCharacters(@RequestBody AnimeSeries series) {

        ResponseEntity<AnimeSeries> responseEntityModSeries =
                restTemplate.exchange(seriesServiceBaseUrl + "/series/"+series.getId(),
                        HttpMethod.PUT, new HttpEntity<>(new AnimeSeriesDTO(series)), AnimeSeries.class);

        AnimeSeries modSeries = responseEntityModSeries.getBody();
        if(modSeries == null) return null;

        List<AnimeCharacter> modCharacters = new ArrayList<>();

        if(series.getCharacters() != null && series.getCharacters().size() > 0) {
            for(AnimeCharacter character : series.getCharacters()) {

                if(!series.getName().equals(character.getAnimeName())) {
                    character.setAnimeName(series.getName());
                }

                modCharacters.add(
                        restTemplate.exchange(characterServiceBaseUrl+"/characters",
                                HttpMethod.PUT, new HttpEntity<>(character), AnimeCharacter.class).getBody()
                );
            }

            modSeries.setCharacters(modCharacters);
        }

        return modSeries;
    }

    @PostMapping("/characters")
    public AnimeCharacter postCharacter(@RequestBody AnimeCharacter character) {

        return restTemplate.exchange(characterServiceBaseUrl+"/characters",
                HttpMethod.POST, new HttpEntity<>(new AnimeCharacterDTO(character)), AnimeCharacter.class).getBody();
    }

    @DeleteMapping("/characters/{characterId}")
    public void deleteCharacter(@PathVariable String characterId) {
        restTemplate.delete(characterServiceBaseUrl+"/characters/{characterId}", characterId);
    }
}
