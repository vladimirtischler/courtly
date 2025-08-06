package com.courtly.dto;

import com.courtly.enums.GameType;
import com.courtly.enums.TariffType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto extends BaseDto {
    private CourtDto court;
    private LocalDateTime startTime;
    private TariffType tariffType;
    private GameType gameType;
    private CustomerDto customer;
    private Double price;
}
