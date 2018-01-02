package com.inledco.light.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.inledco.blemanager.BleCommunicateListener;
import com.inledco.blemanager.BleManager;
import com.inledco.light.R;
import com.inledco.light.bean.DevicePrefer;
import com.inledco.light.bean.Light;
import com.inledco.light.bean.LightManual;
import com.inledco.light.bean.LightModel;
import com.inledco.light.fragment.AutoModeFragment;
import com.inledco.light.fragment.DataInvalidFragment;
import com.inledco.light.fragment.LightAutoFragment;
import com.inledco.light.fragment.ManualModeFragment;
import com.inledco.light.fragment.ManualAutoSwitchFragment;
import com.inledco.light.util.CommUtil;
import com.inledco.light.util.DeviceUtil;

import java.util.ArrayList;


public class LightingActivity extends BaseActivity implements DataInvalidFragment.OnRetryClickListener {
    // 设备参数
    private DevicePrefer mPrefer;
    private Light mLight;
    private Toolbar mLightingToolbar;
    private TextView mTitleTextView;
    private ProgressDialog mProgressDialog;
    private CountDownTimer mCountDownTimer;
    private BleCommunicateListener mCommunicateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lighting);

        // 接收传递的设备参数
        Intent intent = getIntent();
        mPrefer = (DevicePrefer) intent.getSerializableExtra("DevicePrefer");

        initView();
        initEvent();
        initData();
    }

    @Override
    protected void initView() {
        // 设置圆形颜色设置宽高相同
        FrameLayout colorFrameLayout = (FrameLayout) findViewById(R.id.lighting_fragment);

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) colorFrameLayout.getLayoutParams();

        colorFrameLayout.setLayoutParams(layoutParams);

        // 设置手动自动模式切换按钮
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.manual_auto_fragment, ManualAutoSwitchFragment.newInstance(mPrefer.getDeviceMac(),
                                                                                         mPrefer.getDevId()))
                .commit();

        // 获取各个控件
        mLightingToolbar = (Toolbar) findViewById(R.id.lightingToolbar);

        mTitleTextView = (TextView) findViewById(R.id.titleTextView);
        mTitleTextView.setText(mPrefer.getDeviceName());

        // 初始化提示框
        mProgressDialog = new ProgressDialog(this );
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                BleManager.getInstance().disconnectDevice(mPrefer.getDeviceMac());
            }
        });
    }

    @Override
    protected void initEvent() {
        mLightingToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        mCountDownTimer = new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                // 1.隐藏正在连接提示
                // 2.隐藏手动自动模式切换按钮
                // 3.显示连接失败界面
                if (mProgressDialog.isShowing()){
                    mProgressDialog.dismiss();
                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.lighting_fragment, DataInvalidFragment.newInstance( mPrefer.getDeviceMac() ))
                        .commit();
            }
        };

        mCommunicateListener = new BleCommunicateListener() {
            @Override
            public void onDataValid(final String mac) {
                // 数据获取成功回调
                if (mac.equals(mPrefer.getDeviceMac())){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgressDialog.setMessage(getString(R.string.msg_get_data_success));
                            CommUtil.syncDeviceTime(mac);
                            mCountDownTimer.start();
                        }
                    });
                }
            }

            @Override
            public void onDataInvalid(String mac) {
                if (mac.equals(mPrefer.getDeviceMac())){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mProgressDialog.isShowing()){
                                mProgressDialog.dismiss();
                            }

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.lighting_fragment, DataInvalidFragment.newInstance( mPrefer.getDeviceMac()))
                                    .commit();
                        }
                    });
                }
            }

            @Override
            public void onReadMfr(String mac, String s)
            {

            }

            @Override
            public void onDataReceived(String mac, ArrayList<Byte> list)
            {
                if (mac.equals(mPrefer.getDeviceMac()))
                {
                    decodeReceiveData(mac, list);
                }
            }
        };

        // 开始蓝牙扫描
        BleManager.getInstance().addBleCommunicateListener(mCommunicateListener);

        mLight = new Light(mPrefer, false, false, null, (LightModel) null );

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.lighting_fragment, DataInvalidFragment.newInstance(mPrefer.getDeviceMac()))
                .commit();

        getDeviceData();
    }

    @Override
    public void onRetryClick()
    {
        getDeviceData();
    }

    // 连接设备获取设备数据
    private void getDeviceData()
    {
        // 连接设备
        if (!BleManager.getInstance().isConnected(mPrefer.getDeviceMac()))
        {
            // 没有连接则连接设备
            mProgressDialog.setMessage(getString(R.string.msg_connecting_device));
            mProgressDialog.show();
            BleManager.getInstance().connectDevice(mPrefer.getDeviceMac());
        }
        else if (BleManager.getInstance().isDataValid(mPrefer.getDeviceMac()))
        {
            // 如果已经连接，并且MAC地址合法，则同步设备时间
            mProgressDialog.setMessage(getString(R.string.msg_get_device_data));
            mProgressDialog.show();
            CommUtil.syncDeviceTime(mPrefer.getDeviceMac());
            mCountDownTimer.start();
        }
    }

    // 解析获取到的数据
    private void decodeReceiveData(final String mac, ArrayList< Byte > list)
    {
        Object object = CommUtil.decodeOldInledcoLight(list, mPrefer.getDevId());
        if (object != null)
        {
            final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (object instanceof LightModel)
            {
                mCountDownTimer.cancel();
                // 设置模型
                mLight.setAuto(true);
                mLight.setmLightModel((LightModel) object);

                // 切换到自动模式界面
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mProgressDialog.dismiss();
                        if (mPrefer.getDevId() == DeviceUtil.LIGHT_ID_STRIP_III) {
                            fragmentTransaction.replace(R.id.lighting_fragment,
                                    AutoModeFragment.newInstance(mPrefer.getDeviceMac(),
                                                                 mPrefer.getDevId(),
                                                                 mLight.getmLightModel())).commit();
                        }
                    }
                });
            }
            else if (object instanceof LightManual)
            {
                mCountDownTimer.cancel();

                // 设置模型为手动模式
                mLight.setAuto(false);
                mLight.setLightManual((LightManual) object);
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mProgressDialog.dismiss();
                        // 根据灯具类型显示不同灯具的手动调光界面
                        if (mPrefer.getDevId() == DeviceUtil.LIGHT_ID_STRIP_III)
                        {
                            // 显示圆盘手动调光界面
                            fragmentTransaction.replace(R.id.lighting_fragment,
                                                        ManualModeFragment.newInstance(mPrefer.getDeviceMac(),
                                                                                              mPrefer.getDevId(),
                                                                                              mLight.getLightManual())).commit();
                        }
                    }
                });
            }
        }
    }
}