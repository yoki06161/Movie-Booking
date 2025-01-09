package com.example.moviebooking.repository;

import com.example.moviebooking.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    // 인기순으로 정렬
    List<Movie> findAllByOrderByReservationCountDesc();
}
