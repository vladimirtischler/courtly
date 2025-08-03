package com.courtly.enums;

import lombok.Getter;

@Getter
public enum GameType {

    SINGLES(1.0),
    DOUBLES(1.5);

    private final double coefficient;

    GameType(double coefficient) {
        this.coefficient = coefficient;
    }
}
