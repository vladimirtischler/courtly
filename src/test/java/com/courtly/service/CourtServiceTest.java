package com.courtly.service;

import com.courtly.dao.CourtDao;
import com.courtly.dao.SurfaceTypeDao;
import com.courtly.dto.CourtDto;
import com.courtly.dto.SurfaceTypeDto;
import com.courtly.entity.Court;
import com.courtly.entity.SurfaceType;
import com.courtly.mapper.CourtMapper;
import com.courtly.service.impl.CourtServiceImpl;
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
public class CourtServiceTest {
    @Mock
    private CourtDao courtDao;

    @Mock
    private CourtMapper mapper;

    @Mock
    private SurfaceTypeDao surfaceTypeDao;

    @InjectMocks
    private CourtServiceImpl courtService;

    @Test
    void save_ExistingSurfaceTypeDefinedByIdInDto() {
        SurfaceType surfaceType = new SurfaceType();
        surfaceType.setId(1L);
        surfaceType.setName("Grass");
        surfaceType.setFirstTariff(20.0);
        surfaceType.setSecondTariff(45.0);
        surfaceType.setThirdTariff(55.0);
        surfaceType.setFourthTariff(60.0);
        surfaceType.setDeleted(Boolean.FALSE);
        surfaceType.setCreated(LocalDateTime.now());

        SurfaceTypeDto surfaceTypeDto = new SurfaceTypeDto();
        surfaceTypeDto.setId(1L);
        surfaceTypeDto.setFirstTariff(20.0);
        surfaceTypeDto.setSecondTariff(45.0);
        surfaceTypeDto.setThirdTariff(55.0);
        surfaceTypeDto.setFourthTariff(60.0);
        surfaceTypeDto.setCreated(LocalDateTime.now());

        Court court = new Court();
        court.setId(1L);
        court.setCreated(LocalDateTime.now());
        court.setDeleted(Boolean.FALSE);
        court.setName("Tennis court");
        court.setSurfaceType(surfaceType);

        CourtDto courtDto = new CourtDto();
        courtDto.setId(1L);
        courtDto.setCreated(LocalDateTime.now());
        courtDto.setName("Tennis court");
        courtDto.setSurfaceType(surfaceTypeDto);

        when(mapper.toEntity(courtDto)).thenReturn(court);
        when(surfaceTypeDao.findById(surfaceTypeDto.getId())).thenReturn(surfaceType);
        doNothing().when(courtDao).save(any(Court.class));

        courtService.save(courtDto);

        ArgumentCaptor<Court> captor = ArgumentCaptor.forClass(Court.class);
        verify(courtDao).save(captor.capture());

        Court saved = captor.getValue();
        Assertions.assertEquals("Tennis court", saved.getName());
    }

    @Test
    void save_ExistingSurfaceTypeDefinedByNameInDto() {
        SurfaceType surfaceType = new SurfaceType();
        surfaceType.setId(1L);
        surfaceType.setName("Grass");
        surfaceType.setFirstTariff(20.0);
        surfaceType.setSecondTariff(45.0);
        surfaceType.setThirdTariff(55.0);
        surfaceType.setFourthTariff(60.0);
        surfaceType.setDeleted(Boolean.FALSE);
        surfaceType.setCreated(LocalDateTime.now());

        SurfaceTypeDto surfaceTypeDto = new SurfaceTypeDto();
        surfaceTypeDto.setName("Grass");
        surfaceTypeDto.setFirstTariff(20.0);
        surfaceTypeDto.setSecondTariff(45.0);
        surfaceTypeDto.setThirdTariff(55.0);
        surfaceTypeDto.setFourthTariff(60.0);
        surfaceTypeDto.setCreated(LocalDateTime.now());

        Court court = new Court();
        court.setId(1L);
        court.setCreated(LocalDateTime.now());
        court.setDeleted(Boolean.FALSE);
        court.setName("Tennis court");
        court.setSurfaceType(surfaceType);

        CourtDto courtDto = new CourtDto();
        courtDto.setId(1L);
        courtDto.setCreated(LocalDateTime.now());
        courtDto.setName("Tennis court");
        courtDto.setSurfaceType(surfaceTypeDto);

        when(mapper.toEntity(courtDto)).thenReturn(court);
        when(surfaceTypeDao.findByName(surfaceTypeDto.getName())).thenReturn(surfaceType);
        doNothing().when(courtDao).save(any(Court.class));

        courtService.save(courtDto);

        ArgumentCaptor<Court> captor = ArgumentCaptor.forClass(Court.class);
        verify(courtDao).save(captor.capture());

        Court saved = captor.getValue();
        Assertions.assertEquals("Tennis court", saved.getName());
    }

