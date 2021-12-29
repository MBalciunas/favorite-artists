package com.favorite.artists.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserPref {

    @Id
    @GeneratedValue
    public Long id;
    public String userId;

    @OneToOne
    public Artist favoriteArtist;
}
