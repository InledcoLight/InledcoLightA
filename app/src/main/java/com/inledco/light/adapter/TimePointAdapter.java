package com.inledco.light.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.inledco.light.R;
import com.inledco.light.bean.TimePoint;
import com.inledco.light.util.MeasureUtil;

import java.sql.Time;
import java.util.zip.Inflater;

/**
 * Created by huangzhengguo on 2018/1/4.
 * 时间点列表适配器
 */

public class TimePointAdapter extends RecyclerView.Adapter {

    // 数据源
    private TimePoint[] mTimePoints;

    public TimePointAdapter(TimePoint[] timePoints) {
        mTimePoints = timePoints;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_time_point, viewGroup, false);
        // 固定item高度
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        layoutParams.height = MeasureUtil.Dp2Px(viewGroup.getContext(), 150);

        view.setLayoutParams(layoutParams);

        return new TimePointViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        TimePointViewHolder timePointViewHolder = (TimePointViewHolder) viewHolder;
        TimePoint timePoint = mTimePoints[i];

        timePointViewHolder.mTimePicker.setIs24HourView(true);
        timePointViewHolder.mTimePicker.setHour(timePoint.getmHour());
        timePointViewHolder.mTimePicker.setMinute(timePoint.getmMinute());
    }

    @Override
    public int getItemCount() {
        // 可以防止数据为空的情况，如果为空，则返回0
        return mTimePoints == null ? 0 : mTimePoints.length;
    }

    private class TimePointViewHolder extends RecyclerView.ViewHolder {

        private TimePicker mTimePicker;
        private CheckBox mCheckBox;

        private TimePointViewHolder(View itemView) {
            super(itemView);

            mTimePicker = (TimePicker) itemView.findViewById(R.id.item_time_point_datePicker);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.item_time_point_check_checkBox);
        }
    }
}
