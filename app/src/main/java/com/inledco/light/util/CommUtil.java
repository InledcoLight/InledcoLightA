package com.inledco.light.util;

import android.util.Log;

import com.ble.api.DataUtil;
import com.inledco.blemanager.BleManager;
import com.inledco.light.bean.LightModel;
import com.inledco.light.bean.RampTime;
import com.inledco.light.bean.RunMode;
import com.inledco.light.bean.TimePoint;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 与硬件设备通信工具类
 * Created by liruya on 2016/8/26.
 */
public class CommUtil
{
    private static final String TAG = "CommUtil";

    private static final int MAX_SEND_BYTES = 17;
    public static final byte CHNL_RED = 0x01;
    public static final byte CHNL_GREEN = 0x02;
    public static final byte CHNL_BLUE = 0x04;
    public static final byte CHNL_WHITE = 0x08;
    public static final byte CHNL_ALL = 0x0F;

    private static final byte FRM_HDR = 0x68;
    private static final byte LED_ON = 0x01;
    private static final byte LED_OFF = 0x00;
    private static final byte LED_AUTO = 0x01;
    private static final byte LED_MANUAL = 0x00;
    private static final byte CMD_AUTO = 0x02;
    private static final byte CMD_SWITCH = 0x03;
    private static final byte CMD_CTRL = 0x04;
    private static final byte CMD_READ = 0x05;
    private static final byte CMD_CUSTOM = 0x06;
    private static final byte CMD_CYCLE= 0x07;
    private static final byte CMD_CHN_INC = 0x08;
    private static final byte CMD_CHN_DEC = 0x09;
    private static final byte CMD_DYN = 0x0A;
    private static final byte CMD_PREVIEW = 0x0B;
    private static final byte CMD_STOP_PREVIEW = 0x0C;
    private static final byte CMD_READTIME = 0x0D;
    private static final byte CMD_SYNC_TIME = 0x01;
    private static final byte CMD_FIND = 0x0F;

    public static ArrayList<Byte> mRcvBytes = new ArrayList<>();

    /**
     * 计算异或校验值
     * @param array   数组
     * @param len     长度
     * @return        返回校验值
     */
    public static byte getCRC(byte[] array, int len)
    {
        byte crc = 0x00;
        for(int i = 0; i < len; i++)
        {
            crc ^= array[i];
        }
        return crc;
    }

    /**
     *
     * @param bytes
     * @param len
     * @return
     */
    public static byte getCRC(ArrayList<Byte> bytes, int len)
    {
        byte crc = 0x00;
        for(int i = 0; i < len; i++)
        {
            crc ^= bytes.get(i);
        }
        return crc;
    }

    /**
     * 打开LED
     * @param mac 地址
     */
    public static void turnOnLed(String mac)
    {
        BleManager.getInstance().sendBytes(mac, new byte[]{FRM_HDR, CMD_SWITCH, LED_ON, FRM_HDR^CMD_SWITCH^LED_ON});
    }

    /**
     * 关闭LED
     * @param mac 地址
     */
    public static void turnOffLed(String mac)
    {
        BleManager.getInstance().sendBytes(mac, new byte[]{FRM_HDR, CMD_SWITCH, LED_OFF, FRM_HDR^CMD_SWITCH^LED_OFF});
    }


    public static void setAuto(String mac)
    {
        BleManager.getInstance().sendBytes(mac, new byte[]{FRM_HDR, CMD_AUTO, LED_AUTO, FRM_HDR^CMD_AUTO^LED_AUTO});
    }

    public static void setManual(String mac)
    {
        BleManager.getInstance().sendBytes(mac, new byte[]{FRM_HDR, CMD_AUTO, LED_MANUAL, FRM_HDR^CMD_AUTO^LED_MANUAL});
    }
    /**
     * 设置LED运行参数
     * @param mac       地址
     * @param value     要设置的值
     */
    public static void setLed(String mac, short[] value)
    {
        //FRM_HDR CMD_CTRL value xor
        byte[] txs = new byte[3+value.length*2];
        txs[0] = FRM_HDR;
        txs[1] = CMD_CTRL;
        for(int i = 0; i < value.length; i++)
        {
            txs[2+2*i] =(byte)(value[i] >> 8);
            txs[3+2*i] =(byte)(value[i] & 0xFF);
        }
        txs[txs.length-1] = getCRC(txs, txs.length-1);
        BleManager.getInstance().sendBytes(mac, txs);
    }

    /**
     * 快速预览自动模式
     * @param mac
     * @param value
     */
    public static void previewAuto(String mac, short[] value)
    {
        //FRM_HDR CMD_CTRL chn max_8 xor
        byte[] txs = new byte[3+value.length*2];
        txs[0] = FRM_HDR;
        txs[1] = CMD_PREVIEW;
        for(int i = 0; i < value.length; i++)
        {
            txs[2+2*i] =(byte)(value[i] >> 8);
            txs[3+2*i] =(byte)(value[i] & 0xFF);
        }
        txs[txs.length-1] = getCRC(txs, txs.length-1);
        Log.e(TAG, "previewAuto: " + DataUtil.byteArrayToHex(txs));
        BleManager.getInstance().sendBytes(mac, txs);
    }

