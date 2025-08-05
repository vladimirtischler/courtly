package com.courtly.service;

import com.courtly.entity.Reservation;

import java.util.List;

public interface ReservationService extends CommonService<Reservation>{
    List<Reservation> findByCourtId(Long courtId, Boolean ascending);
    List<Reservation> findByPhoneNumber(String phoneNumber, Boolean inFuture);
}
