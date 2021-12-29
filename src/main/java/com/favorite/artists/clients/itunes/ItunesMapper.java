package com.favorite.artists.clients.itunes;

import com.favorite.artists.clients.itunes.entities.ItunesAlbum;
import com.favorite.artists.clients.itunes.entities.ItunesArtist;
import com.favorite.artists.domain.models.Album;
import com.favorite.artists.domain.models.Artist;
import org.springframework.stereotype.Service;

@Service
public class ItunesMapper {

    public Album itunesAlbumMapper(ItunesAlbum itunesAlbum) {
        return Album.builder()
                .artistName(itunesAlbum.getArtistName())
                .title(itunesAlbum.getCollectionName())
                .trackCount(itunesAlbum.getTrackCount())
                .albumUrl(itunesAlbum.getCollectionViewUrl())
                .build();
    }

    public Artist itunesArtistMapper(ItunesArtist itunesArtist) {
        return Artist.builder()
                .name(itunesArtist.getArtistName())
                .artistId(itunesArtist.getArtistId())
                .primaryGenre(itunesArtist.getPrimaryGenreName())
                .primaryGenreId(itunesArtist.getPrimaryGenreId())
                .amgArtistId(itunesArtist.getAmgArtistId())
                .itunesUrl(itunesArtist.getArtistLinkUrl())
                .build();
    }
}
