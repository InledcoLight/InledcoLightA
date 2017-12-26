package com.inledco.light.fragment;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.gigamole.library.ArcProgressStackView;
import com.inledco.light.R;
import com.inledco.light.bean.Channel;
import com.inledco.light.bean.LightManual;
import com.inledco.light.util.DeviceUtil;

import java.util.ArrayList;

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

    // 控件
    private ArcProgressStackView mArcProgressStackView;

    // 所有通道数据
    private ArrayList<Channel> mChannels;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_light_circle_manual, container, false);

        // 初始化
        initView(view);
        initData();
        initEvent();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
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
        // 根据模型数据创建圆盘型调光数据
        mArcProgressStackView = (ArcProgressStackView) view.findViewById(R.id.manual_circle_color);
        ArrayList<ArcProgressStackView.Model> manual_circle_color_models = new ArrayList<>();
        Channel[] channels = DeviceUtil.getLightChannel(getContext(), mDeviceId);
        for(int i=0; i<channels.length; i++){

            manual_circle_color_models.add(new ArcProgressStackView.Model("",0, channels[i].getColor() & 0x80FFFFFF, channels[i].getColor()));
        }

        // 设置色条宽度
        DisplayMetrics dm =getResources().getDisplayMetrics();
        mArcProgressStackView.setDrawWidthDimension((dm.widthPixels - 60) / manual_circle_color_models.size());
        mArcProgressStackView.setModels(manual_circle_color_models);
    }

    @Override
    protected void initEvent() {
        mArcProgressStackView.setAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                String s = String.valueOf(animation.getAnimatedValue());
                // 动画是获取不到当前索引的
                int f = mArcProgressStackView.getModels().size();

                for(int i=0; i<mArcProgressStackView.getModels().size(); i++){

                }

                Log.d("onAnimationUpdate: ", "f:" + f);
            }
        });


    }

    @Override
    protected void initData() {
        refreshData();
    }

    private void refreshData(){
        mChannels = new ArrayList<>();
        Channel[] channels = DeviceUtil.getLightChannel(getContext(), mDeviceId);
        // 获取圆盘数据
        for (int i=0; i< mLightManual.getChnValues().length; i++) {
            // 这个值是在[0,1000]范围内的值，需要转换成百分比显示
            // 圆弧的默认范围值是[0,100]
            short value = mLightManual.getChnValues()[i];

            channels[i].setValue(value);
            mChannels.add(channels[i]);

            mArcProgressStackView.getModels().get(i).setProgress((float)value / 10.0f);
        }

        mArcProgressStackView.invalidate();
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
