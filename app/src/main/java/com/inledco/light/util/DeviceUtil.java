package com.inledco.light.util;

import android.content.Context;

import com.inledco.light.R;
import com.inledco.light.bean.Channel;
import com.inledco.light.bean.LightDevice;
import com.inledco.light.bean.LightModel;
import com.inledco.light.bean.RampTime;
import com.inledco.light.constant.CustomColor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DeviceUtil
{
    // 控制器ID
    public static final short LIGHT_ID_ONE_COLOR = 0x0001;
    public static final short LIGHT_ID_TWO_COLOR = 0x0002;
    public static final short LIGHT_ID_THREE_COLOR = 0x0003;
    public static final short LIGHT_ID_FOUR_COLOR = 0x0004;
    public static final short LIGHT_ID_FIVE_COLOR = 0x0005;
    public static final short LIGHT_ID_SIX_COLOR = 0x0006;

    // 控制器类型名称
    public static final String LIGHT_TYPE_ONE_COLOR = "ONE COLOR";
    public static final String LIGHT_TYPE_TWO_COLOR = "TWO COLOR";
    public static final String LIGHT_TYPE_THREE_COLOR = "THREE COLOR";
    public static final String LIGHT_TYPE_FOUR_COLOR = "FOUR COLOR";
    public static final String LIGHT_TYPE_FIVE_COLOR = "FIVE COLOR";
    public static final String LIGHT_TYPE_SIX_COLOR = "SIX COLOR";

    // 控制器支持灯具ID
    public static final short SUPPORT_LIGHT_ID_ONE = 0x0001;
    public static final short SUPPORT_LIGHT_ID_TWO = 0x0002;
    public static final short SUPPORT_LIGHT_ID_THREE = 0x0003;
    public static final short SUPPORT_LIGHT_ID_FOUR = 0x0004;
    public static final short SUPPORT_LIGHT_ID_FIVE = 0x0005;
    public static final short SUPPORT_LIGHT_ID_SIX = 0x0006;

    // 控制器
    private static Map<Short, String> mDeviceMap;
    private static Map<Short, Integer> mIconMap;

    static
    {
        mDeviceMap = new HashMap<>();
        mIconMap = new HashMap<>();

        // 一到六路类型定义
        mDeviceMap.put(LIGHT_ID_ONE_COLOR, LIGHT_TYPE_ONE_COLOR);
        mDeviceMap.put(LIGHT_ID_TWO_COLOR, LIGHT_TYPE_TWO_COLOR);
        mDeviceMap.put(LIGHT_ID_THREE_COLOR, LIGHT_TYPE_THREE_COLOR);
        mDeviceMap.put(LIGHT_ID_FOUR_COLOR, LIGHT_TYPE_FOUR_COLOR);
        mDeviceMap.put(LIGHT_ID_FIVE_COLOR, LIGHT_TYPE_FIVE_COLOR);
        mDeviceMap.put(LIGHT_ID_SIX_COLOR, LIGHT_TYPE_SIX_COLOR);

        // 一到六路图标
        mIconMap.put(LIGHT_ID_ONE_COLOR, R.mipmap.led);
        mIconMap.put(LIGHT_ID_TWO_COLOR, R.mipmap.led);
        mIconMap.put(LIGHT_ID_THREE_COLOR, R.mipmap.led);
        mIconMap.put(LIGHT_ID_FOUR_COLOR, R.mipmap.led);
        mIconMap.put(LIGHT_ID_FIVE_COLOR, R.mipmap.led);
        mIconMap.put(LIGHT_ID_SIX_COLOR, R.mipmap.led);
    }


    /** 是否是合法的id
     * @param id
     * @return
     */
    public static boolean isCorrectDevType(short id)
    {
        return mDeviceMap.containsKey(id);
    }

    /**
     * 由设备id获取设备类型
     *
     * @param devId
     * @return
     */
    public static String getDeviceType (short devId)
    {
        if (mDeviceMap.containsKey(devId))
        {
            return mDeviceMap.get(devId);
        }
        return "UnKnown device";
    }


    /**
     * @param devId
     * @return
     */
    public static int getDeviceIcon (short devId)
    {
        int resId = R.drawable.ic_bluetooth_white_48dp;
        if (mIconMap == null)
        {
            return resId;
        }
        if (mIconMap.containsKey(devId))
        {
            return mIconMap.get(devId);
        }
        return resId;
    }

    /** 获取控制器支持的灯具
     * @param devId
     * @return
     */
    public static LightDevice[] getSupportLight(short devId) {
        LightDevice[] listItem = null;

        switch (devId) {
            case LIGHT_ID_ONE_COLOR:
                listItem = new LightDevice[] {};
                break;
            case LIGHT_ID_TWO_COLOR:
                listItem = new LightDevice[] {};
                break;
            case LIGHT_ID_THREE_COLOR:
                listItem = new LightDevice[] {};
                break;
            case LIGHT_ID_FOUR_COLOR:
                listItem = new LightDevice[] {};
                break;
            case LIGHT_ID_FIVE_COLOR:
                listItem = new LightDevice[] { new LightDevice("五路", "图片", "0005","0005") };
                break;
            case LIGHT_ID_SIX_COLOR:
                listItem = new LightDevice[] {};
                break;
        }

        return listItem;
    }

    public static Channel[] getLightChannel(Context context, short devId)
    {
        Channel[] channels = null;
        switch (devId)
        {
            case LIGHT_ID_ONE_COLOR:
                channels = new Channel[]{ new Channel(context.getString(R.string.chn_name_red), CustomColor.COLOR_RED_A700, (short) 0)};
                break;
            case LIGHT_ID_TWO_COLOR:
                channels = new Channel[]{ new Channel(context.getString(R.string.chn_name_red), CustomColor.COLOR_RED_A700, (short) 0),
                        new Channel(context.getString(R.string.chn_name_green), CustomColor.COLOR_GREEN_A700, (short) 0) };
                break;
            case LIGHT_ID_THREE_COLOR:
                channels = new Channel[]{ new Channel(context.getString(R.string.chn_name_red), CustomColor.COLOR_RED_A700, (short) 0),
                        new Channel(context.getString(R.string.chn_name_green), CustomColor.COLOR_GREEN_A700, (short) 0),
                        new Channel(context.getString(R.string.chn_name_blue), CustomColor.COLOR_BLUE_A700, (short) 0) };
                break;
            case LIGHT_ID_FOUR_COLOR:
                channels = new Channel[]{ new Channel(context.getString(R.string.chn_name_red), CustomColor.COLOR_RED_A700, (short) 0),
                        new Channel(context.getString(R.string.chn_name_green), CustomColor.COLOR_GREEN_A700, (short) 0),
                        new Channel(context.getString(R.string.chn_name_blue), CustomColor.COLOR_BLUE_A700, (short) 0),
                        new Channel(context.getString(R.string.chn_name_white), CustomColor.COLOR_WHITE_COLD, (short) 0) };
                break;
            case LIGHT_ID_FIVE_COLOR:
                channels = new Channel[]{ new Channel(context.getString(R.string.chn_name_red), CustomColor.COLOR_RED_A700, (short) 0),
                        new Channel(context.getString(R.string.chn_name_green), CustomColor.COLOR_GREEN_A700, (short) 0),
                        new Channel(context.getString(R.string.chn_name_blue), CustomColor.COLOR_BLUE_A700, (short) 0),
                        new Channel(context.getString(R.string.chn_name_white), CustomColor.COLOR_WHITE_COLD, (short) 0),
                        new Channel(context.getString(R.string.chn_name_purple), CustomColor.COLOR_PURPLE_A700, (short) 0)};
                break;
            case LIGHT_ID_SIX_COLOR:
                channels = new Channel[]{ new Channel(context.getString(R.string.chn_name_red), CustomColor.COLOR_RED_A700, (short) 0),
                        new Channel(context.getString(R.string.chn_name_green), CustomColor.COLOR_GREEN_A700, (short) 0),
                        new Channel(context.getString(R.string.chn_name_blue), CustomColor.COLOR_BLUE_A700, (short) 0),
                        new Channel(context.getString(R.string.chn_name_coldwhite), CustomColor.COLOR_WHITE_COLD, (short) 0),
                        new Channel(context.getString(R.string.chn_name_purple), CustomColor.COLOR_PURPLE_A700, (short) 0),
                        new Channel(context.getString(R.string.chn_name_pink), CustomColor.COLOR_PINK_A700, (short) 0)};
                break;
        }
        return channels;
    }

    public static int[] getThumb (short devid)
    {
        int[] thumbs = null;
        switch (devid)
        {
            case LIGHT_ID_ONE_COLOR:
                thumbs = new int[]{
                        R.drawable.shape_circle_thumb_red };
                break;
            case LIGHT_ID_TWO_COLOR:
                thumbs = new int[]{
                        R.drawable.shape_circle_thumb_red,
                        R.drawable.shape_circle_thumb_green };
                break;
            case LIGHT_ID_THREE_COLOR:
                thumbs = new int[]{
                        R.drawable.shape_circle_thumb_red,
                        R.drawable.shape_circle_thumb_green,
                        R.drawable.shape_circle_thumb_blue };
                break;
            case LIGHT_ID_FOUR_COLOR:
                thumbs = new int[]{
                        R.drawable.shape_circle_thumb_red,
                        R.drawable.shape_circle_thumb_green,
                        R.drawable.shape_circle_thumb_blue,
                        R.drawable.shape_circle_thumb_white };
                break;
            case LIGHT_ID_FIVE_COLOR:
                thumbs = new int[]{
                        R.drawable.shape_circle_thumb_red,
                        R.drawable.shape_circle_thumb_green,
                        R.drawable.shape_circle_thumb_blue,
                        R.drawable.shape_circle_thumb_white,
                        R.drawable.shape_circle_thumb_purple };
                break;
            case LIGHT_ID_SIX_COLOR:
                thumbs = new int[]{
                        R.drawable.shape_circle_thumb_red,
                        R.drawable.shape_circle_thumb_green,
                        R.drawable.shape_circle_thumb_blue,
                        R.drawable.shape_circle_thumb_white,
                        R.drawable.shape_circle_thumb_purple,
                        R.drawable.shape_circle_thumb_pink};
                break;
        }
        return thumbs;
    }

    public static int[] getSeekbar (short devid)
    {
        int[] seekBars = null;
        switch (devid)
        {
            case LIGHT_ID_ONE_COLOR:
                seekBars = new int[]{
                        R.drawable.custom_seekebar_red };
                break;
            case LIGHT_ID_TWO_COLOR:
                seekBars = new int[]{ R.drawable.custom_seekebar_red,
                        R.drawable.custom_seekebar_green };
                break;
            case LIGHT_ID_THREE_COLOR:
                seekBars = new int[]{ R.drawable.custom_seekebar_red,
                        R.drawable.custom_seekebar_green,
                        R.drawable.custom_seekebar_blue };
                break;
            case LIGHT_ID_FOUR_COLOR:
                seekBars = new int[]{ R.drawable.custom_seekebar_red,
                        R.drawable.custom_seekebar_green,
                        R.drawable.custom_seekebar_blue,
                        R.drawable.custom_seekebar_coldwhite };
                break;
            case LIGHT_ID_FIVE_COLOR:
                seekBars = new int[]{ R.drawable.custom_seekbar_pink,
                        R.drawable.custom_seekbar_cyan,
                        R.drawable.custom_seekebar_blue,
                        R.drawable.custom_seekbar_purple,
                        R.drawable.custom_seekebar_coldwhite };
                break;
            case LIGHT_ID_SIX_COLOR:
                seekBars = new int[]{ R.drawable.custom_seekbar_pink,
                        R.drawable.custom_seekbar_cyan,
                        R.drawable.custom_seekebar_blue,
                        R.drawable.custom_seekbar_purple,
                        R.drawable.custom_seekebar_coldwhite,
                        R.drawable.custom_seekebar_coldwhite };
                break;
        }
        return seekBars;
    }

    public static Map<String, LightModel> getPresetProfiles (short devid, boolean hasAutoDynamic)
    {
        Map<String, LightModel> profiles = new LinkedHashMap<>();
        switch (devid)
        {

        }
        if (hasAutoDynamic)
        {
            for (String key : profiles.keySet())
            {
                // profiles.get(key).setHasDynamic(true);
                profiles.get(key).setSun(false);
                profiles.get(key).setMon(false);
                profiles.get(key).setTue(false);
                profiles.get(key).setWed(false);
                profiles.get(key).setThu(false);
                profiles.get(key).setFri(false);
                profiles.get(key).setSat(false);
//                profiles.get(key).setDynamicEnable(false);
//                profiles.get(key).setDynamicPeriod(new RampTime((byte) 0, (byte) 0, (byte) 0, (byte) 0));
//                profiles.get(key).setDynamicMode((byte) 0);
            }
        }
        return profiles;
    }

    /**
     * 根据设备ID获取设备通道数量
     * @param devId 设备标识
     * @return 设备通道数量
     */
    public static int getChannelCount (short devId)
    {
        int channelNum = 0;
        switch (devId)
        {
            case LIGHT_ID_ONE_COLOR:
                channelNum = 1;
                break;
            case LIGHT_ID_TWO_COLOR:
                channelNum = 2;
                break;
            case LIGHT_ID_THREE_COLOR:
                channelNum = 3;
                break;
            case LIGHT_ID_FOUR_COLOR:
                channelNum = 4;
                break;
            case  LIGHT_ID_FIVE_COLOR:
                channelNum = 5;
                break;
            case LIGHT_ID_SIX_COLOR:
                channelNum = 6;
                break;
        }

        return channelNum;
    }
}