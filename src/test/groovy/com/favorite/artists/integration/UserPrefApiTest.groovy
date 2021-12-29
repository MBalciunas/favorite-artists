package com.favorite.artists.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.favorite.artists.api.dto.AlbumResponse
import com.favorite.artists.clients.itunes.ItunesClient
import com.favorite.artists.clients.itunes.entities.ItunesAlbum
import com.favorite.artists.clients.itunes.entities.ItunesArtist
import com.favorite.artists.clients.itunes.entities.ItunesResponse
import com.favorite.artists.domain.models.Artist
import com.favorite.artists.domain.models.UserPref
import com.favorite.artists.persistence.UserPrefRepository
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserPrefApiTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    UserPrefRepository userPrefRepository

    @SpringBean
    ItunesClient itunesClient = Mock()

    @Autowired
    ObjectMapper objectMapper

    def setup() {
        userPrefRepository.deleteAll()
    }

    def "test save user pref and fetch top albums"() {
        expect:
        mockItunesClient()

        this.mockMvc.perform(
                post(
                        "/user-prefs/artists?userId=123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Artist(name: "Eminem", artistId: 111051, itunesUrl: "itunesUrl1", amgArtistId: 347307, primaryGenre: "Hip-Hop/Rap", primaryGenreId: 18))))
                .andExpect(status().isNoContent())

        def result = this.mockMvc.perform(
                get("/user-prefs/albums?userId=123"))
                .andExpect(status().isOk())
                .andReturn()

        result.getResponse().getContentAsString() == objectMapper.writeValueAsString(albums)
    }

    def "test fail fetch top albums with user missing"() {
        expect:
        this.mockMvc.perform(
                get("/user-prefs/albums?userId=123"))
                .andExpect(status().isBadRequest())
    }

    def "test save user pref and fail fetch top albums without saved artist"() {
        expect:
        userPrefRepository.save(new UserPref(userId: 123))

        this.mockMvc.perform(
                get("/user-prefs/albums?userId=123"))
                .andExpect(status().isBadRequest())
    }

    private void mockItunesClient() {
        itunesClient.fetchAlbumsById(111051, 5) >> new ItunesResponse(3, [
                new ItunesArtist(artistName: "eminem1", artistId: 1, amgArtistId: 1, primaryGenreName: "rap1", primaryGenreId: 1, artistLinkUrl: "itunesUrl1"),
                new ItunesAlbum(collectionName: "eminemAlbum1", artistName: "eminem1", artistId: 1, amgArtistId: 1, primaryGenreName: "rap1", collectionViewUrl: "itunesUrl1", artistViewUrl: "itunesUrl1", trackCount: 5),
                new ItunesAlbum(collectionName: "eminemAlbum2", artistName: "eminem1", artistId: 1, amgArtistId: 1, primaryGenreName: "rap2", collectionViewUrl: "itunesUrl2", artistViewUrl: "itunesUrl2", trackCount: 6),
                new ItunesAlbum(collectionName: "eminemAlbum3", artistName: "eminem1", artistId: 1, amgArtistId: 1, primaryGenreName: "rap3", collectionViewUrl: "itunesUrl3", artistViewUrl: "itunesUrl3", trackCount: 7),
        ])
    }

    def albums = [
            new AlbumResponse(id: 3, title: "eminemAlbum1", artistName: "eminem1", albumUrl: "itunesUrl1", trackCount: 5),
            new AlbumResponse(id: 4, title: "eminemAlbum2", artistName: "eminem1", albumUrl: "itunesUrl2", trackCount: 6),
            new AlbumResponse(id: 5, title: "eminemAlbum3", artistName: "eminem1", albumUrl: "itunesUrl3", trackCount: 7),
    ]
}
