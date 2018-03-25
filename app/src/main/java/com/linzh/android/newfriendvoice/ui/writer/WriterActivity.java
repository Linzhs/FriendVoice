package com.linzh.android.newfriendvoice.ui.writer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.linzh.android.newfriendvoice.R;
import com.linzh.android.newfriendvoice.ui.base.BaseActivity;
import com.linzh.android.newfriendvoice.ui.custom.WriteBoardView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WriterActivity extends BaseActivity implements WriterMvpView {

    private static final String TAG = "WriterActivity";

    @Inject
    WriterMvpPresenter<WriterMvpView> mPresenter;

    @BindView(R.id.write_board_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.writing_board)
    WriteBoardView mWriteBoardView;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, WriterActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writer);

        Log.d(TAG, "onCreate: ");

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
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_writer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.writing_board_delete:
                clearWirtedBoard();
            default:
        }
        return true;
    }

    @Override
    public void clearWirtedBoard() {
        mWriteBoardView.clearDraw();
    }
}
