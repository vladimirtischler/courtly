package com.courtly.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurfaceTypeDto extends BaseDto {

    @NotBlank(message = "Name of surface type must be not blank")
    private String name;

    @NotNull(message = "First tariff must be not null")
    private Double firstTariff;

    @NotNull(message = "Second tariff must be not null")
    private Double secondTariff;

    @NotNull(message = "Third tariff must be not null")
    private Double thirdTariff;

    @NotNull(message = "Fourth tariff must be not null")
    private Double fourthTariff;
}
