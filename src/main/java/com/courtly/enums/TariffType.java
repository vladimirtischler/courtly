package com.courtly.enums;

import lombok.Getter;

@Getter
public enum TariffType {
    FIRST_TARIFF(30),
    SECOND_TARIFF(60),
    THIRD_TARIFF(90),
    FOURTH_TARIFF(120);

    private final Integer minutesDuration;

    TariffType(Integer minutesDuration) {
        this.minutesDuration = minutesDuration;
    }
}
