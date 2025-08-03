package com.courtly.dao;

import com.courtly.entity.Reservation;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao extends BaseDao<Reservation>{
    public ReservationDao() {
        super(Reservation.class);
    }
}