    public static void stopPreview(String mac)
    {
        BleManager.getInstance().sendBytes(mac, new byte[]{FRM_HDR, CMD_STOP_PREVIEW, FRM_HDR^CMD_STOP_PREVIEW});
    }

    /**
     * 设置led
     * @param mac
     * @param bytes
     */
    public static void setLed(String mac, byte[] bytes)
    {
        BleManager.getInstance().sendBytes(mac, bytes);
    }

    /**
     * 读取设备运行状态
     * @param mac 地址
     */
    public static void readDevice(String mac)
    {
        BleManager.getInstance().sendBytes(mac, new byte[]{FRM_HDR, CMD_READ, FRM_HDR^CMD_READ});
    }

    public static void setLedCustom(String mac, byte idx)
    {
        byte[] txs = new byte[]{ FRM_HDR, CMD_CUSTOM, idx,(byte)(FRM_HDR ^ CMD_CUSTOM ^ idx) };
        BleManager.getInstance().sendBytes(mac, txs);
    }

    public static void increaseBright(String mac, byte chn, byte delta)
    {
        byte[] txs = new byte[]{ FRM_HDR, CMD_CHN_INC, chn, delta,(byte)(FRM_HDR ^ CMD_CHN_INC ^ chn ^ delta) };
        BleManager.getInstance().sendBytes(mac, txs);
    }

    public static void decreaseBright(String mac, byte chn, byte delta)
    {
        byte[] txs = new byte[]{ FRM_HDR, CMD_CHN_DEC, chn, delta,(byte)(FRM_HDR ^ CMD_CHN_DEC ^ chn ^ delta) };
        BleManager.getInstance().sendBytes(mac, txs);
    }

    public static LightModel decodeLightModel(ArrayList<Byte> bytes, short devId) {
        LightModel lightModel = null;
        int receiveDataLength = bytes.size();
        // 检查数据完整性，第5个为数据字节，减去6：减去命令头 校验码 数据量等占用的数量
        if (receiveDataLength > 5 && bytes.get(5) == receiveDataLength - 6 && getCRC(bytes, receiveDataLength) == 0x00) {
            lightModel = new LightModel();

            int dataIndex = 5;
            byte runModeByte = bytes.get(2);
            lightModel.setControllerNum(DeviceUtil.getChannelCount(devId));

            if (runModeByte == 0) {
                // 手动模式数据
                lightModel.setRunMode(RunMode.MANUAL_MODE);
                lightModel.setPowerOn(bytes.get(dataIndex) != 0);

                dataIndex = dataIndex + 1;
                // 这个地方要用控制器的通道数量，数据返回时控制器会返回所有通道的值
                short[] channelValues = new short[lightModel.getControllerNum()];
                for (int i = 0; i < lightModel.getControllerNum(); i++) {
                    channelValues[i] = bytes.get(dataIndex);

                    dataIndex ++;
                }

                lightModel.setmChnValues(channelValues);

                // 解析用户自定义数据
                ArrayList<byte[]> userDefineValues = new ArrayList<>(4);
                for (int i = 0; i < 4; i++) {
                    byte[] userValues = new byte[lightModel.getControllerNum()];
                    for (int j = 0; j < lightModel.getControllerNum(); j++) {
                        userValues[j] = bytes.get(dataIndex);

                        dataIndex ++;
                    }

                    userDefineValues.add(userValues);
                }

                lightModel.setUserDefineColorValue(userDefineValues);
            } else if (runModeByte == 1) {
                // 自动模式数据
                lightModel.setRunMode(RunMode.AUTO_MODE);
                lightModel.setTimePointCount(bytes.get(dataIndex));

                dataIndex = dataIndex + 1;
                TimePoint[] timePoints = new TimePoint[lightModel.getTimePointCount()];
                ArrayList<byte[]> timePointColorValue = new ArrayList<>();
                for (int i = 0; i < lightModel.getTimePointCount(); i++) {
                    timePoints[i] = new TimePoint(bytes.get(dataIndex),bytes.get(++dataIndex));

                    dataIndex ++;
                    byte[] colorValues = new byte[lightModel.getControllerNum()];
                    for (int j = 0; j < lightModel.getControllerNum(); j++) {
                        colorValues[j] = bytes.get(dataIndex);

                        dataIndex ++;
                    }

                    timePointColorValue.add(colorValues);
                }

                lightModel.setTimePoints(timePoints);
                lightModel.setTimePointColorValue(timePointColorValue);
            }
        }

        return lightModel;
    }

    public static void sendKey(String mac, byte key)
    {
        BleManager.getInstance().sendBytes(mac, new byte[]{ FRM_HDR, CMD_DYN, key,(byte)(FRM_HDR ^ CMD_DYN ^ key) });
    }

    public static void findDevice(String mac)
    {
        BleManager.getInstance().sendBytes(mac, new byte[]{FRM_HDR, CMD_FIND, FRM_HDR^CMD_FIND});
    }

