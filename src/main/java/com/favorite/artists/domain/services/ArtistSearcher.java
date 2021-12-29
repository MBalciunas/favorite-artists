package com.favorite.artists.domain.services;

import com.favorite.artists.clients.itunes.ItunesService;
import com.favorite.artists.domain.models.Artist;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtistSearcher {

    private final ItunesService itunesService;

    public List<Artist> searchForArtist(String keyword) {
        return itunesService.fetchArtists(keyword);
    }
}
