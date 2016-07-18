package ru.teaz.examplerssviewer.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import ru.teaz.examplerssviewer.data.db.dao.NewsDAO;
import ru.teaz.examplerssviewer.data.db.model.News;

public class DbHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DbHelper.class.getCanonicalName();

    private static final String DATABASE_NAME = "rss_news_db";
    private static final int DATABASE_VERSION = 1;

    private static final class Lock { }
    private static final Lock LOCK = new Lock();

    private NewsDAO mNewsDao;

    public DbHelper(@NonNull Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, News.class);
        } catch (SQLException se) {
            Log.e(TAG, "Error creating DB " + DATABASE_NAME + ": " + se.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            dropTables(connectionSource);
            onCreate(db,connectionSource);
        } catch (SQLException e) {
            Log.e(TAG, "Error onUpgrade db " + DATABASE_NAME + " from version "
                    + oldVersion + " to version " + newVersion);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, newVersion, oldVersion);
    }

    private void dropTables(ConnectionSource connectionSource) throws SQLException {
        TableUtils.dropTable(connectionSource, News.class, true);
    }

    @Override
    public void close() {
        super.close();
        mNewsDao = null;
    }

    public NewsDAO getNewsDao() throws SQLException{
        if (mNewsDao == null) {
            synchronized (LOCK) {
                if (mNewsDao == null) {
                    mNewsDao = new NewsDAO(getConnectionSource());
                }
            }
        }

        return mNewsDao;
    }
}
