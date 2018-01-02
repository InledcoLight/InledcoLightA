package com.inledco.light.bean;

import java.io.Serializable;

/**
 * Created by liruya on 2016/10/28.
 */

public class Light extends BaseDevice implements Serializable
{
    private static final long serialVersionUID = -4162709866411397526L;
    private boolean mAuto;
    private LightManual mLightManual;
    private LightAuto mLightAuto;

    // 新的模型
    private LightModel mLightModel;

    // 新模型构造方法
    public Light(DevicePrefer devicePrefer, boolean online, boolean auto, LightManual lightManual, LightModel lightModel) {
        super(devicePrefer, online);

        mAuto = auto;
        mLightManual = lightManual;
        mLightModel = lightModel;
    }

    public Light ( DevicePrefer devicePrefer, boolean online, boolean auto, LightManual lightManual, LightAuto lightAuto )
    {
        super( devicePrefer, online );
        mAuto = auto;
        mLightManual = lightManual;
        mLightAuto = lightAuto;
    }

    public Light ( byte majorVersion, byte minorVersion, DevicePrefer devicePrefer, boolean online, DeviceTime deviceTime, boolean auto,
                   LightManual lightManual, LightAuto lightAuto )
    {
        super( majorVersion, minorVersion, devicePrefer, online, deviceTime );
        mAuto = auto;
        mLightManual = lightManual;
        mLightAuto = lightAuto;
    }

    public static long getSerialVersionUID ()
    {
        return serialVersionUID;
    }

    public boolean isAuto ()
    {
        return mAuto;
    }

    public void setAuto ( boolean auto )
    {
        mAuto = auto;
    }

    public LightManual getLightManual ()
    {
        return mLightManual;
    }

    public void setLightManual ( LightManual lightManual )
    {
        mLightManual = lightManual;
    }

    public LightAuto getLightAuto ()
    {
        return mLightAuto;
    }

    public void setLightAuto ( LightAuto lightAuto )
    {
        mLightAuto = lightAuto;
    }

    public LightModel getmLightModel() {
        return mLightModel;
    }

    public void setmLightModel(LightModel mLightModel) {
        this.mLightModel = mLightModel;
    }
}
