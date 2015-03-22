package com.mobileappscompany.training.minialarmclock.com.mobileappscompany.training.minialarmclock.domain;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Android1 on 3/20/2015.
 */
public class Alarm implements Parcelable {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alarm alarm = (Alarm) o;

        if (hour != alarm.hour) return false;
        if (minute != alarm.minute) return false;
        if (repeatDays != alarm.repeatDays) return false;
        if (!label.equals(alarm.label)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = hour;
        result = 31 * result + minute;
        result = 31 * result + label.hashCode();
        result = 31 * result + (int) repeatDays;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Alarm{");
        sb.append("on=").append(on);
        sb.append(", hour=").append(hour);
        sb.append(", minute=").append(minute);
        sb.append(", label='").append(label).append('\'');
        sb.append(", repeatDays=").append(repeatDays);
        sb.append(", soundType=").append(soundType);
        sb.append(", soundToPlay=").append(soundToPlay);
        sb.append(", vibrate=").append(vibrate);
        sb.append(", volumeCrescendo=").append(volumeCrescendo);
        sb.append(", timeToMaxVolume=").append(timeToMaxVolume);
        sb.append(", volume=").append(volume);
        sb.append(", snoozeMethod=").append(snoozeMethod);
        sb.append(", dismissMethod=").append(dismissMethod);
        sb.append(", onInSilentMode=").append(onInSilentMode);
        sb.append(", autoSnoozeDuration=").append(autoSnoozeDuration);
        sb.append(", autoDismissDuration=").append(autoDismissDuration);
        sb.append(", snoozed=").append(snoozed);
        sb.append(", triggered=").append(triggered);
        sb.append(", hasBeenTriggeredToday=").append(hasBeenTriggeredToday);
        sb.append('}');
        return sb.toString();
    }

    protected Alarm(Parcel in) {
        on = in.readByte() != 0x00;
        hour = in.readInt();
        minute = in.readInt();
        label = in.readString();
        repeatDays = in.readByte();
        soundType = (SoundType) in.readValue(SoundType.class.getClassLoader());
        soundToPlay = (Uri) in.readValue(Uri.class.getClassLoader());
        vibrate = in.readByte() != 0x00;
        volumeCrescendo = in.readByte() != 0x00;
        timeToMaxVolume = (Duration) in.readValue(Duration.class.getClassLoader());
        volume = in.readInt();
        snoozeMethod = (SnoozeMethod) in.readValue(SnoozeMethod.class.getClassLoader());
        dismissMethod = (DismissMethod) in.readValue(DismissMethod.class.getClassLoader());
        onInSilentMode = in.readByte() != 0x00;
        autoSnoozeDuration = (Duration) in.readValue(Duration.class.getClassLoader());
        autoDismissDuration = (Duration) in.readValue(Duration.class.getClassLoader());
        snoozed = in.readByte() != 0x00;
        triggered = in.readByte() != 0x00;
        hasBeenTriggeredToday = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (on ? 0x01 : 0x00));
        dest.writeInt(hour);
        dest.writeInt(minute);
        dest.writeString(label);
        dest.writeByte(repeatDays);
        dest.writeValue(soundType);
        dest.writeValue(soundToPlay);
        dest.writeByte((byte) (vibrate ? 0x01 : 0x00));
        dest.writeByte((byte) (volumeCrescendo ? 0x01 : 0x00));
        dest.writeValue(timeToMaxVolume);
        dest.writeInt(volume);
        dest.writeValue(snoozeMethod);
        dest.writeValue(dismissMethod);
        dest.writeByte((byte) (onInSilentMode ? 0x01 : 0x00));
        dest.writeValue(autoSnoozeDuration);
        dest.writeValue(autoDismissDuration);
        dest.writeByte((byte) (snoozed ? 0x01 : 0x00));
        dest.writeByte((byte) (triggered ? 0x01 : 0x00));
        dest.writeByte((byte) (hasBeenTriggeredToday ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Alarm> CREATOR = new Parcelable.Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };
}