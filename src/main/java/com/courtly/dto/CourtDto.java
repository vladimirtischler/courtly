package com.courtly.dto;

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
public class CourtDto extends BaseDto {

    @NotBlank(message = "Name of court must be not blank")
    private String name;

    @NotNull(message = "Surface type must be not null, needed only id or name")
    private SurfaceTypeDto surfaceType;
}