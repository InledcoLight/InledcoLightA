package com.inledco.light.bean;

import android.util.Log;

import java.io.Serializable;

public class RampTime implements Serializable,Cloneable {
    private static final long serialVersionUID = -3985727232820727495L;
    private byte startHour;
    private byte startMinute;
    private byte endHour;
    private byte endMinute;

    public RampTime (byte startHour, byte startMinute, byte endHour, byte endMinute) {
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
    }

    public Object clone() {
        Object o = null;
        try {
            o = (RampTime)super.clone();
        }catch (CloneNotSupportedException ex) {
            Log.d("RampTime Clone","不支持克隆");
        }
        return o;
    }

    public byte getStartHour ()
    {
        return startHour;
    }

    public void setStartHour ( byte startHour )
    {
        this.startHour = startHour;
    }

    public byte getStartMinute ()
    {
        return startMinute;
    }

    public void setStartMinute ( byte startMinute )
    {
        this.startMinute = startMinute;
    }

    public byte getEndHour ()
    {
        return endHour;
    }

    public void setEndHour ( byte endHour )
    {
        this.endHour = endHour;
    }

    public byte getEndMinute ()
    {
        return endMinute;
    }

    public void setEndMinute ( byte endMinute )
    {
        this.endMinute = endMinute;
    }
}
