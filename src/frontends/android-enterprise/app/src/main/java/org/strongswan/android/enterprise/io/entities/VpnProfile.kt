package org.strongswan.android.enterprise.io.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vpnprofile")
class VpnProfile(
	var name: String,
	var gateway: String,
	var username: String,
) {
	companion object {
		const val ID = "_id"
	}

	@ColumnInfo(name = ID)
	@PrimaryKey(autoGenerate = true)
	var id: Long? = null

	var password: String? = null

	var certificate: String? = null

	override fun equals(other: Any?): Boolean {
		if (this === other) {
			return true
		}
		if (javaClass != other?.javaClass) {
			return false
		}

		other as VpnProfile
		return id == other.id
	}

	override fun hashCode(): Int {
		return id.hashCode()
	}
}
