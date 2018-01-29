package com.inledco.light.util;

import android.content.Context;

import com.inledco.light.R;
import com.inledco.light.bean.Channel;
import com.inledco.light.bean.LightAuto;
import com.inledco.light.bean.RampTime;
import com.inledco.light.constant.CustomColor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/21.
 */

public class DeviceUtil
{
    //灯具类别--0x**** 前两位表示设备类型,如灯,插座,第三位表示设备子类型,如marine,fresh,第四位表示该设备的不同规格 如长度500,800,1100
//    public static final byte LIGHT_MAINID = 0x01;
    public static final short LIGHT_ID_RGBW = 0x0105;
    public static final short LIGHT_ID_STRIP_III = 0x0111;
    public static final short LIGHT_ID_EGG = 0x0115;
//    public static final short LIGHT_ID_MARINE = 0x012;
    public static final short LIGHT_ID_MARINE_500 = 0x0121;
    public static final short LIGHT_ID_MARINE_800 = 0x0122;
    public static final short LIGHT_ID_MARINE_1100 = 0x0123;
//    public static final short LIGHT_ID_FRESH = 0x013;
    public static final short LIGHT_ID_FRESH_500 = 0x0131;
    public static final short LIGHT_ID_FRESH_800 = 0x0132;
    public static final short LIGHT_ID_FRESH_1100 = 0x0133;
//    public static final short LIGHT_ID_AQUASKY = 0x014;
    public static final short LIGHT_ID_AQUASKY_600 = 0x0141;
    public static final short LIGHT_ID_AQUASKY_900 = 0x0142;
    public static final short LIGHT_ID_AQUASKY_1200 = 0x0143;
    public static final short LIGHT_ID_AQUASKY_380 = 0x0144;
    public static final short LIGHT_ID_AQUASKY_530 = 0x0145;
    public static final short LIGHT_ID_AQUASKY_835 = 0x0146;
    public static final short LIGHT_ID_AQUASKY_990 = 0x0147;
    public static final short LIGHT_ID_AQUASKY_750 = 0x0148;
    public static final short LIGHT_ID_AQUASKY_1150 = 0x0149;
    public static final short LIGHT_ID_NANO_MARINE = 0x0151;
    public static final short LIGHT_ID_NANO_FRESH = 0x0152;

    // 一到六路ID定义
    public static final short LIGHT_ID_ONE_COLOR = 0x0001;
    public static final short LIGHT_ID_TWO_COLOR = 0x0002;
    public static final short LIGHT_ID_THREE_COLOR = 0x0003;
    public static final short LIGHT_ID_FOUR_COLOR = 0x0004;
    public static final short LIGHT_ID_FIVE_COLOR = 0x0005;
    public static final short LIGHT_ID_SIX_COLOR = 0x0006;


    public static final String LIGHT_TYPE_RGBW = "RGBW Strip II";
    public static final String LIGHT_TYPE_STRIP_III = "Hagen Strip III";
    public static final String LIGHT_TYPE_EGG = "Egg Light";
    public static final String LIGHT_TYPE_MARINE = "Marine & Reef";
    public static final String LIGHT_TYPE_FRESH = "Fresh & Plant";
    public static final String LIGHT_TYPE_AQUASKY = "Aquasky";
    public static final String LIGHT_TYPE_NANO_MARINE = "Wing Nano Marine";
    public static final String LIGHT_TYPE_NANO_FRESH = "Wing Nano Fresh";

    // 一到六路类型定义
    public static final String LIGHT_TYPE_ONE_COLOR = "ONE COLOR";
    public static final String LIGHT_TYPE_TWO_COLOR = "TWO COLOR";
    public static final String LIGHT_TYPE_THREE_COLOR = "THREE COLOR";
    public static final String LIGHT_TYPE_FOUR_COLOR = "FOUR COLOR";
    public static final String LIGHT_TYPE_FIVE_COLOR = "FIVE COLOR";
    public static final String LIGHT_TYPE_SIX_COLOR = "SIX COLOR";

