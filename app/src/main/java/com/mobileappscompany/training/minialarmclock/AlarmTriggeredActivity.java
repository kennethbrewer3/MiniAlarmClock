package com.mobileappscompany.training.minialarmclock;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Vibrator;

import com.mobileappscompany.training.minialarmclock.com.mobileappscompany.training.minialarmclock.domain.Alarm;
import com.mobileappscompany.training.minialarmclock.com.mobileappscompany.training.minialarmclock.domain.Constants;


public class AlarmTriggeredActivity extends Activity {

    private static final String TAG = "AlarmTriggeredActivity";

    private TextView textAlarmTriggeredLabel;
    private TextView textAlarmTriggeredCurrentTime;
    private Button   buttonDismiss;
    private Button   buttonSnooze;

    private MediaPlayer mediaPlayer;

    private Alarm alarm;
    Vibrator vibrator;
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
        textAlarmTriggeredCurrentTime.setText(alarm.getAlarmTime().getHourOfDay() + ":" +
                                             (alarm.getAlarmTime().getMinuteOfHour() < 10?"0":"")
                                            + alarm.getAlarmTime().getMinuteOfHour());

        if(alarm.isTriggered()) {
            if(alarm.getSoundToPlay() == null) {
                mediaPlayer = MediaPlayer.create(this, R.raw.default_alarm_sound);
            } else {
                mediaPlayer = MediaPlayer.create(this,alarm.getSoundToPlay());
            }
            mediaPlayer.setVolume(alarm.getVolume(),alarm.getVolume());
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }

        if(alarm.isVibrate()) {
            // Get instance of Vibrator from current Context
            vibrator = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);

            if(vibrator.hasVibrator()) {
                long[] pattern = {0, 250, 200, 250, 150, 150, 75, 150, 75, 150
                };
                vibrator.vibrate(pattern, 0);
            } else {
                Log.d(TAG, "Can't vibrate");
            }
        }

        buttonDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Constants.DISMISSED_ALARM,alarm);
                setResult(Constants.DISMISSED_ALARM_RESULT_CODE,intent);

                mediaPlayer.stop();
                if(vibrator != null) {
                    vibrator.cancel();
                }

                finish();
            }
        });

        buttonSnooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TAG, "buttonSnooze: Snoozed alarm: " + alarm.toString());
                Intent intent = new Intent();
                intent.putExtra(Constants.SNOOZED_ALARM,alarm);
                setResult(Constants.SNOOZED_ALARM_RESULT_CODE,intent);

                mediaPlayer.stop();
                if(vibrator != null) {
                    vibrator.cancel();
                }

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
