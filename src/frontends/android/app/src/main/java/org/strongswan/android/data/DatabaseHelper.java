package org.strongswan.android.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class DatabaseHelper extends SQLiteOpenHelper
{
	private static final String TAG = DatabaseHelper.class.getSimpleName();

	private static final String DATABASE_NAME = "strongswan.db";

	private static final String TABLE_NAME_VPN_PROFILE = "vpnprofile";

	static final DbTable TABLE_VPN_PROFILE = new DbTable(TABLE_NAME_VPN_PROFILE, 1, new DbColumn[]{
		new DbColumn(VpnProfileDataSource.KEY_ID, "INTEGER PRIMARY KEY AUTOINCREMENT", 1),
		new DbColumn(VpnProfileDataSource.KEY_UUID, "TEXT UNIQUE", 9),
		new DbColumn(VpnProfileDataSource.KEY_NAME, "TEXT NOT NULL", 1),
		new DbColumn(VpnProfileDataSource.KEY_GATEWAY, "TEXT NOT NULL", 1),
		new DbColumn(VpnProfileDataSource.KEY_VPN_TYPE, "TEXT NOT NULL DEFAULT ''", 3),
		new DbColumn(VpnProfileDataSource.KEY_USERNAME, "TEXT", 1),
		new DbColumn(VpnProfileDataSource.KEY_PASSWORD, "TEXT", 1),
		new DbColumn(VpnProfileDataSource.KEY_CERTIFICATE, "TEXT", 1),
		new DbColumn(VpnProfileDataSource.KEY_USER_CERTIFICATE, "TEXT", 2),
		new DbColumn(VpnProfileDataSource.KEY_MTU, "INTEGER", 5),
		new DbColumn(VpnProfileDataSource.KEY_PORT, "INTEGER", 6),
		new DbColumn(VpnProfileDataSource.KEY_SPLIT_TUNNELING, "INTEGER", 7),
		new DbColumn(VpnProfileDataSource.KEY_LOCAL_ID, "TEXT", 8),
		new DbColumn(VpnProfileDataSource.KEY_REMOTE_ID, "TEXT", 8),
		new DbColumn(VpnProfileDataSource.KEY_EXCLUDED_SUBNETS, "TEXT", 10),
		new DbColumn(VpnProfileDataSource.KEY_INCLUDED_SUBNETS, "TEXT", 11),
		new DbColumn(VpnProfileDataSource.KEY_SELECTED_APPS, "INTEGER", 12),
		new DbColumn(VpnProfileDataSource.KEY_SELECTED_APPS_LIST, "TEXT", 12),
		new DbColumn(VpnProfileDataSource.KEY_NAT_KEEPALIVE, "INTEGER", 13),
		new DbColumn(VpnProfileDataSource.KEY_FLAGS, "INTEGER", 14),
		new DbColumn(VpnProfileDataSource.KEY_IKE_PROPOSAL, "TEXT", 15),
		new DbColumn(VpnProfileDataSource.KEY_ESP_PROPOSAL, "TEXT", 15),
		new DbColumn(VpnProfileDataSource.KEY_DNS_SERVERS, "TEXT", 17),
	});

	private static final int DATABASE_VERSION = 17;

	private static final Set<DbTable> TABLES;

	static
	{
		TABLES = new HashSet<>();
		TABLES.add(TABLE_VPN_PROFILE);
	}

	public DatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database)
	{
		for (final String sql : getDatabaseCreate(DATABASE_VERSION))
		{
			database.execSQL(sql);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
		addNewTables(db, oldVersion, newVersion);
		addNewColumns(db, oldVersion, newVersion);

		if (oldVersion < 4 && newVersion >= 4)
		{    /* remove NOT NULL constraint from username column */
			updateColumns(db, TABLE_VPN_PROFILE, 4);
		}
		if (oldVersion < 9 && newVersion >= 9)
		{
			updateColumns(db, TABLE_VPN_PROFILE, 9);
		}
		if (oldVersion < 16 && newVersion >= 16)
		{    /* add a UUID to all entries that haven't one yet */
			db.beginTransaction();
			try
			{
				Cursor cursor = db.query(TABLE_VPN_PROFILE.Name, TABLE_VPN_PROFILE.getColumnNames(16), VpnProfileDataSource.KEY_UUID + " is NULL", null, null, null, null);
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
				{
					ContentValues values = new ContentValues();
					values.put(VpnProfileDataSource.KEY_UUID, UUID.randomUUID().toString());
					db.update(TABLE_VPN_PROFILE.Name, values, VpnProfileDataSource.KEY_ID + " = " + cursor.getLong(cursor.getColumnIndexOrThrow(VpnProfileDataSource.KEY_ID)), null);
				}
				cursor.close();
				db.setTransactionSuccessful();
			}
			finally
			{
				db.endTransaction();
			}
		}
	}

	private void updateColumns(SQLiteDatabase db, DbTable table, int version)
	{
		db.beginTransaction();
		try
		{
			db.execSQL("ALTER TABLE " + table.Name + " RENAME TO tmp_" + table.Name + ";");
			db.execSQL(getTableCreate(table, version));
			StringBuilder insert = new StringBuilder("INSERT INTO " + table.Name + " SELECT ");
			SQLiteQueryBuilder.appendColumns(insert, table.getColumnNames(version));
			db.execSQL(insert.append(" FROM tmp_" + table.Name + ";").toString());
			db.execSQL("DROP TABLE tmp_" + table.Name + ";");
			db.setTransactionSuccessful();
		}
		finally
		{
			db.endTransaction();
		}
	}

	private List<String> getDatabaseCreate(final int version)
	{
		List<String> statements = new ArrayList<>(TABLES.size());
		for (final DbTable table : TABLES)
		{
			if (table.Since <= version)
			{
				final String statement = getTableCreate(table, version);
				statements.add(statement);
			}
		}
		return statements;
	}

	private static String getTableCreate(DbTable table, int version)
	{
		boolean first = true;
		StringBuilder create = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
		create.append(table.Name);
		create.append(" (");

		final List<DbColumn> columns = table.getColumns(version);
		for (final DbColumn column : columns)
		{
			if (!first)
			{
				create.append(",");
			}
			first = false;
			create.append(column.Name);
			create.append(" ");
			create.append(column.Type);
		}
		create.append(");");
		return create.toString();
	}

	private void addNewTables(final SQLiteDatabase database, final int oldVersion, final int newVersion)
	{
		for (final String sql : getTableCreates(oldVersion, newVersion))
		{
			database.execSQL(sql);
		}
	}

	private List<String> getTableCreates(final int oldVersion, final int newVersion)
	{
		List<String> statements = new ArrayList<>(TABLES.size());
		for (final DbTable table : TABLES)
		{
			if (table.Since > oldVersion && table.Since <= newVersion)
			{
				statements.add(getTableCreate(table, newVersion));
			}
		}
		return statements;
	}

	private void addNewColumns(final SQLiteDatabase database, final int oldVersion, final int newVersion)
	{
		for (final String sql : getAlterTables(oldVersion, newVersion))
		{
			database.execSQL(sql);
		}
	}

	private List<String> getAlterTables(final int oldVersion, final int newVersion)
	{
		List<String> statements = new ArrayList<>(TABLES.size());
		for (final DbTable table : TABLES)
		{
			if (table.Since < newVersion)
			{
				final List<String> tableStatements = getAlterTables(table, oldVersion, newVersion);
				statements.addAll(tableStatements);
			}
		}
		return statements;
	}

	private static List<String> getAlterTables(DbTable table, final int oldVersion, final int newVersion)
	{
		final List<DbColumn> columns = table.getColumns(newVersion);
		final List<String> sql = new ArrayList<>();

		for (final DbColumn column : columns)
		{
			if (column.Since > oldVersion)
			{
				sql.add("ALTER TABLE " + table.Name + " ADD " + column.Name + " " + column.Type + ";");
			}
		}
		return sql;
	}

	public static class DbTable
	{
		public final String Name;
		public final int Since;
		public final DbColumn[] Columns;

		private DbTable(final String name, final int since, final DbColumn[] columns)
		{
			Name = name;
			Since = since;
			Columns = columns;
		}

		private List<DbColumn> getColumns(int version)
		{
			final List<DbColumn> columns = new ArrayList<>(Columns.length);
			for (final DbColumn column : Columns)
			{
				if (column.Since <= version)
				{
					columns.add(column);
				}
			}
			return columns;
		}

		private String[] getColumnNames(final int version)
		{
			final List<DbColumn> columns = getColumns(version);
			final List<String> columnNames = new ArrayList<>(columns.size());
			for (DbColumn column : columns)
			{
				columnNames.add(column.Name);
			}
			return columnNames.toArray(new String[0]);
		}

		public String[] columnNames()
		{
			return getColumnNames(DATABASE_VERSION);
		}
	}

	public static class DbColumn
	{
		public final String Name;
		public final String Type;
		public final int Since;

		private DbColumn(String name, String type, int since)
		{
			Name = name;
			Type = type;
			Since = since;
		}
	}
}
