package com.clock.sunyangyang.ss;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.clock.sunyangyang.ss.View.CommonPopView;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sunyangyang on 17/9/18.
 */

public class AlarmActivity extends BaseActivity implements View.OnClickListener {
    private CommonPopView mPopView;
    private TextView mTxtDate;
    private View mView;
    private MaterialCalendarView mCalendarView;
    private DateFormat mDateFormat = new SimpleDateFormat("d LLLL", Locale.getDefault());
    private Date mDate;
    private final Date mDateToday = new Date();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);/**/
        setContentView(R.layout.activity_alarm);
        mView = LayoutInflater.from(this).inflate(R.layout.layout_date_picker, null, false);
        mCalendarView = (MaterialCalendarView) mView.findViewById(R.id.calendar_view);
        mTxtDate = (TextView) findViewById(R.id.txt_date);
        findViewById(R.id.layout_date).setOnClickListener(this);
        mDate = new Date();
        mTxtDate.setText(String.valueOf(mDateFormat.format(mDateToday)));
        setCalendarListener();
    }

    private void setCalendarListener() {
        mCalendarView.setSelectedDate(mDateToday);
        mCalendarView.setBackgroundColor(0xffeeeeee);
        mCalendarView.state().edit()
                .setMinimumDate(mDateToday)
                .commit();
        mCalendarView.getCurrentDate();
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
        }
    }
}
