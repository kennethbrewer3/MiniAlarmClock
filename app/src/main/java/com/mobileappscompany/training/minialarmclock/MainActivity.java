package com.mobileappscompany.training.minialarmclock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mobileappscompany.training.minialarmclock.com.mobileappscompany.training.minialarmclock.domain.Alarm;
import com.mobileappscompany.training.minialarmclock.com.mobileappscompany.training.minialarmclock.domain.Constants;

import java.util.ArrayList;



public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    private static final String TAG = "MainActivity";

    private ListView listAlarms;

    private boolean is24;

    private AlarmArrayAdapter alarmAdapter;
    private ArrayList<Alarm> alarms;
    private Alarm currentAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarms = new ArrayList();

        alarmAdapter =
                new AlarmArrayAdapter(this,
                                      android.R.layout.simple_list_item_1,
                                      R.layout.alarm_list_item,alarms);

        is24 = DateFormat.is24HourFormat(this);



        listAlarms = (ListView)findViewById(R.id.listAlarms);
        listAlarms.setItemsCanFocus(true);


        listAlarms.setAdapter(alarmAdapter);
        alarmAdapter.notifyDataSetChanged();

        listAlarms.setOnItemClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

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
        switch(item.getItemId()) {
            case R.id.action_add_alarm:
                addAlarm();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(),AlarmEditActivity.class);
        intent.putExtra(Constants.EDITED_ALARM,alarms.get(position));
        startActivityForResult(intent, Constants.EDITED_ALARM_RESULT_CODE);
    }

    //Set up the intent to move to the edit alarm activity
    //with a new alarm.
    private void addAlarm() {
        Intent newAlarmIntent = new Intent(this,AlarmEditActivity.class);
        newAlarmIntent.putExtra(Constants.EDIT_ALARM_COMMAND_NAME,Constants.NEW_ALARM_COMMAND);
        startActivityForResult(newAlarmIntent, Constants.NEW_ALARM_RESULT_CODE);
    }
}
