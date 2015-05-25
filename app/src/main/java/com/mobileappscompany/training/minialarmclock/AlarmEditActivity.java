package com.mobileappscompany.training.minialarmclock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.mobileappscompany.training.minialarmclock.R;
import com.mobileappscompany.training.minialarmclock.com.mobileappscompany.training.minialarmclock.domain.Alarm;
import com.mobileappscompany.training.minialarmclock.com.mobileappscompany.training.minialarmclock.domain.Constants;
import com.mobileappscompany.training.minialarmclock.com.mobileappscompany.training.minialarmclock.domain.Day;

import org.joda.time.DateTime;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AlarmEditActivity extends Activity {
    private static final String TAG = "AlarmEditActivity";

    private int command;
    private Alarm alarm;
    private byte repeatDays;

    @InjectView(R.id.timePicker)      TimePicker timePicker;
    @InjectView(R.id.toggleMonday)    ToggleButton toggleMonday;
    @InjectView(R.id.toggleTuesday)   ToggleButton toggleTuesday;
    @InjectView(R.id.toggleWednesday) ToggleButton toggleWednesday;
    @InjectView(R.id.toggleThursday)  ToggleButton toggleThursday;
    @InjectView(R.id.toggleFriday)    ToggleButton toggleFriday;
    @InjectView(R.id.toggleSaturday)  ToggleButton toggleSaturday;
    @InjectView(R.id.toggleSunday)    ToggleButton toggleSunday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);

        ButterKnife.inject(this);

        repeatDays = 0;
        timePicker.setIs24HourView(DateFormat.is24HourFormat(this));

        Intent i = getIntent();
        command = i.getIntExtra(Constants.EDIT_ALARM_COMMAND_NAME,0);

        if(command == Constants.NEW_ALARM_COMMAND) {

        }

        if(command == Constants.EDIT_ALARM_COMMAND) {
            alarm = (Alarm) i.getParcelableExtra(Constants.EDITED_ALARM);

            timePicker.setCurrentHour(alarm.getAlarmTime().getHourOfDay());
            timePicker.setCurrentMinute(alarm.getAlarmTime().getMinuteOfHour());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_edit_alarm_done:
                alarm = buildNewAlarm();
                Intent newAlarmIntent =  new Intent();
                newAlarmIntent.putExtra(Constants.NEW_ALARM_LABEL,alarm);
                setResult(Constants.SUCCESSFUL_EDIT_ALARM_RESULT_CODE);
                finish();
                return true;
            case R.id.action_edit_alarm_cancel:
                setResult(Constants.CANCEL_EDIT_ALARM_RESULT_CODE);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Alarm buildNewAlarm() {
        DateTime currentTime = new DateTime();
        DateTime alarmTime =
                new DateTime(currentTime.getYear(),
                        currentTime.getMonthOfYear(),
                        currentTime.getDayOfMonth(),
                        timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute());
        alarm = new Alarm(alarmTime);

        updateRepeatDays();

        return alarm;
    }

    private void updateRepeatDays() {
        if(alarm != null) {
            alarm.setRepeatDays(repeatDays);
        }
    }

    private void setDayBit(final boolean toggle, final byte dayBitmask) {
        repeatDays = toggle? (byte)(repeatDays | dayBitmask):
                             (byte)(repeatDays ^ dayBitmask);
        updateRepeatDays();
        Log.d(TAG, "Day bit: " + repeatDays);
    }

    @OnClick(R.id.toggleMonday)
    public void toggleMondayClick() {
        setDayBit(toggleMonday.isChecked(),Day.MONDAY.getBitmask());
    }

    @OnClick(R.id.toggleTuesday)
    public void setToggleTuesdayClick() {
        setDayBit(toggleTuesday.isChecked(),Day.TUESDAY.getBitmask());
    }

    @OnClick(R.id.toggleWednesday)
    public void setToggleWednesdayClick() {
        setDayBit(toggleWednesday.isChecked(),Day.WEDNESDAY.getBitmask());
    }

    @OnClick(R.id.toggleThursday)
    public void setToggleThursdayClick() {
        setDayBit(toggleThursday.isChecked(),Day.THURSDAY.getBitmask());
    }

    @OnClick(R.id.toggleFriday)
    public void setToggleFridayClick() {
        setDayBit(toggleFriday.isChecked(),Day.FRIDAY.getBitmask());
    }

    @OnClick(R.id.toggleSaturday)
    public void setToggleSaturdayClick() {
        setDayBit(toggleSaturday.isChecked(),Day.SATURDAY.getBitmask());
    }

    @OnClick(R.id.toggleSunday)
    public void setToggleSundayClick() {
        setDayBit(toggleSunday.isChecked(), Day.SUNDAY.getBitmask());
    }
}
