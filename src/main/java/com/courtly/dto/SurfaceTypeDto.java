package com.courtly.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurfaceTypeDto extends BaseDto {
    private String name;
    private Double firstTariff;
    private Double secondTariff;
    private Double thirdTariff;
    private Double fourthTariff;
}
