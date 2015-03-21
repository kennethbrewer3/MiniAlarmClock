package com.mobileappscompany.training.minialarmclock.com.mobileappscompany.training.minialarmclock.domain;

/**
 * Created by Android1 on 3/21/2015.
 */
public enum Day {

    SUNDAY   ((byte)64),
    MONDAY   ((byte)32),
    TUESDAY  ((byte)16),
    WEDNESDAY((byte)8),
    THURSDAY ((byte)4),
    FRIDAY   ((byte)2),
    SATURDAY ((byte)1);

    private byte bitmask;

    private Day(byte bitmask) {
        this.bitmask = bitmask;
    }

    public byte getBitmask() {
        return bitmask;
    }
}
