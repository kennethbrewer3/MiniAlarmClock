package com.mobileappscompany.training.minialarmclock.com.mobileappscompany.training.minialarmclock.domain;

/**
 * Created by Android1 on 3/21/2015.
 */
public enum Day {

    MONDAY   ((byte)64),
    TUESDAY  ((byte)32),
    WEDNESDAY((byte)16),
    THURSDAY ((byte)8),
    FRIDAY   ((byte)4),
    SATURDAY ((byte)2),
    SUNDAY   ((byte)1);

    private byte bitmask;

    private Day(byte bitmask) {
        this.bitmask = bitmask;
    }

    public byte getBitmask() {
        return bitmask;
    }
}
