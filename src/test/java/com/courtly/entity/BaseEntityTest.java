package com.courtly.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class BaseEntityTest {
    @Test
    void setCreatedNow_SetsCurrentTime() {
        Reservation r = new Reservation();

        r.onCreate();

        Assertions.assertNotNull(r.getCreated());
    }
}
