package com.courtly.dao;

import com.courtly.entity.Reservation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationDaoTest {
    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Reservation> typedQuery;

    @InjectMocks
    private ReservationDao reservationDao;

    @Test
    void findByCourtIdAndDate() {
        Long courtId = 1L;
        LocalDateTime date = LocalDateTime.of(2025, 8, 10, 15, 0);
        List<Reservation> expected = List.of(new Reservation(), new Reservation());
        LocalDateTime expectedStart = date.toLocalDate().atStartOfDay();
        LocalDateTime expectedEnd = date.toLocalDate().atTime(LocalTime.MAX);

        when(entityManager.createNamedQuery(Reservation.FIND_BY_COURT_ID_AND_DATE, Reservation.class))
                .thenReturn(typedQuery);
        when(typedQuery.setParameter("courtId", courtId)).thenReturn(typedQuery);
        when(typedQuery.setParameter("start", expectedStart)).thenReturn(typedQuery);
        when(typedQuery.setParameter("end", expectedEnd)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(expected);

        List<Reservation> result = reservationDao.findByCourtIdAndDate(courtId, date);

        Assertions.assertEquals(expected, result);

        verify(typedQuery).setParameter("start", expectedStart);
        verify(typedQuery).setParameter("end", expectedEnd);
    }

    @Test
    void findByCourtId_Ascending() {
        Long courtId = 1L;
        List<Reservation> expected = List.of(new Reservation());

        when(entityManager.createNamedQuery(Reservation.FIND_BY_COURT_ID_ORDER_BY_CREATED_DATE_ASC, Reservation.class))
                .thenReturn(typedQuery);
        when(typedQuery.setParameter("courtId", courtId)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(expected);

        List<Reservation> result = reservationDao.findByCourtId(courtId, true);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void findByCourtId_Descending() {
        Long courtId = 1L;
        List<Reservation> expected = List.of(new Reservation());

        when(entityManager.createNamedQuery(Reservation.FIND_BY_COURT_ID_ORDER_BY_CREATED_DATE_DESC, Reservation.class))
                .thenReturn(typedQuery);
        when(typedQuery.setParameter("courtId", courtId)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(expected);

        List<Reservation> result = reservationDao.findByCourtId(courtId, false);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void findByPhoneNumber_InFuture() {
        String phone = "123456789";
        List<Reservation> expected = List.of(new Reservation());

        when(entityManager.createNamedQuery(Reservation.FIND_BY_PHONE_NUMBER_IN_FUTURE, Reservation.class))
                .thenReturn(typedQuery);
        when(typedQuery.setParameter("phoneNumber", phone)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(expected);

        List<Reservation> result = reservationDao.findByPhoneNumber(phone, true);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void findByPhoneNumber_Past() {
        String phone = "987654321";
        List<Reservation> expected = List.of(new Reservation());

        when(entityManager.createNamedQuery(Reservation.FIND_BY_PHONE_NUMBER, Reservation.class))
                .thenReturn(typedQuery);
        when(typedQuery.setParameter("phoneNumber", phone)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(expected);

        List<Reservation> result = reservationDao.findByPhoneNumber(phone, false);

        Assertions.assertEquals(expected, result);
    }
}
