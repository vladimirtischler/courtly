package com.courtly.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class ReservationEntityTest {
    @Test
    void compareTo_otherIsNullReturnsPositive() {
        Reservation r1 = new Reservation();
        r1.setCreated(LocalDateTime.now());

        Assertions.assertTrue(r1.compareTo(null) > 0);
    }

    @Test
    void compareTo_thisCreatedBeforeOtherReturnsNegative() {
        Reservation r1 = new Reservation();
        r1.setCreated(LocalDateTime.of(2025, 1, 1, 10, 0));

        Reservation r2 = new Reservation();
        r2.setCreated(LocalDateTime.of(2025, 1, 2, 10, 0));

        Assertions.assertTrue(r1.compareTo(r2) < 0);
    }

    @Test
    void compareTo_sameCreatedReturnsZero() {
        LocalDateTime sameDate = LocalDateTime.of(2025, 1, 1, 10, 0);

        Reservation r1 = new Reservation();
        r1.setCreated(sameDate);

        Reservation r2 = new Reservation();
        r2.setCreated(sameDate);

        Assertions.assertEquals(0, r1.compareTo(r2));
    }

    @Test
    void compareTo_thisCreatedAfterOtherReturnsPositive() {
        Reservation r1 = new Reservation();
        r1.setCreated(LocalDateTime.of(2025, 1, 3, 10, 0));

        Reservation r2 = new Reservation();
        r2.setCreated(LocalDateTime.of(2025, 1, 2, 10, 0));

        Assertions.assertTrue(r1.compareTo(r2) > 0);
    }
}
