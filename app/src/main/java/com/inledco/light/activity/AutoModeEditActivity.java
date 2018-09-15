package com.inledco.light.activity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.inledco.light.R;
import com.inledco.light.adapter.ColorSliderAdapter;
import com.inledco.light.adapter.TimePointAdapter;
import com.inledco.light.bean.Channel;
import com.inledco.light.bean.LightModel;
import com.inledco.light.bean.TimePoint;
import com.inledco.light.util.CommUtil;
import com.inledco.light.util.DeviceUtil;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 增加一个时间点
                TimePickerDialog timePickerDialog = new TimePickerDialog(AutoModeEditActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // 添加到当前模型中
                                int currentTimeCount = hourOfDay * 60 + minute;
                                int insertIndex = 0;
                                for (TimePoint timePoint: mLightModel.getTimePoints()) {
                                    if (currentTimeCount < timePoint.getmHour() * 60 + timePoint.getmMinute()) {
                                        break;
                                    }

                                    insertIndex ++;
                                }

                                // 添加时间点
                                TimePoint insertTimePoint = new TimePoint((byte) hourOfDay, (byte) minute);
                                mLightModel.getTimePoints().add(insertIndex, insertTimePoint);

                                // 添加时间点对应的颜色值
                                for (int i=mLightModel.getTimePointColorValue().size()-1;i>=0;i--) {
                                    mLightModel.getTimePointColorValue().add(mLightModel.getTimePointColorValue().get((short)i));
                                    if (i == insertIndex) {
                                        byte[] insertValues = new byte[DeviceUtil.getChannelCount(mLightModel.getDeviceId())];

                                        for (int j=0;j<insertValues.length;j++) {
                                            insertValues[j] = 0;
                                        }

                                        mLightModel.getTimePointColorValue().add(insertValues);
                                        break;
                                    }
                                }

                                mTimePointIndex = insertIndex;

                                // 更新视图
                                updateRecyclerView();
                            }
                        },
                        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                        Calendar.getInstance().get(Calendar.MINUTE),
                        true);

                timePickerDialog.show();
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 删除时间点
                @SuppressLint("DefaultLocale") String s = getResources().getString(R.string.confirm_delete) +
                        String.format("%02d", mLightModel.getTimePoints().get(mTimePointIndex).getmHour()) + ":" +
                        String.format("%02d", mLightModel.getTimePoints().get(mTimePointIndex).getmMinute()) + " ?";
                new AlertDialog.Builder(AutoModeEditActivity.this)
                        .setTitle(null)
                        .setMessage(s)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 删除操作
                                ArrayList<TimePoint> lightModels = mLightModel.getTimePoints();

                                // 删除时间点
                                lightModels.remove(mTimePointIndex);

                                // 删除时间点对应的颜色值
                                for (int i=mTimePointIndex;i<mLightModel.getTimePointColorValue().size()-1; i++) {
                                    mLightModel.getTimePointColorValue().add(mLightModel.getTimePointColorValue().get((short)(i+1)));
                                }

                                mTimePointIndex = 0;
                                // 更新视图
                                updateRecyclerView();
                            }
                        })
                        .setNegativeButton(R.string.cancel,null)
                        .show();
            }
        });

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
                new AlertDialog.Builder(AutoModeEditActivity.this)
                        .setTitle(null)
                        .setMessage(R.string.give_up_save)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 放弃的话直接返回即可
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.cancel,null)
                        .show();
            }
        });

    }

    @Override
    protected void initData() {
        // 接收传递的模型
        Bundle bundle = getIntent().getExtras();

        mLightModel = (LightModel) bundle.getSerializable("LIGHT_AUTO_MODE_MODEL");

        // 初始化滑动条列表适配器
        mChannels = getChannels(0);

        int[] thumbs = DeviceUtil.getThumb(mLightModel.getDeviceId());
        mColorSliderAdapter = new ColorSliderAdapter(this, mChannels, thumbs);

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

        // 时间点列表
        mTimePointAdapter = new TimePointAdapter(mLightModel.getTimePoints());

        mTimePointAdapter.setTimePointInterface(new TimePointAdapter.TimePointInterface() {
            @Override
            public void checkBoxChanged(int position) {
                mTimePointIndex = position;
                mChannels = getChannels(position);

                mColorSliderAdapter.notifyDataSetChanged();
            }

            @Override
            public void datePickerValueChanged(int position, int hourOfDay, int minute) {
                // 更改时间点设置
                TimePoint timePoint = new TimePoint((byte)hourOfDay, (byte)minute);

                mLightModel.getTimePoints().set(position, timePoint);
            }
        });

        mTimePointRecyclerView.setAdapter(mTimePointAdapter);
    }

    /**
     *
     * @param timePointIndex 时间点索引
     * @return 时间点对应的通道数据
     */
    private Channel[] getChannels(int timePointIndex) {
        if (mLightModel == null) {
            return null;
        }

        if (timePointIndex > mLightModel.getTimePointColorValue().size() - 1) {
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

    private void updateRecyclerView() {
        mChannels = getChannels(mTimePointIndex);
        mTimePointAdapter.setTimePointIndex(mTimePointIndex);
        mColorSliderAdapter.notifyDataSetChanged();
        mTimePointAdapter.notifyDataSetChanged();
    }
}




