    @Test
    void save_NotExistingSurfaceTypeDefinedByIdInDto() {
        SurfaceType surfaceType = new SurfaceType();
        surfaceType.setId(1L);
        surfaceType.setName("Grass");
        surfaceType.setFirstTariff(20.0);
        surfaceType.setSecondTariff(45.0);
        surfaceType.setThirdTariff(55.0);
        surfaceType.setFourthTariff(60.0);
        surfaceType.setDeleted(Boolean.FALSE);
        surfaceType.setCreated(LocalDateTime.now());

        SurfaceTypeDto surfaceTypeDto = new SurfaceTypeDto();
        surfaceTypeDto.setId(3L);
        surfaceTypeDto.setFirstTariff(20.0);
        surfaceTypeDto.setSecondTariff(45.0);
        surfaceTypeDto.setThirdTariff(55.0);
        surfaceTypeDto.setFourthTariff(60.0);
        surfaceTypeDto.setCreated(LocalDateTime.now());

        Court court = new Court();
        court.setId(1L);
        court.setCreated(LocalDateTime.now());
        court.setDeleted(Boolean.FALSE);
        court.setName("Tennis court");
        court.setSurfaceType(surfaceType);

        CourtDto courtDto = new CourtDto();
        courtDto.setId(1L);
        courtDto.setCreated(LocalDateTime.now());
        courtDto.setName("Tennis court");
        courtDto.setSurfaceType(surfaceTypeDto);

        when(mapper.toEntity(courtDto)).thenReturn(court);

        assertThrows(IllegalArgumentException.class, () ->
                courtService.save(courtDto)
        );

        verify(courtDao, never()).save(any());
    }

    @Test
    void save_NotDefinedSurfaceTypeInDto() {
        SurfaceType surfaceType = new SurfaceType();
        surfaceType.setId(1L);
        surfaceType.setName("Grass");
        surfaceType.setFirstTariff(20.0);
        surfaceType.setSecondTariff(45.0);
        surfaceType.setThirdTariff(55.0);
        surfaceType.setFourthTariff(60.0);
        surfaceType.setDeleted(Boolean.FALSE);
        surfaceType.setCreated(LocalDateTime.now());

        SurfaceTypeDto surfaceTypeDto = new SurfaceTypeDto();
        surfaceTypeDto.setFirstTariff(20.0);
        surfaceTypeDto.setSecondTariff(45.0);
        surfaceTypeDto.setThirdTariff(55.0);
        surfaceTypeDto.setFourthTariff(60.0);
        surfaceTypeDto.setCreated(LocalDateTime.now());

        Court court = new Court();
        court.setId(1L);
        court.setCreated(LocalDateTime.now());
        court.setDeleted(Boolean.FALSE);
        court.setName("Tennis court");
        court.setSurfaceType(surfaceType);

        CourtDto courtDto = new CourtDto();
        courtDto.setId(1L);
        courtDto.setCreated(LocalDateTime.now());
        courtDto.setName("Tennis court");
        courtDto.setSurfaceType(surfaceTypeDto);

        when(mapper.toEntity(courtDto)).thenReturn(court);

        assertThrows(IllegalArgumentException.class, () ->
                courtService.save(courtDto)
        );

        verify(courtDao, never()).save(any());
    }

