package com.mobileappscompany.training.minialarmclock;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.mobileappscompany.training.minialarmclock.com.mobileappscompany.training.minialarmclock.domain.Alarm;

/**
 * Created by Android1 on 3/20/2015.
 */
public class AlarmListItem extends RelativeLayout {

    private final String TAG = "AlarmListItem";

    private Switch       switchAlarmOn;
    private TextView     textAlarmTime;
    private TextView     textAlarmLabel;
    private TextView     textRepeatDays;

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

        switchAlarmOn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                alarm.setOn(switchAlarmOn.isChecked());
            }
        });

    }

    private void populateItems() {
        switchAlarmOn.setChecked(alarm.isOn());
        textAlarmTime.setText("" + alarm.getHour() + ":" + (alarm.getMinute()<10?"0":"") + alarm.getMinute() );

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

        if(checkBitMask(alarm.getRepeatDays(),(byte)64)) {
            builder.append(context.getString(R.string.sunday_abbrev));
        }

        if(checkBitMask(alarm.getRepeatDays(),(byte)32)) {
            if(builder.toString().length() >= 3) builder.append(",");
            builder.append(context.getString(R.string.monday_abbrev));
        }

        if(checkBitMask(alarm.getRepeatDays(),(byte)16)) {
            if(builder.toString().length() >= 3) builder.append(",");
            builder.append(context.getString(R.string.tuesday_abbrev));
        }

        if(checkBitMask(alarm.getRepeatDays(),(byte)8)) {
            if(builder.toString().length() >= 3) builder.append(",");
            builder.append(context.getString(R.string.wednesday_abbrev));
        }

        if(checkBitMask(alarm.getRepeatDays(),(byte)4)) {
            if(builder.toString().length() >= 3) builder.append(",");
            builder.append(context.getString(R.string.thursday_abbrev));
        }

        if(checkBitMask(alarm.getRepeatDays(),(byte)2)) {
            if(builder.toString().length() >= 3) builder.append(",");
            builder.append(context.getString(R.string.friday_abbrev));
        }

        if(checkBitMask(alarm.getRepeatDays(),(byte)1)) {
            if(builder.toString().length() >= 3) builder.append(",");
            builder.append(context.getString(R.string.saturday_abbrev));
        }

        return builder.toString();
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
        populateItems();
    }
}
