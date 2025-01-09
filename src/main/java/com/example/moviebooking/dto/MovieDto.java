package com.example.moviebooking.dto;

import com.example.moviebooking.domain.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDto {
    private Long id;
    private String title;
    private int runningTime;
    private int reservationCount;

    public static MovieDto from(Movie movie) {
        return MovieDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .runningTime(movie.getRunningTime())
                .reservationCount(movie.getReservationCount())
                .build();
    }
}
