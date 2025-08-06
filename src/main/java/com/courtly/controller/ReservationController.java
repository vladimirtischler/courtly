package com.courtly.controller;

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
    public ResponseEntity<Object> findAllByCourtId(@PathVariable(name="courtId") Long courtId, @RequestParam(name=
            "order", required = false) String order) {
        try {
            return ResponseEntity.ok(reservationService.findByCourtId(courtId, !"DESC".equalsIgnoreCase(order)));
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("phone-number/{phoneNumber}")
    public ResponseEntity<Object> findAllByPhoneNumber(@PathVariable(name="phoneNumber") String phoneNumber,
                                                      @RequestParam(name= "inFuture", required = false) Boolean inFuture) {

        return ResponseEntity.ok(reservationService.findByPhoneNumber(phoneNumber, Boolean.TRUE.equals(inFuture)));
    }
}
