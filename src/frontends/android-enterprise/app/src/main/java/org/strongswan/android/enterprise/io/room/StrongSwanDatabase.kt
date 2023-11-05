package org.strongswan.android.enterprise.io.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.strongswan.android.enterprise.io.converters.VpnTypeConverter
import org.strongswan.android.enterprise.io.entities.VpnProfile
import org.strongswan.android.enterprise.io.room.dao.VpnProfileDao

@Database(
	version = StrongSwanDatabase.SCHEMA_VERSION,
	exportSchema = true,
	entities = [
		VpnProfile::class,
	],
	autoMigrations = [
		AutoMigration(from = 1, to = 2),
		AutoMigration(from = 2, to = 3),
		AutoMigration(from = 3, to = 4),
		AutoMigration(from = 4, to = 5),
		AutoMigration(from = 5, to = 6),
		AutoMigration(from = 6, to = 7),
		AutoMigration(from = 7, to = 8),
	],
)
@TypeConverters(
	VpnTypeConverter::class
)
abstract class StrongSwanDatabase : RoomDatabase() {
	companion object {
		const val SCHEMA_VERSION = 8
	}

	abstract fun vpnProfileDao(): VpnProfileDao
}