    private static Map< Short, String > mDeviceMap;
    private static Map< Short, Integer > mIconMap;

    static
    {
        mDeviceMap = new HashMap<>();
        mIconMap = new HashMap<>();

        mDeviceMap.put( LIGHT_ID_RGBW, LIGHT_TYPE_RGBW );
        mDeviceMap.put( LIGHT_ID_STRIP_III, LIGHT_TYPE_STRIP_III );
        mDeviceMap.put( LIGHT_ID_EGG, LIGHT_TYPE_EGG );
        mDeviceMap.put( LIGHT_ID_MARINE_500, LIGHT_TYPE_MARINE + " 500mm" );
        mDeviceMap.put( LIGHT_ID_MARINE_800, LIGHT_TYPE_MARINE + " 800mm" );
        mDeviceMap.put( LIGHT_ID_MARINE_1100, LIGHT_TYPE_MARINE + " 1100mm" );
        mDeviceMap.put( LIGHT_ID_FRESH_500, LIGHT_TYPE_FRESH + " 500mm" );
        mDeviceMap.put( LIGHT_ID_FRESH_800, LIGHT_TYPE_FRESH + " 800mm" );
        mDeviceMap.put( LIGHT_ID_FRESH_1100, LIGHT_TYPE_FRESH + " 1100mm" );
        mDeviceMap.put( LIGHT_ID_AQUASKY_600, LIGHT_TYPE_AQUASKY + " 600mm" );
        mDeviceMap.put( LIGHT_ID_AQUASKY_900, LIGHT_TYPE_AQUASKY + " 900mm" );
        mDeviceMap.put( LIGHT_ID_AQUASKY_1200, LIGHT_TYPE_AQUASKY + " 1200mm" );
        mDeviceMap.put( LIGHT_ID_AQUASKY_380, LIGHT_TYPE_AQUASKY + " 380mm" );
        mDeviceMap.put( LIGHT_ID_AQUASKY_530, LIGHT_TYPE_AQUASKY + " 530mm" );
        mDeviceMap.put( LIGHT_ID_AQUASKY_835, LIGHT_TYPE_AQUASKY + " 835mm" );
        mDeviceMap.put( LIGHT_ID_AQUASKY_990, LIGHT_TYPE_AQUASKY + " 990mm" );
        mDeviceMap.put( LIGHT_ID_AQUASKY_750, LIGHT_TYPE_AQUASKY + " 750mm" );
        mDeviceMap.put( LIGHT_ID_AQUASKY_1150, LIGHT_TYPE_AQUASKY + " 1150mm" );
        mDeviceMap.put( LIGHT_ID_NANO_MARINE, LIGHT_TYPE_NANO_MARINE );
        mDeviceMap.put( LIGHT_ID_NANO_FRESH, LIGHT_TYPE_NANO_FRESH );

        // 一到六路类型定义
        mDeviceMap.put(LIGHT_ID_FIVE_COLOR, LIGHT_TYPE_ONE_COLOR);
        mDeviceMap.put(LIGHT_ID_SIX_COLOR, LIGHT_TYPE_TWO_COLOR);
        mDeviceMap.put(LIGHT_ID_FIVE_COLOR, LIGHT_TYPE_THREE_COLOR);
        mDeviceMap.put(LIGHT_ID_SIX_COLOR, LIGHT_TYPE_FOUR_COLOR);
        mDeviceMap.put(LIGHT_ID_FIVE_COLOR, LIGHT_TYPE_FIVE_COLOR);
        mDeviceMap.put(LIGHT_ID_SIX_COLOR, LIGHT_TYPE_SIX_COLOR);


        mIconMap.put( LIGHT_ID_RGBW, R.mipmap.led );
        mIconMap.put( LIGHT_ID_STRIP_III, R.mipmap.led );
        mIconMap.put( LIGHT_ID_EGG, R.mipmap.led );
        mIconMap.put( LIGHT_ID_MARINE_500, R.mipmap.led );
        mIconMap.put( LIGHT_ID_MARINE_800, R.mipmap.led );
        mIconMap.put( LIGHT_ID_MARINE_1100, R.mipmap.led );
        mIconMap.put( LIGHT_ID_FRESH_500, R.mipmap.led );
        mIconMap.put( LIGHT_ID_FRESH_800, R.mipmap.led );
        mIconMap.put( LIGHT_ID_FRESH_1100, R.mipmap.led );
        mIconMap.put( LIGHT_ID_AQUASKY_600, R.mipmap.led );
        mIconMap.put( LIGHT_ID_AQUASKY_900, R.mipmap.led );
        mIconMap.put( LIGHT_ID_AQUASKY_1200, R.mipmap.led );
        mIconMap.put( LIGHT_ID_AQUASKY_380, R.mipmap.led );
        mIconMap.put( LIGHT_ID_AQUASKY_530, R.mipmap.led );
        mIconMap.put( LIGHT_ID_AQUASKY_835, R.mipmap.led );
        mIconMap.put( LIGHT_ID_AQUASKY_990, R.mipmap.led );
        mIconMap.put( LIGHT_ID_AQUASKY_750, R.mipmap.led );
        mIconMap.put( LIGHT_ID_AQUASKY_1150, R.mipmap.led );
        mIconMap.put( LIGHT_ID_NANO_MARINE, R.mipmap.led );
        mIconMap.put( LIGHT_ID_NANO_FRESH, R.mipmap.led );

        // 一到六路图标
        mIconMap.put(LIGHT_ID_ONE_COLOR, R.mipmap.led);
        mIconMap.put(LIGHT_ID_TWO_COLOR, R.mipmap.led);
        mIconMap.put(LIGHT_ID_THREE_COLOR, R.mipmap.led);
        mIconMap.put(LIGHT_ID_FOUR_COLOR, R.mipmap.led);
        mIconMap.put(LIGHT_ID_FIVE_COLOR, R.mipmap.led);
        mIconMap.put(LIGHT_ID_SIX_COLOR, R.mipmap.led);
    }