    @Test
    void update_ExistingSurfaceTypeDefinedByIdInDto() {
        SurfaceType surfaceType = new SurfaceType();
        surfaceType.setId(1L);
        surfaceType.setName("Grass");
        surfaceType.setFirstTariff(20.0);
        surfaceType.setSecondTariff(45.0);
        surfaceType.setThirdTariff(55.0);
        surfaceType.setFourthTariff(60.0);
        surfaceType.setDeleted(Boolean.FALSE);
        surfaceType.setCreated(LocalDateTime.now());

        SurfaceType otherSurfaceType = new SurfaceType();
        surfaceType.setId(2L);
        surfaceType.setName("Soft");
        surfaceType.setFirstTariff(20.0);
        surfaceType.setSecondTariff(45.0);
        surfaceType.setThirdTariff(55.0);
        surfaceType.setFourthTariff(60.0);
        surfaceType.setDeleted(Boolean.FALSE);
        surfaceType.setCreated(LocalDateTime.now());

        SurfaceTypeDto surfaceTypeDto = new SurfaceTypeDto();
        surfaceTypeDto.setId(2L);
        surfaceTypeDto.setFirstTariff(20.0);
        surfaceTypeDto.setSecondTariff(45.0);
        surfaceTypeDto.setThirdTariff(55.0);
        surfaceTypeDto.setFourthTariff(60.0);
        surfaceTypeDto.setCreated(LocalDateTime.now());

        Court court = new Court();
        court.setId(1L);
        court.setCreated(LocalDateTime.now());
        court.setDeleted(Boolean.FALSE);
        court.setName("Tennis court");
        court.setSurfaceType(surfaceType);

        CourtDto courtDto = new CourtDto();
        courtDto.setId(1L);
        courtDto.setCreated(LocalDateTime.now());
        courtDto.setName("Football court");
        courtDto.setSurfaceType(surfaceTypeDto);

        when(courtDao.findById(1L)).thenReturn(court);
        when(surfaceTypeDao.findById(surfaceTypeDto.getId())).thenReturn(otherSurfaceType);
        doAnswer(invocation -> {
            Court entityArg = invocation.getArgument(0);
            CourtDto dtoArg = invocation.getArgument(1);

            entityArg.setName(dtoArg.getName());
            return null;
        }).when(mapper).update(any(Court.class), any(CourtDto.class));

        courtService.update(courtDto, 1L);

        ArgumentCaptor<Court> captor = ArgumentCaptor.forClass(Court.class);
        verify(courtDao).update(captor.capture());

        Assertions.assertEquals(otherSurfaceType, court.getSurfaceType());
        Assertions.assertEquals("Football court", court.getName());
    }

    @Test
    void update_ExistingSurfaceTypeDefinedByNameInDto() {
        SurfaceType surfaceType = new SurfaceType();
        surfaceType.setId(1L);
        surfaceType.setName("Grass");
        surfaceType.setFirstTariff(20.0);
        surfaceType.setSecondTariff(45.0);
        surfaceType.setThirdTariff(55.0);
        surfaceType.setFourthTariff(60.0);
        surfaceType.setDeleted(Boolean.FALSE);
        surfaceType.setCreated(LocalDateTime.now());

        SurfaceType otherSurfaceType = new SurfaceType();
        surfaceType.setId(2L);
        surfaceType.setName("Soft");
        surfaceType.setFirstTariff(20.0);
        surfaceType.setSecondTariff(45.0);
        surfaceType.setThirdTariff(55.0);
        surfaceType.setFourthTariff(60.0);
        surfaceType.setDeleted(Boolean.FALSE);
        surfaceType.setCreated(LocalDateTime.now());

        SurfaceTypeDto surfaceTypeDto = new SurfaceTypeDto();
        surfaceTypeDto.setName("Soft");
        surfaceTypeDto.setFirstTariff(20.0);
        surfaceTypeDto.setSecondTariff(45.0);
        surfaceTypeDto.setThirdTariff(55.0);
        surfaceTypeDto.setFourthTariff(60.0);
        surfaceTypeDto.setCreated(LocalDateTime.now());

        Court court = new Court();
        court.setId(1L);
        court.setCreated(LocalDateTime.now());
        court.setDeleted(Boolean.FALSE);
        court.setName("Tennis court");
        court.setSurfaceType(surfaceType);

        CourtDto courtDto = new CourtDto();
        courtDto.setId(1L);
        courtDto.setCreated(LocalDateTime.now());
        courtDto.setName("Football court");
        courtDto.setSurfaceType(surfaceTypeDto);

        when(courtDao.findById(1L)).thenReturn(court);
        when(surfaceTypeDao.findByName(surfaceTypeDto.getName())).thenReturn(otherSurfaceType);
        doAnswer(invocation -> {
            Court entityArg = invocation.getArgument(0);
            CourtDto dtoArg = invocation.getArgument(1);

            entityArg.setName(dtoArg.getName());
            return null;
        }).when(mapper).update(any(Court.class), any(CourtDto.class));

        courtService.update(courtDto, 1L);

        ArgumentCaptor<Court> captor = ArgumentCaptor.forClass(Court.class);
        verify(courtDao).update(captor.capture());

        Assertions.assertEquals(otherSurfaceType, court.getSurfaceType());
        Assertions.assertEquals("Football court", court.getName());
    }

