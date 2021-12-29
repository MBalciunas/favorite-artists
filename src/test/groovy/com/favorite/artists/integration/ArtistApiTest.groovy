package com.favorite.artists.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.favorite.artists.api.dto.ArtistResponse
import com.favorite.artists.clients.itunes.ItunesClient
import com.favorite.artists.clients.itunes.entities.ItunesArtist
import com.favorite.artists.clients.itunes.entities.ItunesResponse
import com.favorite.artists.persistence.UserPrefRepository
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Sort
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import com.favorite.artists.config.PageJacksonModule.SimplePageImpl

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class ArtistApiTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    UserPrefRepository userPrefRepository

    @SpringBean
    ItunesClient itunesClient = Mock()

    @Autowired
    ObjectMapper objectMapper

    def "test save user pref and fetch top albums"() {
        expect:
        mockItunesClient()

        def result = this.mockMvc.perform(
                get("/artists?page=0&size=2&artistName=eminem"))
                .andExpect(status().isOk())
                .andReturn()

        def artistPage = objectMapper.readValue(result.getResponse().getContentAsString(), SimplePageImpl<ArtistResponse>.class)

        artistPage.getNumberOfElements() == 2
        artistPage.getTotalPages() == 2
        artistPage.getSort() == Sort.unsorted()
        artistPage.getSize() == 2
        (ArtistResponse) artistPage.getContent().get(0) == new ArtistResponse(name: "eminem1", artistId: 1, itunesUrl: "itunesUrl1", amgArtistId: 1, primaryGenre: "rap1", primaryGenreId: 1)
        (ArtistResponse) artistPage.getContent().get(1) == new ArtistResponse(name: "eminem2", artistId: 2, itunesUrl: "itunesUrl2", amgArtistId: 2, primaryGenre: "rap2", primaryGenreId: 2)

    }

    private void mockItunesClient() {
        itunesClient.searchArtistsByName("eminem") >> new ItunesResponse(3, [
                new ItunesArtist(artistName: "eminem1", artistId: 1, amgArtistId: 1, primaryGenreName: "rap1", primaryGenreId: 1, artistLinkUrl: "itunesUrl1"),
                new ItunesArtist(artistName: "eminem2", artistId: 2, amgArtistId: 2, primaryGenreName: "rap2", primaryGenreId: 2, artistLinkUrl: "itunesUrl2"),
                new ItunesArtist(artistName: "eminem3", artistId: 3, amgArtistId: 3, primaryGenreName: "rap3", primaryGenreId: 3, artistLinkUrl: "itunesUrl3"),
        ])
    }
}