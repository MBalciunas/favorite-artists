package com.favorite.artists.persistence;

import com.favorite.artists.domain.models.Album;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumsRepository extends CrudRepository<Album, Long> {
}
