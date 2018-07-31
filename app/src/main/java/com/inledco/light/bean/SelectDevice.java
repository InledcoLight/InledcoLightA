package com.inledco.light.bean;

/**
 * 扫描蓝牙设备界面 可选择的设备
 * Created by liruya on 2016/10/25.
 */

public class SelectDevice
{
    private String mBrand; // 品牌
    private String mGroup; // 分组
    private boolean mSelectable;
    private boolean mSelected;
    private DevicePrefer mPrefer;

    public SelectDevice ( boolean selectable, boolean selected, DevicePrefer prefer )
    {
        mSelectable = selectable;
        mSelected = selected;
        mPrefer = prefer;
    }

    public boolean isSelected ()
    {
        return mSelected;
    }

    public void setSelected ( boolean selected )
    {
        mSelected = selected;
    }

    public boolean isSelectable ()
    {
        return mSelectable;
    }

    public void setSelectable ( boolean selectable )
    {
        mSelectable = selectable;
    }

    public DevicePrefer getPrefer ()
    {
        return mPrefer;
    }

    public void setPrefer ( DevicePrefer prefer )
    {
        mPrefer = prefer;
    }

    public String getmBrand() {
        return mBrand;
    }

    public void setmBrand(String mBrand) {
        this.mBrand = mBrand;
    }

    public String getmGroup() {
        return mGroup;
    }

    public void setmGroup(String mGroup) {
        this.mGroup = mGroup;
    }
}
