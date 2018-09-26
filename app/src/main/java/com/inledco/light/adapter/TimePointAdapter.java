package com.inledco.light.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
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

    public TimePointAdapter(ArrayList<TimePoint>  timePoints) {
        mTimePoints = timePoints;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_time_point, viewGroup, false);
        // 固定item高度
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        layoutParams.height = MeasureUtil.Dp2Px(viewGroup.getContext(), 30);

        view.setLayoutParams(layoutParams);

        final TimePointViewHolder viewHolder = new TimePointViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        TimePointViewHolder timePointViewHolder = (TimePointViewHolder)viewHolder;
        TimePoint timePoint = mTimePoints.get(i);

        timePointViewHolder.mTimePointTv.setText(timePoint.getFormatTimePoint("%02d:%02d"));

        viewHolder.itemView.setTag(i);
    }

    @Override
    public int getItemCount() {
        // 可以防止数据为空的情况，如果为空，则返回0
        return mTimePoints == null ? 0 : mTimePoints.size();
    }

    private class TimePointViewHolder extends RecyclerView.ViewHolder {

        private TextView mTimePointTv;

        private TimePointViewHolder(View itemView) {
            super(itemView);

            mTimePointTv = itemView.findViewById(R.id.item_time_point_time);
        }
    }
}
