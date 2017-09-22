package com.clock.sunyangyang.ss;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.clock.sunyangyang.ss.View.CommonPopView;
import com.clock.sunyangyang.ss.bean.AlarmBean;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by sunyangyang on 17/9/18.
 */

public class AlarmActivity extends BaseActivity implements View.OnClickListener {
    private final int REPEAT_REQUEST = 0;
    private final int ALARM_VOLUME_REQUEST = 1;
    private CommonPopView mPopView;
    private TextView mTxtDate;
    private TextView mTxtRepeat;
    private TextView mTxtAlarmVolume;
    private View mView;
    private TimePicker mTimePicker;
    private MaterialCalendarView mCalendarView;
    private DateFormat mDateFormat = new SimpleDateFormat("yyyy dd LLLL", Locale.getDefault());
    private Date mDate;
    private final Date mDateToday = new Date();
    private List<String> mList = new ArrayList<String>();
    private AlarmBean mBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        mView = LayoutInflater.from(this).inflate(R.layout.layout_date_picker, null, false);
        mCalendarView = (MaterialCalendarView) mView.findViewById(R.id.calendar_view);
        mTimePicker = (TimePicker) findViewById(R.id.time_picker);
        mTxtDate = (TextView) findViewById(R.id.txt_date);
        mTxtRepeat = (TextView) findViewById(R.id.txt_repeat);
        mTxtAlarmVolume = (TextView) findViewById(R.id.txt_alarm_volume);
        findViewById(R.id.layout_date).setOnClickListener(this);
        findViewById(R.id.layout_repeat).setOnClickListener(this);
        findViewById(R.id.layout_alarm_name).setOnClickListener(this);
        findViewById(R.id.layout_alarm_volume).setOnClickListener(this);
        mTxtDate.setText(String.valueOf(mDateFormat.format(mDateToday)).substring(5));
        Intent intent = getIntent();
        if (intent != null && intent.getBundleExtra("alarm") != null) {
            mBean = (AlarmBean) intent.getBundleExtra("alarm").get("bean");
        }
        if (mBean == null) {
            mBean = new AlarmBean();
        }
        mTxtRepeat.setText(mBean.getRepeatDay());
        setTimeChangeListener();
    }

    private void setTimeChangeListener() {
        mTimePicker.setIs24HourView(true);
        mTimePicker.setCurrentHour(mBean.hour);
        mTimePicker.setCurrentMinute(mBean.minute);

        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Log.e("XXXXX", "hourOfDay = " + hourOfDay + ", minute = " + minute);
            }
        });
//        mTimePicker.
        mCalendarView.setSelectedDate(mDateToday);
//        mCalendarView.setBackgroundColor(0xffeeeeee);
        mCalendarView.state().edit()
                .setMinimumDate(mDateToday)
                .commit();
        mCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
                mDate = date.getDate();
                String value = String.valueOf(mDateFormat.format(mDate)).substring(5);
                mTxtDate.setText(value);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_date:
                if (mPopView == null) {
                    mPopView = new CommonPopView(this, mView);
                }
                mPopView.showAtLocation(findViewById(R.id.layout_alarm), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.layout_repeat:
                Intent intent = new Intent(this, WeekSelectActivity.class);
                startActivityForResult(intent, REPEAT_REQUEST);
                break;
            case R.id.layout_alarm_name:

                break;
            case R.id.layout_alarm_volume:
                Intent alarmVolumeIntent = new Intent(
                        RingtoneManager.ACTION_RINGTONE_PICKER);
                alarmVolumeIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,
                        RingtoneManager.TYPE_ALARM);
                alarmVolumeIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE,
                        "Select ringTone");
                startActivityForResult(alarmVolumeIntent, ALARM_VOLUME_REQUEST);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REPEAT_REQUEST) {
            mList = data.getStringArrayListExtra("result");
            StringBuilder repeat = new StringBuilder();
            for (int i = 0; i < mList.size(); i++) {
                int pos = -1;
                pos = Integer.parseInt(mList.get(i));
                if (pos >= 0) {
                    repeat.append(Constants.WEEK_DAY[pos]);
                }
                if (i != mList.size() - 1) {
                    repeat.append(" ");
                }
            }
            mTxtRepeat.setText(!TextUtils.isEmpty(repeat) ? repeat : "Never");
        } else if (requestCode == ALARM_VOLUME_REQUEST) {
            Uri uri = data
                    .getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            if (uri != null) {
                mBean.ring = uri.getPath();
                Ringtone ringtone = RingtoneManager.getRingtone(AlarmActivity.this, uri);
                mBean.ringTitle = ringtone.getTitle(AlarmActivity.this);
            } else {
                mBean.ring = "";
                mBean.ringTitle = "None";
            }
            mTxtAlarmVolume.setText(mBean.ringTitle);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(-10, null);
        super.onBackPressed();
    }
}
