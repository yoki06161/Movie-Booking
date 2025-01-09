package com.example.moviebooking.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private int runningTime; // 상영 시간(분)
    private int reservationCount; // 예매 횟수 (인기 지표)

}
