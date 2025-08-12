package com.courtly.controller;

import com.courtly.dto.ApiResponse;
import com.courtly.dto.ReservationDto;
import com.courtly.dto.SurfaceTypeDto;
import com.courtly.service.ReservationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {
    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    @Test
    void findAllByCourtId_CatchIAE() {
        doThrow(new IllegalArgumentException("Something is bad")).when(reservationService).findByCourtId(any(Long.class), any(
                Boolean.class));

        ResponseEntity<ApiResponse<List<ReservationDto>>> response = reservationController.findAllByCourtId(1L, "ASC");

        Assertions.assertEquals(ResponseEntity.badRequest().build().getStatusCode(), response.getStatusCode());
        Assertions.assertNotNull(response.getBody().getErrors());

        verify(reservationService).findByCourtId(any(), any());
    }

    @Test
    void findAllByCourtId() {
        ReservationDto reservationDto = new ReservationDto();

        when(reservationService.findByCourtId(any(Long.class), any(Boolean.class))).thenReturn(List.of(reservationDto));

        ResponseEntity<ApiResponse<List<ReservationDto>>> response = reservationController.findAllByCourtId(1L, "ASC");

        Assertions.assertEquals(ResponseEntity.ok().build().getStatusCode(), response.getStatusCode());
        Assertions.assertEquals(1, response.getBody().getData().size());
        Assertions.assertEquals(reservationDto, response.getBody().getData().getFirst());

        verify(reservationService).findByCourtId(any(), any());
    }

    @Test
    void findAllByPhoneNumber() {
        when(reservationService.findByPhoneNumber(any(String.class), any(
                Boolean.class))).thenReturn(new ArrayList<>());

        ResponseEntity<ApiResponse<List<ReservationDto>>> response = reservationController.findAllByPhoneNumber(
                "95416564", Boolean.TRUE);

        Assertions.assertEquals(ResponseEntity.ok().build().getStatusCode(), response.getStatusCode());
        Assertions.assertEquals(0, response.getBody().getData().size());

        verify(reservationService).findByPhoneNumber(any(), any());
    }
}
