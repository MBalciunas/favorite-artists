package com.favorite.artists.api.controllers;

import com.favorite.artists.api.PageBuilder;
import com.favorite.artists.api.dto.ArtistResponse;
import com.favorite.artists.domain.services.ArtistSearcher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistSearcher artistSearcher;
    private final PageBuilder<ArtistResponse> pageBuilder;

    @GetMapping
    public ResponseEntity<Page<ArtistResponse>> fetchArtistsByKeyword(
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam String artistName) {

        var artists = artistSearcher.searchForArtist(artistName)
                .stream().map(ArtistResponse::fromArtist)
                .collect(Collectors.toList());

        return ResponseEntity.ok(pageBuilder.buildPageFrom(page, size, artists));
    }
}
