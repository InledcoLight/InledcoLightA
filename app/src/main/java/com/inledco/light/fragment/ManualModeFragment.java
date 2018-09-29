package com.inledco.light.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CheckableImageButton;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inledco.light.R;
import com.inledco.light.bean.Channel;
import com.inledco.light.bean.LightModel;
import com.inledco.light.util.CommUtil;
import com.inledco.light.util.DeviceUtil;

import io.feeeei.circleseekbar.CircleSeekBar;

/**
 * 手动模式界面
 */
public class ManualModeFragment extends BaseFragment
{
    private OnFragmentInteractionListener mListener;
    // 参数的键值
    private static final String ARG_PARAM_DEVICE_ADDRESS = "address";
    private static final String ARG_PARAM_DEVICE_ID = "deviceId";
    private static final String ARG_PARAM_LIGHT_MODEL = "lightModel";

    // 传递的设备参数
    private String mDeviceMacAddress;
    private Short mDeviceId;
    private LightModel mLightModel;

    // 当前时间秒数
    private long mCurrentMillisSecond = System.currentTimeMillis();

    // 布局
    private RelativeLayout mColorRelativeLayout;

    // 通道数据
    private Channel[] mChannels;

    // 各个通道数值
    private short[] mChannelsValues;

    // 圆弧
    private CircleSeekBar mCircleSeekBar;

    // 开关按钮
    private CheckableImageButton mPowerButton;

    // 显示百分比
    private TextView percentTextView;

    // 圆形滑动回调
    private CircleSeekBar.OnSeekBarChangeListener mColorSeekBarChangeListener = new CircleSeekBar.OnSeekBarChangeListener() {
        @Override
        public void onChanged(CircleSeekBar circleSeekBar, int i) {
            int colorIndex = (int) circleSeekBar.getTag();

            percentTextView.setText(String.format("%s%%", i / 10));

            long currentTime = System.currentTimeMillis();
            if (currentTime - mCurrentMillisSecond > 32) {
                short[] colorValues = new short[mChannels.length];
                for (int j=0; j<colorValues.length; j++) {
                    colorValues[j] = (short) 0xFFFF;
                }

                colorValues[colorIndex] = (short) i;

                CommUtil.setLed(mDeviceMacAddress, colorValues);

                mCurrentMillisSecond = currentTime;
            }

            Log.v("teg;",Integer.toString(colorIndex) + ":" + Integer.toString(i));
        }
    };

    public ManualModeFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * 使用这个工厂方法根据提供的参数创建一个实例
     *
     * @param address MAC 地址.
     * @param deviceId 设备 Id.
     * @param lightModel 手动模式.
     * @return 一个新的实例.
     */
    public static ManualModeFragment newInstance(String address, short deviceId, LightModel lightModel)
    {
        ManualModeFragment fragment = new ManualModeFragment();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM_DEVICE_ADDRESS, address);
        args.putShort(ARG_PARAM_DEVICE_ID, deviceId);
        args.putSerializable(ARG_PARAM_LIGHT_MODEL, lightModel);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mDeviceMacAddress = getArguments().getString(ARG_PARAM_DEVICE_ADDRESS);
            mDeviceId = getArguments().getShort(ARG_PARAM_DEVICE_ID);
            mLightModel = (LightModel) getArguments().getSerializable(ARG_PARAM_LIGHT_MODEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manual_mode, null );

        return view;
        // 使用相对布局，由于灯具的路数不一定，所以使用代码布局
//        mColorRelativeLayout = new RelativeLayout(getContext());
//
//        // 初始化
//        initView(mColorRelativeLayout);
//        initData();
//        initEvent();

        // return mColorRelativeLayout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void initView(View view) {
        // 通道数值
        mChannelsValues = mLightModel.getmChnValues();

        // 获取通道数量
        int channelNum = mChannelsValues.length;

        // 初始化类型数据
        mChannels = DeviceUtil.getLightChannel(getContext(), mDeviceId);

        // 获取设备屏幕宽高
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        // 圆盘距离手动自动切换的距离
        int circleDistance = 30;

        // 圆形中心直径
        int diameter = displayMetrics.widthPixels / 6;

        // 圆盘距离边缘的距离
        int distance = displayMetrics.widthPixels / 12;

        // 圆环宽度
        float circleWidth = (displayMetrics.widthPixels - distance * 2 - diameter) / (mChannelsValues.length * 2);

        // 添加百分比显示
        percentTextView = new TextView(getContext());
        percentTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        RelativeLayout.LayoutParams textViewLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        textViewLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textViewLayoutParams.width = diameter;
        textViewLayoutParams.height = diameter;
        textViewLayoutParams.topMargin = (int) circleWidth * (mChannelsValues.length) + circleDistance + 3 * diameter / 8;

        mColorRelativeLayout.addView(percentTextView, textViewLayoutParams);

