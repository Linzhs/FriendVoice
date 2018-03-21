package com.linzh.android.newfriendvoice.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linzh.android.newfriendvoice.R;
import com.linzh.android.newfriendvoice.di.component.ActivityComponent;
import com.linzh.android.newfriendvoice.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by linzh on 2018/3/22.
 */

public class GestureTabFragment extends BaseFragment implements GestureMvpView {

    private static final String TAG = "GestureTabFragment";

    @Inject
    GestureMvpPresenter<GestureMvpView> mPresenter;

    @BindView(R.id.le_device_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.float_button_ble_connect)
    FloatingActionButton mFloatingActionButton;

    public static GestureTabFragment newInstance() {
        Bundle bundle = new Bundle();
        GestureTabFragment fragment = new GestureTabFragment();
        fragment.setArguments(bundle);
        return fragment;
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

    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
