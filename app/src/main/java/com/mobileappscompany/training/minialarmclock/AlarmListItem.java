package com.mobileappscompany.training.minialarmclock;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.mobileappscompany.training.minialarmclock.com.mobileappscompany.training.minialarmclock.domain.Alarm;
import com.mobileappscompany.training.minialarmclock.com.mobileappscompany.training.minialarmclock.domain.Constants;
import com.mobileappscompany.training.minialarmclock.com.mobileappscompany.training.minialarmclock.domain.Day;

/**
 * Created by Kenneth Brewer on 3/20/2015.
 */
public class AlarmListItem extends RelativeLayout {

    private final String TAG = "AlarmListItem";

    private Switch       switchAlarmOn;
    private TextView     textAlarmTime;
    private TextView     textAlarmLabel;
    private TextView     textRepeatDays;
    private TextView     textSnoozed;

    private Context context;

    private Alarm alarm;

    public AlarmListItem(Context context) {
        this(context, null, 0);
    }

    public AlarmListItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlarmListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.alarm_list_item, this);

        initControls();
    }

    private void initControls() {
        switchAlarmOn  = (Switch)findViewById(R.id.switchAlarmOn);
        textAlarmTime  = (TextView)findViewById(R.id.textAlarmTime);
        textAlarmLabel = (TextView)findViewById(R.id.textAlarmLabel);
        textRepeatDays = (TextView)findViewById(R.id.textRepeatDays);
        textSnoozed    = (TextView)findViewById(R.id.textSnoozed);

        switchAlarmOn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                alarm.setOn(switchAlarmOn.isChecked());
                if (!alarm.isOn()) {
                    textSnoozed.setVisibility(View.INVISIBLE);
                    alarm.snooze(false);
                    populateItems();
                }
                Log.d(TAG, "switchAlarmOn: Alarm on? " + alarm.isOn());
            }
        });

    }

    private void populateItems() {
        switchAlarmOn.setChecked(alarm.isOn());



        if(alarm.isSnoozed()) {
            switchAlarmOn.setVisibility(View.INVISIBLE);
            textSnoozed.setVisibility(View.VISIBLE);
            textSnoozed.setText(context.getString(R.string.snoozed));

            textAlarmTime.setText("" + alarm.getSnoozedTime().getHourOfDay() + ":"
                    + (alarm.getSnoozedTime().getMinuteOfHour()<10?"0":"")
                    +  alarm.getSnoozedTime().getMinuteOfHour() );
        } else {
            switchAlarmOn.setVisibility(View.VISIBLE);
            textSnoozed.setVisibility(View.INVISIBLE);

            textAlarmTime.setText("" + alarm.getAlarmTime().getHourOfDay() + ":"
                    + (alarm.getAlarmTime().getMinuteOfHour()<10?"0":"")
                    +  alarm.getAlarmTime().getMinuteOfHour() );
        }

        if(alarm.getLabel() != null && alarm.getLabel().length() > 0) {
            textAlarmLabel.setText(alarm.getLabel());
        } else {
            textAlarmLabel.setVisibility(View.INVISIBLE);
        }

        if(alarm.getRepeatDays() > 0) {
            textRepeatDays.setText(buildRepeatDays());
        } else {
            textRepeatDays.setVisibility(View.INVISIBLE);
        }

    }

    private boolean checkBitMask(byte value, byte bitmask) {
        return (value & bitmask) == 0?false:true;
    }

    private String buildRepeatDays() {
        StringBuilder builder = new StringBuilder();

        if(checkBitMask(alarm.getRepeatDays(),Day.MONDAY.getBitmask())) {
            builder.append(context.getString(R.string.monday_abbrev));
        }

        if(checkBitMask(alarm.getRepeatDays(),Day.TUESDAY.getBitmask())) {
            if(builder.toString().length() >= 3) builder.append(",");
            builder.append(context.getString(R.string.tuesday_abbrev));
        }

        if(checkBitMask(alarm.getRepeatDays(),Day.WEDNESDAY.getBitmask())) {
            if(builder.toString().length() >= 3) builder.append(",");
            builder.append(context.getString(R.string.wednesday_abbrev));
        }

        if(checkBitMask(alarm.getRepeatDays(),Day.THURSDAY.getBitmask())) {
            if(builder.toString().length() >= 3) builder.append(",");
            builder.append(context.getString(R.string.thursday_abbrev));
        }

        if(checkBitMask(alarm.getRepeatDays(),Day.FRIDAY.getBitmask())) {
            if(builder.toString().length() >= 3) builder.append(",");
            builder.append(context.getString(R.string.friday_abbrev));
        }

        if(checkBitMask(alarm.getRepeatDays(),Day.SATURDAY.getBitmask())) {
            if(builder.toString().length() >= 3) builder.append(",");
            builder.append(context.getString(R.string.saturday_abbrev));
        }

        if(checkBitMask(alarm.getRepeatDays(), Day.SUNDAY.getBitmask())) {
            if(builder.toString().length() >= 3) builder.append(",");
            builder.append(context.getString(R.string.sunday_abbrev));
        }

        return builder.toString();
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
        populateItems();
    }
}
