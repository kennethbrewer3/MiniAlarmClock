package com.mobileappscompany.training.minialarmclock.com.mobileappscompany.training.minialarmclock.domain;

import android.net.Uri;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Android1 on 3/20/2015.
 */
public class Alarm {
    private boolean on;
    private int hour;
    private int minute;
    private String label;
    private byte repeatDays;
    private SoundType soundType;
    private Uri soundToPlay;
    private boolean vibrate;
    private boolean volumeCrescendo;
    private Duration timeToMaxVolume;
    private int volume;
    private SnoozeMethod snoozeMethod;
    private DismissMethod dismissMethod;
    private boolean onInSilentMode;
    private Duration autoSnoozeDuration;
    private Duration autoDismissDuration;

    private boolean snoozed;
    private boolean triggered;
    private boolean hasBeenTriggeredToday;

    public Alarm(int hour, int minute) {
        this.hour   = hour;
        this.minute = minute;

        //By default a new alarm will be on
        on = true;
        hasBeenTriggeredToday = false;
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

    public SoundType getSoundType() {
        return soundType;
    }

    public void setSoundType(SoundType soundType) {
        this.soundType = soundType;
    }

    public Uri getSoundToPlay() {
        return soundToPlay;
    }

    public void setSoundToPlay(Uri soundToPlay) {
        this.soundToPlay = soundToPlay;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public boolean isVolumeCrescendo() {
        return volumeCrescendo;
    }

    public void setVolumeCrescendo(boolean volumeCrescendo) {
        this.volumeCrescendo = volumeCrescendo;
    }

    public Duration getTimeToMaxVolume() {
        return timeToMaxVolume;
    }

    public void setTimeToMaxVolume(Duration timeToMaxVolume) {
        this.timeToMaxVolume = timeToMaxVolume;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public SnoozeMethod getSnoozeMethod() {
        return snoozeMethod;
    }

    public void setSnoozeMethod(SnoozeMethod snoozeMethod) {
        this.snoozeMethod = snoozeMethod;
    }

    public DismissMethod getDismissMethod() {
        return dismissMethod;
    }

    public void setDismissMethod(DismissMethod dismissMethod) {
        this.dismissMethod = dismissMethod;
    }

    public boolean isOnInSilentMode() {
        return onInSilentMode;
    }

    public void setOnInSilentMode(boolean onInSilentMode) {
        this.onInSilentMode = onInSilentMode;
    }

    public Duration getAutoSnoozeDuration() {
        return autoSnoozeDuration;
    }

    public void setAutoSnoozeDuration(Duration autoSnoozeDuration) {
        this.autoSnoozeDuration = autoSnoozeDuration;
    }

    public Duration getAutoDismissDuration() {
        return autoDismissDuration;
    }

    public void setAutoDismissDuration(Duration autoDismissDuration) {
        this.autoDismissDuration = autoDismissDuration;
    }

    public void cancelTrigger(GregorianCalendar currentDay) {
        triggered = false;
        hasBeenTriggeredToday = true;

        int dayIndex = currentDay.get(Calendar.DAY_OF_WEEK) - 1;
        Day tomorrow = Day.values()[dayIndex+1];
        if((repeatDays & tomorrow.getBitmask()) == 1) {
            on = true;
        } else {
            on = false;
        }
    }

    public boolean isTriggered() {
        return triggered;
    }

    public void checkForTrigger(int hour, int minute) {
        if(hasBeenTriggeredToday) return;

        if(this.hour == hour && this.minute == minute) {
            triggered = true;
        }
    }
}
