package com.favorite.artists.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Album {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String artistName;
    private String albumUrl;
    private int trackCount;

    @ManyToOne
    private Artist artist;
}
