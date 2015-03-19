package com.mobileappscompany.training.minialarmclock;

import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class MainActivity extends ActionBarActivity {

    private GregorianCalendar time;
    private Handler mHandler = new Handler();

    private TextView textCurrentTime;

    private boolean is24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        is24 = DateFormat.is24HourFormat(this);

        textCurrentTime = (TextView)findViewById(R.id.textCurrentTime);
        updateTime();

        mHandler.postDelayed(updateTimeRunnable, 1000);
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
