package com.linzh.android.newfriendvoice.ui.scan;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.linzh.android.newfriendvoice.R;
import com.linzh.android.newfriendvoice.ui.base.BaseActivity;
import com.linzh.android.newfriendvoice.ui.main.fragment.GestureTabFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceScanActivity extends BaseActivity implements DeviceScanMvpView {

    private static final String TAG = "DeviceScanActivity";

    private static final int REQUEST_ENABLE_BT = 1;
    private static final int SCAN_PERIOD = 10000;// Stops scanning after 10 seconds.

    @Inject
    DeviceScanMvpPresenter<DeviceScanMvpView> mPresenter;

    @Inject
    LinearLayoutManager mLinearLayoutManager;

    @Inject
    LeDeviceAdapter mLeDeviceAdapter;

    @BindView(R.id.device_scan_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.le_device_recycler_view)
    RecyclerView mRecyclerView;

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mBluetoothLeScanner;

    private boolean isScanning = false;

    public static Intent getIntent(Context context) {
        return new Intent(context, DeviceScanActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_scan);

        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));
        mPresenter.onAttach(this);

        setUp();
    }

    @Override
    protected void setUp() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("扫描可用蓝牙设备...");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_24dp);
        }

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mLeDeviceAdapter);

        mLeDeviceAdapter.setOnItemClickListener(new LeDeviceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                final BluetoothDevice device = mLeDeviceAdapter.getDevice(position);
                if (device == null) return;
                final Intent intent = new Intent();
                intent.putExtra(GestureTabFragment.EXTRAS_DEVICE_NAME, device.getName());
                intent.putExtra(GestureTabFragment.EXTRAS_DEVICE_ADDRESS, device.getAddress());
                if (isScanning) {
                    mBluetoothLeScanner.stopScan(mLeScanCallback);
                    //mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    isScanning = false;
                }
                Log.d(TAG, "onItemClick: start bluetooth control activity.");

                //设置返回值并结束程序
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        getBluetoothPermission();
    }

    private void getBluetoothPermission() {
        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }

        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();//获取蓝牙扫描器实例
        scanLeDevice(true);
    }

    //扫描蓝牙设备
    private void scanLeDevice(final boolean enable) {
        if (enable) {//使能扫描
            //将Runnable接口对象装入消息队列(message queue)每隔一段时间(SCAN_PERIOD)执行Runnable的run方法
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {//循环周期到，标志位复位，关闭扫描
                    isScanning = false;
                    mBluetoothLeScanner.stopScan(mLeScanCallback);
                    //mBluetoothAdapter.stopLeScan(mLeScanCallback);//此方法API21开始已被弃用
                    invalidateOptionsMenu();
                }
            }, SCAN_PERIOD);

            isScanning = true;
            mBluetoothLeScanner.startScan(mLeScanCallback);
            //mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {//关闭扫描
            isScanning = false;
            mBluetoothLeScanner.stopScan(mLeScanCallback);
            //mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
        invalidateOptionsMenu();
    }

    @Override
    protected void onPause() {
        super.onPause();

        scanLeDevice(false);
        mLeDeviceAdapter.clear();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_CANCELED) {
                    finish();
                }
                break;
            default:
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bluetooth_scan, menu);
        if (!isScanning) {
            menu.findItem(R.id.menu_scan).setVisible(true);
            menu.findItem(R.id.menu_stop).setVisible(false);
            menu.findItem(R.id.menu_refresh).setActionView(null);
        } else {
            menu.findItem(R.id.menu_scan).setVisible(false);
            menu.findItem(R.id.menu_stop).setVisible(true);
            menu.findItem(R.id.menu_refresh).setActionView(R.layout.actionbar_indeterminate_progress);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_scan:
                mLeDeviceAdapter.clear();
                scanLeDevice(true);
                break;
            case R.id.menu_stop:
                scanLeDevice(false);
                break;
        }

        return true;
    }

    private ScanCallback mLeScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
        }
    };
}
