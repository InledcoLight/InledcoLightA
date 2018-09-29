package com.inledco.light.util;

import com.inledco.light.bean.LightModel;
import com.inledco.light.constant.ConstVal;
import com.inledco.light.impl.PreviewTaskListener;

import java.util.TimerTask;

/**
 * 预览功能
 */

public class PreviewTimerTask extends TimerTask {
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
        if (tm >= ConstVal.MAX_MINUTES_INDEX)
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

    /**
     * 获取指定索引的颜色值
     * @param ct 索引值
     * @param lightModel 设备参数模型
     * @return 颜色数组
     */
    private short[] getColorValues(int ct, LightModel lightModel) {
        short[] values = new short[mChannelNum];

        // 获取时间点个数
        int timePointNum = lightModel.getTimePoints().size();
        int[] tms = new int[timePointNum];
        for (int i=0;i<timePointNum;i++) {
            tms[i] = lightModel.getTimePoints().get(i).getHour() * 60 + lightModel.getTimePoints().get(i).getMinute();
        }

        // 构造颜色值信息
        byte[][] vals = new byte[timePointNum][mChannelNum];
        for (int i = 0; i < timePointNum; i++)
        {
            for (int j=0;j<mChannelNum;j++) {
                vals[i][j] = lightModel.getTimePointColorValue().get((short)i)[j];
            }
        }

        for (int i = 0; i < timePointNum; i++)
        {
            int j = (i + 1) % timePointNum;
            int st = tms[i];
            int et = tms[j];
            int duration;
            int dt;
            int dbrt;
            if (et >= st)
            {
                if (ct >= st && ct < et)
                {
                    duration = et - st;
                    dt = ct - st;
                }
                else
                {
                    continue;
                }
            }
            else
            {
                if (ct >= st || ct < et)
                {
                    duration = ConstVal.MAX_MINUTES_INDEX - st + et;
                    if (ct >= st)
                    {
                        dt = ct - st;
                    }
                    else
                    {
                        dt = ConstVal.MAX_MINUTES_INDEX - st + ct;
                    }
                }
                else
                {
                    continue;
                }
            }
            for (int k = 0; k < mChannelNum; k++)
            {
                byte sbrt = vals[i][k];
                byte ebrt = vals[j][k];
                if (ebrt >= sbrt)
                {
                    dbrt = ebrt - sbrt;
                    values[k] = (short) ((sbrt & 0xFF) * 10 + dbrt * 10 * dt / duration);
                }
                else
                {
                    dbrt = sbrt - ebrt;
                    values[k] = (short) ((sbrt & 0xFF) * 10 - dbrt * 10 * dt / duration);
                }
            }
        }

        return values;
    }
}