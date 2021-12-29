package com.favorite.artists.domain.services

import com.favorite.artists.clients.itunes.ItunesService
import com.favorite.artists.domain.models.Album
import com.favorite.artists.domain.models.Artist
import com.favorite.artists.persistence.AlbumsRepository
import org.spockframework.spring.SpringBean
import spock.lang.Specification

import java.time.LocalDateTime

class TopAlbumsServiceTest extends Specification {

    TopAlbumsService topAlbumsService

    @SpringBean
    ItunesService itunesService = Mock()

    @SpringBean
    AlbumsRepository albumsRepository = Mock()

    @SpringBean
    ArtistSaver artistSaver = Mock()

    def setup() {
        topAlbumsService = new TopAlbumsService(itunesService, artistSaver, albumsRepository)
    }

    def "test fetch top 5 albums when refresh time is not expired"() {
        given:
        def artist = new Artist(albumsUpdatedAt: LocalDateTime.MAX,
                top5Albums:
                        [new Album(title: "first"),
                         new Album(title: "second"),
                         new Album(title: "third"),
                         new Album(title: "fourth"),
                         new Album(title: "fifth")]
        )

        when:
        def albums = topAlbumsService.fetchTop5Albums(artist)
        then:
        albums == artist.getTop5Albums()
        0 * itunesService.fetchTop5Albums(_)
    }

    def "test fetch top 5 albums when refresh time is expired"() {
        def fetchedAlbums = [new Album(title: "first"),
                             new Album(title: "second"),
                             new Album(title: "third"),
                             new Album(title: "fourth"),
                             new Album(title: "fifth")]

        def expectedArtist = new Artist(artistId: 1, albumsUpdatedAt: LocalDateTime.MIN, top5Albums: fetchedAlbums)
        given:
        def artist = new Artist(artistId: 1, albumsUpdatedAt: LocalDateTime.MIN)

        when:
        def albums = topAlbumsService.fetchTop5Albums(artist)
        then:
        1 * itunesService.fetchTop5Albums(artist.artistId) >> fetchedAlbums
        1 * albumsRepository.saveAll(fetchedAlbums) >> fetchedAlbums
        1 * artistSaver.save(_) >> expectedArtist
        albums == fetchedAlbums
    }
}
