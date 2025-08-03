package com.courtly.entity;

import com.courtly.enums.GameType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Reservation extends BaseEntity implements Comparable<Reservation>{

    @ManyToOne
    @JoinColumn(name = "court_id")
    private Court court;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "game_type")
    @Enumerated(value = EnumType.STRING)
    private GameType gameType;


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
