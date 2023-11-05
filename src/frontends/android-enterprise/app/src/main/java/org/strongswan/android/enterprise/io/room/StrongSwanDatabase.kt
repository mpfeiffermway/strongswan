package org.strongswan.android.enterprise.io.room

import androidx.room.Database
import androidx.room.RoomDatabase
import org.strongswan.android.enterprise.io.entities.VpnProfile
import org.strongswan.android.enterprise.io.room.dao.VpnProfileDao

@Database(
	version = StrongSwanDatabase.SCHEMA_VERSION,
	exportSchema = true,
	entities = [
		VpnProfile::class,
	],
	autoMigrations = [
	],
)
abstract class StrongSwanDatabase : RoomDatabase() {
	companion object {
		const val SCHEMA_VERSION = 1
	}

	abstract fun vpnProfileDao(): VpnProfileDao
}
