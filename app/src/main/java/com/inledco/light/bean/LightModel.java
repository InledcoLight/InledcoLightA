package com.inledco.light.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by HuangZhengGuo on 2018/1/1.
 * 手动和自动模式应该使用一个模型
 */

public class LightModel implements Serializable {
    private static final long serialVersionUID = 7284666673318500459L;
    // 设备id
    private short mDeviceId;

    // 手动模式数据
    private boolean isPowerOn;
    // 手动模式动态效果
    private byte mManualDynamic;
    // 手动模式通道数据
    private short[] mChnValues;
    // 用户自定义数据
    private Map<Short, byte[]> mUserDefineColorValue;

    // 自动模式数据
    // 时间点数组
    private ArrayList<TimePoint> mTimePoints;
    // 时间点对应颜色值，时间点按照索引从0开始计算
    private Map<Short, byte[]> mTimePointColorValue;
    // 动态效果标记
    private boolean mHasDynamic;
    // 是否开启动态效果
    private boolean mDynamicEnable;
    // 每周动态效果是否开启标记
    private boolean mSun;
    private boolean mMon;
    private boolean mTue;
    private boolean mWed;
    private boolean mThu;
    private boolean mFri;
    private boolean mSat;
    // 动态效果持续时间
    private RampTime mDynamicPeriod;
    // 动态效果类型
    private byte mDynamicMode;

    public LightModel(short deviceId, ArrayList<TimePoint> timePoints, Map<Short, byte[]> timePointColorValue) {
        mDeviceId = deviceId;
        mTimePoints = timePoints;
        mTimePointColorValue = timePointColorValue;
    }

    public short getDeviceId() {
        return mDeviceId;
    }

    public void setTimePointColorValue(int timePointIndex, int colorIndex, int colorValue) {
        if (timePointIndex > mTimePointColorValue.keySet().size() - 1) {
            return;
        }

        byte[] bytes = mTimePointColorValue.get((short)timePointIndex);

        if (colorIndex > bytes.length - 1) {
            return;
        }

        bytes[colorIndex] = (byte) (colorValue / 10);
    }

    // 获取动态效果使能
    public byte getWeek()
    {
        byte b = 0x00;
        if ( mDynamicEnable )
        {
            b |= 0x80;
        }
        if ( mSat )
        {
            b |= 0x40;
        }
        if ( mFri )
        {
            b |= 0x20;
        }
        if ( mThu )
        {
            b |= 0x10;
        }
        if ( mWed )
        {
            b |= 0x08;
        }
        if ( mTue )
        {
            b |= 0x04;
        }
        if ( mMon )
        {
            b |= 0x02;
        }
        if ( mSun )
        {
            b |= 0x01;
        }
        return b;
    }

    public boolean isPowerOn() {
        return isPowerOn;
    }

    public void setPowerOn(boolean powerOn) {
        isPowerOn = powerOn;
    }

    public byte getmManualDynamic() {
        return mManualDynamic;
    }

    public void setmManualDynamic(byte mManualDynamic) {
        this.mManualDynamic = mManualDynamic;
    }

    public Map<Short, byte[]> getmUserDefineColorValue() {
        return mUserDefineColorValue;
    }

    public void setmUserDefineColorValue(Map<Short, byte[]> mUserDefineColorValue) {
        this.mUserDefineColorValue = mUserDefineColorValue;
    }

    public ArrayList<TimePoint> getTimePoints() {
        return mTimePoints;
    }

    public void setTimePoints(ArrayList<TimePoint> timePoints) {
        this.mTimePoints = timePoints;
    }

    public Map<Short, byte[]> getTimePointColorValue() {
        return mTimePointColorValue;
    }

    public void setTimePointColorValue(Map<Short, byte[]> timePointColorValue) {
        this.mTimePointColorValue = timePointColorValue;
    }

    public boolean ismHasDynamic() {
        return mHasDynamic;
    }

    public void setmHasDynamic(boolean mHasDynamic) {
        this.mHasDynamic = mHasDynamic;
    }

    public boolean ismDynamicEnable() {
        return mDynamicEnable;
    }

    public void setmDynamicEnable(boolean mDynamicEnable) {
        this.mDynamicEnable = mDynamicEnable;
    }

    public boolean isSun ()
    {
        return mSun;
    }

    public void setSun ( boolean sun )
    {
        mSun = sun;
    }

    public boolean isMon ()
    {
        return mMon;
    }

    public void setMon ( boolean mon )
    {
        mMon = mon;
    }

    public boolean isTue ()
    {
        return mTue;
    }

    public boolean isWed ()
    {
        return mWed;
    }

    public void setWed ( boolean wed )
    {
        mWed = wed;
    }

    public boolean isThu ()
    {
        return mThu;
    }

    public void setThu ( boolean thu )
    {
        mThu = thu;
    }

    public void setTue ( boolean tue )
    {
        mTue = tue;
    }

    public boolean isFri ()
    {
        return mFri;
    }

    public void setFri ( boolean fri )
    {
        mFri = fri;
    }

    public boolean isSat ()
    {
        return mSat;
    }

    public void setSat ( boolean sat )
    {
        mSat = sat;
    }

    public RampTime getmDynamicPeriod() {
        return mDynamicPeriod;
    }

    public void setmDynamicPeriod(RampTime mDynamicPeriod) {
        this.mDynamicPeriod = mDynamicPeriod;
    }

    public byte getmDynamicMode() {
        return mDynamicMode;
    }

    public void setmDynamicMode(byte mDynamicMode) {
        this.mDynamicMode = mDynamicMode;
    }
}









































