package com.favorite.artists.clients.itunes.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItunesAlbum implements ItunesEntity {

    private String collectionType;
    private int artistId;
    private int collectionId;
    private int amgArtistId;
    private String artistName;
    private String collectionName;
    private String collectionCensoredName;
    private String artistViewUrl;
    private String collectionViewUrl;
    private String artworkUrl60;
    private String artworkUrl100;
    private double collectionPrice;
    private String collectionExplicitness;
    private int trackCount;
    private String copyright;
    private String country;
    private String currency;
    private LocalDateTime releaseDate;
    private String primaryGenreName;
}
