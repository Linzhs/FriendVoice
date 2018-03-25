package com.linzh.android.newfriendvoice.ui.main.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linzh.android.newfriendvoice.R;
import com.linzh.android.newfriendvoice.ui.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by linzh on 2018/3/25.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private List<String> mList;

    static class ViewHolder extends BaseViewHolder {

        @BindView(R.id.msg_layout)
        LinearLayout mLinearLayout;

        @BindView(R.id.msg_text)
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            //mLinearLayout = itemView.findViewById(R.id.msg_layout);
            //mTextView = itemView.findViewById(R.id.msg_text);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {
            mTextView.setText("");
        }
    }

    public MsgAdapter(List<String> list) {
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_msg, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String msg = mList.get(position);
        holder.mLinearLayout.setVisibility(View.VISIBLE);
        holder.mTextView.setText(msg);
    }

    @Override
    public int getItemCount() {
        if (mList != null && mList.size() > 0)
            return mList.size();
        else
            return 1;
    }

    public void addItem(String str) {
        mList.add(str);
        notifyDataSetChanged();
    }
}
