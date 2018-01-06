package com.inledco.light.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.inledco.blemanager.BleManager;
import com.inledco.blemanager.BleStateListener;
import com.inledco.light.R;
import com.inledco.light.fragment.DeviceFragment;
import com.inledco.light.fragment.NewsFragment;
import com.inledco.light.fragment.UserFragment;
import com.inledco.light.prefer.Setting;

public class MainActivity extends BaseActivity
{
    private Toolbar toolbar;
    private MenuItem menuItemBleSearch;
    private BottomNavigationView main_bottom_navigation;

    //双击back退出标志位
    private boolean mExiting;

    private BleStateListener mBleStateListener;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        initView();
        initEvent();
        initData();
    }

    @Override
    protected void onResume ()
    {
        super.onResume();
    }

    @Override
    protected void onPause ()
    {
        super.onPause();
    }

    @Override
    protected void onDestroy ()
    {
        BleManager.getInstance().unbindService( this );
        BleManager.getInstance().disConnectAll();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data )
    {
        super.onActivityResult( requestCode, resultCode, data );

        if ( requestCode == 1 && resultCode == 1 )
        {
            setFlag();
        }
    }

    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults )
    {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        BleManager.getInstance().getResultForCoarseLocation( requestCode, permissions[0], grantResults[0] );
    }

    @Override
    public boolean onCreateOptionsMenu ( Menu menu )
    {
        getMenuInflater().inflate( R.menu.menu_main, menu );
        menuItemBleSearch = menu.findItem( R.id.menu_search_ble );
        menuItemBleSearch.setOnMenuItemClickListener( new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick ( MenuItem item )
            {
                startScanActivity();
                return false;
            }
        } );
        return true;
    }

    @Override
    protected void initView ()
    {
        main_bottom_navigation = (BottomNavigationView) findViewById( R.id.main_bottom_navigation );

        toolbar = (Toolbar) findViewById( R.id.toolbar );
        toolbar.setTitle( "" );
        setSupportActionBar( toolbar );
    }

    @Override
    protected void initData()
    {
        mBleStateListener = new BleStateListener() {
            @Override
            public void onBluetoothEnabled ()
            {
            }

            @Override
            public void onBluetoothDisabled ()
            {

            }

            @Override
            public void onBluetoothDenied ()
            {
            }

            @Override
            public void onCoarseLocationGranted ()
            {
                startScanActivity();
            }

            @Override
            public void onCoarseLocationDenied ()
            {
                Snackbar.make( main_bottom_navigation, R.string.snackbar_coarselocation_denied, Snackbar.LENGTH_LONG ).show();
            }

            @Override
            public void onBleInitialized ()
            {

            }
        };

        BleManager.getInstance().setBleStateListener( mBleStateListener );
        BleManager.getInstance().bindService( this );

        getSupportFragmentManager().beginTransaction().replace( R.id.main_fl_show, new DeviceFragment() ).commit();
    }

    @Override
    protected void initEvent()
    {
        main_bottom_navigation.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected ( @NonNull MenuItem item )
            {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch ( item.getItemId() )
                {
                    case R.id.menu_btm_device:
                        transaction.replace( R.id.main_fl_show, new DeviceFragment() ).commit();
                        menuItemBleSearch.setVisible( true );
                        break;
                    case R.id.menu_btm_setting:
                        transaction.replace( R.id.main_fl_show, new UserFragment() ).commit();
                        menuItemBleSearch.setVisible( false );
                        break;
                }
                return true;
            }
        } );
    }

    /**
     * back按键关闭app时 弹出确认关闭蓝牙dialog
     * @param keyCode 按键代码
     * @param event 按键事件
     * @return 是否响应按键事件
     */
    @Override
    public boolean onKeyDown ( int keyCode, KeyEvent event )
    {
        if ( keyCode == KeyEvent.KEYCODE_BACK )
        {
            if ( !mExiting )
            {
                mExiting = true;
                new Handler().postDelayed( new Runnable()
                {
                    @Override
                    public void run ()
                    {
                        mExiting = false;
                    }
                }, 1500 );
                Toast.makeText( MainActivity.this, R.string.exit_app_tips, Toast.LENGTH_SHORT ).show();
            }
            else
            {
                //如果退出时不提示 且设置为退出关闭BLE
                if ( Setting.mExitTurnOffBle )
                {
                    BleManager.getInstance().closeBluetooth();
                }
                finish();
            }
        }
        return true;
    }

    private void setFlag ()
    {
        SharedPreferences sp = getSharedPreferences( "device_scan_flag", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit().putBoolean( "flag", true );
        SharedPreferencesCompat.EditorCompat.getInstance().apply( editor );
    }

    private void startScanActivity()
    {
        Intent intent = new Intent( this, ScanActivity.class );
        startActivityForResult( intent, 1 );
    }
}
