package com.inledco.light.bean;


/**
 * 控制器设备模型
 */
public class BaseDevice extends ListItem {
    // 品牌及分组信息
    private byte mMajorVersion;
    private byte mMinorVersion;
    private DevicePrefer mDevicePrefer;
    private boolean mOnline;
    private DeviceTime mDeviceTime;

    public BaseDevice(DevicePrefer devicePrefer, boolean online) {
        mDevicePrefer = devicePrefer;
        mOnline = online;
    }

    public BaseDevice(byte majorVersion, byte minorVersion, DevicePrefer devicePrefer, boolean online, DeviceTime deviceTime) {
        mMajorVersion = majorVersion;
        mMinorVersion = minorVersion;
        mDevicePrefer = devicePrefer;
        mOnline = online;
        mDeviceTime = deviceTime;
    }

    /**
     * 用来区分item类型
     * @return
     */
    @Override
    public int getType() {
        return TYPE_HEADER;
    }

    public byte getMajorVersion() {
        return mMajorVersion;
    }

    public void setMajorVersion(byte majorVersion) {
        mMajorVersion = majorVersion;
    }

    public byte getMinorVersion() {
        return mMinorVersion;
    }

    public void setMinorVersion(byte minorVersion) {
        mMinorVersion = minorVersion;
    }

    public DevicePrefer getDevicePrefer() {
        return mDevicePrefer;
    }

    public void setDevicePrefer(DevicePrefer devicePrefer) {
        mDevicePrefer = devicePrefer;
    }

    public boolean isOnline() {
        return mOnline;
    }

    public void setOnline(boolean online) {
        mOnline = online;
    }

    public DeviceTime getDeviceTime() {
        return mDeviceTime;
    }

    public void setDeviceTime(DeviceTime deviceTime) {
        mDeviceTime = deviceTime;
    }

    class DeviceTime {
        private byte year;
        private byte month;
        private byte day;
        private byte weekday;
        private byte hour;
        private byte minute;
        private byte second;

        public byte getYear() {
            return year;
        }

        public void setYear(byte year) {
            this.year = year;
        }

        public byte getMonth() {
            return month;
        }

        public void setMonth(byte month) {
            this.month = month;
        }

        public byte getDay() {
            return day;
        }

        public void setDay(byte day) {
            this.day = day;
        }

        public byte getWeekday() {
            return weekday;
        }

        public void setWeekday(byte weekday) {
            this.weekday = weekday;
        }

        public byte getHour() {
            return hour;
        }

        public void setHour(byte hour) {
            this.hour = hour;
        }

        public byte getMinute() {
            return minute;
        }

        public void setMinute(byte minute) {
            this.minute = minute;
        }

        public byte getSecond() {
            return second;
        }

        public void setSecond(byte second) {
            this.second = second;
        }
    }
}
