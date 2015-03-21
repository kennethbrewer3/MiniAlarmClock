package com.mobileappscompany.training.minialarmclock.com.mobileappscompany.training.minialarmclock.domain;

/**
 * Created by Android1 on 3/21/2015.
 */
public enum Duration {
    FIVE_SECONDS(5),
    TEN_SECONDS(10),
    FIFTEEN_SECONDS(15),
    THIRTY_SECONDS(30),
    FORTY_FIVE_SECONDS(45),
    SIXTY_SECONDS(60),
    NINETY_SECONDS(90),
    TWO_MINUTES(120),
    THREE_MINUTES(180),
    FOUR_MINUTES(240),
    FIVE_MINUTES(300),
    TEN_MINUTES(600),
    FIFTEEN_MINUTES(900),
    TWENTY_MINUTES(1200),
    THIRTY_MINUTES(1800);

    private int value;
    private Duration(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
