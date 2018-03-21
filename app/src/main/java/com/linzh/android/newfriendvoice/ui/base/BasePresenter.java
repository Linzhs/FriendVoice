package com.linzh.android.newfriendvoice.ui.base;

import com.androidnetworking.error.ANError;
import com.linzh.android.newfriendvoice.data.DataManager;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by linzh on 2018/3/20.
 */

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private static final String TAG = "BasePresenter";

    // model
    private final DataManager mDataManager;
    private final CompositeDisposable mCompositeDisposable;

    // view
    private V mMvpView;

    @Inject
    public BasePresenter(CompositeDisposable compositeDisposable, DataManager dataManager) {
        mCompositeDisposable = compositeDisposable;
        mDataManager = dataManager;
    }

    @Override
    public void onAttach(V mvpView) {
        this.mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        mCompositeDisposable.dispose();
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    @Override
    public void handleApiError(ANError error) {

    }

    public static class MvpViewNotAttachedException extends RuntimeException {

        /**
         * Don't let anyone else instantiate this class
         */
        public MvpViewNotAttachedException() {
            super("Please call Presenter.onAttach(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
