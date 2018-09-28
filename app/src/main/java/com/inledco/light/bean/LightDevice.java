package com.inledco.light.bean;


import java.io.Serializable;

/**
 * 控制器支持灯具模型
 */
public class LightDevice extends ListItem implements Serializable {
    private DevicePrefer mDevicePrefer;
    private short mLightId;
    private String mLightName;
    private String lightCode;
    private String lightType;
    private short mLightChannelNum;

    public LightDevice(DevicePrefer devicePrefer, short lightId, String lightName, String lightCode, String lightType, short lightChannelNum) {
        this.mDevicePrefer = devicePrefer;
        this.mLightId = lightId;
        this.mLightName = lightName;
        this.lightCode = lightCode;
        this.lightType = lightType;
        this.mLightChannelNum = lightChannelNum;
    }

    @Override
    public int getType() {
        return TYPE_LIGHT;
    }

    public String getLightCode() {
        return lightCode;
    }

    public void setLightCode(String lightCode) {
        this.lightCode = lightCode;
    }

    public String getLightType() {
        return lightType;
    }

    public void setLightType(String lightType) {
        this.lightType = lightType;
    }

    public short getLightId() {
        return mLightId;
    }

    public void setLightId(short lightId) {
        mLightId = lightId;
    }

    public String getLightName() {
        return mLightName;
    }

    public void setLightName(String lightName) {
        mLightName = lightName;
    }

    public short getLightChannelNum() {
        return mLightChannelNum;
    }

    public void setLightChannelNum(short lightChannelNum) {
        mLightChannelNum = lightChannelNum;
    }

    public DevicePrefer getDevicePrefer() {
        return mDevicePrefer;
    }

    public void setDevicePrefer(DevicePrefer devicePrefer) {
        mDevicePrefer = devicePrefer;
    }
}
