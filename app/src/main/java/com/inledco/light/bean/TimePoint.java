package com.inledco.light.bean;

import java.io.Serializable;

/**
 * Created by huangzhengguo on 2018/1/2.
 * 时间点，存储小时和分钟
 */

public class TimePoint implements Serializable {
    private byte mHour;
    private byte mMinute;

    public TimePoint(byte hour, byte minute) {
        mHour = hour;
        mMinute = minute;
    }

    public String getFormatTimePoint(String format) {
        return String.format(format, mHour, mMinute);
    }

    public byte getHour() {
        return mHour;
    }

    public void setHour(byte hour) {
        this.mHour = hour;
    }

    public byte getMinute() {
        return mMinute;
    }

    public void setMinute(byte minute) {
        this.mMinute = minute;
    }
}
