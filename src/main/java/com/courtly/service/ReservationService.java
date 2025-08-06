package com.courtly.service;

import com.courtly.dto.ReservationDto;
import com.courtly.entity.Reservation;

import java.util.List;

public interface ReservationService extends CommonService<ReservationDto, Reservation>{
    List<ReservationDto> findByCourtId(Long courtId, Boolean ascending);
    List<ReservationDto> findByPhoneNumber(String phoneNumber, Boolean inFuture);
}