    public static boolean isCorrectDevType(short id)
    {
        return mDeviceMap.containsKey( id );
    }

    /**
     * 由设备id获取设备类型
     *
     * @param devId
     * @return
     */
    public static String getDeviceType ( short devId )
    {
        if ( mDeviceMap.containsKey( devId ) )
        {
            return mDeviceMap.get( devId );
        }
        return "Unkown device";
    }


    /**
     * @param devId
     * @return
     */
    public static int getDeviceIcon ( short devId )
    {
        int resId = R.drawable.ic_bluetooth_white_48dp;
        if ( mIconMap == null )
        {
            return resId;
        }
        if ( mIconMap.containsKey( devId ) )
        {
            return mIconMap.get( devId );
        }
        return resId;
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
                channels = new Channel[]{ new Channel( context.getString( R.string.chn_name_red ), CustomColor.COLOR_RED_A700, (short) 0 ),
                        new Channel( context.getString( R.string.chn_name_green ), CustomColor.COLOR_GREEN_A700, (short) 0 ),
                        new Channel( context.getString( R.string.chn_name_blue ), CustomColor.COLOR_BLUE_A700, (short) 0 ),
                        new Channel( context.getString( R.string.chn_name_white ), CustomColor.COLOR_WHITE_COLD, (short) 0 ),
                        new Channel( context.getString( R.string.chn_name_purple ), CustomColor.COLOR_PURPLE_A700, (short) 0 )};
                break;
            case LIGHT_ID_SIX_COLOR:
                channels = new Channel[]{ new Channel( context.getString( R.string.chn_name_red ), CustomColor.COLOR_RED_A700, (short) 0 ),
                        new Channel( context.getString( R.string.chn_name_green ), CustomColor.COLOR_GREEN_A700, (short) 0 ),
                        new Channel( context.getString( R.string.chn_name_blue ), CustomColor.COLOR_BLUE_A700, (short) 0 ),
                        new Channel( context.getString( R.string.chn_name_coldwhite ), CustomColor.COLOR_WHITE_COLD, (short) 0 ),
                        new Channel( context.getString( R.string.chn_name_purple ), CustomColor.COLOR_PURPLE_A700, (short) 0 ),
                        new Channel( context.getString( R.string.chn_name_pink ), CustomColor.COLOR_PINK_A700, (short) 0 )};
                break;
            case LIGHT_ID_RGBW:
            case LIGHT_ID_STRIP_III:
                channels = new Channel[]{ new Channel( context.getString( R.string.chn_name_red ), CustomColor.COLOR_RED_A700, (short) 0 ),
                                          new Channel( context.getString( R.string.chn_name_green ), CustomColor.COLOR_GREEN_A700, (short) 0 ),
                                          new Channel( context.getString( R.string.chn_name_blue ), CustomColor.COLOR_BLUE_A700, (short) 0 ),
                                          new Channel( context.getString( R.string.chn_name_white ), CustomColor.COLOR_WHITE_COLD, (short) 0 ) };
                break;

            case LIGHT_ID_EGG:
                channels = new Channel[]{ new Channel( context.getString( R.string.chn_name_red ), CustomColor.COLOR_RED_A700, (short) 0 ),
                                          new Channel( context.getString( R.string.chn_name_green ), CustomColor.COLOR_GREEN_A700, (short) 0 ),
                                          new Channel( context.getString( R.string.chn_name_blue ), CustomColor.COLOR_BLUE_A700, (short) 0 ),
                                          new Channel( context.getString( R.string.chn_name_coldwhite ), CustomColor.COLOR_WHITE_COLD, (short) 0 ),
                                          new Channel( context.getString( R.string.chn_name_Warmwhite ), CustomColor.COLOR_WHITE_WARM, (short) 0 )};
                break;

            case LIGHT_ID_MARINE_500:
            case LIGHT_ID_MARINE_800:
            case LIGHT_ID_MARINE_1100:
            case LIGHT_ID_NANO_MARINE:
                channels = new Channel[]{ new Channel( context.getString( R.string.chn_name_pink ), CustomColor.COLOR_PINK_A700, (short) 0 ),
                                          new Channel( context.getString( R.string.chn_name_cyan ), CustomColor.COLOR_CYAN_A700, (short) 0 ),
                                          new Channel( context.getString( R.string.chn_name_blue ), CustomColor.COLOR_BLUE_A700, (short) 0 ),
                                          new Channel( context.getString( R.string.chn_name_purple ), CustomColor.COLOR_PURPLE_A700, (short) 0 ),
                                          new Channel( context.getString( R.string.chn_name_coldwhite ), CustomColor.COLOR_WHITE_COLD, (short) 0 ), };
                break;

            case LIGHT_ID_FRESH_500:
            case LIGHT_ID_FRESH_800:
            case LIGHT_ID_FRESH_1100:
            case LIGHT_ID_NANO_FRESH:
                channels = new Channel[]{ new Channel( context.getString( R.string.chn_name_pink ), CustomColor.COLOR_PINK_A700, (short) 0 ),
                                          new Channel( context.getString( R.string.chn_name_blue ), CustomColor.COLOR_BLUE_A700, (short) 0 ),
                                          new Channel( context.getString( R.string.chn_name_coldwhite ), CustomColor.COLOR_WHITE_COLD, (short) 0 ),
                                          new Channel( context.getString( R.string.chn_name_purewhite ), CustomColor.COLOR_WHITE_PURE, (short) 0 ),
                                          new Channel( context.getString( R.string.chn_name_Warmwhite ), CustomColor.COLOR_WHITE_WARM, (short) 0 ) };
                break;

            case LIGHT_ID_AQUASKY_600:
            case LIGHT_ID_AQUASKY_900:
            case LIGHT_ID_AQUASKY_1200:
            case LIGHT_ID_AQUASKY_380:
            case LIGHT_ID_AQUASKY_530:
            case LIGHT_ID_AQUASKY_835:
            case LIGHT_ID_AQUASKY_990:
            case LIGHT_ID_AQUASKY_750:
            case LIGHT_ID_AQUASKY_1150:
                channels = new Channel[]{ new Channel( context.getString( R.string.chn_name_red ), CustomColor.COLOR_RED_A700, (short) 0 ),
                                          new Channel( context.getString( R.string.chn_name_green ), CustomColor.COLOR_GREEN_A700, (short) 0 ),
                                          new Channel( context.getString( R.string.chn_name_blue ), CustomColor.COLOR_BLUE_A700, (short) 0 ),
                                          new Channel( context.getString( R.string.chn_name_white ), CustomColor.COLOR_WHITE_PURE, (short) 0 ) };
                break;
        }
        return channels;
    }

    public static int[] getThumb ( short devid )
    {
        int[] thumbs = null;
        switch ( devid )
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
            case LIGHT_ID_RGBW:
            case LIGHT_ID_STRIP_III:
                thumbs = new int[]{
                        R.drawable.shape_circle_thumb_red,
                        R.drawable.shape_circle_thumb_green,
                        R.drawable.shape_circle_thumb_blue,
                        R.drawable.shape_circle_thumb_white };
                break;
            case LIGHT_ID_EGG:
                thumbs = new int[]{ R.drawable.shape_thumb_red, R.drawable.shape_thumb_green, R.drawable.shape_thumb_blue, R.drawable.shape_thumb_coldwhite,
                                    R.drawable.shape_thumb_warmwhite };
                break;
            case LIGHT_ID_MARINE_500:
            case LIGHT_ID_MARINE_800:
            case LIGHT_ID_MARINE_1100:
            case LIGHT_ID_NANO_MARINE:
                thumbs = new int[]{ R.drawable.shape_thumb_pink,
                                    R.drawable.shape_thumb_cyan,
                                    R.drawable.shape_thumb_blue,
                                    R.drawable.shape_thumb_purple,
                                    R.drawable.shape_thumb_coldwhite };
                break;
            case LIGHT_ID_FRESH_500:
            case LIGHT_ID_FRESH_800:
            case LIGHT_ID_FRESH_1100:
            case LIGHT_ID_NANO_FRESH:
                thumbs = new int[]{ R.drawable.shape_thumb_pink,
                                    R.drawable.shape_thumb_blue,
                                    R.drawable.shape_thumb_coldwhite,
                                    R.drawable.shape_thumb_purewhite,
                                    R.drawable.shape_thumb_warmwhite, };
                break;
            case LIGHT_ID_AQUASKY_600:
            case LIGHT_ID_AQUASKY_900:
            case LIGHT_ID_AQUASKY_1200:
            case LIGHT_ID_AQUASKY_380:
            case LIGHT_ID_AQUASKY_530:
            case LIGHT_ID_AQUASKY_835:
            case LIGHT_ID_AQUASKY_990:
            case LIGHT_ID_AQUASKY_750:
            case LIGHT_ID_AQUASKY_1150:
                thumbs = new int[]{ R.drawable.shape_thumb_red, R.drawable.shape_thumb_green, R.drawable.shape_thumb_blue, R.drawable.shape_thumb_purewhite };
                break;
        }
        return thumbs;
    }

    public static int[] getSeekbar ( short devid )
    {
        int[] seekBars = null;
        switch ( devid )
        {
            case LIGHT_ID_ONE_COLOR:
                seekBars = new int[]{
                        R.drawable.custom_seekebar_red };
                break;
            case LIGHT_ID_TWO_COLOR:
                seekBars = new int[]{ R.drawable.custom_seekebar_red,
                        R.drawable.custom_seekebar_green};
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
                        R.drawable.custom_seekebar_coldwhite  };
                break;
            case LIGHT_ID_RGBW:
            case LIGHT_ID_STRIP_III:
                seekBars = new int[]{ R.drawable.custom_seekebar_red,
                                      R.drawable.custom_seekebar_green,
                                      R.drawable.custom_seekebar_blue,
                                      R.drawable.custom_seekebar_coldwhite };
                break;
            case LIGHT_ID_EGG:
                seekBars = new int[]{ R.drawable.custom_seekebar_red,
                                      R.drawable.custom_seekebar_green,
                                      R.drawable.custom_seekebar_blue,
                                      R.drawable.custom_seekebar_coldwhite,
                                      R.drawable.custom_seekebar_warmwhite};
                break;
            case LIGHT_ID_MARINE_500:
            case LIGHT_ID_MARINE_800:
            case LIGHT_ID_MARINE_1100:
            case LIGHT_ID_NANO_MARINE:
                seekBars = new int[]{ R.drawable.custom_seekbar_pink,
                                      R.drawable.custom_seekbar_cyan,
                                      R.drawable.custom_seekebar_blue,
                                      R.drawable.custom_seekbar_purple,
                                      R.drawable.custom_seekebar_coldwhite };
                break;
            case LIGHT_ID_FRESH_500:
            case LIGHT_ID_FRESH_800:
            case LIGHT_ID_FRESH_1100:
            case LIGHT_ID_NANO_FRESH:
                seekBars = new int[]{ R.drawable.custom_seekbar_pink,
                                      R.drawable.custom_seekebar_blue,
                                      R.drawable.custom_seekebar_coldwhite,
                                      R.drawable.custom_seekebar_purewhite,
                                      R.drawable.custom_seekebar_warmwhite };
                break;
            case LIGHT_ID_AQUASKY_600:
            case LIGHT_ID_AQUASKY_900:
            case LIGHT_ID_AQUASKY_1200:
            case LIGHT_ID_AQUASKY_380:
            case LIGHT_ID_AQUASKY_530:
            case LIGHT_ID_AQUASKY_835:
            case LIGHT_ID_AQUASKY_990:
            case LIGHT_ID_AQUASKY_750:
            case LIGHT_ID_AQUASKY_1150:
                seekBars = new int[]{ R.drawable.custom_seekebar_red,
                                      R.drawable.custom_seekebar_green,
                                      R.drawable.custom_seekebar_blue,
                                      R.drawable.custom_seekebar_purewhite };
                break;
        }
        return seekBars;
    }

    public static Map<String, LightAuto> getPresetProfiles (short devid, boolean hasAutoDynamic )
    {
        Map<String, LightAuto> profiles = new LinkedHashMap<>(  );
        switch ( devid )
        {
            case LIGHT_ID_MARINE_500:
            case LIGHT_ID_MARINE_800:
            case LIGHT_ID_MARINE_1100:
            case LIGHT_ID_NANO_MARINE:
                profiles.put( "Preset - Deep Sea Glo", new LightAuto( new RampTime( (byte) 0x06, (byte) 0x00, (byte) 0x07,(byte) 0x00 ),
                                                         new byte[]{16, 100, 100, 100, 0},
                                                         new RampTime( (byte) 0x11, (byte) 0x00, (byte) 0x12,(byte) 0x00 ),
                                                         new byte[]{0, 0, 5, 0, 0}) );
                profiles.put( "Preset - Sunny Reef", new LightAuto( new RampTime( (byte) 0x06, (byte) 0x00, (byte) 0x07,(byte) 0x00 ),
                                                         new byte[]{100, 100, 100, 100, 100},
                                                         new RampTime( (byte) 0x11, (byte) 0x00, (byte) 0x12,(byte) 0x00 ),
                                                         new byte[]{0, 0, 5, 0, 0}) );
                profiles.put( "Preset - Color Boost", new LightAuto( new RampTime( (byte) 0x06, (byte) 0x00, (byte) 0x07,(byte) 0x00 ),
                                                         new byte[]{68, 100, 100, 85, 90},
                                                         new RampTime( (byte) 0x11, (byte) 0x00, (byte) 0x12,(byte) 0x00 ),
                                                         new byte[]{0, 0, 5, 0, 0}) );
                break;
            case LIGHT_ID_FRESH_500:
            case LIGHT_ID_FRESH_800:
            case LIGHT_ID_FRESH_1100:
            case LIGHT_ID_NANO_FRESH:
                profiles.put( "Preset - Tropical River", new LightAuto( new RampTime( (byte) 0x06, (byte) 0x00, (byte) 0x07,(byte) 0x00 ),
                                                                      new byte[]{80, 0, 37, 100, 100},
                                                                      new RampTime( (byte) 0x11, (byte) 0x00, (byte) 0x12,(byte) 0x00 ),
                                                                      new byte[]{0, 5, 0, 0, 0}) );
                profiles.put( "Preset - Lake Malawi", new LightAuto( new RampTime( (byte) 0x06, (byte) 0x00, (byte) 0x07,(byte) 0x00 ),
                                                                    new byte[]{50, 0, 37, 100, 0},
                                                                    new RampTime( (byte) 0x11, (byte) 0x00, (byte) 0x12,(byte) 0x00 ),
                                                                    new byte[]{0, 5, 0, 0, 0}) );
                profiles.put( "Preset - Planted", new LightAuto( new RampTime( (byte) 0x06, (byte) 0x00, (byte) 0x07,(byte) 0x00 ),
                                                                     new byte[]{84, 20, 73, 100, 80},
                                                                     new RampTime( (byte) 0x11, (byte) 0x00, (byte) 0x12,(byte) 0x00 ),
                                                                     new byte[]{0, 5, 0, 0, 0}) );
                break;
            case LIGHT_ID_RGBW:
            case LIGHT_ID_STRIP_III:
            case LIGHT_ID_AQUASKY_600:
            case LIGHT_ID_AQUASKY_900:
            case LIGHT_ID_AQUASKY_1200:
            case LIGHT_ID_AQUASKY_380:
            case LIGHT_ID_AQUASKY_530:
            case LIGHT_ID_AQUASKY_835:
            case LIGHT_ID_AQUASKY_990:
            case LIGHT_ID_AQUASKY_750:
            case LIGHT_ID_AQUASKY_1150:
                profiles.put( "Preset - Color Boost", new LightAuto( new RampTime( (byte) 0x06, (byte) 0x00, (byte) 0x07,(byte) 0x00 ),
                                                                     new byte[]{68, 100, 100, 90},
                                                                     new RampTime( (byte) 0x11, (byte) 0x00, (byte) 0x12,(byte) 0x00 ),
                                                                     new byte[]{0, 0, 5, 0}) );
                profiles.put( "Preset - Plant Boost", new LightAuto( new RampTime( (byte) 0x06, (byte) 0x00, (byte) 0x07,(byte) 0x00 ),
                                                                 new byte[]{100, 100, 100, 100},
                                                                 new RampTime( (byte) 0x11, (byte) 0x00, (byte) 0x12,(byte) 0x00 ),
                                                                 new byte[]{0, 0, 5, 0}) );
                break;
        }
        if ( hasAutoDynamic )
        {
            for ( String key : profiles.keySet() )
            {
                profiles.get( key ).setHasDynamic( true );
                profiles.get( key ).setSun( false );
                profiles.get( key ).setMon( false );
                profiles.get( key ).setTue( false );
                profiles.get( key ).setWed( false );
                profiles.get( key ).setThu( false );
                profiles.get( key ).setFri( false );
                profiles.get( key ).setSat( false );
                profiles.get( key ).setDynamicEnable( false );
                profiles.get( key ).setDynamicPeriod( new RampTime( (byte) 0, (byte) 0, (byte) 0, (byte) 0 ) );
                profiles.get( key ).setDynamicMode( (byte) 0 );
            }
        }
        return profiles;
    }