        // 根据通道数量创建圆环
        for (int i=0; i<1; i++) {
            // 圆环
            mCircleSeekBar = new CircleSeekBar(getContext());

            mCircleSeekBar.setId(10000 + i);
            mCircleSeekBar.setReachedColor(mChannels[i].getColor());
            mCircleSeekBar.setUnreachedColor(mChannels[i].getColor() & 0x80FFFFFF);
            mCircleSeekBar.setReachedWidth(circleWidth);
            mCircleSeekBar.setUnreachedWidth(circleWidth);
            mCircleSeekBar.setMaxProcess(1000);
            mCircleSeekBar.setCurProcess(mChannelsValues[i]);
            mCircleSeekBar.setTag(i);
            mCircleSeekBar.setOnSeekBarChangeListener(mColorSeekBarChangeListener);

            // 布局参数
            RelativeLayout.LayoutParams circleSeekBarLayoutParams =
                    new RelativeLayout.LayoutParams(displayMetrics.widthPixels - distance * 2 - i * (int)mCircleSeekBar.getReachedWidth() * 2,
                                                    displayMetrics.widthPixels - distance * 2 - i * (int)mCircleSeekBar.getReachedWidth() * 2);

            circleSeekBarLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            circleSeekBarLayoutParams.topMargin = (int) circleWidth * i + circleDistance;

            mColorRelativeLayout.addView(mCircleSeekBar, circleSeekBarLayoutParams);

            // 设置第一个颜色值
            if (i == 0) {
                percentTextView.setText(String.format("%s%%", mChannelsValues[i] / 10));
            }
        }

        // 添加中间百分比及微调按钮
        TextView centerTextView = new TextView(getContext());

        mColorRelativeLayout.addView(centerTextView);

        Button decreaseBtn = new Button(getContext());

        mColorRelativeLayout.addView(decreaseBtn);

        Button increaseBtn = new Button(getContext());

        mColorRelativeLayout.addView(increaseBtn);

        // 添加颜色选择
        for (int i = 0; i < channelNum; i++) {
            Button btn = new Button(getContext());

            mColorRelativeLayout.addView(btn);
        }

        // 添加开关按钮
        mPowerButton = new CheckableImageButton(getContext());

        mPowerButton.setId(20000 + 1);
        mPowerButton.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        if (mLightModel.isPowerOn()) {
            mPowerButton.setImageResource(R.drawable.ic_power);
        } else {
            mPowerButton.setImageResource(R.drawable.ic_power_off);
        }

        mPowerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLightModel.isPowerOn()) {
                    CommUtil.turnOffLed(mDeviceMacAddress);
                } else {
                    CommUtil.turnOnLed(mDeviceMacAddress);
                }
            }
        });

        RelativeLayout.LayoutParams powerButtonLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        powerButtonLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        powerButtonLayoutParams.addRule(RelativeLayout.BELOW, mCircleSeekBar.getId());

        mColorRelativeLayout.addView(mPowerButton, powerButtonLayoutParams);

        // 添加用户自定义按钮
        for (int i=0; i<3; i++) {
            Button userDefineButton = new Button(getContext());

            userDefineButton.setId(30000 + i);
            userDefineButton.setTag(i);
            userDefineButton.setBackgroundColor(getResources().getColor(R.color.colorPureBlue));
            userDefineButton.setText("M" + Integer.toString(i + 1));
            userDefineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int userDefineIndex = (int) v.getTag();

                    byte[] userDefineValues = mLightModel.getUserDefineColorValue().get(userDefineIndex);
                    
                    // 设置对应的用户设置
                    short[] values = new short[userDefineValues.length];
                    for (int i = 0; i < userDefineValues.length; i++)
                    {
                        values[i] = (short) ((userDefineValues[i] & 0xFF) * 10);
                    }
                    CommUtil.setLed(mDeviceMacAddress, values);
                }
            });

            userDefineButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int userDefineIndex = (int) v.getTag();

                    CommUtil.setLedCustom(mDeviceMacAddress, (byte) userDefineIndex);

                    return true;
                }
            });

            RelativeLayout.LayoutParams userDefineButtonParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            userDefineButtonParams.addRule(RelativeLayout.BELOW, mPowerButton.getId());
            if (i == 0) {
                userDefineButtonParams.addRule(RelativeLayout.LEFT_OF, mPowerButton.getId());
                userDefineButtonParams.rightMargin = 50;
            } else if (i == 1) {
                userDefineButtonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            } else {
                userDefineButtonParams.addRule(RelativeLayout.RIGHT_OF, mPowerButton.getId());
                userDefineButtonParams.leftMargin = 50;
            }

            mColorRelativeLayout.addView(userDefineButton, userDefineButtonParams);
        }
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        refreshData();
    }

    private void refreshData() {
        Channel[] channels = DeviceUtil.getLightChannel(getContext(), mDeviceId);
        // 获取圆盘数据
//        for (int i=0; i< mLightManual.getChnValues().length; i++) {
//            short value = mLightManual.getChnValues()[i];
//
//            channels[i].setValue(value);
//        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
