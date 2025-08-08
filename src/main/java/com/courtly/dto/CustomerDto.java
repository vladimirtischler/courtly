package com.courtly.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto extends BaseDto {

    @NotBlank(message = "Firstname must be not blank")
    private String firstName;

    @NotBlank(message = "Lastname must be not blank")
    private String lastName;

    @NotBlank(message = "Phone number must be not blank")
    private String phoneNumber;
}
