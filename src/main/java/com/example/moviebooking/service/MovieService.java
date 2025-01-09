package com.example.moviebooking.service;

import com.example.moviebooking.domain.Movie;
import com.example.moviebooking.dto.MovieDto;
import com.example.moviebooking.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String POPULAR_MOVIES_KEY = "popular_movies";

    public List<MovieDto> getPopularMovies() {
        // 캐시 조회
        List<MovieDto> cached = (List<MovieDto>) redisTemplate.opsForValue().get(POPULAR_MOVIES_KEY);
        if (cached != null) {
            return cached;
        }
        // DB 조회
        List<Movie> movies = movieRepository.findAllByOrderByReservationCountDesc();
        List<MovieDto> result = movies.stream().map(MovieDto::from).collect(Collectors.toList());
        // 캐시에 저장
        redisTemplate.opsForValue().set(POPULAR_MOVIES_KEY, result, 1, TimeUnit.HOURS);
        return result;
    }
}
