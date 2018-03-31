package com.linzh.android.newfriendvoice.ui.main.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothClass;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.linzh.android.newfriendvoice.R;
import com.linzh.android.newfriendvoice.di.component.ActivityComponent;
import com.linzh.android.newfriendvoice.service.BluetoothLeService;
import com.linzh.android.newfriendvoice.ui.base.BaseFragment;
import com.linzh.android.newfriendvoice.ui.debug.WordAdapter;
import com.linzh.android.newfriendvoice.ui.main.MainActivity;
import com.linzh.android.newfriendvoice.ui.scan.DeviceScanActivity;
import com.linzh.android.newfriendvoice.utils.AppLogger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by linzh on 2018/3/22.
 */

public class GestureTabFragment extends BaseFragment implements GestureMvpView {

    private static final String TAG = "GestureTabFragment";

    private static final int REQUEST_CONNECT_DEVICE = 1;//查询设备句柄

    private static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    private static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";

    @Inject
    GestureMvpPresenter<GestureMvpView> mPresenter;

    @Inject
    LinearLayoutManager mLinearLayoutManager;

    @Inject
    MsgAdapter mMsgAdapter;

    @BindView(R.id.le_device_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.float_button_ble_connect)
    FloatingActionButton mFloatingActionButton;

    private Context mContext;

    private BluetoothLeService mBluetoothLeService;
    private boolean mConnected = false;

    public static Intent getIntentWithParams(String deviceName, String deviceAddress) {
        Intent intent = new Intent();
        intent.putExtra(EXTRAS_DEVICE_NAME, deviceName);
        intent.putExtra(EXTRAS_DEVICE_ADDRESS, deviceAddress);
        return intent;
    }

    public static GestureTabFragment newInstance() {
        Bundle bundle = new Bundle();
        GestureTabFragment fragment = new GestureTabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    // Code to manage Service lifecycle.
    // 管理BLE数据收发服务整个生命周期
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                AppLogger.e(TAG, "onServiceConnected: Unable to initialize Bluetooth");
                //finish();
                Toast.makeText(mContext, "蓝牙识别失败", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBluetoothLeService = null;
        }
    };

    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    // 定义处理BLE收发服务的各类事件接收机mGattUpdateReceiver，主要包括下面几种：
    // ACTION_GATT_CONNECTED: 连接到GATT
    // ACTION_GATT_DISCONNECTED: 断开GATT
    // ACTION_GATT_SERVICES_DISCOVERED: 发现GATT下的服务
    // ACTION_DATA_AVAILABLE: BLE收到数据
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                //clearUI();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                // 获得所有的GATT服务，对于BLE透传模块，包括GAP（General Access Profile），
                // GATT（General Attribute Profile），还有Unknown（用于数据读取）
                mBluetoothLeService.getSupportedGattServices();
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                String msg = mPresenter.showTime(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
                mMsgAdapter.addItem(msg);
                mRecyclerView.scrollToPosition(mMsgAdapter.getItemCount() - 1);
            }
        }
    };

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gesture, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnbinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        return view;
    }

    @Override
    protected void setUp(View view) {
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMsgAdapter.addItem("手语语音");
        mRecyclerView.setAdapter(mMsgAdapter);

        getActivity().bindService(BluetoothLeService.getIntent(getActivity()),
                mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getContext();
    }

    @Override
    public void onResume() {
        super.onResume();

        //注册BLE收发服务广播接收器mGattUpdateReceiver
        getActivity().registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            Timber.d("onResume: mBluetoothLeService NOT null");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    String deviceName = data.getExtras().getString(EXTRAS_DEVICE_NAME);
                    String deviceAddress = data.getExtras().getString(EXTRAS_DEVICE_ADDRESS);

                    Timber.i("onActivityResult: " + "mDeviceName: " + deviceName + ", mDeviceAddress:" + deviceAddress);

                    //连接该BLE模块
                    if (mBluetoothLeService != null) {
                        final boolean result = mBluetoothLeService.connect(deviceAddress);
                        AppLogger.d("onActivityResult: Connect request result = " + result);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void openDeviceScanActivity() {
        startActivityForResult(DeviceScanActivity.getIntent(getActivity()), REQUEST_CONNECT_DEVICE);

    }

    @OnClick(R.id.float_button_ble_connect)
    void onFloatBleConnectButtonClick() {
        if (mConnected) {
            mFloatingActionButton.setImageResource(R.drawable.ic_bluetooth_connected_white_24dp);
            mBluetoothLeService.disconnect();
        } else {
            mFloatingActionButton.setImageResource(R.drawable.ic_bluetooth_disabled_white_24dp);
            openDeviceScanActivity();
        }
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解绑BLE收发服务
        getActivity().unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }
}
