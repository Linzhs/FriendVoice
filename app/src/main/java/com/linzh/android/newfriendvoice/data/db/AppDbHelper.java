package com.linzh.android.newfriendvoice.data.db;

import com.linzh.android.newfriendvoice.data.db.model.DaoMaster;
import com.linzh.android.newfriendvoice.data.db.model.DaoSession;
import com.linzh.android.newfriendvoice.data.db.model.User;
import com.linzh.android.newfriendvoice.data.db.model.UserDao;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by linzh on 2018/3/25.
 */

@Singleton
public class AppDbHelper implements DbHelper {

    private final DaoSession mDaoSession;

    @Inject
    public AppDbHelper(DbOpenHelper dbOpenHelper) {
        mDaoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }

    @Override
    public Observable<Long> insertUser(final User user) {
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return mDaoSession.getUserDao().insert(user);
            }
        });
    }

    @Override
    public Observable<User> queryUser(final Long id) {
        return Observable.fromCallable(new Callable<User>() {
            @Override
            public User call() throws Exception {
                return mDaoSession.getUserDao().queryBuilder().where(UserDao.Properties.Id.eq(id)).unique();
            }
        });
    }

    @Override
    public Observable<Void> updateUser(final User user) {
        return Observable.fromCallable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                mDaoSession.getUserDao().update(user);
                return null;
            }
        });
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return Observable.fromCallable(new Callable<List<User>>() {
            @Override
            public List<User> call() throws Exception {
                return mDaoSession.getUserDao().loadAll();
            }
        });
    }
}
