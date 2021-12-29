package com.favorite.artists.domain.services

import com.favorite.artists.domain.models.Artist
import com.favorite.artists.persistence.ArtistRepository
import org.spockframework.spring.SpringBean
import spock.lang.Specification

class ArtistSaverTest extends Specification {

    ArtistSaver artistSaver

    @SpringBean
    ArtistRepository artistRepository = Mock()

    def setup() {
        artistSaver = new ArtistSaver(artistRepository)
    }

    def "SaveFavoriteArtist"() {
        given:
        def artist = new Artist(name: "eminem")
        when:
        artistSaver.saveFavoriteArtist(artist)
        then:
        1 * artistRepository.save(artist) >> artist
        artist.getAlbumsUpdatedAt() != null
    }
}
