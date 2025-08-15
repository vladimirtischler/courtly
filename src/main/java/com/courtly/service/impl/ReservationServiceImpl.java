package com.courtly.service.impl;

import com.courtly.dao.CourtDao;
import com.courtly.dao.CustomerDao;
import com.courtly.dao.ReservationDao;
import com.courtly.dto.ReservationDto;
import com.courtly.entity.Court;
import com.courtly.entity.Customer;
import com.courtly.entity.Reservation;
import com.courtly.mapper.ReservationMapper;
import com.courtly.service.CustomerService;
import com.courtly.service.ReservationService;
import com.courtly.utils.ReservationUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Service
public class ReservationServiceImpl extends AbstractService<Reservation, ReservationDao, ReservationDto, ReservationMapper> implements ReservationService {
    private final CourtDao courtDao;
    private final CustomerDao customerDao;

    public ReservationServiceImpl(ReservationDao dao, ReservationMapper reservationMapper, CourtDao courtDao, CustomerDao customerDao) {
        super(dao, reservationMapper);
        this.courtDao = courtDao;
        this.customerDao = customerDao;
    }

    @Override
    public void save(ReservationDto dto) {
        Reservation reservation = super.mapper.toEntity(dto);
        this.processReservation(reservation);

        super.dao.save(reservation);
    }

    @Override
    public void update(ReservationDto dto, Long id) {
        Reservation reservation = super.dao.findById(id);
        if (reservation == null){
            throw new IllegalArgumentException("Reservation with id "+id+" not found");
        }
        super.mapper.update(reservation, dto);
        this.processReservation(reservation);

        super.dao.update(reservation);
    }

    @Override
    public List<ReservationDto> findByCourtId(Long courtId, Boolean ascending) {
        Court court = courtDao.findById(courtId);
        if (court == null){
            throw new IllegalArgumentException("Court with id "+courtId+" not found");
        }
        return super.mapper.toDtos(super.dao.findByCourtId(courtId, ascending));
    }

    @Override
    public List<ReservationDto> findByPhoneNumber(String phoneNumber, Boolean inFuture) {
        List<Reservation> reservations = super.dao.findByPhoneNumber(phoneNumber, inFuture);
        return super.mapper.toDtos(reservations);
    }

    private void processReservation(Reservation reservation){
        Court court = this.getCourt(reservation);
        if (court == null){
            throw new IllegalArgumentException("Court not found");
        }
        reservation.setCourt(court);
        Customer customer = customerDao.findByPhoneNumber(reservation.getCustomer().getPhoneNumber());
        if (customer == null){
            customerDao.save(reservation.getCustomer());
            customer = customerDao.findByPhoneNumber(reservation.getCustomer().getPhoneNumber());
        }
        reservation.setCustomer(customer);
        this.checkCollisionWithOtherReservations(reservation);
        this.calculatePrice(reservation);
    }

    private Court getCourt(Reservation reservation){
        if (reservation.getCourt() == null || reservation.getCourt().getId() == null){
            return null;
        }

        return courtDao.findById(reservation.getCourt().getId());
    }

    private void checkCollisionWithOtherReservations(Reservation reservation){
        List<Reservation> otherReservations = super.dao.findByCourtIdAndDate(reservation.getCourt().getId(),
                                                                                  reservation.getStartTime());
        if (CollectionUtils.isEmpty(otherReservations)){
            return;
        }

        for (Reservation other : otherReservations){
            if (ReservationUtils.isInCollision(reservation, other) && !other.getId().equals(reservation.getId())){
                throw new IllegalArgumentException("Time of reservation is in collision with other");
            }
        }
    }

    private void calculatePrice(Reservation reservation) {
        Court court = reservation.getCourt();
        Double finalPrice = switch (reservation.getTariffType()) {
            case FIRST_TARIFF -> court.getSurfaceType().getFirstTariff();
            case SECOND_TARIFF -> court.getSurfaceType().getSecondTariff();
            case THIRD_TARIFF -> court.getSurfaceType().getThirdTariff();
            case FOURTH_TARIFF -> court.getSurfaceType().getFourthTariff();
        };

        finalPrice *= reservation.getGameType().getCoefficient();
        reservation.setPrice(finalPrice);
    }
}
