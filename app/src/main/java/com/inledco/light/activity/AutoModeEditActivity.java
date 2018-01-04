package com.inledco.light.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.inledco.light.R;
import com.inledco.light.adapter.ColorSliderAdapter;
import com.inledco.light.adapter.TimePointAdapter;
import com.inledco.light.bean.Channel;
import com.inledco.light.bean.LightModel;
import com.inledco.light.util.CommUtil;
import com.inledco.light.util.DeviceUtil;

import java.util.ArrayList;

public class AutoModeEditActivity extends BaseActivity {

    // 滑动条
    private RecyclerView mColorSliderRecyclerView;

    // 时间点
    private RecyclerView mTimePointRecyclerView;

    // 增加  删除  保存  取消
    private Button mAddButton;
    private Button mDeleteButton;
    private Button mSaveButton;
    private Button mCancelButton;

    // 自动模型
    private LightModel mLightModel;

    // 滑动条适配器
    private ColorSliderAdapter mColorSliderAdapter;
    private Channel[] mChannels;

    // 时间点适配器
    private TimePointAdapter mTimePointAdapter;

    // 当前选择的时间点索引
    private int mTimePointIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_auto_mode_edit);

        initView();
        initData();
        initEvent();
    }

    @Override
    protected void initView() {
        mColorSliderRecyclerView = (RecyclerView) findViewById(R.id.auto_mode_edit_color_slider_recyclerView);
        mTimePointRecyclerView = (RecyclerView) findViewById(R.id.auto_mode_edit_time_point_recyclerView);
        mAddButton = (Button) findViewById(R.id.auto_mode_edit_add_button);
        mDeleteButton = (Button) findViewById(R.id.auto_mode_edit_delete_button);
        mSaveButton = (Button) findViewById(R.id.auto_mode_edit_save_button);
        mCancelButton = (Button) findViewById(R.id.auto_mode_edit_cancel_button);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                Bundle bundle1 = new Bundle();

                bundle1.putSerializable("1", mLightModel);

                intent.putExtras(bundle1);

                setResult(10, intent);

                finish();
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 提示是否放弃即可
                finish();
            }
        });

        // 滑动条线性布局管理器
        LinearLayoutManager colorLayoutManager = new LinearLayoutManager(this);

        colorLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mColorSliderRecyclerView.setLayoutManager(colorLayoutManager);

        // 时间点线性布局管理器
        LinearLayoutManager timePointLayoutManager = new LinearLayoutManager(this);
        mTimePointRecyclerView.setLayoutManager(timePointLayoutManager);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        // 接收传递的模型
        Bundle bundle = getIntent().getExtras();

        mLightModel = (LightModel) bundle.getSerializable("LIGHT_AUTO_MODE_MODEL");

        // 初始化滑动条列表适配器
        mChannels = getChannels(0);

        mColorSliderAdapter = new ColorSliderAdapter(this, mChannels);

        mColorSliderAdapter.setColorSliderInterface(new ColorSliderAdapter.ColorSliderInterface() {
            @Override
            public void seekBarProgressChanged(int position, int progress) {
                // position:颜色索引值
                // progress:颜色值
                // mTimePointIndex:当前时间点的值
                mLightModel.setTimePointColorValue(mTimePointIndex, position, progress);
            }
        });
        mColorSliderRecyclerView.setAdapter(mColorSliderAdapter);
        mColorSliderAdapter.notifyDataSetChanged();

        // 时间点列表
        mTimePointAdapter = new TimePointAdapter(mLightModel.getTimePoints());

        mTimePointAdapter.setmTimePointInterface(new TimePointAdapter.TimePointInterface() {
            @Override
            public void checkBoxChanged(int position) {
                mTimePointIndex = position;
                mChannels = getChannels(position);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mColorSliderAdapter.notifyDataSetChanged();
                    }
                });

            }
        });

        mTimePointRecyclerView.setAdapter(mTimePointAdapter);
        mTimePointAdapter.notifyDataSetChanged();
    }

    public Channel[] getChannels(int timePointIndex) {
        if (mLightModel == null) {
            return null;
        }

        if (timePointIndex > mLightModel.getTimePointColorValue().keySet().size() - 1) {
            return null;
        }

        Channel[] channels = DeviceUtil.getLightChannel(this, mLightModel.getDeviceId());
        if (mChannels == null) {
            // 这样初始化后，数组中的所有对象都指向空
            mChannels = new Channel[channels.length];
        }
        // 获取第timePointIndex个时间点的颜色值
        byte[] colors = mLightModel.getTimePointColorValue().get((short)timePointIndex);
        for (int i=0;i<mChannels.length;i++) {
            Channel channel = new Channel(channels[i].getName(), channels[i].getColor(), colors[i]);

            mChannels[i] = channel;
        }

        return mChannels;
    }
}




































