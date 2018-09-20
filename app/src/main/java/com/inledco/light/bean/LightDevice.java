package com.inledco.light.bean;


/**
 * 控制器支持灯具模型
 */
public class LightDevice extends ListItem {
    // 控制器信息
    private DevicePrefer mDevicePrefer;
    private short mLightId;
    private String mLightName;
    private String lightCode;
    private String lightType;

    public LightDevice(DevicePrefer devicePrefer, short lightId, String lightName, String lightCode, String lightType) {
        mDevicePrefer = devicePrefer;
        this.mLightId = lightId;
        this.mLightName = lightName;
        this.lightCode = lightCode;
        this.lightType = lightType;
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

    public DevicePrefer getDevicePrefer() {
        return mDevicePrefer;
    }

    public void setDevicePrefer(DevicePrefer devicePrefer) {
        mDevicePrefer = devicePrefer;
    }
}
