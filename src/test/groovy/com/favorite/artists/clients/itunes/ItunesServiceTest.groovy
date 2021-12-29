package com.favorite.artists.clients.itunes

import com.favorite.artists.clients.itunes.entities.ItunesAlbum
import com.favorite.artists.clients.itunes.entities.ItunesArtist
import com.favorite.artists.clients.itunes.entities.ItunesResponse
import com.favorite.artists.domain.models.Album
import com.favorite.artists.domain.models.Artist
import org.spockframework.spring.SpringBean
import spock.lang.Specification

class ItunesServiceTest extends Specification {

    @SpringBean
    ItunesClient itunesClient = Mock()

    ItunesService itunesService

    def setup() {
        itunesService = new ItunesService(itunesClient, new ItunesMapper())
    }

    def "test fetch and convert artists from itunes"() {
        given:
        def artistName = "eminem"
        def expectedArtists = [
                new Artist(name: "eminem1", artistId: 1, itunesUrl: "itunesUrl1", amgArtistId: 1, primaryGenre: "rap1", primaryGenreId: 1),
                new Artist(name: "eminem2", artistId: 2, itunesUrl: "itunesUrl2", amgArtistId: 2, primaryGenre: "rap2", primaryGenreId: 2),
                new Artist(name: "eminem3", artistId: 3, itunesUrl: "itunesUrl3", amgArtistId: 3, primaryGenre: "rap3", primaryGenreId: 3),
        ]
        when:
        def actualArtists = itunesService.fetchArtists(artistName)
        then:
        1 * itunesClient.searchArtistsByName(artistName) >> new ItunesResponse(3, [
                new ItunesArtist(artistName: "eminem1", artistId: 1, amgArtistId: 1, primaryGenreName: "rap1", primaryGenreId: 1, artistLinkUrl: "itunesUrl1"),
                new ItunesArtist(artistName: "eminem2", artistId: 2, amgArtistId: 2, primaryGenreName: "rap2", primaryGenreId: 2, artistLinkUrl: "itunesUrl2"),
                new ItunesArtist(artistName: "eminem3", artistId: 3, amgArtistId: 3, primaryGenreName: "rap3", primaryGenreId: 3, artistLinkUrl: "itunesUrl3"),
        ])

        actualArtists == expectedArtists
    }

    def "test fetch and convert albums from itunes"() {
        given:
        def artistId = 1
        def expectedAlbums = [
                new Album(title: "eminemAlbum1", artistName: "eminem1", albumUrl: "itunesUrl1", trackCount: 5),
                new Album(title: "eminemAlbum2", artistName: "eminem1", albumUrl: "itunesUrl2", trackCount: 6),
                new Album(title: "eminemAlbum3", artistName: "eminem1", albumUrl: "itunesUrl3", trackCount: 7),
        ]
        when:
        def actualAlbums = itunesService.fetchTop5Albums(artistId)
        then:
        1 * itunesClient.fetchAlbumsById(artistId, 5) >> new ItunesResponse(3, [
                new ItunesArtist(artistName: "eminem1", artistId: 1, amgArtistId: 1, primaryGenreName: "rap1", primaryGenreId: 1, artistLinkUrl: "itunesUrl1"),
                new ItunesAlbum(collectionName: "eminemAlbum1", artistName: "eminem1", artistId: 1, amgArtistId: 1, primaryGenreName: "rap1", collectionViewUrl: "itunesUrl1", artistViewUrl: "itunesUrl1", trackCount: 5),
                new ItunesAlbum(collectionName: "eminemAlbum2", artistName: "eminem1", artistId: 1, amgArtistId: 1, primaryGenreName: "rap2", collectionViewUrl: "itunesUrl2", artistViewUrl: "itunesUrl2", trackCount: 6),
                new ItunesAlbum(collectionName: "eminemAlbum3", artistName: "eminem1", artistId: 1, amgArtistId: 1, primaryGenreName: "rap3", collectionViewUrl: "itunesUrl3", artistViewUrl: "itunesUrl3", trackCount: 7),
        ])

        actualAlbums == expectedAlbums
    }
}
