package com.inledco.light.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.inledco.blemanager.BleManager;
import com.inledco.blemanager.BleStateListener;
import com.inledco.light.R;
import com.inledco.light.prefer.Setting;

public class LaunchActivity extends BaseActivity
{
    private BleStateListener mBleStateListener;
    private CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        initView();
        initEvent();
        initData();
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        BleManager.getInstance().getResultForBluetoothEnable(requestCode, resultCode);
    }

    @Override
    protected void initView ()
    {

    }

    @Override
    protected void initEvent ()
    {

    }

    @Override
    protected void initData ()
    {
        Setting.initSetting(this);
        mBleStateListener = new BleStateListener() {
            @Override
            public void onBluetoothEnabled ()
            {
                mCountDownTimer.start();
            }

            @Override
            public void onBluetoothDisabled ()
            {

            }

            @Override
            public void onBluetoothDenied ()
            {
                Toast.makeText(LaunchActivity.this, R.string.snackbar_bluetooth_denied, Toast.LENGTH_LONG)
                     .show();
                mCountDownTimer.start();
            }

            @Override
            public void onCoarseLocationGranted ()
            {

            }

            @Override
            public void onCoarseLocationDenied ()
            {

            }

            @Override
            public void onBleInitialized ()
            {

            }
        };
        BleManager.getInstance().setBleStateListener(mBleStateListener);
        mCountDownTimer = new CountDownTimer(1500, 1500) {
            @Override
            public void onTick (long millisUntilFinished)
            {

            }

            @Override
            public void onFinish ()
            {
                startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                finish();
            }
        };
        if (BleManager.getInstance().checkBleSupported(this))
        {
            if (BleManager.getInstance().isBluetoothEnabled() || (Setting.mBleEnabled && BleManager.getInstance().autoOpenBluetooth()))
            {
                mCountDownTimer.start();
            }
            else
            {
                BleManager.getInstance().requestBluetoothEnable(this);
            }
        }
        else
        {
            Toast.makeText(this, R.string.ble_no_support, Toast.LENGTH_SHORT)
                 .show();
            finish();
            return;
        }
    }
}
