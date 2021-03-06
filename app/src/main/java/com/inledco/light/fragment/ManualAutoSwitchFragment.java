package com.inledco.light.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.inledco.light.R;
import com.inledco.light.util.CommUtil;
import com.inledco.light.util.MeasureUtil;

public class ManualAutoSwitchFragment extends BaseFragment {

    // 参数
    private static final String ARG_PARAM_DEVICE_ADDRESS = "address";
    private static final String ARG_PARAM_DEVICE_ID = "deviceId";
    private static final String ARG_PARAM_MANUAL_AUTO = "manual_auto";

    // 传递的变量
    private String mDeviceAddress;
    private Short mDeviceId;
    private boolean isManualMode;

    private OnFragmentInteractionListener mListener;
    private Button mManualButton;
    private Button mAutoButton;

    public ManualAutoSwitchInterface mManualAutoSwitchInterface;

    public ManualAutoSwitchFragment() {
        // Required empty public constructor
    }

    public static ManualAutoSwitchFragment newInstance(String address, short deviceId, boolean manualAuto) {
        ManualAutoSwitchFragment fragment = new ManualAutoSwitchFragment();

        Bundle args = new Bundle();

        args.putString(ARG_PARAM_DEVICE_ADDRESS, address);
        args.putShort(ARG_PARAM_DEVICE_ID, deviceId);
        args.putBoolean(ARG_PARAM_MANUAL_AUTO, manualAuto);

        fragment.setArguments(args);

        return  fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mDeviceAddress = getArguments().getString(ARG_PARAM_DEVICE_ADDRESS);
            mDeviceId = getArguments().getShort(ARG_PARAM_DEVICE_ID);
            isManualMode = getArguments().getBoolean(ARG_PARAM_MANUAL_AUTO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manual_auto_switch, null );

        initView(view);
        initEvent();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    protected void initView(View view) {
        mManualButton = view.findViewById(R.id.manualButton);
        mAutoButton = view.findViewById(R.id.autoButton);

        if (isManualMode) {
            mManualButton.setBackgroundResource(R.drawable.manual_auto_switch_manual_background);
            mManualButton.setTextColor(getResources().getColor(R.color.colorPureBlue));

            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mManualButton.getLayoutParams();

            layoutParams.setMargins(10,10,10,10);

            mManualButton.setLayoutParams(layoutParams);

        } else {
            mAutoButton.setBackgroundResource(R.drawable.manual_auto_switch_manual_background);
            mAutoButton.setTextColor(getResources().getColor(R.color.colorPureBlue));

            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mAutoButton.getLayoutParams();

            layoutParams.setMargins(10,10,10,10);

            mAutoButton.setLayoutParams(layoutParams);
        }
    }

    @Override
    protected void initEvent() {
        // 手动自动模式切换方法
        mManualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mManualAutoSwitchInterface.setManualMode();
            }
        });

        mAutoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mManualAutoSwitchInterface.setAutoMode();
            }
        });
    }

    @Override
    protected void initData() {

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

    public void setManualMode(boolean isManual) {
        isManualMode = isManual;
    }

    public interface ManualAutoSwitchInterface {
        void setManualMode();
        void setAutoMode();
    }
}
