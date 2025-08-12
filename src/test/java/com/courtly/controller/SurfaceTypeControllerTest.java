package com.courtly.controller;

import com.courtly.dto.ApiResponse;
import com.courtly.dto.SurfaceTypeDto;
import com.courtly.service.SurfaceTypeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SurfaceTypeControllerTest {

    @Mock
    private SurfaceTypeService surfaceTypeService;

    @InjectMocks
    private SurfaceTypeController surfaceTypeController;

    @Test
    void save_CatchIAE() {
        doThrow(new IllegalArgumentException("DTO is invalid")).when(surfaceTypeService).save(any(SurfaceTypeDto.class));

        ResponseEntity<ApiResponse<SurfaceTypeDto>> response = surfaceTypeController.save(new SurfaceTypeDto());

        Assertions.assertEquals(ResponseEntity.badRequest().build().getStatusCode(), response.getStatusCode());
        Assertions.assertNotNull(response.getBody().getErrors());

        verify(surfaceTypeService).save(any());
    }

    @Test
    void save_Correct() {
        doNothing().when(surfaceTypeService).save(any(SurfaceTypeDto.class));

        ResponseEntity<ApiResponse<SurfaceTypeDto>> response = surfaceTypeController.save(new SurfaceTypeDto());

        Assertions.assertEquals(ResponseEntity.ok().build().getStatusCode(), response.getStatusCode());
        Assertions.assertNull(response.getBody());

        verify(surfaceTypeService).save(any());
    }

    @Test
    void update_CatchIAE() {
        doThrow(new IllegalArgumentException("DTO is invalid")).when(surfaceTypeService).update(any(SurfaceTypeDto.class), any(
                Long.class));

        ResponseEntity<ApiResponse<SurfaceTypeDto>> response = surfaceTypeController.update(new SurfaceTypeDto(), 1L);

        Assertions.assertEquals(ResponseEntity.badRequest().build().getStatusCode(), response.getStatusCode());
        Assertions.assertNotNull(response.getBody().getErrors());

        verify(surfaceTypeService).update(any(), any());
    }

    @Test
    void update_Correct() {
        doNothing().when(surfaceTypeService).update(any(SurfaceTypeDto.class), any(Long.class));

        ResponseEntity<ApiResponse<SurfaceTypeDto>> response = surfaceTypeController.update(new SurfaceTypeDto(), 1L);

        Assertions.assertEquals(ResponseEntity.ok().build().getStatusCode(), response.getStatusCode());
        Assertions.assertNull(response.getBody());

        verify(surfaceTypeService).update(any(), any());
    }

    @Test
    void findById_NotFoundResponse() {
        when(surfaceTypeService.findById(1L)).thenReturn(null);

        ResponseEntity<ApiResponse<SurfaceTypeDto>> response = surfaceTypeController.findById(1L);
        Assertions.assertEquals(ResponseEntity.notFound().build(), response);

        verify(surfaceTypeService).findById(any());
    }

    @Test
    void findById_FoundResponse() {
        SurfaceTypeDto surfaceTypeDto = new SurfaceTypeDto();
        surfaceTypeDto.setId(1L);

        when(surfaceTypeService.findById(1L)).thenReturn(surfaceTypeDto);

        ResponseEntity<ApiResponse<SurfaceTypeDto>> response = surfaceTypeController.findById(1L);

        ApiResponse<SurfaceTypeDto> expected = new ApiResponse<>();
        expected.setData(surfaceTypeDto);
        Assertions.assertEquals(expected.getData(), response.getBody().getData());
        Assertions.assertEquals(ResponseEntity.ok().build().getStatusCode(), response.getStatusCode());

        verify(surfaceTypeService).findById(1L);
    }

    @Test
    void getAll_FoundResponse() {
        SurfaceTypeDto surfaceTypeDto = new SurfaceTypeDto();
        surfaceTypeDto.setId(1L);

        when(surfaceTypeService.getAll()).thenReturn(List.of(surfaceTypeDto));

        ResponseEntity<ApiResponse<List<SurfaceTypeDto>>> response = surfaceTypeController.getAll();

        ApiResponse<List<SurfaceTypeDto>> expected = new ApiResponse<>();
        expected.setData(List.of(surfaceTypeDto));

        Assertions.assertEquals(1, response.getBody().getData().size());
        Assertions.assertEquals(expected.getData().getFirst(), response.getBody().getData().getFirst());
        Assertions.assertEquals(ResponseEntity.ok().build().getStatusCode(), response.getStatusCode());

        verify(surfaceTypeService).getAll();
    }

    @Test
    void delete() {

        doNothing().when(surfaceTypeService).delete(1L);

        ResponseEntity<ApiResponse<SurfaceTypeDto>> response = surfaceTypeController.delete(1L);

        Assertions.assertNull(response.getBody());
        Assertions.assertEquals(ResponseEntity.ok().build().getStatusCode(), response.getStatusCode());
    }
}
