package com.favorite.artists.clients.itunes.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.*;

@JsonTypeInfo(
        use = Id.NAME,
        property = "wrapperType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ItunesAlbum.class, name = "collection"),
        @JsonSubTypes.Type(value = ItunesArtist.class, name = "artist"),
})
public interface ItunesEntity {
}
