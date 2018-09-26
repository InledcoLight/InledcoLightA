package com.inledco.light.bean;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by huangzhengguo on 2018/1/2.
 * 时间点，存储小时和分钟
 */

public class TimePoint implements Serializable,Cloneable {
    private byte mHour;
    private byte mMinute;

    public TimePoint(byte hour, byte minute) {
        mHour = hour;
        mMinute = minute;
    }

    public Object clone() {
        Object o = null;
        try {
            o = (TimePoint)super.clone();
        }catch (CloneNotSupportedException ex) {
            Log.d("TimePoint Clone","不支持克隆");
        }
        return o;
    }

    public String getFormatTimePoint(String format) {
        return String.format(format, mHour, mMinute);
    }

    public int getMinutesOfTimePoint() {
        return 60 * mHour + mMinute;
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
