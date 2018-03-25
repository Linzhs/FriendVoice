package com.linzh.android.newfriendvoice.data.db;

import com.linzh.android.newfriendvoice.data.db.model.User;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by linzh on 2018/3/25.
 */

public interface DbHelper {

    Observable<Long> insertUser(final User user);

    Observable<User> queryUser(final Long id);

    Observable<Void> updateUser(final User user);

    Observable<List<User>> getAllUsers();
}
