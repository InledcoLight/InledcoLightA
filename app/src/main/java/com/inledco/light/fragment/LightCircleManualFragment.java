package com.inledco.light.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gigamole.library.ArcProgressStackView;
import com.inledco.light.R;
import com.inledco.light.bean.Channel;
import com.inledco.light.bean.LightManual;
import com.inledco.light.util.DeviceUtil;

import java.util.ArrayList;

import io.feeeei.circleseekbar.CircleSeekBar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LightCircleManualFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LightCircleManualFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LightCircleManualFragment extends BaseFragment
{
    private OnFragmentInteractionListener mListener;
    // 参数的键值
    private static final String ARG_PARAM_DEVICE_ADDRESS = "address";
    private static final String ARG_PARAM_DEVICE_ID = "deviceId";
    private static final String ARG_PARAM_MANUAL_MODEL = "manualModel";

    // 传递的设备参数
    private String mDeviceMacAddress;
    private Short mDeviceId;
    private LightManual mLightManual;

    // 圆弧
    private CircleSeekBar mCircleSeekBar;

    public LightCircleManualFragment()
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
     * @param lightManual 手动模式.
     * @return 一个新的实例.
     */
    public static LightCircleManualFragment newInstance(String address, short deviceId, LightManual lightManual)
    {
        LightCircleManualFragment fragment = new LightCircleManualFragment();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM_DEVICE_ADDRESS, address);
        args.putShort(ARG_PARAM_DEVICE_ID, deviceId);
        args.putSerializable(ARG_PARAM_MANUAL_MODEL, lightManual);
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
            mLightManual = (LightManual) getArguments().getSerializable(ARG_PARAM_MANUAL_MODEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_light_circle_manual, container, false);

        // 初始化
        initView(view);
        initData();
        initEvent();

        return view;
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
    protected void initView(View view) {
        ArrayList<ArcProgressStackView.Model> manual_circle_color_models = new ArrayList<>();
        Channel[] channels = DeviceUtil.getLightChannel(getContext(), mDeviceId);
        for(int i=0; i<channels.length; i++) {
            manual_circle_color_models.add(new ArcProgressStackView.Model("",0, channels[i].getColor() & 0x80FFFFFF, channels[i].getColor()));
        }

        mCircleSeekBar = new CircleSeekBar(getContext());
    }

    @Override
    protected void initEvent() {
        mCircleSeekBar.setOnSeekBarChangeListener(new CircleSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onChanged(CircleSeekBar circleSeekBar, int i) {

            }
        });
    }

    @Override
    protected void initData() {
        refreshData();
    }

    private void refreshData() {
        Channel[] channels = DeviceUtil.getLightChannel(getContext(), mDeviceId);
        // 获取圆盘数据
        for (int i=0; i< mLightManual.getChnValues().length; i++) {
            short value = mLightManual.getChnValues()[i];

            channels[i].setValue(value);
        }
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
