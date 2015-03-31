package com.mobileappscompany.training.minialarmclock.com.mobileappscompany.training.minialarmclock.domain;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * Created by Android1 on 3/20/2015.
 */
public class Alarm implements Parcelable {
    public static final String TAG = "Alarm";

    private UUID id;
    private boolean on;
    private DateTime alarmTime;
    private DateTime snoozedTime;
    private String label;
    private byte repeatDays;
    private SoundType soundType;
    private Uri soundToPlay;
    private boolean vibrate;
    private boolean volumeCrescendo;
    private Duration timeToMaxVolume;
    private int volume;
    private SnoozeMethod snoozeMethod;
    private Duration snoozeDuration;
    private DismissMethod dismissMethod;
    private boolean onInSilentMode;
    private Duration autoSnoozeDuration;
    private Duration autoDismissDuration;

    private boolean snoozed;
    private boolean triggered;
    private boolean hasBeenTriggeredToday;

    public Alarm(DateTime alarmTime) {
        id = UUID.randomUUID();
        this.alarmTime = alarmTime;

        //By default a new alarm will be on
        on = true;
        snoozed = false;
        snoozeDuration = Duration.TEN_MINUTES;
        hasBeenTriggeredToday = false;
        soundToPlay = null;
    }

    public UUID getId() { return id; }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public DateTime getAlarmTime() { return alarmTime; }

    public void setAlarmTime(DateTime alarmTime) { this.alarmTime = alarmTime; }

    public DateTime getSnoozedTime() { return snoozedTime; }

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

    public Duration getSnoozeDuration() { return snoozeDuration;}

    public void setSnoozeDuration(Duration snoozeDuration) {this.snoozeDuration = snoozeDuration;}

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

    public void cancelTrigger(DateTime currentDay) {
        triggered = false;
        if(!snoozed) hasBeenTriggeredToday = true;

        int dayIndex = currentDay.getDayOfWeek();//-1;
        Day tomorrow = Day.values()[dayIndex];

        Log.d(TAG, "dayIndex: " + dayIndex + " tomorrow: " + tomorrow.toString());

        Log.d(TAG, "repeatDays: " + repeatDays + " tomorrow.getBitmask(): " + tomorrow.getBitmask());
        Log.d(TAG, "repeatDays & tomorrow.getBitmask(): " + (repeatDays & tomorrow.getBitmask()));
        if(snoozed || (repeatDays & tomorrow.getBitmask()) == tomorrow.getBitmask()) {
            on = true;
        } else {
            on = false;
        }
    }

    public boolean isTriggered() {
        return triggered;
    }

    public void checkForTrigger(DateTime time) {
        Day today = Day.values()[time.getDayOfWeek()-1];
        if(hasBeenTriggeredToday) return;

//        Log.d(TAG,"checkForTrigger: today: " + today);

        if(!snoozed) {
            if (repeatDays == 0 || (repeatDays & today.getBitmask()) == today.getBitmask()) {
                if (alarmTime.getHourOfDay() == time.getHourOfDay() &&
                        alarmTime.getMinuteOfHour() == time.getMinuteOfHour()) {
                    triggered = true;
                } else {
                    triggered = false;
                }
            }
        }

        if(snoozed) {
            if( snoozedTime.getHourOfDay() == time.getHourOfDay() &&
                    snoozedTime.getMinuteOfHour() == time.getMinuteOfHour()) {
                triggered = true;
            } else {
                triggered = false;
            }
        }
    }

    public boolean isSnoozed() { return snoozed; }

    public void snooze(boolean snoozed) {
        this.snoozed = snoozed;

        if(snoozed) {
            snoozedTime = new DateTime();
            Log.d(TAG, "snooze: now: " + snoozedTime.toString() + " snoozeDuration.getValue(): "
                                       + snoozeDuration.getValue());
            snoozedTime = snoozedTime.plusSeconds(snoozeDuration.getValue());
            Log.d(TAG, "snooze: now + 10 mins: " + snoozedTime.toString());
            cancelTrigger(snoozedTime);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alarm alarm = (Alarm) o;

        if (!id.equals(alarm.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Alarm{");
        sb.append("id=").append(id);
        sb.append(", on=").append(on);
        sb.append(", hour=").append(alarmTime.getHourOfDay());
        sb.append(", minute=").append(alarmTime.getMinuteOfHour());
        sb.append(", label='").append(label).append('\'');
        sb.append(", repeatDays=").append(repeatDays);
        sb.append(", soundType=").append(soundType);
        sb.append(", soundToPlay=").append(soundToPlay);
        sb.append(", vibrate=").append(vibrate);
        sb.append(", volumeCrescendo=").append(volumeCrescendo);
        sb.append(", timeToMaxVolume=").append(timeToMaxVolume);
        sb.append(", volume=").append(volume);
        sb.append(", snoozeMethod=").append(snoozeMethod);
        sb.append(", snoozeDuration=").append(snoozeDuration);
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
        id = (UUID) in.readValue(UUID.class.getClassLoader());
        on = in.readByte() != 0x00;
        alarmTime = (DateTime) in.readValue(DateTime.class.getClassLoader());
        snoozedTime = (DateTime) in.readValue(DateTime.class.getClassLoader());
        label = in.readString();
        repeatDays = in.readByte();
        soundType = (SoundType) in.readValue(SoundType.class.getClassLoader());
        soundToPlay = (Uri) in.readValue(Uri.class.getClassLoader());
        vibrate = in.readByte() != 0x00;
        volumeCrescendo = in.readByte() != 0x00;
        timeToMaxVolume = (Duration) in.readValue(Duration.class.getClassLoader());
        volume = in.readInt();
        snoozeMethod = (SnoozeMethod) in.readValue(SnoozeMethod.class.getClassLoader());
        snoozeDuration = (Duration) in.readValue(Duration.class.getClassLoader());
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
        dest.writeValue(id);
        dest.writeByte((byte) (on ? 0x01 : 0x00));
        dest.writeValue(alarmTime);
        dest.writeValue(snoozedTime);
        dest.writeString(label);
        dest.writeByte(repeatDays);
        dest.writeValue(soundType);
        dest.writeValue(soundToPlay);
        dest.writeByte((byte) (vibrate ? 0x01 : 0x00));
        dest.writeByte((byte) (volumeCrescendo ? 0x01 : 0x00));
        dest.writeValue(timeToMaxVolume);
        dest.writeInt(volume);
        dest.writeValue(snoozeMethod);
        dest.writeValue(snoozeDuration);
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