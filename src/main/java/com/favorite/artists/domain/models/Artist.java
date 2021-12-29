package com.favorite.artists.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Artist {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String itunesUrl;
    private int amgArtistId;
    private int artistId;
    private String primaryGenre;
    private int primaryGenreId;

    @OneToMany(
            mappedBy = "artist",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Album> top5Albums;

    private LocalDateTime albumsUpdatedAt;
}
