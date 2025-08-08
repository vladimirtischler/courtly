package com.courtly.controller;

import com.courtly.dto.ApiResponse;
import com.courtly.dto.ReservationDto;
import com.courtly.entity.Reservation;
import com.courtly.service.ReservationService;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/reservations")
public class ReservationController extends CommonController<Reservation, ReservationDto, ReservationService>{
    private final ReservationService reservationService;

    public ReservationController(ReservationService service) {
        super(service);
        this.reservationService = service;
    }

    @GetMapping("court/{courtId}")
    public ResponseEntity<ApiResponse<List<ReservationDto>>> findAllByCourtId(@PathVariable(name="courtId") Long courtId, @RequestParam(name=
            "order", required = false) String order) {

        ApiResponse<List<ReservationDto>> response = new ApiResponse<>();
        try {
            response.setData(reservationService.findByCourtId(courtId, !"DESC".equalsIgnoreCase(order)));
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e){
            response.setErrors(List.of(e.getMessage()));
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("phone-number/{phoneNumber}")
    public ResponseEntity<ApiResponse<List<ReservationDto>>> findAllByPhoneNumber(@PathVariable(name="phoneNumber") String phoneNumber,
                                                      @RequestParam(name= "inFuture", required = false) Boolean inFuture) {

        ApiResponse<List<ReservationDto>> response = new ApiResponse<>();
        response.setData(reservationService.findByPhoneNumber(phoneNumber, Boolean.TRUE.equals(inFuture)));
        return ResponseEntity.ok(response);
    }
}
