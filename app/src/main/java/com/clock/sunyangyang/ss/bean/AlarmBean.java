package com.clock.sunyangyang.ss.bean;

import com.clock.sunyangyang.ss.Constants;

import java.io.Serializable;

/**
 * Created by sunyangyang on 17/9/15.
 */

public class AlarmBean implements Serializable {
    public int id;
    public long startTime;
    public int hour;
    public int minute;
    public int mon;
    public int tue;
    public int wed;
    public int thu;
    public int fri;
    public int sat;
    public int sun;
    public boolean isTurnOn;
    public String ring;
    public String ringTitle;

    @Override
    public String toString() {
        return hour + "," + minute + "," + mon + ","
                + tue + "," + wed + "," + thu + ","
                + fri + "," + sat + "," + sun + ","
                + ring + "," + ringTitle;
    }

    public String getRepeatDay() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(sun == 0 ? "" : Constants.WEEK_DAY[0] + " ");
        stringBuilder.append(mon == 0 ? "" : Constants.WEEK_DAY[1] + " ");
        stringBuilder.append(tue == 0 ? "" : Constants.WEEK_DAY[2] + " ");
        stringBuilder.append(wed == 0 ? "" : Constants.WEEK_DAY[3] + " ");
        stringBuilder.append(thu == 0 ? "" : Constants.WEEK_DAY[4] + " ");
        stringBuilder.append(fri == 0 ? "" : Constants.WEEK_DAY[5] + " ");
        stringBuilder.append(sat == 0 ? "" : Constants.WEEK_DAY[6] + " ");
        String repeatDay = "Never";
        if (stringBuilder.length() > 0) {
            repeatDay = stringBuilder.substring(0, stringBuilder.length() - 1);
        }

        return repeatDay;
    }
}
