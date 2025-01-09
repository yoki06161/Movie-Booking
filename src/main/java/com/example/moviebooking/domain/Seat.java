package com.example.moviebooking.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int seatNumber; // 좌석 번호
    private boolean reserved; // 예매 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule; // 어떤 상영 스케줄에 속한 좌석인지
}