    public static void syncDeviceTime(String mac)
    {
        Calendar calendar = Calendar.getInstance();
        byte year =(byte)(calendar.get(Calendar.YEAR) - 2000);
        byte month =(byte) calendar.get(Calendar.MONTH);
        byte day =(byte) calendar.get(Calendar.DAY_OF_MONTH);
        byte wk =(byte)(calendar.get(Calendar.DAY_OF_WEEK) - 1);
        byte hour =(byte) calendar.get(Calendar.HOUR_OF_DAY);
        byte minute =(byte) calendar.get(Calendar.MINUTE);
        byte second =(byte) calendar.get(Calendar.SECOND);
        byte[] ct = new byte[]{ FRM_HDR,
                                CMD_SYNC_TIME,
                                year,
                                month,
                                day,
                                wk,
                                hour,
                                minute,
                                second,
                               (byte)(FRM_HDR ^ CMD_SYNC_TIME ^ year ^ month ^ day ^ wk ^ hour ^ minute ^ second) };
        BleManager.getInstance().sendBytes(mac, ct);
    }

    public static void readDeviceTime(String mac)
    {
        BleManager.getInstance().sendBytes(mac, new byte[]{FRM_HDR, CMD_READTIME, FRM_HDR^CMD_READTIME});
    }

    public static void setLedAuto(String mac, LightAuto lightAuto)
    {
        int dlen = lightAuto.getDayBright().length;
        int nlen = lightAuto.getNightBright().length;
        int len = 11 + dlen + nlen;
        if(lightAuto.isHasDynamic())
        {
            len += 6;
        }
        byte[] datas = new byte[len];
        RampTime sunrise = lightAuto.getSunrise();
        RampTime sunset = lightAuto.getSunset();
        datas[0] = FRM_HDR;
        datas[1] = CMD_CYCLE;
        datas[2] = sunrise.getStartHour();
        datas[3] = sunrise.getStartMinute();
        datas[4] = sunrise.getEndHour();
        datas[5] = sunrise.getEndMinute();
        datas[6+dlen] = sunset.getStartHour();
        datas[7+dlen] = sunset.getStartMinute();
        datas[8+dlen] = sunset.getEndHour();
        datas[9+dlen] = sunset.getEndMinute();
        for(int i = 0; i < dlen; i++)
        {
            datas[6+i] = lightAuto.getDayBright()[i];
        }
        for(int i = 0; i < nlen; i++)
        {
            datas[10+dlen+i] = lightAuto.getNightBright()[i];
        }
        if(lightAuto.isHasDynamic())
        {
            datas[10+dlen+nlen] = lightAuto.getWeek();
            datas[11+dlen+nlen] = lightAuto.getDynamicPeriod().getStartHour();
            datas[12+dlen+nlen] = lightAuto.getDynamicPeriod().getStartMinute();
            datas[13+dlen+nlen] = lightAuto.getDynamicPeriod().getEndHour();
            datas[14+dlen+nlen] = lightAuto.getDynamicPeriod().getEndMinute();
            datas[15+dlen+nlen] = lightAuto.getDynamicMode();
        }
        datas[len-1] = getCRC(datas, len-1);
        BleManager.getInstance().sendBytes(mac, datas);
    }

    public static void runAutoMode(String mac, LightModel lightModel) {
        // 数据长度：命令头 + （时间长度 + 通道数量）* 时间段数量
        int dataLength = 2 +(lightModel.getChannelNum() + 4) * lightModel.getTimePoints().length /  2 + 1;

        if(lightModel.ismDynamicEnable())
        {
            dataLength += 6;
        }

        byte[] values = new byte[dataLength];
        values[0] = FRM_HDR;
        values[1] = CMD_CYCLE;
        for(int i=0;i<lightModel.getTimePoints().length / 2;i++) {
            // 填充时间
            TimePoint startTimePoint = lightModel.getTimePoints()[2 * i];
            TimePoint endTimePoint = lightModel.getTimePoints()[2 * i + 1];
            values[2 + i *(lightModel.getChannelNum() + lightModel.getTimePoints().length)] = startTimePoint.getmHour();
            values[2 + i *(lightModel.getChannelNum() + lightModel.getTimePoints().length) + 1] = startTimePoint.getmMinute();
            values[2 + i *(lightModel.getChannelNum() + lightModel.getTimePoints().length) + 2] = endTimePoint.getmHour();
            values[2 + i *(lightModel.getChannelNum() + lightModel.getTimePoints().length) + 3] = endTimePoint.getmMinute();

            // 填充颜色值
            byte[] colorValues = lightModel.getTimePointColorValue().get((short)(2 * i + 1));
            for(int j=0;j<colorValues.length;j++) {
                values[2 + i *(lightModel.getChannelNum() + lightModel.getTimePoints().length) + 4 + j] = colorValues[j];
            }
        }

        // 动态模式
        if(lightModel.ismDynamicEnable()) {

        }

        values[dataLength-1] = getCRC(values, dataLength-1);
        BleManager.getInstance().sendBytes(mac, values);
    }
}
