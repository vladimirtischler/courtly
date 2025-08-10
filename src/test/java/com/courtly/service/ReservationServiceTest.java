package com.courtly.service;

import com.courtly.dao.CourtDao;
import com.courtly.dao.CustomerDao;
import com.courtly.dao.ReservationDao;
import com.courtly.dto.CourtDto;
import com.courtly.dto.CustomerDto;
import com.courtly.dto.ReservationDto;
import com.courtly.dto.SurfaceTypeDto;
import com.courtly.entity.Court;
import com.courtly.entity.Customer;
import com.courtly.entity.Reservation;
import com.courtly.entity.SurfaceType;
import com.courtly.enums.GameType;
import com.courtly.enums.TariffType;
import com.courtly.mapper.ReservationMapper;
import com.courtly.service.impl.ReservationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Mock
    private ReservationDao reservationDao;

    @Mock
    private ReservationMapper reservationMapper;

    @Mock
    private CourtDao courtDao;

    @Mock
    private CustomerDao customerDao;

    @Test
    public void findAll_ReturnList() {
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

        Customer customer = new Customer();
        customer.setFirstName("Janko");
        customer.setLastName("Hrasko");
        customer.setPhoneNumber("0948562321");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName("Janko");
        customerDto.setLastName("Hrasko");
        customerDto.setPhoneNumber("0948562321");

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setPrice(50.0);
        reservation.setCourt(court);
        reservation.setDeleted(Boolean.FALSE);
        reservation.setGameType(GameType.SINGLES);
        reservation.setCreated(LocalDateTime.now());
        reservation.setTariffType(TariffType.FIRST_TARIFF);
        reservation.setStartTime(LocalDateTime.now().plusDays(2));
        reservation.setCustomer(customer);

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(1L);
        reservationDto.setPrice(50.0);
        reservationDto.setCourt(courtDto);
        reservationDto.setGameType(GameType.SINGLES);
        reservationDto.setCreated(LocalDateTime.now());
        reservationDto.setTariffType(TariffType.FIRST_TARIFF);
        reservationDto.setStartTime(LocalDateTime.now().plusDays(2));
        reservationDto.setCustomer(customerDto);

        List<Reservation> entities = List.of(reservation);
        List<ReservationDto> dtos = List.of(reservationDto);

        when(reservationDao.findAll()).thenReturn(entities);
        when(reservationMapper.toDtos(entities)).thenReturn(dtos);

        List<ReservationDto> result = reservationService.getAll();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(reservation.getPrice(), result.getFirst().getPrice());
        Assertions.assertEquals(reservation.getCustomer().getPhoneNumber(), result.getFirst().getCustomer().getPhoneNumber());
        verify(reservationDao).findAll();
        verify(reservationMapper).toDtos(List.of(reservation));
    }

    @Test
    void findById() {
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

        Customer customer = new Customer();
        customer.setFirstName("Janko");
        customer.setLastName("Hrasko");
        customer.setPhoneNumber("0948562321");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName("Janko");
        customerDto.setLastName("Hrasko");
        customerDto.setPhoneNumber("0948562321");

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setPrice(50.0);
        reservation.setCourt(court);
        reservation.setDeleted(Boolean.FALSE);
        reservation.setGameType(GameType.SINGLES);
        reservation.setCreated(LocalDateTime.now());
        reservation.setTariffType(TariffType.FIRST_TARIFF);
        reservation.setStartTime(LocalDateTime.now().plusDays(2));
        reservation.setCustomer(customer);

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(1L);
        reservationDto.setPrice(50.0);
        reservationDto.setCourt(courtDto);
        reservationDto.setGameType(GameType.SINGLES);
        reservationDto.setCreated(LocalDateTime.now());
        reservationDto.setTariffType(TariffType.FIRST_TARIFF);
        reservationDto.setStartTime(LocalDateTime.now().plusDays(2));
        reservationDto.setCustomer(customerDto);

        when(reservationDao.findById(1L)).thenReturn(reservation);
        when(reservationMapper.toDto(reservation)).thenReturn(reservationDto);

        ReservationDto result = reservationService.findById(1L);

        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals(reservation.getPrice(), result.getPrice());
        Assertions.assertEquals(reservation.getCustomer().getPhoneNumber(), result.getCustomer().getPhoneNumber());
        verify(reservationDao).findById(1L);
        verify(reservationMapper).toDto(reservation);
    }

    @Test
    void findByCourtId_WithExistingCourt() {
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

        Customer customer = new Customer();
        customer.setFirstName("Janko");
        customer.setLastName("Hrasko");
        customer.setPhoneNumber("0948562321");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName("Janko");
        customerDto.setLastName("Hrasko");
        customerDto.setPhoneNumber("0948562321");

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setPrice(50.0);
        reservation.setCourt(court);
        reservation.setDeleted(Boolean.FALSE);
        reservation.setGameType(GameType.SINGLES);
        reservation.setCreated(LocalDateTime.now());
        reservation.setTariffType(TariffType.FIRST_TARIFF);
        reservation.setStartTime(LocalDateTime.now().plusDays(2));
        reservation.setCustomer(customer);

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(1L);
        reservationDto.setPrice(50.0);
        reservationDto.setCourt(courtDto);
        reservationDto.setGameType(GameType.SINGLES);
        reservationDto.setCreated(LocalDateTime.now());
        reservationDto.setTariffType(TariffType.FIRST_TARIFF);
        reservationDto.setStartTime(LocalDateTime.now().plusDays(2));
        reservationDto.setCustomer(customerDto);

        when(reservationDao.findByCourtId(1L, true)).thenReturn(List.of(reservation));
        when(reservationMapper.toDtos(List.of(reservation))).thenReturn(List.of(reservationDto));
        when(courtDao.findById(reservation.getCourt().getId())).thenReturn(court);

        List<ReservationDto> result = reservationService.findByCourtId(1L, true);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(reservation.getPrice(), result.getFirst().getPrice());
        Assertions.assertEquals(reservation.getCustomer().getPhoneNumber(),
                                result.getFirst().getCustomer().getPhoneNumber());
        verify(courtDao).findById(1L);
        verify(reservationMapper).toDtos(List.of(reservation));
        verify(reservationDao).findByCourtId(1L, true);
    }

    @Test
    void findByCourtId_WithNotExistingCourt() {

        when(courtDao.findById(1L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () ->
                reservationService.findByCourtId(1L, true)
        );

        verify(courtDao).findById(1L);
        verify(reservationMapper, never()).toDtos(any());
        verify(reservationDao, never()).findByCourtId(1L, true);
    }

    @Test
    void findByPhoneNumber_WithExistingPhoneNumber() {
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

        Customer customer = new Customer();
        customer.setFirstName("Janko");
        customer.setLastName("Hrasko");
        customer.setPhoneNumber("0948562321");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName("Janko");
        customerDto.setLastName("Hrasko");
        customerDto.setPhoneNumber("0948562321");

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setCourt(court);
        reservation.setDeleted(Boolean.FALSE);
        reservation.setGameType(GameType.SINGLES);
        reservation.setCreated(LocalDateTime.now());
        reservation.setTariffType(TariffType.FIRST_TARIFF);
        reservation.setStartTime(LocalDateTime.now().plusDays(2));
        reservation.setCustomer(customer);

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(1L);
        reservationDto.setCourt(courtDto);
        reservationDto.setGameType(GameType.SINGLES);
        reservationDto.setCreated(LocalDateTime.now());
        reservationDto.setTariffType(TariffType.FIRST_TARIFF);
        reservationDto.setStartTime(LocalDateTime.now().plusDays(2));
        reservationDto.setCustomer(customerDto);

        when(reservationDao.findByPhoneNumber("0948562321", true)).thenReturn(List.of(reservation));
        when(reservationMapper.toDtos(List.of(reservation))).thenReturn(List.of(reservationDto));

        List<ReservationDto> result = reservationService.findByPhoneNumber("0948562321", true);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(reservation.getPrice(), result.getFirst().getPrice());
        Assertions.assertEquals(reservation.getCustomer().getPhoneNumber(),
                                result.getFirst().getCustomer().getPhoneNumber());
        verify(reservationDao).findByPhoneNumber("0948562321", true);
        verify(reservationMapper).toDtos(List.of(reservation));
    }

    @Test
    void findByPhoneNumber_WithNotExistingPhoneNumber() {

        when(reservationDao.findByPhoneNumber("0948562321", true)).thenReturn(new ArrayList<>());
        when(reservationMapper.toDtos(new ArrayList<>())).thenReturn(new ArrayList<>());

        List<ReservationDto> result = reservationService.findByPhoneNumber("0948562321", true);

        Assertions.assertEquals(0, result.size());;
        verify(reservationDao).findByPhoneNumber("0948562321", true);
        verify(reservationMapper).toDtos(any());
    }

    @Test
    void save_WithNotDefinedCourtId() {
        CourtDto courtDto = new CourtDto();

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(1L);
        reservationDto.setCourt(courtDto);
        reservationDto.setGameType(GameType.SINGLES);
        reservationDto.setCreated(LocalDateTime.now());
        reservationDto.setTariffType(TariffType.FIRST_TARIFF);
        reservationDto.setStartTime(LocalDateTime.now().plusDays(2));

        Court court = new Court();

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setCourt(court);
        reservation.setGameType(GameType.SINGLES);
        reservation.setCreated(LocalDateTime.now());
        reservation.setTariffType(TariffType.FIRST_TARIFF);
        reservation.setStartTime(LocalDateTime.now().plusDays(2));

        when(reservationMapper.toEntity(reservationDto)).thenReturn(reservation);

        assertThrows(IllegalArgumentException.class, () -> reservationService.save(reservationDto));

        verify(reservationDao, never()).save(any());
        verify(reservationMapper).toEntity(any());
    }

    @Test
    void save_WithNotDefinedCourt() {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(1L);
        reservationDto.setPrice(50.0);
        reservationDto.setGameType(GameType.SINGLES);
        reservationDto.setCreated(LocalDateTime.now());
        reservationDto.setTariffType(TariffType.FIRST_TARIFF);
        reservationDto.setStartTime(LocalDateTime.now().plusDays(2));

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setPrice(50.0);
        reservation.setGameType(GameType.SINGLES);
        reservation.setCreated(LocalDateTime.now());
        reservation.setTariffType(TariffType.FIRST_TARIFF);
        reservation.setStartTime(LocalDateTime.now().plusDays(2));

        when(reservationMapper.toEntity(reservationDto)).thenReturn(reservation);

        assertThrows(IllegalArgumentException.class, () -> reservationService.save(reservationDto));

        verify(reservationMapper).toEntity(any());
        verify(reservationDao, never()).save(any());
    }

    @Test
    void save_WithTimeCollisionWithOtherCase1() {
        Court court = new Court();
        court.setId(1L);
        court.setCreated(LocalDateTime.now());
        court.setDeleted(Boolean.FALSE);
        court.setName("Tennis court");

        CourtDto courtDto = new CourtDto();
        courtDto.setId(1L);
        courtDto.setCreated(LocalDateTime.now());
        courtDto.setName("Tennis court");

        Customer customer = new Customer();
        customer.setFirstName("Janko");
        customer.setLastName("Hrasko");
        customer.setPhoneNumber("0948562321");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName("Janko");
        customerDto.setLastName("Hrasko");
        customerDto.setPhoneNumber("0948562321");

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setCourt(court);
        reservation.setDeleted(Boolean.FALSE);
        reservation.setGameType(GameType.SINGLES);
        reservation.setCreated(LocalDateTime.now());
        reservation.setTariffType(TariffType.FIRST_TARIFF);
        reservation.setStartTime(LocalDateTime.now().plusDays(2));
        reservation.setCustomer(customer);

        Reservation collidedReservation = new Reservation();
        collidedReservation.setId(2L);
        collidedReservation.setPrice(50.0);
        collidedReservation.setCourt(court);
        collidedReservation.setDeleted(Boolean.FALSE);
        collidedReservation.setGameType(GameType.SINGLES);
        collidedReservation.setCreated(LocalDateTime.now());
        collidedReservation.setTariffType(TariffType.THIRD_TARIFF);
        collidedReservation.setStartTime(LocalDateTime.now().plusDays(2).minusHours(1));

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(1L);
        reservationDto.setCourt(courtDto);
        reservationDto.setGameType(GameType.SINGLES);
        reservationDto.setCreated(LocalDateTime.now());
        reservationDto.setTariffType(TariffType.FIRST_TARIFF);
        reservationDto.setStartTime(LocalDateTime.now().plusDays(2));
        reservationDto.setCustomer(customerDto);

        when(courtDao.findById(courtDto.getId())).thenReturn(court);
        when(reservationMapper.toEntity(reservationDto)).thenReturn(reservation);
        when(customerDao.findByPhoneNumber(reservation.getCustomer().getPhoneNumber())).thenReturn(customer);
        when(reservationDao.findByCourtIdAndDate(reservation.getCourt().getId(), reservation.getStartTime())).thenReturn(List.of(collidedReservation));

        assertThrows(IllegalArgumentException.class, () -> reservationService.save(reservationDto));

        verify(reservationDao).findByCourtIdAndDate(any(), any());
        verify(customerDao).findByPhoneNumber(any());
        verify(customerDao, never()).save(any());
        verify(reservationMapper).toEntity(any());
        verify(courtDao).findById(any());
        verify(reservationDao, never()).save(any());
    }

    @Test
    void save_WithTimeCollisionWithOtherCase2() {
        Court court = new Court();
        court.setId(1L);
        court.setCreated(LocalDateTime.now());
        court.setDeleted(Boolean.FALSE);
        court.setName("Tennis court");

        CourtDto courtDto = new CourtDto();
        courtDto.setId(1L);
        courtDto.setCreated(LocalDateTime.now());
        courtDto.setName("Tennis court");

        Customer customer = new Customer();
        customer.setFirstName("Janko");
        customer.setLastName("Hrasko");
        customer.setPhoneNumber("0948562321");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName("Janko");
        customerDto.setLastName("Hrasko");
        customerDto.setPhoneNumber("0948562321");

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setCourt(court);
        reservation.setDeleted(Boolean.FALSE);
        reservation.setGameType(GameType.SINGLES);
        reservation.setCreated(LocalDateTime.now());
        reservation.setTariffType(TariffType.FIRST_TARIFF);
        reservation.setStartTime(LocalDateTime.now().plusDays(2));
        reservation.setCustomer(customer);

        Reservation collidedReservation = new Reservation();
        collidedReservation.setId(2L);
        collidedReservation.setPrice(50.0);
        collidedReservation.setCourt(court);
        collidedReservation.setDeleted(Boolean.FALSE);
        collidedReservation.setGameType(GameType.SINGLES);
        collidedReservation.setCreated(LocalDateTime.now());
        collidedReservation.setTariffType(TariffType.THIRD_TARIFF);
        collidedReservation.setStartTime(LocalDateTime.now().plusDays(2).plusMinutes(25));

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(1L);
        reservationDto.setCourt(courtDto);
        reservationDto.setGameType(GameType.SINGLES);
        reservationDto.setCreated(LocalDateTime.now());
        reservationDto.setTariffType(TariffType.FIRST_TARIFF);
        reservationDto.setStartTime(LocalDateTime.now().plusDays(2));
        reservationDto.setCustomer(customerDto);

        when(courtDao.findById(courtDto.getId())).thenReturn(court);
        when(reservationMapper.toEntity(reservationDto)).thenReturn(reservation);
        when(customerDao.findByPhoneNumber(reservation.getCustomer().getPhoneNumber())).thenReturn(customer);
        when(reservationDao.findByCourtIdAndDate(reservation.getCourt().getId(), reservation.getStartTime())).thenReturn(List.of(collidedReservation));

        assertThrows(IllegalArgumentException.class, () -> reservationService.save(reservationDto));

        verify(reservationDao).findByCourtIdAndDate(any(), any());
        verify(customerDao).findByPhoneNumber(any());
        verify(customerDao, never()).save(any());
        verify(reservationMapper).toEntity(any());
        verify(courtDao).findById(any());
        verify(reservationDao, never()).save(any());
    }

    @Test
    void save_CorrectReservationSingles() {
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

        Customer customer = new Customer();
        customer.setFirstName("Janko");
        customer.setLastName("Hrasko");
        customer.setPhoneNumber("0948562321");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName("Janko");
        customerDto.setLastName("Hrasko");
        customerDto.setPhoneNumber("0948562321");

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setCourt(court);
        reservation.setDeleted(Boolean.FALSE);
        reservation.setGameType(GameType.SINGLES);
        reservation.setCreated(LocalDateTime.now());
        reservation.setTariffType(TariffType.FIRST_TARIFF);
        reservation.setStartTime(LocalDateTime.now().plusDays(2));
        reservation.setCustomer(customer);

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(1L);
        reservationDto.setCourt(courtDto);
        reservationDto.setGameType(GameType.SINGLES);
        reservationDto.setCreated(LocalDateTime.now());
        reservationDto.setTariffType(TariffType.FIRST_TARIFF);
        reservationDto.setStartTime(LocalDateTime.now().plusDays(2));
        reservationDto.setCustomer(customerDto);

        when(courtDao.findById(courtDto.getId())).thenReturn(court);
        when(reservationMapper.toEntity(reservationDto)).thenReturn(reservation);
        when(customerDao.findByPhoneNumber(reservation.getCustomer().getPhoneNumber())).thenReturn(customer);
        when(reservationDao.findByCourtIdAndDate(reservation.getCourt().getId(), reservation.getStartTime())).thenReturn(new ArrayList<>());
        doNothing().when(reservationDao).save(any(Reservation.class));

        reservationService.save(reservationDto);

        ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
        verify(reservationDao).save(captor.capture());

        Reservation saved = captor.getValue();
        Assertions.assertEquals(20.0, saved.getPrice());

        verify(reservationDao).findByCourtIdAndDate(any(), any());
        verify(customerDao).findByPhoneNumber(any());
        verify(customerDao, never()).save(any());
        verify(reservationMapper).toEntity(any());
        verify(courtDao).findById(any());
    }

    @Test
    void save_CorrectReservationDoubles() {
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

        Customer customer = new Customer();
        customer.setFirstName("Janko");
        customer.setLastName("Hrasko");
        customer.setPhoneNumber("0948562321");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName("Janko");
        customerDto.setLastName("Hrasko");
        customerDto.setPhoneNumber("0948562321");

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setCourt(court);
        reservation.setDeleted(Boolean.FALSE);
        reservation.setGameType(GameType.DOUBLES);
        reservation.setCreated(LocalDateTime.now());
        reservation.setTariffType(TariffType.FIRST_TARIFF);
        reservation.setStartTime(LocalDateTime.now().plusDays(2));
        reservation.setCustomer(customer);

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(1L);
        reservationDto.setCourt(courtDto);
        reservationDto.setGameType(GameType.DOUBLES);
        reservationDto.setCreated(LocalDateTime.now());
        reservationDto.setTariffType(TariffType.FIRST_TARIFF);
        reservationDto.setStartTime(LocalDateTime.now().plusDays(2));
        reservationDto.setCustomer(customerDto);

        when(courtDao.findById(courtDto.getId())).thenReturn(court);
        when(reservationMapper.toEntity(reservationDto)).thenReturn(reservation);
        when(customerDao.findByPhoneNumber(reservation.getCustomer().getPhoneNumber())).thenReturn(customer);
        when(reservationDao.findByCourtIdAndDate(reservation.getCourt().getId(), reservation.getStartTime())).thenReturn(new ArrayList<>());
        doNothing().when(reservationDao).save(any(Reservation.class));

        reservationService.save(reservationDto);

        ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
        verify(reservationDao).save(captor.capture());

        Reservation saved = captor.getValue();
        Assertions.assertEquals(30.0, saved.getPrice());

        verify(reservationDao).findByCourtIdAndDate(any(), any());
        verify(customerDao).findByPhoneNumber(any());
        verify(customerDao, never()).save(any());
        verify(reservationMapper).toEntity(any());
        verify(courtDao).findById(any());
    }

    @Test
    void save_CorrectReservationSecondTariff() {
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

        Customer customer = new Customer();
        customer.setFirstName("Janko");
        customer.setLastName("Hrasko");
        customer.setPhoneNumber("0948562321");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName("Janko");
        customerDto.setLastName("Hrasko");
        customerDto.setPhoneNumber("0948562321");

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setCourt(court);
        reservation.setDeleted(Boolean.FALSE);
        reservation.setGameType(GameType.SINGLES);
        reservation.setCreated(LocalDateTime.now());
        reservation.setTariffType(TariffType.SECOND_TARIFF);
        reservation.setStartTime(LocalDateTime.now().plusDays(2));
        reservation.setCustomer(customer);

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(1L);
        reservationDto.setCourt(courtDto);
        reservationDto.setGameType(GameType.SINGLES);
        reservationDto.setCreated(LocalDateTime.now());
        reservationDto.setTariffType(TariffType.SECOND_TARIFF);
        reservationDto.setStartTime(LocalDateTime.now().plusDays(2));
        reservationDto.setCustomer(customerDto);

        when(courtDao.findById(courtDto.getId())).thenReturn(court);
        when(reservationMapper.toEntity(reservationDto)).thenReturn(reservation);
        when(customerDao.findByPhoneNumber(reservation.getCustomer().getPhoneNumber())).thenReturn(customer);
        when(reservationDao.findByCourtIdAndDate(reservation.getCourt().getId(), reservation.getStartTime())).thenReturn(new ArrayList<>());
        doNothing().when(reservationDao).save(any(Reservation.class));

        reservationService.save(reservationDto);

        ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
        verify(reservationDao).save(captor.capture());

        Reservation saved = captor.getValue();
        Assertions.assertEquals(45.0, saved.getPrice());

        verify(reservationDao).findByCourtIdAndDate(any(), any());
        verify(customerDao).findByPhoneNumber(any());
        verify(customerDao, never()).save(any());
        verify(reservationMapper).toEntity(any());
        verify(courtDao).findById(any());
    }

    @Test
    void save_CorrectReservationThirdTariff() {
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

        Customer customer = new Customer();
        customer.setFirstName("Janko");
        customer.setLastName("Hrasko");
        customer.setPhoneNumber("0948562321");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName("Janko");
        customerDto.setLastName("Hrasko");
        customerDto.setPhoneNumber("0948562321");

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setCourt(court);
        reservation.setDeleted(Boolean.FALSE);
        reservation.setGameType(GameType.SINGLES);
        reservation.setCreated(LocalDateTime.now());
        reservation.setTariffType(TariffType.THIRD_TARIFF);
        reservation.setStartTime(LocalDateTime.now().plusDays(2));
        reservation.setCustomer(customer);

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(1L);
        reservationDto.setCourt(courtDto);
        reservationDto.setGameType(GameType.SINGLES);
        reservationDto.setCreated(LocalDateTime.now());
        reservationDto.setTariffType(TariffType.THIRD_TARIFF);
        reservationDto.setStartTime(LocalDateTime.now().plusDays(2));
        reservationDto.setCustomer(customerDto);

        when(courtDao.findById(courtDto.getId())).thenReturn(court);
        when(reservationMapper.toEntity(reservationDto)).thenReturn(reservation);
        when(customerDao.findByPhoneNumber(reservation.getCustomer().getPhoneNumber())).thenReturn(customer);
        when(reservationDao.findByCourtIdAndDate(reservation.getCourt().getId(), reservation.getStartTime())).thenReturn(new ArrayList<>());
        doNothing().when(reservationDao).save(any(Reservation.class));

        reservationService.save(reservationDto);

        ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
        verify(reservationDao).save(captor.capture());

        Reservation saved = captor.getValue();
        Assertions.assertEquals(55.0, saved.getPrice());

        verify(reservationDao).findByCourtIdAndDate(any(), any());
        verify(customerDao).findByPhoneNumber(any());
        verify(customerDao, never()).save(any());
        verify(reservationMapper).toEntity(any());
        verify(courtDao).findById(any());
    }

    @Test
    void save_CorrectReservationFourthTariff() {
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

        Customer customer = new Customer();
        customer.setFirstName("Janko");
        customer.setLastName("Hrasko");
        customer.setPhoneNumber("0948562321");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName("Janko");
        customerDto.setLastName("Hrasko");
        customerDto.setPhoneNumber("0948562321");

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setCourt(court);
        reservation.setDeleted(Boolean.FALSE);
        reservation.setGameType(GameType.SINGLES);
        reservation.setCreated(LocalDateTime.now());
        reservation.setTariffType(TariffType.FOURTH_TARIFF);
        reservation.setStartTime(LocalDateTime.now().plusDays(2));
        reservation.setCustomer(customer);

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(1L);
        reservationDto.setCourt(courtDto);
        reservationDto.setGameType(GameType.SINGLES);
        reservationDto.setCreated(LocalDateTime.now());
        reservationDto.setTariffType(TariffType.FOURTH_TARIFF);
        reservationDto.setStartTime(LocalDateTime.now().plusDays(2));
        reservationDto.setCustomer(customerDto);

        when(courtDao.findById(courtDto.getId())).thenReturn(court);
        when(reservationMapper.toEntity(reservationDto)).thenReturn(reservation);
        when(customerDao.findByPhoneNumber(reservation.getCustomer().getPhoneNumber())).thenReturn(customer);
        when(reservationDao.findByCourtIdAndDate(reservation.getCourt().getId(), reservation.getStartTime())).thenReturn(new ArrayList<>());
        doNothing().when(reservationDao).save(any(Reservation.class));

        reservationService.save(reservationDto);

        ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
        verify(reservationDao).save(captor.capture());

        Reservation saved = captor.getValue();
        Assertions.assertEquals(60.0, saved.getPrice());

        verify(reservationDao).findByCourtIdAndDate(any(), any());
        verify(customerDao).findByPhoneNumber(any());
        verify(customerDao, never()).save(any());
        verify(reservationMapper).toEntity(any());
        verify(courtDao).findById(any());
    }

    @Test
    void update_NotExistingReservation() {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setGameType(GameType.SINGLES);
        reservationDto.setCreated(LocalDateTime.now());
        reservationDto.setTariffType(TariffType.SECOND_TARIFF);
        reservationDto.setStartTime(LocalDateTime.now().plusDays(2));

        when(reservationDao.findById(1L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> reservationService.update(reservationDto, 1L));

        verify(reservationDao, never()).findByCourtIdAndDate(any(), any());
        verify(customerDao, never()).findByPhoneNumber(any());
        verify(customerDao, never()).save(any());
        verify(reservationMapper, never()).toEntity(any());
        verify(courtDao, never()).findById(any());
    }

    @Test
    void update_CorrectReservationSecondTariff() {
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

        Customer customer = new Customer();
        customer.setFirstName("Janko");
        customer.setLastName("Hrasko");
        customer.setPhoneNumber("0948562321");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName("Janko");
        customerDto.setLastName("Hrasko");
        customerDto.setPhoneNumber("0948562321");

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setCourt(court);
        reservation.setDeleted(Boolean.FALSE);
        reservation.setGameType(GameType.SINGLES);
        reservation.setCreated(LocalDateTime.now());
        reservation.setTariffType(TariffType.SECOND_TARIFF);
        reservation.setStartTime(LocalDateTime.now().plusDays(2));
        reservation.setCustomer(customer);

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(1L);
        reservationDto.setCourt(courtDto);
        reservationDto.setGameType(GameType.SINGLES);
        reservationDto.setCreated(LocalDateTime.now());
        reservationDto.setTariffType(TariffType.SECOND_TARIFF);
        reservationDto.setStartTime(LocalDateTime.now().plusDays(3));
        reservationDto.setCustomer(customerDto);

        when(courtDao.findById(reservationDto.getCourt().getId())).thenReturn(court);
        when(reservationDao.findById(1L)).thenReturn(reservation);
        when(customerDao.findByPhoneNumber(reservation.getCustomer().getPhoneNumber())).thenReturn(customer);
        when(reservationDao.findByCourtIdAndDate(reservationDto.getCourt().getId(), reservationDto.getStartTime())).thenReturn(new ArrayList<>());
        doAnswer(invocation -> {
            Reservation entityArg = invocation.getArgument(0);
            ReservationDto dtoArg = invocation.getArgument(1);

            entityArg.setStartTime(dtoArg.getStartTime());
            return null;
        }).when(reservationMapper).update(any(Reservation.class), any(ReservationDto.class));
        doNothing().when(reservationDao).update(any(Reservation.class));

        reservationService.update(reservationDto, 1L);

        ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
        verify(reservationDao).update(captor.capture());

        Reservation updated = captor.getValue();
        Assertions.assertEquals(reservationDto.getStartTime(), updated.getStartTime());

        verify(reservationDao).findByCourtIdAndDate(any(), any());
        verify(customerDao).findByPhoneNumber(any());
        verify(customerDao, never()).save(any());
        verify(courtDao).findById(any());
    }
}
