package com.courtly.entity;

import com.courtly.enums.GameType;
import com.courtly.enums.TariffType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = Reservation.FIND_BY_COURT_ID_AND_DATE,
                    query = "SELECT r FROM Reservation r WHERE r.court.id=:courtId AND r.startTime BETWEEN :start AND" +
                            " :end"),
        @NamedQuery(name = Reservation.FIND_BY_COURT_ID_ORDER_BY_CREATED_DATE_ASC,
                    query = "SELECT r FROM Reservation r WHERE r.court.id=:courtId ORDER BY r.created ASC"),
        @NamedQuery(name = Reservation.FIND_BY_COURT_ID_ORDER_BY_CREATED_DATE_DESC,
                    query = "SELECT r FROM Reservation r WHERE r.court.id=:courtId ORDER BY r.created DESC"),
        @NamedQuery(name = Reservation.FIND_BY_PHONE_NUMBER,
                    query = "SELECT r FROM Reservation r WHERE r.customer.phoneNumber=:phoneNumber"),
        @NamedQuery(name = Reservation.FIND_BY_PHONE_NUMBER_IN_FUTURE,
                    query = "SELECT r FROM Reservation r WHERE r.customer.phoneNumber=:phoneNumber AND r.startTime > " +
                            "now()")
})
public class Reservation extends BaseEntity implements Comparable<Reservation>{
    public static final String FIND_BY_COURT_ID_AND_DATE = "Reservation.findByCourtIdAndDate";
    public static final String FIND_BY_COURT_ID_ORDER_BY_CREATED_DATE_ASC = "Reservation" +
            ".findByCourtIdOrderByCreatedDateAsc";
    public static final String FIND_BY_COURT_ID_ORDER_BY_CREATED_DATE_DESC = "Reservation" +
            ".findByCourtIdOrderByCreatedDateDesc";
    public static final String FIND_BY_PHONE_NUMBER = "Reservation" +
            ".findByPhoneNumber";
    public static final String FIND_BY_PHONE_NUMBER_IN_FUTURE = "Reservation" +
            ".findByPhoneNumberInFuture";

    @ManyToOne
    @JoinColumn(name = "court_id")
    private Court court;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "tariff_type")
    private TariffType tariffType;

    @Column(name = "game_type")
    @Enumerated(value = EnumType.STRING)
    private GameType gameType;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "price")
    private Double price;

    @Override
    public int compareTo(Reservation o) {
        if (o == null) {
            return 1;
        } else if (this.getCreated().isBefore(o.getCreated())){
            return -1;
        } else if (this.getCreated().equals(o.getCreated())) {
            return 0;
        }

        return 1;
    }
}