    @Test
    void update_NotExistingSurfaceTypeDefinedByNameInDto() {
        SurfaceType surfaceType = new SurfaceType();
        surfaceType.setId(1L);
        surfaceType.setName("Grass");
        surfaceType.setFirstTariff(20.0);
        surfaceType.setSecondTariff(45.0);
        surfaceType.setThirdTariff(55.0);
        surfaceType.setFourthTariff(60.0);
        surfaceType.setDeleted(Boolean.FALSE);
        surfaceType.setCreated(LocalDateTime.now());

        SurfaceTypeDto surfaceTypeDto = new SurfaceTypeDto();
        surfaceTypeDto.setName("Soft");
        surfaceTypeDto.setFirstTariff(20.0);
        surfaceTypeDto.setSecondTariff(45.0);
        surfaceTypeDto.setThirdTariff(55.0);
        surfaceTypeDto.setFourthTariff(60.0);
        surfaceTypeDto.setCreated(LocalDateTime.now());

        Court court = new Court();
        court.setId(1L);
        court.setCreated(LocalDateTime.now());
        court.setDeleted(Boolean.FALSE);
        court.setName("Tennis court");
        court.setSurfaceType(surfaceType);

        CourtDto courtDto = new CourtDto();
        courtDto.setId(1L);
        courtDto.setCreated(LocalDateTime.now());
        courtDto.setName("Football court");
        courtDto.setSurfaceType(surfaceTypeDto);

        when(courtDao.findById(1L)).thenReturn(court);
        when(surfaceTypeDao.findByName(surfaceTypeDto.getName())).thenReturn(null);
        doAnswer(invocation -> {
            Court entityArg = invocation.getArgument(0);
            CourtDto dtoArg = invocation.getArgument(1);

            entityArg.setName(dtoArg.getName());
            return null;
        }).when(mapper).update(any(Court.class), any(CourtDto.class));

        assertThrows(IllegalArgumentException.class, () ->
                courtService.update(courtDto, 1L)
        );
        verify(courtDao, never()).update(any());
    }

    @Test
    void update_NotDefinedSurfaceTypeInDto() {
        SurfaceType surfaceType = new SurfaceType();
        surfaceType.setId(1L);
        surfaceType.setName("Grass");
        surfaceType.setFirstTariff(20.0);
        surfaceType.setSecondTariff(45.0);
        surfaceType.setThirdTariff(55.0);
        surfaceType.setFourthTariff(60.0);
        surfaceType.setDeleted(Boolean.FALSE);
        surfaceType.setCreated(LocalDateTime.now());

        SurfaceTypeDto surfaceTypeDto = new SurfaceTypeDto();
        surfaceTypeDto.setFirstTariff(20.0);
        surfaceTypeDto.setSecondTariff(45.0);
        surfaceTypeDto.setThirdTariff(55.0);
        surfaceTypeDto.setFourthTariff(60.0);
        surfaceTypeDto.setCreated(LocalDateTime.now());

        Court court = new Court();
        court.setId(1L);
        court.setCreated(LocalDateTime.now());
        court.setDeleted(Boolean.FALSE);
        court.setName("Tennis court");
        court.setSurfaceType(surfaceType);

        CourtDto courtDto = new CourtDto();
        courtDto.setId(1L);
        courtDto.setCreated(LocalDateTime.now());
        courtDto.setName("Football court");
        courtDto.setSurfaceType(surfaceTypeDto);

        when(courtDao.findById(1L)).thenReturn(court);
        doAnswer(invocation -> {
            Court entityArg = invocation.getArgument(0);
            CourtDto dtoArg = invocation.getArgument(1);

            entityArg.setName(dtoArg.getName());
            return null;
        }).when(mapper).update(any(Court.class), any(CourtDto.class));

        assertThrows(IllegalArgumentException.class, () ->
                courtService.update(courtDto, 1L)
        );
        verify(courtDao, never()).update(any());
    }

//    @Test
//    void delete_DeleteById() {
//        SurfaceType surfaceType = new SurfaceType();
//        surfaceType.setId(1L);
//        surfaceType.setName("Grass");
//        surfaceType.setFirstTariff(20.0);
//        surfaceType.setSecondTariff(45.0);
//        surfaceType.setThirdTariff(55.0);
//        surfaceType.setFourthTariff(60.0);
//        surfaceType.setDeleted(Boolean.FALSE);
//        surfaceType.setCreated(LocalDateTime.now());
//
//        Court court = new Court();
//        court.setId(1L);
//        court.setCreated(LocalDateTime.now());
//        court.setDeleted(Boolean.FALSE);
//        court.setName("Tennis court");
//        court.setSurfaceType(surfaceType);
//
//        when(courtDao.findById(1L)).thenReturn(court);
//
//        courtService.delete(1L);
//        verify(courtDao).delete(court);
//    }
//
//    @Test
//    void delete_DeleteByIdNotExisting() {
//
//        when(courtDao.findById(1L)).thenReturn(null);
//
//        assertThrows(IllegalArgumentException.class, () ->
//                courtService.delete(1L)
//        );
//        verify(courtDao, never()).delete(any());
//    }
}
