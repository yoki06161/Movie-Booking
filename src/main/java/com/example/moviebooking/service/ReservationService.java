package com.example.moviebooking.service;

import com.example.moviebooking.domain.Reservation;
import com.example.moviebooking.domain.Seat;
import com.example.moviebooking.repository.ReservationRepository;
import com.example.moviebooking.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;

    /**
     * 특정 좌석을 예매하는 메서드
     */
    @Transactional
    public Reservation reserveSeat(Long seatId, Long userId) {
        // 비관적 락 (SELECT FOR UPDATE)
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        if (seat.isReserved()) {
            throw new RuntimeException("이미 예매된 좌석입니다.");
        }

        seat.setReserved(true);

        Reservation reservation = Reservation.builder()
                .userId(userId)
                .seatId(seat.getId())
                .build();

        return reservationRepository.save(reservation);
    }
}
