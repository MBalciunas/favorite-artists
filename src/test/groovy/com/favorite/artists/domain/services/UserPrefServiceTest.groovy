package com.favorite.artists.domain.services

import com.favorite.artists.domain.exceptions.FavoriteArtistIsNotAssignedException
import com.favorite.artists.domain.exceptions.UserNotFoundException
import com.favorite.artists.domain.models.Album
import com.favorite.artists.domain.models.Artist
import com.favorite.artists.domain.models.UserPref
import com.favorite.artists.persistence.UserPrefRepository
import org.spockframework.spring.SpringBean
import spock.lang.Specification

class UserPrefServiceTest extends Specification {

    UserPrefService userPrefService

    @SpringBean
    TopAlbumsService topAlbumsService = Mock()

    @SpringBean
    ArtistSaver artistSaver = Mock()

    @SpringBean
    UserPrefRepository userPrefRepository = Mock()

    def setup() {
        userPrefService = new UserPrefService(topAlbumsService, artistSaver, userPrefRepository)
    }

    def "test fetch top 5 albums"() {
        given:
        def fetchedAlbums = [new Album(title: "first"),
                             new Album(title: "second"),
                             new Album(title: "third"),
                             new Album(title: "fourth"),
                             new Album(title: "fifth")]

        def userId = "userId"
        def userPref = new UserPref(userId: userId, favoriteArtist: new Artist(artistId: 1))

        when:
        def actualAlbums = userPrefService.fetchTop5ArtistAlbums(userId)

        then:
        1 * userPrefRepository.findByUserId(userId) >> Optional.of(userPref)
        1 * topAlbumsService.fetchTop5Albums(userPref.getFavoriteArtist()) >> fetchedAlbums
        actualAlbums == fetchedAlbums
    }

    def "test fetch top 5 albums when user is missing"() {
        given:
        def userId = "userId"

        when:
        userPrefService.fetchTop5ArtistAlbums(userId)

        then:
        1 * userPrefRepository.findByUserId(userId) >> Optional.empty()
        0 * topAlbumsService.fetchTop5Albums(_)
        def e = thrown(UserNotFoundException)
        e.message == "User with id=(userId) was not found"
    }

    def "test fetch top 5 albums when favorite artist is not assigned"() {
        given:
        def userId = "userId"
        def userPref = new UserPref(userId: userId)

        when:
        userPrefService.fetchTop5ArtistAlbums(userId)
        then:
        1 * userPrefRepository.findByUserId(userId) >> Optional.of(userPref)
        0 * topAlbumsService.fetchTop5Albums(_)
        def e = thrown(FavoriteArtistIsNotAssignedException)
        e.message == "User with id=(userId) doesn't have a favorite artist"
    }

    def "test save favorite artist with existing user"() {
        given:
        def userId = "userId"
        def artist = new Artist(artistId: 1)
        def userPref = new UserPref(userId: userId)

        when:
        userPrefService.saveFavoriteArtist(artist, userId)

        then:
        1 * userPrefRepository.findByUserId(userId) >> Optional.of(userPref)
        1 * artistSaver.saveFavoriteArtist(artist) >> artist
        1 * userPrefRepository.save(new UserPref(userId: userId, favoriteArtist: artist))
    }

    def "test save favorite artist with new user"() {
        given:
        def userId = "userId"
        def artist = new Artist(artistId: 1)

        when:
        userPrefService.saveFavoriteArtist(artist, userId)

        then:
        1 * userPrefRepository.findByUserId(userId) >> Optional.empty()
        1 * artistSaver.saveFavoriteArtist(artist) >> artist
        1 * userPrefRepository.save(new UserPref(userId: userId, favoriteArtist: artist))
    }
}
