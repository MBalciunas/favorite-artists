package com.favorite.artists.domain.services;

import com.favorite.artists.domain.models.Artist;
import com.favorite.artists.persistence.ArtistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtistSaver {

    private final ArtistRepository artistRepository;

    public Artist saveFavoriteArtist(Artist artist) {
        artist.setAlbumsUpdatedAt(LocalDateTime.now());
        return save(artist);
    }

    public Artist save(Artist artist) {
        return artistRepository.save(artist);
    }
}
