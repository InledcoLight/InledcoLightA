package com.inledco.light.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CheckableImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.inledco.blemanager.BleCommunicateListener;
import com.inledco.blemanager.BleManager;
import com.inledco.light.R;
import com.inledco.light.bean.Channel;
import com.inledco.light.bean.LightModel;
import com.inledco.light.constant.ConstVal;
import com.inledco.light.util.CommUtil;
import com.inledco.light.util.DeviceUtil;
import com.inledco.light.util.MeasureUtil;
import java.util.ArrayList;
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

    private BleCommunicateListener mCommunicateListener;

    // 布局
    private ConstraintLayout mConstraintLayout;

    // 通道数据
    private Channel[] mChannels;

    // 各个通道数值
    private short[] mChannelsValues;

    // 控件
    private CircleSeekBar mCircleSeekBar;
    private CheckableImageButton mPowerButton;
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
                for (int j=0; j<1; j++) {
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
        // 使用相对布局，由于灯具的路数不一定，所以使用代码布局
        mConstraintLayout = new ConstraintLayout(getContext());

        // 初始化
        initView(mConstraintLayout);
        initData();
        initEvent();

        return mConstraintLayout;
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

        BleManager.getInstance().removeBleCommunicateListener(mCommunicateListener);
        mCommunicateListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void initView(View view) {
        initConstraintLayout(view);
    }

    /**
     * 初始化布局
     * @param view 视图
     */
    @SuppressLint("RestrictedApi")
    private void initConstraintLayout(View view) {
        // 通道数值
        mChannelsValues = mLightModel.getChnValues();

        // 初始化类型数据
        mChannels = DeviceUtil.getLightChannel(getContext(), mDeviceId);

        mCircleSeekBar = new CircleSeekBar(getContext());
        mCircleSeekBar.setOnSeekBarChangeListener(mColorSeekBarChangeListener);
        mCircleSeekBar.setPointerRadius(30);
        mCircleSeekBar.setReachedColor(mChannels[0].getColor());
        mCircleSeekBar.setUnreachedColor(mChannels[0].getColor() & 0x80FFFFFF);
        mCircleSeekBar.setReachedWidth(60);
        mCircleSeekBar.setUnreachedWidth(60);
        mCircleSeekBar.setMaxProcess((int) ConstVal.MAX_COLOR_VALUE);
        mCircleSeekBar.setWheelShadow(5);
        mCircleSeekBar.setPointerShadowRadius(0);
        mCircleSeekBar.setHasReachedCornerRound(true);
        mCircleSeekBar.setCurProcess(mChannelsValues[0]);

        ConstraintLayout.LayoutParams circleSeekBarLayoutParams = new ConstraintLayout.LayoutParams(0,0);

        circleSeekBarLayoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        circleSeekBarLayoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        circleSeekBarLayoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        circleSeekBarLayoutParams.dimensionRatio = "1";
        circleSeekBarLayoutParams.setMargins(60,30,60,0);

        mConstraintLayout.addView(mCircleSeekBar, circleSeekBarLayoutParams);

        // 添加用户自定义按钮
        for (int i=0; i<3; i++) {
            Button userDefineButton = new Button(getContext());

            userDefineButton.setId(30000 + i);
            userDefineButton.setTag(i);
            userDefineButton.setBackgroundColor(getResources().getColor(R.color.colorPureBlue));
            userDefineButton.setText("M" + Integer.toString(i + 1));
            userDefineButton.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
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

                    CommUtil.writeData(mDeviceMacAddress, (byte) 0x02, (byte) (mLightModel.getControllerNum() * 2), values);
                }
            });

            userDefineButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int userDefineIndex = (int) v.getTag();

                    // 把当前亮度保存到对应用户设置用
                    byte[] values = new byte[mLightModel.getChnValues().length];
                    for (int i=0;i<values.length;i++) {
                        values[i] = (byte) (mLightModel.getChnValues()[i] * 100 / ConstVal.MAX_COLOR_VALUE);
                    }

                    CommUtil.writeData(mDeviceMacAddress, (byte)(0x0c + userDefineIndex * mLightModel.getControllerNum()), (byte) (mLightModel.getControllerNum()), values);

                    return true;
                }
            });

            ConstraintLayout.LayoutParams userDefineButtonParams = new ConstraintLayout.LayoutParams(
                    MeasureUtil.Dp2Px(getContext(), 70),
                    MeasureUtil.Dp2Px(getContext(), 35));

            userDefineButtonParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
            userDefineButtonParams.bottomMargin = 10;
            if (i == 0) {
                userDefineButtonParams.endToStart = 30001;
                userDefineButtonParams.rightMargin = 10;
            } else if (i == 1) {
                userDefineButtonParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
                userDefineButtonParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            } else {
                userDefineButtonParams.startToEnd = 30001;
                userDefineButtonParams.setMarginStart(10);
            }

            mConstraintLayout.addView(userDefineButton, userDefineButtonParams);
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

        ConstraintLayout.LayoutParams powerButtonLayoutParams = new ConstraintLayout.LayoutParams(
                MeasureUtil.Dp2Px(getContext(), 64),
                MeasureUtil.Dp2Px(getContext(), 64));

        powerButtonLayoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        powerButtonLayoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        powerButtonLayoutParams.bottomToTop = 30002;
        powerButtonLayoutParams.bottomMargin = MeasureUtil.Dp2Px(getContext(), 10);

        mConstraintLayout.addView(mPowerButton, powerButtonLayoutParams);
    }

    @Override
    protected void initEvent() {
        mCommunicateListener = new BleCommunicateListener() {
            @Override
            public void onDataValid(String mac) {

            }

            @Override
            public void onDataInvalid(String mac) {

            }

            @Override
            public void onReadMfr(String mac, String s) {

            }

            @Override
            public void onDataReceived(String mac, ArrayList<Byte> list) {
                if (mLightModel != null && CommUtil.decodeLightModel(list, mLightModel)) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 刷新手动模式界面
                            refreshManualModeView(mLightModel);
                        }
                    });
                }
            }
        };

        BleManager.getInstance().addBleCommunicateListener(mCommunicateListener);
    }

    @Override
    protected void initData() {

    }

    private void refreshManualModeView(LightModel lightModel) {
        // 刷新圆盘

        // 刷新开关
        if (mLightModel.isPowerOn()) {
            mPowerButton.setImageResource(R.drawable.ic_power);
        } else {
            mPowerButton.setImageResource(R.drawable.ic_power_off);
        }

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
