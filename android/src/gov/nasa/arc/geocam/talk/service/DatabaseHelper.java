package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.inject.Inject;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Database helper class used to manage the creation and upgrading of your
 * database. This class also usually provides the DAOs used by the other
 * classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper implements IDatabaseHelper {

	/** name of the database file for your application */
	private static final String DATABASE_NAME = "geocamTalk.db";

	/** any time you make changes to your database objects, you may have to
	increase the database version */
	private static final int DATABASE_VERSION = 6;
	
	/** the DAO object we use to access the SimpleData table */
	private Dao<GeoCamTalkMessage, Integer> simpleDao = null;

	/**
	 * Instantiates a new database helper.
	 *
	 * @param context the context
	 */
	@Inject
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * This is called when the database is first created. Usually you should
	 * call createTable statements here to create the tables that will store
	 * your data.
	 *
	 * @param db the db
	 * @param connectionSource the connection source
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, GeoCamTalkMessage.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * This is called when your application is upgraded and it has a higher
	 * version number. This allows you to adjust the various data to match the
	 * new version number.
	 *
	 * @param db the db
	 * @param connectionSource the connection source
	 * @param oldVersion the old version
	 * @param newVersion the new version
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion,
			int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, GeoCamTalkMessage.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns the Database Access Object (DAO) for our SimpleData class. It
	 * will create it or just give the cached value.
	 *
	 * @return the geo cam talk message dao
	 * @throws SQLException the sQL exception
	 */
	@Override
	public Dao<GeoCamTalkMessage, Integer> getGeoCamTalkMessageDao() throws SQLException {
		if (simpleDao == null) {
			simpleDao = getDao(GeoCamTalkMessage.class);
		}
		return simpleDao;
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		simpleDao = null;
	}
}