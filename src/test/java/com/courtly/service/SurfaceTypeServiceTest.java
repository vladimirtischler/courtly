package com.courtly.service;

import com.courtly.dao.SurfaceTypeDao;
import com.courtly.dto.SurfaceTypeDto;
import com.courtly.entity.SurfaceType;
import com.courtly.mapper.SurfaceTypeMapper;
import com.courtly.service.impl.SurfaceTypeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SurfaceTypeServiceTest {
    @Mock
    private SurfaceTypeDao surfaceTypeDao;

    @Mock
    private SurfaceTypeMapper mapper;

    @InjectMocks
    private SurfaceTypeServiceImpl surfaceTypeService;

    @Test
    public void findAll_ReturnList() {
        SurfaceType surfaceType = new SurfaceType();

        SurfaceTypeDto surfaceTypeDto = new SurfaceTypeDto();

        List<SurfaceType> entities = List.of(surfaceType);
        List<SurfaceTypeDto> dtos = List.of(surfaceTypeDto);

        // Mock repository to return entity list
        when(surfaceTypeDao.findAll()).thenReturn(entities);
        // Mock mapper to convert entity list to dto list
        when(mapper.toDtos(entities)).thenReturn(dtos);

        List<SurfaceTypeDto> result = surfaceTypeService.getAll();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(surfaceTypeDto, result.getFirst());
        verify(surfaceTypeDao).findAll();
        verify(mapper).toDtos(List.of(surfaceType));
    }

    @Test
    void findById_ReturnSurfaceType() {
        Long id = 1L;
        SurfaceType surfaceType = new SurfaceType();
        surfaceType.setId(id);

        SurfaceTypeDto surfaceTypeDto = new SurfaceTypeDto();
        surfaceTypeDto.setId(id);

        when(surfaceTypeDao.findById(id)).thenReturn(surfaceType);
        when(mapper.toDto(surfaceType)).thenReturn(surfaceTypeDto);

        SurfaceTypeDto result = surfaceTypeService.findById(id);

        Assertions.assertEquals(surfaceTypeDto, result);
        verify(surfaceTypeDao).findById(1L);
    }

    @Test
    void findById_ReturnNull() {

        when(surfaceTypeDao.findById(1L)).thenReturn(null);

        SurfaceTypeDto surfaceTypeDto = surfaceTypeService.findById(1L);

        Assertions.assertNull(surfaceTypeDto);
    }

    @Test
    void save_NewNameForSurfaceType() {
        SurfaceTypeDto surfaceTypeDto = new SurfaceTypeDto();
        surfaceTypeDto.setId(1L);
        surfaceTypeDto.setName("Grass");
        surfaceTypeDto.setFirstTariff(20.0);
        surfaceTypeDto.setSecondTariff(45.0);
        surfaceTypeDto.setThirdTariff(55.0);
        surfaceTypeDto.setFourthTariff(60.0);
        surfaceTypeDto.setCreated(LocalDateTime.now());

        SurfaceType surfaceType = new SurfaceType();
        surfaceType.setId(1L);
        surfaceType.setName("Grass");
        surfaceType.setFirstTariff(20.0);
        surfaceType.setSecondTariff(45.0);
        surfaceType.setThirdTariff(55.0);
        surfaceType.setFourthTariff(60.0);
        surfaceType.setDeleted(Boolean.FALSE);
        surfaceType.setCreated(LocalDateTime.now());

        when(mapper.toEntity(surfaceTypeDto)).thenReturn(surfaceType);
        doNothing().when(surfaceTypeDao).save(any(SurfaceType.class));

        surfaceTypeService.save(surfaceTypeDto);

        ArgumentCaptor<SurfaceType> captor = ArgumentCaptor.forClass(SurfaceType.class);
        verify(surfaceTypeDao).save(captor.capture());

        SurfaceType saved = captor.getValue();
        Assertions.assertEquals("Grass", saved.getName());
    }

    @Test
    void save_ExistingNameForSurfaceType() {
        SurfaceTypeDto surfaceTypeDto = new SurfaceTypeDto();
        surfaceTypeDto.setName("Grass");
        surfaceTypeDto.setFirstTariff(20.0);
        surfaceTypeDto.setSecondTariff(45.0);
        surfaceTypeDto.setThirdTariff(55.0);
        surfaceTypeDto.setFourthTariff(60.0);

        SurfaceType surfaceType = new SurfaceType();
        surfaceType.setId(2L);
        surfaceType.setName("Grass");
        surfaceType.setFirstTariff(20.0);
        surfaceType.setSecondTariff(45.0);
        surfaceType.setThirdTariff(55.0);
        surfaceType.setFourthTariff(60.0);
        surfaceType.setDeleted(Boolean.FALSE);
        surfaceType.setCreated(LocalDateTime.now());

        when(surfaceTypeDao.findByName(surfaceTypeDto.getName())).thenReturn(surfaceType);

        assertThrows(IllegalArgumentException.class, () ->
                surfaceTypeService.save(surfaceTypeDto)
        );

        verify(surfaceTypeDao, never()).save(any());
    }

    @Test
    void update_ModifyAndSaveSurfaceType() {
        SurfaceType surfaceType = new SurfaceType();
        surfaceType.setId(1L);
        surfaceType.setName("Grass");
        surfaceType.setFirstTariff(20.0);
        surfaceType.setSecondTariff(45.0);
        surfaceType.setThirdTariff(55.0);
        surfaceType.setFourthTariff(60.0);
        surfaceType.setDeleted(Boolean.FALSE);
        surfaceType.setCreated(LocalDateTime.now());

        SurfaceTypeDto updated = new SurfaceTypeDto();
        updated.setId(1L);
        updated.setName("Soft");
        updated.setFirstTariff(22.0);
        updated.setSecondTariff(45.0);
        updated.setThirdTariff(55.0);
        updated.setFourthTariff(60.0);
        updated.setCreated(LocalDateTime.now());


        when(surfaceTypeDao.findByName(updated.getName())).thenReturn(surfaceType);
        when(surfaceTypeDao.findById(1L)).thenReturn(surfaceType);

        doAnswer(invocation -> {
            SurfaceType entityArg = invocation.getArgument(0);
            SurfaceTypeDto dtoArg = invocation.getArgument(1);

            entityArg.setName(dtoArg.getName());
            entityArg.setFirstTariff(dtoArg.getFirstTariff());
            return null;
        }).when(mapper).update(any(SurfaceType.class), any(SurfaceTypeDto.class));

        surfaceTypeService.update(updated, 1L);

        ArgumentCaptor<SurfaceType> captor = ArgumentCaptor.forClass(SurfaceType.class);
        verify(surfaceTypeDao).update(captor.capture());

        SurfaceType updatedEntity = captor.getValue();
        Assertions.assertEquals("Soft", updatedEntity.getName());
        Assertions.assertEquals(22.0, updatedEntity.getFirstTariff());
        Assertions.assertEquals(1L, updatedEntity.getId());
        Assertions.assertEquals(surfaceType.getCreated(), updatedEntity.getCreated());
    }

    @Test
    void update_ExistingNameForSurfaceType() {
        SurfaceTypeDto surfaceTypeDto = new SurfaceTypeDto();
        surfaceTypeDto.setName("Update");
        surfaceTypeDto.setFirstTariff(20.0);
        surfaceTypeDto.setSecondTariff(45.0);
        surfaceTypeDto.setThirdTariff(55.0);
        surfaceTypeDto.setFourthTariff(60.0);

        SurfaceType surfaceType = new SurfaceType();
        surfaceType.setId(2L);
        surfaceType.setName("Grass");
        surfaceType.setFirstTariff(20.0);
        surfaceType.setSecondTariff(45.0);
        surfaceType.setThirdTariff(55.0);
        surfaceType.setFourthTariff(60.0);
        surfaceType.setDeleted(Boolean.FALSE);
        surfaceType.setCreated(LocalDateTime.now());

        when(surfaceTypeDao.findByName(surfaceTypeDto.getName())).thenReturn(surfaceType);

        assertThrows(IllegalArgumentException.class, () ->
                surfaceTypeService.update(surfaceTypeDto, 1L)
        );

        verify(surfaceTypeDao, never()).update(any());
    }

    @Test
    void delete_DeleteById() {
        SurfaceType surfaceType = new SurfaceType();
        surfaceType.setId(1L);
        surfaceType.setName("Grass");
        surfaceType.setFirstTariff(20.0);
        surfaceType.setSecondTariff(45.0);
        surfaceType.setThirdTariff(55.0);
        surfaceType.setFourthTariff(60.0);
        surfaceType.setDeleted(Boolean.FALSE);
        surfaceType.setCreated(LocalDateTime.now());

        when(surfaceTypeDao.findById(1L)).thenReturn(surfaceType);

        surfaceTypeService.delete(1L);
        verify(surfaceTypeDao).delete(surfaceType);
    }

    @Test
    void delete_DeleteByIdNotExisting() {

        when(surfaceTypeDao.findById(1L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () ->
                surfaceTypeService.delete(1L)
        );
        verify(surfaceTypeDao, never()).delete(any());
    }
}
