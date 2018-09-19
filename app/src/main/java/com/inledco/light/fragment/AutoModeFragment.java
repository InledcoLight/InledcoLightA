package com.inledco.light.fragment;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.inledco.blemanager.BleCommunicateListener;
import com.inledco.blemanager.BleManager;
import com.inledco.light.R;
import com.inledco.light.bean.Channel;
import com.inledco.light.bean.LightModel;
import com.inledco.light.constant.CustomColor;
import com.inledco.light.impl.PreviewTaskListener;
import com.inledco.light.util.CommUtil;
import com.inledco.light.util.DeviceUtil;
import com.inledco.light.util.PreviewTimerTask;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AutoModeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AutoModeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AutoModeFragment extends BaseFragment {
    private static final String ARG_DEVICE_ADDRESS = "deviceMacAddress";
    private static final String ARG_DEVICE_ID = "deviceId";
    private static final String ARG_DEVICE_MODEL = "deviceModel";

    // 整个布局
    private ConstraintLayout mConstraintLayout;

    private String mDeviceMacAddress;
    private short mDeviceId;
    private LightModel mLightModel;

    // 曲线图
    private LineChart mLineChart;

    // 预览 运行 编辑按钮
    private Button mPreviewButton;
    private Button mRunButton;
    private Button mEditButton;

    // 蓝牙回调
    private BleCommunicateListener mBleCommunicateListener;

    // 曲线图数据
    private LineData mLineData;
    private ArrayList<ILineDataSet> mLineDataSets;

    // 定时器
    private Timer mTimer;
    private PreviewTimerTask mPreviewTimerTask;

    // 编辑区域
    private FrameLayout mAutoModeEditFl;

    private OnFragmentInteractionListener mListener;

    public AutoModeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param deviceMacAddress 设备MAC地址.
     * @param deviceId 设备ID.
     * @param lightModel 设备模型数据
     * @return A new instance of fragment AutoModeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AutoModeFragment newInstance(String deviceMacAddress, short deviceId, LightModel lightModel) {
        AutoModeFragment fragment = new AutoModeFragment();
        Bundle args = new Bundle();

        args.putString(ARG_DEVICE_ADDRESS, deviceMacAddress);
        args.putShort(ARG_DEVICE_ID, deviceId);
        args.putSerializable(ARG_DEVICE_MODEL, lightModel);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDeviceMacAddress = getArguments().getString(ARG_DEVICE_ADDRESS);
            mDeviceId = getArguments().getShort(ARG_DEVICE_ID);
            mLightModel = (LightModel) getArguments().getSerializable(ARG_DEVICE_MODEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_auto_mode, container, false);

        initView(view);
        initData();
        initEvent();

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    public void onResume() {
        super.onResume();

        refreshData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        BleManager.getInstance().removeBleCommunicateListener(mBleCommunicateListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && data != null) {
            Log.d("requestCode:",requestCode + "");

            LightModel lightModel = (LightModel) data.getExtras().getSerializable("1");
            mLightModel = lightModel;
        } else {

        }
    }

    @Override
    protected void initView(View view) {
        mConstraintLayout = view.findViewById(R.id.auto_mode_constrainLayout);

        mLineChart = view.findViewById(R.id.auto_mode_chart);
        mPreviewButton = view.findViewById(R.id.auto_mode_preview);
        mRunButton = view.findViewById(R.id.auto_mode_run);
        mEditButton = view.findViewById(R.id.auto_mode_edit);
        mAutoModeEditFl = view.findViewById(R.id.auto_mode_edit_view_fragment);

        XAxis xAxis = mLineChart.getXAxis();

        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(60 * 24);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(13, true);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(false);

        YAxis yRightAxis = mLineChart.getAxisRight();

        yRightAxis.setEnabled(false);

        YAxis yLeftAxis = mLineChart.getAxisLeft();

        yLeftAxis.setAxisMinimum(0);
        yLeftAxis.setAxisMaximum(100);
        yLeftAxis.setLabelCount(5,true);
        yLeftAxis.setTextColor(Color.WHITE);
        yLeftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yLeftAxis.setDrawAxisLine(false);
        yLeftAxis.setValueFormatter( new PercentFormatter( new DecimalFormat( "##0" ) ) );

        mLineChart.setTouchEnabled(false);
        mLineChart.setDragEnabled(false);
        mLineChart.setScaleEnabled(false);
        mLineChart.setDescription(null);
        mLineChart.setPinchZoom(false);


        IAxisValueFormatter axisValueFormatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return Integer.toString((int) ( value / 60 ));
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        };

        xAxis.setValueFormatter(axisValueFormatter);
    }

    @Override
    protected void initEvent() {
        mPreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 实现预览功能
                Button previewButton = (Button) v;

                if (!previewButton.isSelected()) {
                    mPreviewButton.setSelected(true);
                    mPreviewButton.setText(R.string.light_auto_stop);
                    mPreviewTimerTask = new PreviewTimerTask(mDeviceMacAddress,
                            DeviceUtil.getChannelCount(mDeviceId),
                            mLightModel);

                    mPreviewTimerTask.setListener(new PreviewTaskListener() {
                        @Override
                        public void onFinish() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mPreviewButton.setSelected(false);
                                    mPreviewButton.setText(R.string.light_auto_preview);
                                }
                            });
                        }

                        @Override
                        public void onUpdate(int tm) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mLineChart.getXAxis()
                                            .removeAllLimitLines();
                                    LimitLine limitLine = new LimitLine( mPreviewTimerTask.getTm() );
                                    limitLine.setLineWidth( 1 );
                                    limitLine.setLineColor( CustomColor.COLOR_ACCENT );
                                    mLineChart.getXAxis()
                                            .addLimitLine( limitLine );
                                    mLineChart.invalidate();
                                }
                            });
                        }
                    });

                    mTimer.schedule( mPreviewTimerTask, 0, 40 );
                } else {
                    mPreviewButton.setSelected(false);
                    mPreviewButton.setText(R.string.light_auto_preview);
                    // 取消任务
                    if (mPreviewTimerTask != null) {
                        mPreviewTimerTask.cancel();
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            CommUtil.stopPreview(mDeviceMacAddress);

                            mLineChart.getXAxis()
                                    .removeAllLimitLines();
                            mLineChart.invalidate();
                        }
                    }, 80);
                }
            }
        });

        mRunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            CommUtil.runAutoMode(mDeviceMacAddress, mLightModel);
            }
        });

        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 弹出编辑界面
                if (mConstraintLayout != null) {
                    final FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

                    // 设置可见
                    mAutoModeEditFl.setVisibility(View.VISIBLE);

                    fragmentTransaction.replace(R.id.auto_mode_edit_view_fragment, AutoModeEditFragment.newInstance(mLightModel));
                }
            }
        });
    }

    @Override
    protected void initData() {
        mBleCommunicateListener = new BleCommunicateListener() {
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
                // 蓝牙接收到数据后的操作
                if (mac.equals(mDeviceMacAddress)) {
                    Object object = CommUtil.decodeLightModel(list, mDeviceId);
                    if (object != null && object instanceof LightModel) {
                        mLightModel = (LightModel) object;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshData();
                                Toast.makeText( getContext(), R.string.load_success, Toast.LENGTH_SHORT )
                                        .show();
                            }
                        });
                    }
                }
            }
        };

        // 添加蓝牙监听
        BleManager.getInstance().addBleCommunicateListener(mBleCommunicateListener);

        // 刷新数据
        refreshData();

        // 初始化定时器
        mTimer = new Timer();
    }

    private void refreshData() {
        // 构造曲线图数据
        if (mLineDataSets == null) {
            mLineDataSets = new ArrayList<>();
        }

        mLineDataSets.clear();

        // 如果没有数据则返回
        if (mLightModel.getTimePointColorValue() == null || mLightModel.getTimePoints() == null) {
            return;
        }

        // 获取通道数据
        Channel[] channels = DeviceUtil.getLightChannel(getContext(), mDeviceId);
        for (int i=0;i<channels.length;i++) {
            List<Entry> entryList = new ArrayList<>();
            // 添加坐标0处的点
            byte[] bytes = mLightModel.getTimePointColorValue().get((short) 0);
            entryList.add( new Entry( 0,  bytes[i]) );
            for (int j=0;j<mLightModel.getTimePoints().length;j++) {
                entryList.add( new Entry( mLightModel.getTimePoints()[j].getmHour() * 60 + mLightModel.getTimePoints()[j].getmMinute(),
                                        mLightModel.getTimePointColorValue().get((short)j)[i]) );
            }
            // 添加坐标24*60处的点
            entryList.add( new Entry( 24 * 60,  bytes[i]) );

            LineDataSet lineDataSet = new LineDataSet(entryList, channels[i].getName());

            lineDataSet.setColor(channels[i].getColor());
            lineDataSet.setCircleRadius( 3.0f );
            lineDataSet.setCircleColor(channels[i].getColor());
            lineDataSet.setDrawCircleHole(false);
            lineDataSet.setLineWidth(2.0f);

            mLineDataSets.add(lineDataSet);
        }

        mLineData = new LineData(mLineDataSets);

        mLineChart.setData(mLineData);
        mLineChart.invalidate();
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
