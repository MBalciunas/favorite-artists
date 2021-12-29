package com.favorite.artists.clients.itunes;

import com.favorite.artists.clients.itunes.entities.ItunesAlbum;
import com.favorite.artists.clients.itunes.entities.ItunesArtist;
import com.favorite.artists.domain.models.Album;
import com.favorite.artists.domain.models.Artist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItunesService {

    private final ItunesClient itunesClient;
    private final ItunesMapper itunesMapper;

    public List<Artist> fetchArtists(String keyword) {
        return itunesClient.searchArtistsByName(keyword).getResults().stream()
                .filter(itunesEntity -> itunesEntity instanceof ItunesArtist)
                .map(itunesEntity -> (ItunesArtist) itunesEntity)
                .map(itunesMapper::itunesArtistMapper)
                .collect(Collectors.toList());
    }

    public List<Album> fetchTop5Albums(int artistId) {
        return itunesClient.fetchAlbumsById(artistId, 5).getResults().stream()
                .filter(itunesEntity -> itunesEntity instanceof ItunesAlbum)
                .map(itunesEntity -> (ItunesAlbum) itunesEntity)
                .map(itunesMapper::itunesAlbumMapper)
                .collect(Collectors.toList());
    }
}
