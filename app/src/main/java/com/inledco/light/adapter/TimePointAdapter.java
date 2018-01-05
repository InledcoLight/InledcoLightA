package com.inledco.light.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TimePicker;

import com.inledco.light.R;
import com.inledco.light.bean.TimePoint;
import com.inledco.light.util.MeasureUtil;

import java.util.ArrayList;

/**
 * Created by huangzhengguo on 2018/1/4.
 * 时间点列表适配器
 */

public class TimePointAdapter extends RecyclerView.Adapter {

    // 数据源
    private ArrayList<TimePoint> mTimePoints;
    // 时间段索引
    private int mLastTimePointIndex = 0;
    private int mTimePointIndex = 0;
    // 代理
    private TimePointInterface mTimePointInterface = null;

    public TimePointAdapter(ArrayList<TimePoint> timePoints) {
        mTimePoints = timePoints;
    }

    public void setTimePointInterface(TimePointInterface timePointInterface) {
        this.mTimePointInterface = timePointInterface;
    }

    public void setTimePointIndex(int timePointIndex) {
        this.mTimePointIndex = timePointIndex;
        this.mLastTimePointIndex = timePointIndex;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_time_point, viewGroup, false);
        // 固定item高度
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        layoutParams.height = MeasureUtil.Dp2Px(viewGroup.getContext(), 150);

        view.setLayoutParams(layoutParams);

        final TimePointViewHolder viewHolder = new TimePointViewHolder(view);

        viewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                View parentView = (View) buttonView.getParent();

                int position = (int) parentView.getTag();
                if (isChecked) {
                    mTimePointIndex = position;
                    if (mTimePointIndex != mLastTimePointIndex) {
                        notifyItemChanged(mLastTimePointIndex);
                        mLastTimePointIndex = position;
                        notifyItemChanged(mTimePointIndex);

                        if (mTimePointInterface != null) {
                            mTimePointInterface.checkBoxChanged(position);
                        }
                    }
                }
            }
        });

        viewHolder.mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                View parentView = (View) view.getParent();

                int position = (int) parentView.getTag();

                if (mTimePointInterface != null) {
                    mTimePointInterface.datePickerValueChanged(position, hourOfDay, minute);
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        TimePointViewHolder timePointViewHolder = (TimePointViewHolder) viewHolder;
        TimePoint timePoint = mTimePoints.get(i);

        viewHolder.itemView.setTag(i);

        timePointViewHolder.mTimePicker.setIs24HourView(true);
        timePointViewHolder.mTimePicker.setHour(timePoint.getmHour());
        timePointViewHolder.mTimePicker.setMinute(timePoint.getmMinute());

        if (i == mTimePointIndex) {
            timePointViewHolder.mTimePicker.setEnabled(true);
            timePointViewHolder.mCheckBox.setChecked(true);
        } else {
            timePointViewHolder.mTimePicker.setEnabled(false);
            timePointViewHolder.mCheckBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        // 可以防止数据为空的情况，如果为空，则返回0
        return mTimePoints == null ? 0 : mTimePoints.size();
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

    public interface TimePointInterface {
        void checkBoxChanged(int position);
        void datePickerValueChanged(int position, int hourOfDay, int minute);
    }
}
