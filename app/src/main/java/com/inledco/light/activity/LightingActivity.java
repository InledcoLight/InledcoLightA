package com.inledco.light.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.inledco.blemanager.BleCommunicateListener;
import com.inledco.blemanager.BleManager;
import com.inledco.light.R;
import com.inledco.light.bean.DevicePrefer;
import com.inledco.light.fragment.DataInvalidFragment;
import com.inledco.light.fragment.ManualAutoSwitchFragment;

import java.util.ArrayList;


public class LightingActivity extends BaseActivity implements DataInvalidFragment.OnRetryClickListener{

    // 设备参数
    private DevicePrefer mPrefer;
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
        // 设置手动自动模式切换按钮
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.manual_auto_fragment, new ManualAutoSwitchFragment())
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
            public void onDataValid(String mac) {
                // 数据获取成功回调
                if (mac.equals(mPrefer.getDeviceMac())){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgressDialog.setMessage(getString(R.string.msg_get_data_success));
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
                                    .replace(R.id.lighting_fragment, DataInvalidFragment.newInstance( mPrefer.getDeviceMac() ))
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
                if (mac.equals(mPrefer.getDeviceMac())){

                }
            }
        };

        // 开始蓝牙扫描
        BleManager.getInstance().addBleCommunicateListener(mCommunicateListener);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.lighting_fragment, DataInvalidFragment.newInstance( mPrefer.getDeviceMac() ))
                .commit();
    }

    @Override
    public void onRetryClick() {

    }
}
