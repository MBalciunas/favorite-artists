package com.favorite.artists.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FavoriteArtistIsNotAssignedException extends RuntimeException {
    public FavoriteArtistIsNotAssignedException(String message) {
        super(message);
    }
}
