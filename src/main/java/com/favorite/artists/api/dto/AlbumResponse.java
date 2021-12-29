package com.favorite.artists.api.dto;

import com.favorite.artists.domain.models.Album;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumResponse {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String artistName;
    private String albumUrl;
    private int trackCount;

    public static AlbumResponse fromAlbum(Album album) {

        return AlbumResponse.builder()
                .id(album.getId())
                .albumUrl(album.getAlbumUrl())
                .artistName(album.getArtistName())
                .title(album.getTitle())
                .trackCount(album.getTrackCount())
                .build();
    }
}
