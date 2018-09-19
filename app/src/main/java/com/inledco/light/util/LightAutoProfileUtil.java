package com.inledco.light.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.inledco.light.bean.LightModel;
import com.inledco.light.bean.RampTime;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liruya on 2016/11/28.
 */

public class LightAutoProfileUtil
{
    private static final String LIGHT_AUTO_PROFILE_FILENAME = "profile_";
    private static final RampTime DEFAULT_SUNRISE = new RampTime( (byte) 0x06, (byte) 0x00, (byte) 0x07, (byte) 0x00 );
    private static final RampTime DEFAULT_SUNSET = new RampTime( (byte) 0x11, (byte) 0x00, (byte) 0x12, (byte) 0x00 );

//    public static LightAuto getDefaultProfile( short devid )
//    {
//        LightAuto lightAuto = new LightAuto( DEFAULT_SUNRISE,
//                                             DeviceUtil.getDayBright( devid ),
//                                             DEFAULT_SUNSET,
//                                             DeviceUtil.getNightBright( devid ));
//        return lightAuto;
//    }

    public static void saveProfile (Context context, LightModel lightModel, short devid, String name )
    {
        PreferenceUtil.setObjectToPrefer( context, LIGHT_AUTO_PROFILE_FILENAME + devid, lightModel, name );
    }

    public static void deleteProfile ( Context context, short devid, String name )
    {
        PreferenceUtil.deleteObjectFromPrefer( context, LIGHT_AUTO_PROFILE_FILENAME + devid, name );
    }

    public static Map<String, LightModel> getLocalProfiles ( Context context, short devid, boolean hasAutoDynamic )
    {
        SharedPreferences sp = context.getSharedPreferences( LIGHT_AUTO_PROFILE_FILENAME + devid,
                                                             Context.MODE_PRIVATE );
        Map<String, LightModel> map = DeviceUtil.getPresetProfiles( devid, hasAutoDynamic );
        if ( map == null )
        {
            map = new HashMap<>();
        }
//        map.put( context.getResources().getString( R.string.custom_default ), getDefaultProfile( devid ) );
        for ( String key : sp.getAll().keySet() )
        {
            map.put( key, (LightModel) PreferenceUtil.getObjectFromPrefer( context, LIGHT_AUTO_PROFILE_FILENAME + devid, key ) );
        }
        return map;
    }
}
