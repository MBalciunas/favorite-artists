package com.favorite.artists.api

import com.favorite.artists.api.dto.ArtistResponse
import spock.lang.Specification

class PageBuilderTest extends Specification {

    PageBuilder<ArtistResponse> pageBuilder

    def setup() {
        pageBuilder = new PageBuilder()
    }

    def "test api page from artist"() {
        given:
        int page = 0
        int size = 2
        def artists = [
                new ArtistResponse(name: "eminem1", artistId: 1, itunesUrl: "itunesUrl1", amgArtistId: 1, primaryGenre: "rap1", primaryGenreId: 1),
                new ArtistResponse(name: "eminem2", artistId: 2, itunesUrl: "itunesUrl2", amgArtistId: 2, primaryGenre: "rap2", primaryGenreId: 2),
                new ArtistResponse(name: "eminem3", artistId: 3, itunesUrl: "itunesUrl3", amgArtistId: 3, primaryGenre: "rap3", primaryGenreId: 3),
        ]

        when:
        def pagedArtists = pageBuilder.buildPageFrom(page, size, artists)

        then:
        pagedArtists.getTotalElements() == 3
        pagedArtists.getTotalPages() == 2
        pagedArtists.getContent() == artists.subList(0, 2)
        pagedArtists.getNumberOfElements() == 2
    }
}
