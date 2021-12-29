package com.favorite.artists.domain.services;

import com.favorite.artists.domain.exceptions.FavoriteArtistIsNotAssignedException;
import com.favorite.artists.domain.exceptions.UserNotFoundException;
import com.favorite.artists.domain.models.Album;
import com.favorite.artists.domain.models.Artist;
import com.favorite.artists.domain.models.UserPref;
import com.favorite.artists.persistence.UserPrefRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserPrefService {

    private final TopAlbumsService topAlbumsService;
    private final ArtistSaver artistSaver;
    private final UserPrefRepository userPrefRepository;

    public List<Album> fetchTop5ArtistAlbums(String userId) {
        var user = getUser(userId);
        var favoriteArtist = getFavoriteArtist(userId, user);
        
        return topAlbumsService.fetchTop5Albums(favoriteArtist);
    }

    public void saveFavoriteArtist(Artist artist, String userId) {

        var userPref = userPrefRepository
                .findByUserId(userId)
                .orElseGet(() -> UserPref.builder().userId(userId).build());

        artist = artistSaver.saveFavoriteArtist(artist);

        userPref.setFavoriteArtist(artist);
        userPrefRepository.save(userPref);
    }

    private Artist getFavoriteArtist(String userId, UserPref user) {
        return Optional.ofNullable(user.getFavoriteArtist())
                .orElseThrow(() -> {
                    String errorMessage = format("User with id=(%s) doesn't have a favorite artist", userId);
                    log.error(errorMessage);
                    return new FavoriteArtistIsNotAssignedException(errorMessage);
                });
    }

    private UserPref getUser(String userId) {
        return userPrefRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    String errorMessage = format("User with id=(%s) was not found", userId);
                    log.error(errorMessage);
                    return new UserNotFoundException(errorMessage);
                });
    }

}
