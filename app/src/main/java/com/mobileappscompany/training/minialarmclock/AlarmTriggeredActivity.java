package com.mobileappscompany.training.minialarmclock;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mobileappscompany.training.minialarmclock.com.mobileappscompany.training.minialarmclock.domain.Alarm;
import com.mobileappscompany.training.minialarmclock.com.mobileappscompany.training.minialarmclock.domain.Constants;


public class AlarmTriggeredActivity extends ActionBarActivity {

    private TextView textAlarmTriggeredLabel;
    private TextView textAlarmTriggeredCurrentTime;
    private Button   buttonDismiss;
    private Button   buttonSnooze;

    private Alarm alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_triggered);
        Intent i = getIntent();
        alarm = (Alarm)i.getParcelableExtra(Constants.TRIGGERED_ALARM);

        textAlarmTriggeredLabel       = (TextView)findViewById(R.id.textAlarmTriggeredLabel);
        textAlarmTriggeredCurrentTime = (TextView)findViewById(R.id.textAlarmTriggeredCurrentTime);
        buttonDismiss                 = (Button)findViewById(R.id.buttonDismiss);
        buttonSnooze                  = (Button)findViewById(R.id.buttonSnooze);

        textAlarmTriggeredLabel.setText(alarm.getLabel());
        textAlarmTriggeredCurrentTime.setText(alarm.getHour() + ":" +
                                             (alarm.getMinute() < 10?"0":"") + alarm.getMinute());
        buttonDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Constants.DISMISSED_ALARM,alarm);
                setResult(Constants.DISMISSED_ALARM_RESULT_CODE,intent);
                finish();
            }
        });

        buttonSnooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Constants.SNOOZED_ALARM,alarm);
                setResult(Constants.SNOOZED_ALARM_RESULT_CODE,intent);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm_triggered, menu);
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
