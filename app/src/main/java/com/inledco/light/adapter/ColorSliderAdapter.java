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
import com.inledco.light.constant.ConstVal;
import com.inledco.light.util.MeasureUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by huangzhengguo on 2018/1/3.
 * 实现颜色滑动条数据适配器
 */

public class ColorSliderAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private Channel[] mChannels;
    private int[] thumbs;
    private float mItemHeight = 30;
    // 滑动条代理
    private ColorSliderInterface mColorSliderInterface;

    public ColorSliderAdapter(Context context, Channel[] channels, int[] thumbs) {
        mContext = context;
        mChannels = channels;
        this.thumbs = thumbs;
    }

    public void setColorSliderInterface(ColorSliderInterface colorSliderInterface) {
        this.mColorSliderInterface = colorSliderInterface;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 返回视图，使用item_color_slider
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_color_slider, viewGroup, false);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        layoutParams.height = MeasureUtil.Dp2Px(mContext, mItemHeight);

        view.setLayoutParams(layoutParams);

        final ColorSliderItemViewHolder colorSliderItemViewHolder = new ColorSliderItemViewHolder(view);

        colorSliderItemViewHolder.mColorSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                colorSliderItemViewHolder.mPercentTextView.setText(String.format("%s%%", progress / 10));

                if (!fromUser) {
                    return;
                }

                View parentView = (View) seekBar.getParent();

                int position = (int) parentView.getTag();

                // 更改数据到模型中
                if (mColorSliderInterface != null) {
                    mColorSliderInterface.seekBarProgressChanged(position, progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return colorSliderItemViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ColorSliderItemViewHolder colorSliderItemViewHolder = (ColorSliderItemViewHolder) viewHolder;
        Channel channel = mChannels[i];

        colorSliderItemViewHolder.itemView.setTag(i);

        // colorSliderItemViewHolder.mNameTextView.setText(channel.getName());
        colorSliderItemViewHolder.mColorSeekBar.setMax((int) ConstVal.MAX_COLOR_VALUE);
        colorSliderItemViewHolder.mColorSeekBar.setProgress(channel.getValue() * (int) ConstVal.MAX_COLOR_VALUE / 100);
        colorSliderItemViewHolder.mColorSeekBar.setThumb(mContext.getResources().getDrawable(thumbs[i]));
        colorSliderItemViewHolder.mPercentTextView.setText(String.format("%s%%", channel.getValue()));
    }

    @Override
    public int getItemCount() {
        return mChannels == null ? 0 : mChannels.length;
    }

    public void setItemHeight(float itemHeight) {
        mItemHeight = itemHeight;
    }

    public void setChannels(Channel[] channels) {
        mChannels = channels;
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

    public interface ColorSliderInterface {
        void seekBarProgressChanged(int position, int progress);
    }
}
