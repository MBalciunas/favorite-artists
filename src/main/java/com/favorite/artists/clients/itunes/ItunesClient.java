package com.favorite.artists.clients.itunes;

import com.favorite.artists.clients.itunes.entities.ItunesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "itunes-client", url = "${itunes.apiUrl}")
public interface ItunesClient {

    @GetMapping("/search?entity=allArtist&term={name}")
    ItunesResponse searchArtistsByName(@PathVariable("name") String name);

    @GetMapping(value = "/lookup?id={artistId}&entity=album&limit={limit}")
    ItunesResponse fetchAlbumsById(@PathVariable("artistId") int artistId, @PathVariable("limit") int limit);
}