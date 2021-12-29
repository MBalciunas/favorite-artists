package com.favorite.artists.api.controllers;

import com.favorite.artists.api.dto.AlbumResponse;
import com.favorite.artists.domain.models.Artist;
import com.favorite.artists.domain.services.UserPrefService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user-prefs")
@RequiredArgsConstructor
public class UserPrefController {

    private final UserPrefService userPrefService;

    @GetMapping("/albums")
    public ResponseEntity<List<AlbumResponse>> fetchTop5FavoriteArtistAlbums(@RequestParam String userId) {
        var topAlbums = userPrefService.fetchTop5ArtistAlbums(userId)
                .stream().map(AlbumResponse::fromAlbum)
                .collect(Collectors.toList());

        return ResponseEntity.ok(topAlbums);
    }

    @PostMapping("/artists")
    public ResponseEntity<?> saveFavoriteArtist(@RequestParam String userId, @RequestBody Artist artist) {
        userPrefService.saveFavoriteArtist(artist, userId);
        return ResponseEntity.noContent().build();
    }
}