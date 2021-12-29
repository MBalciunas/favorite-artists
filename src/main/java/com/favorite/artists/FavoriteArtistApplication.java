package com.favorite.artists;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FavoriteArtistApplication {

	public static void main(String[] args) {
		SpringApplication.run(FavoriteArtistApplication.class, args);
	}
}
