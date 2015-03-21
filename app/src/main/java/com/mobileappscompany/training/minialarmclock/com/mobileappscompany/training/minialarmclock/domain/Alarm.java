package com.mobileappscompany.training.minialarmclock.com.mobileappscompany.training.minialarmclock.domain;

import android.net.Uri;

/**
 * Created by Android1 on 3/20/2015.
 */
public class Alarm {
    private boolean on;
    private int hour;
    private int minute;
    private String label;
    private byte repeatDays;
    private Uri soundToPlay;

    public Alarm(int hour, int minute) {
        this.hour   = hour;
        this.minute = minute;

        //By default a new alarm will be on
        on = true;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public byte getRepeatDays() {
        return repeatDays;
    }

    public void setRepeatDays(byte repeatDays) {
        this.repeatDays = repeatDays;
    }

    public Uri getSoundToPlay() {
        return soundToPlay;
    }

    public void setSoundToPlay(Uri soundToPlay) {
        this.soundToPlay = soundToPlay;
    }
}
