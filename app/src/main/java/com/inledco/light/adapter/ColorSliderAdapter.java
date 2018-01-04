package com.inledco.light.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.inledco.light.R;
import com.inledco.light.bean.Channel;
import com.inledco.light.util.MeasureUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by huangzhengguo on 2018/1/3.
 * 实现颜色滑动条数据适配器
 */

public class ColorSliderAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<Channel> mChannelsList;

    public ColorSliderAdapter(Context context, ArrayList<Channel> channels) {
        mContext = context;
        mChannelsList = channels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 返回视图，使用item_color_slider
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_color_slider, viewGroup, false);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        layoutParams.height = MeasureUtil.Dp2Px(mContext, 220) / mChannelsList.size();

        view.setLayoutParams(layoutParams);

        return new ColorSliderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ColorSliderItemViewHolder colorSliderItemViewHolder = (ColorSliderItemViewHolder) viewHolder;

        Channel channel = mChannelsList.get(i);

        colorSliderItemViewHolder.mNameTextView.setText(channel.getName());
        colorSliderItemViewHolder.mColorSeekBar.setProgress(channel.getValue());
        colorSliderItemViewHolder.mPercentTextView.setText(String.valueOf(channel.getValue()));
    }

    @Override
    public int getItemCount() {
        return mChannelsList == null ? 0 : mChannelsList.size();
    }

    private class ColorSliderItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mNameTextView;
        private SeekBar mColorSeekBar;
        private TextView mPercentTextView;

        private ColorSliderItemViewHolder(View itemView) {
            super(itemView);

            mNameTextView = (TextView) itemView.findViewById(R.id.item_color_slider_name);
            mColorSeekBar = (SeekBar) itemView.findViewById(R.id.item_color_slider_slider);
            mPercentTextView = (TextView) itemView.findViewById(R.id.item_color_slider_percent);
        }
    }
}
