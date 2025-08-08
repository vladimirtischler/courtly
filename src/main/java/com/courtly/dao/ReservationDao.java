package com.courtly.dao;

import com.courtly.entity.Reservation;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationDao extends BaseDao<Reservation>{
    public ReservationDao() {
        super(Reservation.class);
    }

    public List<Reservation> findByCourtIdAndDate(Long courtId, LocalDateTime date){
        LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = date.toLocalDate().atTime(LocalTime.MAX);
        return em.createNamedQuery(Reservation.FIND_BY_COURT_ID_AND_DATE, Reservation.class).setParameter("courtId",
                                                                                                          courtId).setParameter("start", startOfDay).setParameter("end", endOfDay).getResultList();
    }

    public List<Reservation> findByCourtId(Long courtId, Boolean ascending) {
        String queryName = ascending
                ? Reservation.FIND_BY_COURT_ID_ORDER_BY_CREATED_DATE_ASC
                : Reservation.FIND_BY_COURT_ID_ORDER_BY_CREATED_DATE_DESC;

        return super.em
                .createNamedQuery(queryName, Reservation.class)
                .setParameter("courtId", courtId)
                .getResultList();
    }

    public List<Reservation> findByPhoneNumber(String phoneNumber, Boolean inFuture) {
        String queryName = inFuture
                ? Reservation.FIND_BY_PHONE_NUMBER_IN_FUTURE
                : Reservation.FIND_BY_PHONE_NUMBER;

        return super.em
                .createNamedQuery(queryName, Reservation.class)
                .setParameter("phoneNumber", phoneNumber)
                .getResultList();
    }
}
