package com.inledco.light.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private ArrayList<Channel> mChannelsList;

    // 时间点适配器
    private TimePointAdapter mTimePointAdapter;

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

        // 初始化滑动条适配器
        Channel[] channels = DeviceUtil.getLightChannel(this, mLightModel.getmDeviceId());
        // 获取第一个时间点的颜色值
        byte[] colors = mLightModel.getTimePointColorValue().get((short)0);

        mChannelsList = new ArrayList<>();
        for (int i=0;i<channels.length;i++) {
            channels[i].setValue(colors[i]);
            mChannelsList.add(channels[i]);
        }

        mColorSliderAdapter = new ColorSliderAdapter(this, mChannelsList);

        mColorSliderRecyclerView.setAdapter(mColorSliderAdapter);
        mColorSliderAdapter.notifyDataSetChanged();

        // 时间点列表
        mTimePointAdapter = new TimePointAdapter(mLightModel.getTimePoints());

        mTimePointRecyclerView.setAdapter(mTimePointAdapter);
        mTimePointAdapter.notifyDataSetChanged();
    }
}




































