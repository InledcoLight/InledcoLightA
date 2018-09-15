package com.inledco.light.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.inledco.light.R;
import com.inledco.light.bean.BaseDevice;
import com.inledco.light.bean.LightDevice;
import com.inledco.light.bean.ListItem;
import com.inledco.light.impl.SwipeItemActionClickListener;
import com.inledco.light.util.DeviceUtil;
import com.inledco.itemtouchhelperextension.SwipeItemViewHolder;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context mContext;
    private List<ListItem> mDevices;
    private SwipeItemActionClickListener mSwipeItemActionClickListener;

    public DeviceAdapter (Context context, List<ListItem> devices) {
        mContext = context;
        mDevices = devices;
    }

    public void setSwipeItemActionClickListener(SwipeItemActionClickListener listener) {
        mSwipeItemActionClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        if (viewType == ListItem.TYPE_HEADER) {
            return new DeviceHeaderViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_header, parent,false));
        } else {
            return new DeviceViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_device, parent,false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        ListItem item = mDevices.get(position);

        return item.getType();
    }

    @Override
    public void onBindViewHolder (final RecyclerView.ViewHolder holder, int position) {
        ListItem itemModel = mDevices.get(holder.getAdapterPosition());
        if (itemModel.getType() == ListItem.TYPE_HEADER) {
            DeviceHeaderViewHolder deviceHeaderViewHolder = (DeviceHeaderViewHolder)holder;
            LightDevice lightDevice = (LightDevice)itemModel;

            deviceHeaderViewHolder.controllerNameTextView.setText("");
        } else if (itemModel.getType() == ListItem.TYPE_LIGHT) {
            DeviceViewHolder deviceViewHolder = (DeviceViewHolder)holder;
            BaseDevice device = (BaseDevice)itemModel;

            deviceViewHolder.iv_icon.setImageResource(DeviceUtil.getDeviceIcon(device.getDevicePrefer().getDevId()));
            deviceViewHolder.tv_name.setText(device.getDevicePrefer().getDeviceName());
            deviceViewHolder.tv_tank.setText(DeviceUtil.getDeviceType(device.getDevicePrefer().getDevId()));

            deviceViewHolder.item_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    if (mSwipeItemActionClickListener != null) {
                        mSwipeItemActionClickListener.onClickContent(holder.getAdapterPosition());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount () {
        return mDevices == null ? 0 : mDevices.size();
    }

    public class DeviceHeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView controllerNameTextView;

        public DeviceHeaderViewHolder(View itemView) {
            super(itemView);

            controllerNameTextView = (TextView)itemView.findViewById(R.id.item_list_header_name);
        }
    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_icon;
        private TextView tv_name;
        private TextView tv_tank;
        private View item_content;

        public DeviceViewHolder (View itemView) {
            super(itemView);
            iv_icon = (ImageView) itemView.findViewById(R.id.item_device_icon);
            tv_name = (TextView) itemView.findViewById(R.id.item_device_name);
            tv_tank = (TextView) itemView.findViewById(R.id.item_device_tank);
            item_content = itemView.findViewById(R.id.item_content);
        }
    }
}
