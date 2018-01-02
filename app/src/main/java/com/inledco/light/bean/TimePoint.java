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

    public byte getmHour() {
        return mHour;
    }

    public void setmHour(byte mHour) {
        this.mHour = mHour;
    }

    public byte getmMinute() {
        return mMinute;
    }

    public void setmMinute(byte mMinute) {
        this.mMinute = mMinute;
    }
}
