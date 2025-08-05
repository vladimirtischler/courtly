package com.courtly.utils;

import com.courtly.entity.Reservation;

import java.time.LocalDateTime;

public class ReservationUtils {

    public static Boolean isInCollision(Reservation reservation, Reservation other) {
        LocalDateTime reservationEndTime =
                reservation.getStartTime().plusMinutes(reservation.getTariffType().getMinutesDuration()-1);
        LocalDateTime reservationStartTime = reservation.getStartTime();
        LocalDateTime otherEndTime = other.getStartTime().plusMinutes(other.getTariffType().getMinutesDuration()-1);
        LocalDateTime otherStartTime = other.getStartTime();

        return !(reservationEndTime.isBefore(otherStartTime) || reservationStartTime.isAfter(otherEndTime));
    }
}
