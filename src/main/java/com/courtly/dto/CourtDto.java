package com.courtly.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourtDto extends BaseDto {
    private String name;
    private SurfaceTypeDto surfaceType;
}
