package com.linzh.android.newfriendvoice.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.linzh.android.newfriendvoice.data.db.model.DaoMaster;
import com.linzh.android.newfriendvoice.di.ApplicationContext;
import com.linzh.android.newfriendvoice.di.DatabaseInfo;
import com.linzh.android.newfriendvoice.utils.AppLogger;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by linzh on 2018/3/25.
 */

@Singleton
public class DbOpenHelper extends DaoMaster.OpenHelper {

    @Inject
    public DbOpenHelper(@ApplicationContext Context context, @DatabaseInfo String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);

        AppLogger.d("DEBUG", "DB_OLD_VERSION : " + oldVersion + ", DB_NEW_VERSION : " + newVersion);
        switch (oldVersion) {
            case 1:
            case 2:
                //db.execSQL("ALTER TABLE " + UserDao.TABLENAME + " ADD COLUMN "
                // + UserDao.Properties.Name.columnName + " TEXT DEFAULT 'DEFAULT_VAL'");
        }
    }
}
