package com.courtly.service.impl;

import com.courtly.dao.ReservationDao;
import com.courtly.entity.Court;
import com.courtly.entity.Customer;
import com.courtly.entity.Reservation;
import com.courtly.service.CourtService;
import com.courtly.service.CustomerService;
import com.courtly.service.ReservationService;
import com.courtly.utils.ReservationUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Service
public class ReservationServiceImpl extends AbstractService<Reservation, ReservationDao> implements ReservationService {
    private final CourtService courtService;
    private final CustomerService customerService;
    private final ReservationDao reservationDao;

    public ReservationServiceImpl(ReservationDao dao, CourtService courtService, CustomerService customerService) {
        super(dao);
        this.courtService = courtService;
        this.customerService = customerService;
        this.reservationDao = dao;
    }

    @Override
    public void save(Reservation reservation) {
        Objects.requireNonNull(reservation);
        Court court = this.getCourt(reservation);
        if (court == null){
            throw new IllegalArgumentException("Court not found");
        }
        reservation.setCourt(court);
        Customer customer = customerService.findByPhoneNumber(reservation.getCustomer().getPhoneNumber());
        if (customer == null){
            customerService.save(reservation.getCustomer());
            customer = customerService.findByPhoneNumber(reservation.getCustomer().getPhoneNumber());
        }
        reservation.setCustomer(customer);
        this.checkCollisionWithOtherReservations(reservation);
        this.calculatePrice(reservation);

        super.save(reservation);
    }

    @Override
    public List<Reservation> findByCourtId(Long courtId, Boolean ascending) {
        Court court = courtService.findById(courtId);
        if (court == null){
            throw new IllegalArgumentException("Court with id "+courtId+" not found");
        }
        return reservationDao.findByCourtId(courtId, ascending);
    }

    @Override
    public List<Reservation> findByPhoneNumber(String phoneNumber, Boolean inFuture) {
        return reservationDao.findByPhoneNumber(phoneNumber, inFuture);
    }

    @Override
    public void validate(Reservation entity) {

    }

    private Court getCourt(Reservation reservation){
        if (reservation.getCourt() == null || reservation.getCourt().getId() == null){
            return null;
        }

        return courtService.findById(reservation.getCourt().getId());
    }

    private void checkCollisionWithOtherReservations(Reservation reservation){
        List<Reservation> otherReservations = reservationDao.findByCourtIdAndDate(reservation.getCourt().getId(),
                                                                                  reservation.getStartTime());
        if (CollectionUtils.isEmpty(otherReservations)){
            return;
        }

        for (Reservation other : otherReservations){
            if (ReservationUtils.isInCollision(reservation, other)){
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
