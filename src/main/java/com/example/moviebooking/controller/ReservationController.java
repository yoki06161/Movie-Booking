package com.example.moviebooking.controller;

import com.example.moviebooking.domain.Reservation;
import com.example.moviebooking.service.ReservationService;
import com.example.moviebooking.util.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
@Tag(name = "Reservation API", description = "영화 좌석 예매 관련 API")
public class ReservationController {

    private final ReservationService reservationService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "좌석 예매",
               description = "특정 Seat ID에 대한 예매를 처리합니다. Authorization 헤더에 JWT 토큰이 필요합니다.")
    @PostMapping("/{seatId}")
    public ResponseEntity<Reservation> reserveSeat(
            @Parameter(name = "seatId", description = "예매할 좌석의 ID", in = ParameterIn.PATH, required = true)
            @PathVariable("seatId") Long seatId,
            @RequestHeader("Authorization") String authHeader
    ) {
        // Bearer 토큰에서 userId 추출
        String token = authHeader.replace("Bearer ", "");
        Long userId = jwtTokenProvider.getUserIdFromToken(token);

        Reservation reservation = reservationService.reserveSeat(seatId, userId);
        return ResponseEntity.ok(reservation);
    }
}
