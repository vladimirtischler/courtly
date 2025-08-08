package com.courtly.dto;

import com.courtly.enums.GameType;
import com.courtly.enums.TariffType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Court must be not null, needed only id")
    private CourtDto court;

    @NotNull(message = "Start time must be not null")
    private LocalDateTime startTime;

    @NotNull(message = "Tariff type must be not null")
    private TariffType tariffType;

    @NotNull(message = "Game type must be not null")
    private GameType gameType;

    @Valid
    @NotNull(message = "Customer must be not null")
    private CustomerDto customer;

    private Double price;
}
