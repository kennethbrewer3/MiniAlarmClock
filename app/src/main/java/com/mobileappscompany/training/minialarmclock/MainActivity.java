package com.mobileappscompany.training.minialarmclock;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mobileappscompany.training.minialarmclock.com.mobileappscompany.training.minialarmclock.domain.Alarm;
import com.mobileappscompany.training.minialarmclock.com.mobileappscompany.training.minialarmclock.domain.Constants;
import com.mobileappscompany.training.minialarmclock.com.mobileappscompany.training.minialarmclock.domain.Day;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";

    private GregorianCalendar time;
    private Handler mHandler = new Handler();

    private TextView textCurrentTime;
    private ListView listAlarms;

    private boolean is24;

    private AlarmArrayAdapter alarmAdapter;
    private ArrayList<Alarm> alarms;

    private boolean alarmTriggered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarms = new ArrayList();

        alarmAdapter = new AlarmArrayAdapter(this,android.R.layout.simple_list_item_1,R.id.textCurrentTime,alarms);

        is24 = DateFormat.is24HourFormat(this);

        textCurrentTime = (TextView)findViewById(R.id.textCurrentTime);
        updateTime();

        mHandler.postDelayed(updateTimeRunnable, 1000);

        listAlarms = (ListView)findViewById(R.id.listAlarms);

        alarmAdapter.add(makeAlarm());
        alarmAdapter.add(makeAnotherAlarm());

        listAlarms.setAdapter(alarmAdapter);
        alarmAdapter.notifyDataSetChanged();

        listAlarms.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG,"Long Cleek!");
                return false;
            }
        });
        alarmTriggered = false;
    }

    private Alarm makeAlarm() {
        int offset = 1;
        GregorianCalendar calendar = new GregorianCalendar();
        Alarm alarm = new Alarm(calendar.get(Calendar.HOUR_OF_DAY),
                                calendar.get(Calendar.MINUTE)==(60-offset)?0:calendar.get(Calendar.MINUTE) + offset);

        alarm.setLabel("First alarm");
        //alarm.setRepeatDays((byte)127);

        return alarm;
    }

    private Alarm makeAnotherAlarm() {
        int offset = 2;
        GregorianCalendar calendar = new GregorianCalendar();
        Alarm alarm = new Alarm(calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE)==(60-offset)?0:calendar.get(Calendar.MINUTE) + offset);

        alarm.setLabel("Second alarm");

        return alarm;
    }

    private Runnable updateTimeRunnable = new Runnable () {
        public void run() {
            updateTime();

            mHandler.postDelayed(updateTimeRunnable, 1000);
        }
    };

    private void updateTime() {
        is24 = DateFormat.is24HourFormat(this);
        time = new GregorianCalendar();
        textCurrentTime.setText(buildTimeString(true));
        checkAlarms(time);
//        Log.d(TAG,"Day of week value: " + Day.values()[time.get(Calendar.DAY_OF_WEEK)-1].toString());
    }

    private void checkAlarms(GregorianCalendar calendar) {
        for(int count = 0; count < alarmAdapter.getCount(); count++) {
            Alarm alarm = alarmAdapter.getItem(count);
            if(!alarm.isOn()) continue;

            alarm.checkForTrigger(calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE));

            if(alarm.isTriggered() && !alarmTriggered) {
                alarmTriggered = true;
                Intent intent = new Intent(getApplicationContext(),AlarmTriggeredActivity.class);
                intent.putExtra(Constants.TRIGGERED_ALARM,alarm);
                startActivityForResult(intent,Constants.TRIGGERED_ALARM_RESULT_CODE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Alarm alarm = (Alarm)data.getParcelableExtra(Constants.DISMISSED_ALARM);
        alarmAdapter.getItem(alarmAdapter.getPosition(alarm)).setOn(false);
        alarmTriggered = false;
        alarmAdapter.notifyDataSetChanged();
    }

    private String buildTimeString(boolean showSeconds) {
        String timeString = "";
        timeString += (is24?time.get(Calendar.HOUR_OF_DAY):time.get(Calendar.HOUR));
        timeString += ":";
        timeString += (time.get(Calendar.MINUTE)<10?"0":"");
        timeString += time.get(Calendar.MINUTE);
        if(showSeconds) {
            timeString += ":";
            timeString += (time.get(Calendar.SECOND)<10?"0":"");
            timeString += time.get(Calendar.SECOND);
        }
        timeString += (is24?"":(time.get(Calendar.AM_PM) == Calendar.AM?"AM":"PM"));

        return timeString;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
