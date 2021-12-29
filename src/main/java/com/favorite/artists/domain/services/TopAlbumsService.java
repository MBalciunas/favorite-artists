package com.favorite.artists.domain.services;

import com.favorite.artists.clients.itunes.ItunesService;
import com.favorite.artists.domain.models.Album;
import com.favorite.artists.domain.models.Artist;
import com.favorite.artists.persistence.AlbumsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopAlbumsService {

    private final ItunesService itunesService;
    private final ArtistSaver artistSaver;
    private final AlbumsRepository albumsRepository;

    @Value("${artist.topAlbums.refreshTimeInHours}")
    private int refreshTimeInHours;

    public List<Album> fetchTop5Albums(Artist artist) {

        if(isTimeToRefreshAlbums(artist)) {
            artist = fetchTopAlbums(artist);
        }
        return artist.getTop5Albums();
    }

    private Artist fetchTopAlbums(Artist artist) {
        var topAlbums = itunesService.fetchTop5Albums(artist.getArtistId());

        topAlbums = (List<Album>) albumsRepository.saveAll(topAlbums);
        artist.setAlbumsUpdatedAt(LocalDateTime.now());
        artist.setTop5Albums(topAlbums);
        artist = artistSaver.save(artist);
        return artist;
    }

    private boolean isTimeToRefreshAlbums(Artist artist) {
        return artist.getAlbumsUpdatedAt().plusHours(refreshTimeInHours).isBefore(LocalDateTime.now())
                || artist.getTop5Albums().isEmpty();
    }
}
