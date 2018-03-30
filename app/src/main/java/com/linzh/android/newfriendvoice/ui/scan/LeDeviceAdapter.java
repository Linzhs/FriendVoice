package com.linzh.android.newfriendvoice.ui.scan;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linzh.android.newfriendvoice.R;
import com.linzh.android.newfriendvoice.service.BluetoothLeService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LeDeviceAdapter extends RecyclerView.Adapter<LeDeviceAdapter.ViewHolder> {

    private List<BluetoothDevice> mDeviceList;

    private OnItemClickListener mOnItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder {

        View mView;

        @BindView(R.id.le_device_address)
        TextView mLeDeviceAddress;

        @BindView(R.id.le_device_name)
        TextView mLeDeviceName;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public LeDeviceAdapter(List<BluetoothDevice> deviceList) {
        mDeviceList = deviceList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        BluetoothDevice device = mDeviceList.get(position);
        holder.mLeDeviceAddress.setText(device.getAddress());

        final String deviceName = mDeviceList.get(position).getName();
        if (!TextUtils.isEmpty(deviceName)) {
            holder.mLeDeviceName.setText(deviceName);
        } else {
            holder.mLeDeviceName.setText(R.string.unknown_device);
        }

        if (mOnItemClickListener != null) {
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mDeviceList != null && mDeviceList.size() > 0)
            return mDeviceList.size();
        return 1;
    }

    public void addDevice(BluetoothDevice device) {
        if (!mDeviceList.contains(device)) {
            mDeviceList.add(device);
        }
    }

    public BluetoothDevice getDevice(int position) {
        return mDeviceList.get(position);
    }

    public void clear() {
        mDeviceList.clear();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
