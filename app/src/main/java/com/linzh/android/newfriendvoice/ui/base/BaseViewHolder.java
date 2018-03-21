package com.linzh.android.newfriendvoice.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by linzh on 2018/3/20.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    private int mCurrentPosition;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    protected abstract void clear();

    public void onBind(int postion) {
        mCurrentPosition = postion;
        clear();
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }
}
