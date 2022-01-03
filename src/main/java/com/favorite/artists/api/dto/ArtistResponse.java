package com.favorite.artists.api.dto;

import com.favorite.artists.domain.models.Artist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtistResponse {

    private Long id;
    private String name;
    private String itunesUrl;
    private int amgArtistId;
    private int artistId;
    private String primaryGenre;
    private int primaryGenreId;

    public static ArtistResponse fromArtist(Artist artist) {
        return ArtistResponse.builder()
                .id(artist.getId())
                .amgArtistId(artist.getAmgArtistId())
                .artistId(artist.getArtistId())
                .itunesUrl(artist.getItunesUrl())
                .name(artist.getName())
                .primaryGenre(artist.getPrimaryGenre())
                .primaryGenreId(artist.getPrimaryGenreId())
                .build();
    }
}
