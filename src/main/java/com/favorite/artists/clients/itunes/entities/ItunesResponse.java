package com.favorite.artists.clients.itunes.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ItunesResponse {

    private int resultCount;
    private List<ItunesEntity> results;
}