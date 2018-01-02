package com.inledco.light.util;

import com.inledco.light.bean.LightModel;
import com.inledco.light.impl.PreviewTaskListener;

import java.util.TimerTask;

/**
 * Created by huangzhengguo on 2018/1/2.
 */

public class PreviewTimerTask extends TimerTask
{
//        private static final int TOTAL_COUNT = 24 * 60;
//        private static final int TIME_STEP = 1;

    private int tm;
    private PreviewTaskListener mListener;
    private String mAddress;
    private int mChannelNum;
    private LightModel mLightModel;

    // 为了编译不出错
    public PreviewTimerTask() {

    }

    public PreviewTimerTask (String address, int channelNum, LightModel lightModel)
    {
        tm = 0;
        mAddress = address;
        mChannelNum = channelNum;
        mLightModel = lightModel;
    }

    public void setListener (PreviewTaskListener listener)
    {
        mListener = listener;
    }

    public int getTm() {
        return tm;
    }

    @Override
    public void run ()
    {
        tm++;
        if (tm >= 1440)
        {
            tm = 0;
            if (mListener != null)
            {
                mListener.onFinish();
            }
        }
        else
        {
            if (mListener != null)
            {
                mListener.onUpdate(tm);
            }
        }

        CommUtil.previewAuto(mAddress, getColorValues(tm, mLightModel));
    }

    private short[] getColorValues(int tm, LightModel lightModel) {
        short[] colorValues = new short[mChannelNum];



        return colorValues;
    }
}