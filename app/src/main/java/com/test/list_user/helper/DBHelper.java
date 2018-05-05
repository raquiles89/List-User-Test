package com.test.list_user.helper;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.test.list_user.util.RepositoryUtil;
import com.test.list_user.R;
import java.sql.SQLException;



public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = "DatabaseHelperImpl";
    private static final String DATABASE_NAME = "test.db";
    private static final int DATABASE_VERSION = 1;

    //private Dao<User, Integer> userDao;

    public DBHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(final SQLiteDatabase database, final ConnectionSource connectionSource) {
        try {
            RepositoryUtil.createTables(DBHelper.this);
            Log.d(TAG, "Successfully table creation");
        } catch (Exception e) {
            Log.e(TAG, "can not create tables in database", e);
        }
    }

    @Override
    public void onUpgrade(final SQLiteDatabase database, final ConnectionSource connectionSource, final int oldVersion, final int newVersion) {
        if (oldVersion < newVersion && newVersion == DATABASE_VERSION) {
            try {
                // drop
                RepositoryUtil.dropTables(DBHelper.this);
                Log.d(TAG, "Successfully table drop");
                //create
                RepositoryUtil.createTables(DBHelper.this);
                Log.d(TAG, "Successfully table creation");
                Log.d(TAG, "Successfully loading of initial data");
            } catch (SQLException e) {
                Log.e(TAG, "can not recreate tables in database", e);
            }
        }
    }

    @Override
    public void close() {
        super.close();
        //userDao = null;
    }

   /* public Dao<User, Integer> getUserDao() throws SQLException {
        if (userDao == null) {
            userDao = getDao(User.class);
        }
        return userDao;
    }*/
}
