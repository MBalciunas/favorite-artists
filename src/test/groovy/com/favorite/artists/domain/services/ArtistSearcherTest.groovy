package com.favorite.artists.domain.services

import com.favorite.artists.clients.itunes.ItunesService
import com.favorite.artists.domain.models.Artist
import org.spockframework.spring.SpringBean
import spock.lang.Specification

class ArtistSearcherTest extends Specification {

    ArtistSearcher artistSearcher

    @SpringBean
    ItunesService itunesService = Mock()

    def setup() {
        artistSearcher = new ArtistSearcher(itunesService)
    }

    def "search for artist by keyword"() {
        given:
        def keyword = "eminem"
        when:
        def artists = artistSearcher.searchForArtist(keyword)
        then:
        1 * itunesService.fetchArtists(keyword) >> [new Artist(name: "eminem1"), new Artist(name: "eminem2")]
        artists == [new Artist(name: "eminem1"), new Artist(name: "eminem2")]
    }
}
