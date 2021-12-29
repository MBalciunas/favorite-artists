package com.favorite.artists.clients.itunes.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItunesArtist implements ItunesEntity {

    private String artistType;
    private String artistName;
    private String artistLinkUrl;
    private int artistId;
    private int amgArtistId;
    private String primaryGenreName;
    private int primaryGenreId;
}
