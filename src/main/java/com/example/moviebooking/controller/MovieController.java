package com.example.moviebooking.controller;

import com.example.moviebooking.dto.MovieDto;
import com.example.moviebooking.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/popular")
    public ResponseEntity<List<MovieDto>> getPopularMovies() {
        List<MovieDto> movieList = movieService.getPopularMovies();
        return ResponseEntity.ok(movieList);
    }
}
