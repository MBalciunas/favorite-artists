package com.favorite.artists.persistence;

import com.favorite.artists.domain.models.UserPref;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPrefRepository extends CrudRepository<UserPref, Long> {
    Optional<UserPref> findByUserId(String userId);
}
