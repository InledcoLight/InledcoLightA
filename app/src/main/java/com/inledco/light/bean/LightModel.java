package com.inledco.light.bean;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by HuangZhengGuo on 2018/1/1.
 * 手动和自动模式应该使用一个模型
 */

public class LightModel implements Serializable,Cloneable {
    private static final long serialVersionUID = 7284666673318500459L;
    // 控制器id
    private short deviceId;
    // 灯具id
    private short mLightId;
    // 设备名称
    private String mDeviceName;
    // mac地址
    private String mMacAddress;
    // 控制器通道数量:默认为最大5
    private int controllerNum = 5;
    // 通道数量
    private short mChannelNum;
    // 运行模式
    private RunMode runMode;

    // 手动模式数据
    private boolean isPowerOn;
    // 手动模式动态效果
    private byte manualDynamic;
    // 手动模式通道数据
    private short[] mChnValues;
    // 用户自定义数据
    private ArrayList<byte[]> mUserDefineColorValue;

    // 自动模式数据
    // 时间点个数
    private int mTimePointCount;
    // 时间点数组
    private ArrayList<TimePoint> mTimePoints;
    // 时间点对应颜色值，使用数组列表存储
    private ArrayList<byte[]> mTimePointColorValue;

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

    public LightModel() {}

    public LightModel(short deviceId, short channelNum, ArrayList<TimePoint> timePoints, ArrayList<byte[]> timePointColorValue) {
        this.deviceId = deviceId;
        this.mChannelNum = channelNum;
        this.mTimePoints = timePoints;
        this.mTimePointColorValue = timePointColorValue;
    }

    public Object clone() {
        LightModel o = null;
        try {
            o = (LightModel)super.clone();
        } catch (CloneNotSupportedException ex) {
            Log.d("Clone","不支持克隆");
        }

        // o.mUserDefineColorValue = (ArrayList<byte[]>)mUserDefineColorValue.clone();
        o.mTimePoints = (ArrayList<TimePoint>)mTimePoints.clone();
        ArrayList<byte[]> timeColorValues = new ArrayList<>();
        for (int i=0;i<mTimePointColorValue.size();i++) {
            byte[] colorValues = new byte[mTimePointColorValue.get(i).length];

            for (int j=0;j<colorValues.length;j++) {
                colorValues[j] = mTimePointColorValue.get(i)[j];
            }
            timeColorValues.add(colorValues);
        }

        o.mTimePointColorValue = timeColorValues;

        // o.mDynamicPeriod = (RampTime)mDynamicPeriod.clone();

        return o;
    }

    public short getChannelNum() {
        return mChannelNum;
    }

    public void setChannelNum(short channelNum) {
        mChannelNum = channelNum;
    }

    public void setTimePointColorValue(int timePointIndex, int colorIndex, int colorValue) {
        if (timePointIndex > mTimePointColorValue.size() - 1) {
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

    public byte getManualDynamic() {
        return manualDynamic;
    }

    public void setmManualDynamic(byte manualDynamic) {
        this.manualDynamic = manualDynamic;
    }

    public ArrayList<byte[]> getTimePointColorValue() {
        return mTimePointColorValue;
    }

    public void setTimePointColorValue(ArrayList<byte[]> timePointColorValue) {
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

    public short[] getmChnValues() {
        return mChnValues;
    }

    public void setmChnValues(short[] mChnValues) {
        this.mChnValues = mChnValues;
    }

    public RunMode getRunMode() {
        return runMode;
    }

    public void setRunMode(RunMode runMode) {
        this.runMode = runMode;
    }

    public int getControllerNum() {
        return controllerNum;
    }

    public void setControllerNum(int controllerNum) {
        this.controllerNum = controllerNum;
    }

    public int getTimePointCount() {
        return mTimePointCount;
    }

    public void setTimePointCount(int timePointCount) {
        this.mTimePointCount = timePointCount;
    }

    public ArrayList<byte[]> getUserDefineColorValue() {
        return mUserDefineColorValue;
    }

    public void setUserDefineColorValue(ArrayList<byte[]> userDefineColorValue) {
        mUserDefineColorValue = userDefineColorValue;
    }

    public ArrayList<TimePoint> getTimePoints() {
        return mTimePoints;
    }

    public void setTimePoints(ArrayList<TimePoint> timePoints) {
        mTimePoints = timePoints;
    }

    public String getMacAddress() {
        return mMacAddress;
    }

    public void setMacAddress(String macAddress) {
        mMacAddress = macAddress;
    }

    public String getDeviceName() {
        return mDeviceName;
    }

    public void setDeviceName(String deviceName) {
        mDeviceName = deviceName;
    }

    public short getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(short deviceId) {
        this.deviceId = deviceId;
    }

    public short getLightId() {
        return mLightId;
    }

    public void setLightId(short lightId) {
        mLightId = lightId;
    }
}
