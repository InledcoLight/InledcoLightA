package com.inledco.light.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.inledco.blemanager.BleCommunicateListener;
import com.inledco.blemanager.BleManager;
import com.inledco.light.R;
import com.inledco.light.bean.DevicePrefer;
import com.inledco.light.bean.LightDevice;
import com.inledco.light.bean.LightModel;
import com.inledco.light.bean.RunMode;
import com.inledco.light.constant.ConstVal;
import com.inledco.light.fragment.AutoModeFragment;
import com.inledco.light.fragment.DataInvalidFragment;
import com.inledco.light.fragment.ManualModeFragment;
import com.inledco.light.fragment.ManualAutoSwitchFragment;
import com.inledco.light.util.CommUtil;
import com.inledco.light.util.DeviceUtil;
import com.inledco.light.util.PreferenceUtil;

import java.util.ArrayList;


public class LightingActivity extends BaseActivity implements DataInvalidFragment.OnRetryClickListener {
    // 设备参数
    private LightDevice mLightDevice;
    private LightModel mLightModel;
    private Toolbar mLightingToolbar;
    private TextView mTitleTextView;
    private ProgressDialog mProgressDialog;
    private CountDownTimer mCountDownTimer;
    private BleCommunicateListener mCommunicateListener;

    private ManualAutoSwitchFragment mManualAutoSwitchFragment;
    private ManualModeFragment mManualModeFragment;
    private AutoModeFragment mAutoModeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lighting);

        // 接收传递的设备参数
        Intent intent = getIntent();
        mLightDevice = (LightDevice) intent.getSerializableExtra("LightDevice");
        
        initView();
        initEvent();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        BleManager.getInstance().removeBleCommunicateListener(mCommunicateListener);
        mCommunicateListener = null;

        BleManager.getInstance().disConnectAll();
    }

    @Override
    protected void initView() {
        // 设置圆形颜色设置宽高相同
        FrameLayout colorFrameLayout = findViewById(R.id.lighting_fragment);

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)colorFrameLayout.getLayoutParams();

        colorFrameLayout.setLayoutParams(layoutParams);

        // 获取各个控件
        mLightingToolbar = findViewById(R.id.lightingToolbar);
        // 这个不设置的话，重命名和查找菜单显示不出来
        setSupportActionBar(mLightingToolbar);

        mTitleTextView = findViewById(R.id.titleTextView);
        mTitleTextView.setText(mLightDevice.getDevicePrefer().getDeviceName());

        // 初始化提示框
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener(){
            @Override
            public void onCancel(DialogInterface dialog){
                BleManager.getInstance().disconnectDevice(mLightDevice.getDevicePrefer().getDeviceMac());
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
            public void onTick(long millisUntilFinished){

            }

            @Override
            public void onFinish() {
                // 1.隐藏正在连接提示
                // 2.隐藏手动自动模式切换按钮
                // 3.显示连接失败界面
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.lighting_fragment, DataInvalidFragment.newInstance(mLightDevice.getDevicePrefer().getDeviceMac()))
                        .commit();
            }
        };

        mCommunicateListener = new BleCommunicateListener() {
            @Override
            public void onDataValid(final String mac) {
                if (mac.equals(mLightDevice.getDevicePrefer().getDeviceMac())) {
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            mProgressDialog.setMessage(getString(R.string.msg_get_data_success));
                            CommUtil.syncDeviceTime(mac);
                            mCountDownTimer.start();
                        }
                    });
                }
            }

            @Override
            public void onDataInvalid(String mac) {
                if (mac.equals(mLightDevice.getDevicePrefer().getDeviceMac())) {
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            if (mProgressDialog.isShowing()) {
                                mProgressDialog.dismiss();
                            }

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.lighting_fragment, DataInvalidFragment.newInstance(mLightDevice.getDevicePrefer().getDeviceMac()))
                                    .commit();
                        }
                    });
                }
            }

            @Override
            public void onReadMfr(String mac, String s) {

            }

            @Override
            public void onDataReceived(String mac, ArrayList<Byte> list) {
                if (mac.equals(mLightDevice.getDevicePrefer().getDeviceMac())) {
                    decodeReceiveData(mac, list);
                }
            }
        };

        // 开始蓝牙扫描
        BleManager.getInstance().addBleCommunicateListener(mCommunicateListener);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.lighting_fragment, DataInvalidFragment.newInstance(mLightDevice.getDevicePrefer().getDeviceMac()))
                .commit();

        getDeviceData();
    }

    @Override
    public void onRetryClick()
    {
        getDeviceData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_device, menu);
        MenuItem menu_device_edit = menu.findItem(R.id.menu_device_edit);
        MenuItem menu_device_find = menu.findItem(R.id.menu_device_find);

        menu_device_edit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick (MenuItem menuItem)
            {
                showRenameDialog(mLightDevice.getDevicePrefer());
                return false;
            }
        });

        menu_device_find.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick (MenuItem menuItem)
            {
                CommUtil.findDevice(mLightModel.getMacAddress());

                return false;
            }
        });

        return true;
    }

    private void showRenameDialog (final DevicePrefer prefer)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setTitle(R.string.rename_device);

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_rename, null);
        Button btn_cancel = (Button)view.findViewById(R.id.rename_cancel);
        Button btn_rename = (Button)view.findViewById(R.id.rename_confirm);

        final EditText newname = (EditText)view.findViewById(R.id.rename_newname);
        newname.setText(prefer.getDeviceName());
        btn_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view)
            {
                dialog.dismiss();
            }
        });

        btn_rename.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view)
            {
                if (TextUtils.isEmpty(newname.getText().toString()))
                {
                    newname.setError(getString(R.string.error_input_empty));
                }
                else if (newname.getText().toString().equals(prefer.getDeviceName()))
                {
                    dialog.dismiss();
                }
                else
                {
                    prefer.setDeviceName(newname.getText().toString());
                    BleManager.getInstance().setSlaverName(prefer.getDeviceMac(), prefer.getDeviceName());
                    PreferenceUtil.setObjectToPrefer(LightingActivity.this, ConstVal.DEV_PREFER_FILENAME, prefer, prefer.getDeviceMac());
                    dialog.dismiss();
                }
            }
        });

        dialog.setView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    // 连接设备获取设备数据
    private void getDeviceData() {
        // 连接设备
        if (!BleManager.getInstance().isConnected(mLightDevice.getDevicePrefer().getDeviceMac())) {
            // 没有连接则连接设备
            mProgressDialog.setMessage(getString(R.string.msg_connecting_device));
            mProgressDialog.show();

            BleManager.getInstance().connectDevice(mLightDevice.getDevicePrefer().getDeviceMac());
        } else if (BleManager.getInstance().isDataValid(mLightDevice.getDevicePrefer().getDeviceMac())) {
            // 如果已经连接，并且MAC地址合法，则同步设备时间
            mProgressDialog.setMessage(getString(R.string.msg_get_device_data));
            mProgressDialog.show();

            CommUtil.syncDeviceTime(mLightDevice.getDevicePrefer().getDeviceMac());

            mCountDownTimer.start();
        }
    }

    /**
     * 解析设备数据
     * @param mac 设备MAC地址
     * @param list 数据
     */
    private void decodeReceiveData(final String mac, ArrayList<Byte> list) {
        mLightModel = new LightModel();

        mLightModel.setMacAddress(mac);
        mLightModel.setLightId(mLightDevice.getLightId());
        mLightModel.setDeviceId(mLightDevice.getDevicePrefer().getDevId());
        mLightModel.setChannelNum(mLightDevice.getLightChannelNum());
        mLightModel.setControllerNum(DeviceUtil.getChannelCount(mLightModel.getDeviceId()));
        if (!CommUtil.decodeLightModel(list, mLightModel)) {
            return;
        }

        if (mLightModel == null) {
            return;
        }

        if (mLightModel != null) {
            final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            mCountDownTimer.cancel();

            // 设置手动自动模式切换按钮
            if (mManualAutoSwitchFragment == null) {
                mManualAutoSwitchFragment = ManualAutoSwitchFragment.newInstance(mLightDevice.getDevicePrefer().getDeviceMac(),
                        mLightDevice.getDevicePrefer().getDevId(), true);

                mManualAutoSwitchFragment.mManualAutoSwitchInterface = new ManualAutoSwitchFragment.ManualAutoSwitchInterface() {
                    @Override
                    public void setManualMode() {
                        BleManager.getInstance().addBleCommunicateListener(mCommunicateListener);
                        CommUtil.setManual(mLightModel.getMacAddress());
                    }

                    @Override
                    public void setAutoMode() {
                        BleManager.getInstance().addBleCommunicateListener(mCommunicateListener);
                        CommUtil.setAuto(mLightModel.getMacAddress());
                    }
                };
            }

            if (mLightModel.getRunMode() == RunMode.MANUAL_MODE) {
                // 手动模式
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressDialog.dismiss();
                        BleManager.getInstance().removeBleCommunicateListener(mCommunicateListener);

                        mManualAutoSwitchFragment.setManualMode(true);
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.manual_auto_fragment, mManualAutoSwitchFragment)
                                .commit();

                        if (mManualModeFragment == null) {
                            mManualModeFragment = ManualModeFragment.newInstance(mLightDevice.getDevicePrefer().getDeviceMac(),
                                    mLightDevice.getDevicePrefer().getDevId(),
                                    mLightModel);
                        }
                        // 显示圆盘手动调光界面
                        fragmentTransaction.replace(R.id.lighting_fragment, mManualModeFragment).commit();
                    }
                });
            } else if (mLightModel.getRunMode() == RunMode.AUTO_MODE) {
                mProgressDialog.dismiss();
                BleManager.getInstance().removeBleCommunicateListener(mCommunicateListener);

                mManualAutoSwitchFragment.setManualMode(false);
                // 自动模式
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.manual_auto_fragment, mManualAutoSwitchFragment)
                        .commit();

                if (mAutoModeFragment == null) {
                    mAutoModeFragment = AutoModeFragment.newInstance(mLightDevice.getDevicePrefer().getDeviceMac(),
                            mLightDevice.getDevicePrefer().getDevId(),
                            mLightModel);
                }
                fragmentTransaction.replace(R.id.lighting_fragment,mAutoModeFragment).commit();
            }
        }
    }
}