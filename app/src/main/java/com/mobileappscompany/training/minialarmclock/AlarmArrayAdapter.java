package com.mobileappscompany.training.minialarmclock;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobileappscompany.training.minialarmclock.com.mobileappscompany.training.minialarmclock.domain.Alarm;

import java.util.ArrayList;

/**
 * Created by Android1 on 3/20/2015.
 */
public class AlarmArrayAdapter extends ArrayAdapter<Alarm> {
    private final String TAG = "AlarmArrayAdapter";
    private Context context;
    ArrayList<Alarm> alarms;
    public AlarmArrayAdapter(Context context, int resource, int textViewResourceId, ArrayList<Alarm> alarms) {
        super(context,resource,textViewResourceId,alarms);
        this.context = context;
        this.alarms = alarms;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Alarm currentAlarm = alarms.get(position);
        AlarmListItem row = new AlarmListItem(context);
        row.setAlarm(currentAlarm);
        return row;
    }
}
