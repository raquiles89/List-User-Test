package com.test.list_user.util;

import com.j256.ormlite.support.ConnectionSource;
import com.test.list_user.helper.DBHelper;

import java.sql.SQLException;


public class RepositoryUtil {

    private RepositoryUtil() {
    }


    public static void createTables(final DBHelper helper) throws SQLException {
        final ConnectionSource connectionSource = helper.getConnectionSource();

        //TableUtils.createTable(connectionSource, User.class);
    }

    public static void dropTables(final DBHelper helper) throws SQLException {
        final ConnectionSource connectionSource = helper.getConnectionSource();

        //TableUtils.dropTable(connectionSource, User.class, true);

    }
}