//    public static byte[] getDayBright ( short devid )
//    {
//        byte[] brts = null;
//        switch ( devid )
//        {
//            case LIGHT_ID_RGBW:
//            case LIGHT_ID_STRIP_III:
//                brts = new byte[]{ 100, 100, 100, 100 };
//                break;
//            case LIGHT_ID_MARINE_500:
//            case LIGHT_ID_MARINE_800:
//            case LIGHT_ID_MARINE_1100:
//                brts = new byte[]{ 100, 100, 100, 100, 100 };
//                break;
//            case LIGHT_ID_FRESH_500:
//            case LIGHT_ID_FRESH_800:
//            case LIGHT_ID_FRESH_1100:
//                brts = new byte[]{ 100, 100, 100, 100, 100 };
//                break;
//            case LIGHT_ID_AQUASKY_600:
//            case LIGHT_ID_AQUASKY_900:
//            case LIGHT_ID_AQUASKY_1200:
//                brts = new byte[]{ 100, 100, 100, 100 };
//                break;
//        }
//
//        return brts;
//    }
//
//    public static byte[] getNightBright ( short devid )
//    {
//        byte[] brts = null;
//        switch ( devid )
//        {
//            case LIGHT_ID_RGBW:
//            case LIGHT_ID_STRIP_III:
//                brts = new byte[]{ 0, 0, 5, 0 };
//                break;
//            case LIGHT_ID_MARINE_500:
//            case LIGHT_ID_MARINE_800:
//            case LIGHT_ID_MARINE_1100:
//                brts = new byte[]{ 0, 0, 0, 0, 5 };
//                break;
//            case LIGHT_ID_FRESH_500:
//            case LIGHT_ID_FRESH_800:
//            case LIGHT_ID_FRESH_1100:
//                brts = new byte[]{ 0, 0, 0, 0, 5 };
//                break;
//            case LIGHT_ID_AQUASKY_600:
//            case LIGHT_ID_AQUASKY_900:
//            case LIGHT_ID_AQUASKY_1200:
//                brts = new byte[]{ 0, 0, 5, 0 };
//                break;
//        }
//
//        return brts;
//    }

    public static int getChannelCount ( short devid )
    {
        int chns = 0;
        switch ( devid )
        {
            case LIGHT_ID_RGBW:
            case LIGHT_ID_STRIP_III:
            case LIGHT_ID_AQUASKY_600:
            case LIGHT_ID_AQUASKY_900:
            case LIGHT_ID_AQUASKY_1200:
            case LIGHT_ID_AQUASKY_380:
            case LIGHT_ID_AQUASKY_530:
            case LIGHT_ID_AQUASKY_835:
            case LIGHT_ID_AQUASKY_990:
            case LIGHT_ID_AQUASKY_750:
            case LIGHT_ID_AQUASKY_1150:
                chns = 4;
                break;
            case LIGHT_ID_EGG:
            case LIGHT_ID_MARINE_500:
            case LIGHT_ID_MARINE_800:
            case LIGHT_ID_MARINE_1100:
            case LIGHT_ID_FRESH_500:
            case LIGHT_ID_FRESH_800:
            case LIGHT_ID_FRESH_1100:
            case LIGHT_ID_NANO_MARINE:
            case LIGHT_ID_NANO_FRESH:
                chns = 5;
                break;
        }
        return chns;
    }
}