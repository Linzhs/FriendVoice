package com.linzh.android.newfriendvoice.ui.debug;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.linzh.android.newfriendvoice.R;
import com.linzh.android.newfriendvoice.ui.base.BaseActivity;
import com.linzh.android.newfriendvoice.utils.VoiceUtils;

import java.lang.invoke.VolatileCallSite;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.zip.Inflater;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DebugActivity extends BaseActivity implements DebugMvpView {

    @Inject
    DebugMvpPresenter<DebugMvpView> mPresenter;

    @Inject
    LinearLayoutManager mLinearLayoutManager;

    @Inject
    WordAdapter mWordAdapter;

    @BindView(R.id.debug_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.developer_debug_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.developer_debug_fab)
    FloatingActionButton mFloatingActionButton;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, DebugActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

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
            actionBar.setTitle("开发者调试");
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_24dp);
        }

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mWordAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        Map<String, String> map = VoiceUtils.getVoiceCodes();
        Set<String> set = map.keySet();
        for (String key : set) {
            mWordAdapter.addItem(new Word(key, map.get(key)));
        }
        mWordAdapter.setOnItemClickListener(new WordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {
                new AlertDialog.Builder(DebugActivity.this)
                        .setTitle("警告")
                        .setMessage("是否确定删除该词汇？")
                        .setCancelable(true)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPresenter.deleteWord(mWordAdapter.getWord(position).getKey());
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create()
                        .show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
        }
        return true;
    }

    @OnClick(R.id.developer_debug_fab)
    void onFloatActionButtonClick() {
        final View view = LayoutInflater.from(this).inflate(R.layout.dialog_input, null);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(R.layout.dialog_input)
                .setPositiveButton("添加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText code = view.findViewById(R.id.input_code);
                        EditText codeText = view.findViewById(R.id.input_code_text);
                        mPresenter.addWord(mWordAdapter, codeText.getText().toString(), code.getText().toString());
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        mPresenter.updateVoiceCodes(mWordAdapter.getAllWords());
        mPresenter.onDetach();
        super.onDestroy();
    }
}
