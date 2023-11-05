package org.strongswan.android.enterprise.io.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
	tableName = "vpnprofile",
	indices = [
		Index(value = ["_uuid"], unique = true)
	]
)
class VpnProfile(
	var name: String,
	var gateway: String,
	@ColumnInfo(name = "vpn_type", defaultValue = "")
	var vpnType: VpnType,
) {
	companion object {
		const val ID = "_id"
	}

	@ColumnInfo(name = ID)
	@PrimaryKey(autoGenerate = true)
	var id: Long? = null

	@ColumnInfo(name = "_uuid")
	var uuid: String? = null

	var username: String? = null

	var password: String? = null

	var certificate: String? = null

	@ColumnInfo(name = "user_certificate")
	var userCertificate: String? = null

	var mtu: Int? = null

	var port: Int? = null

	@ColumnInfo(name = "split_tunneling")
	var splitTunneling: Int? = null

	@ColumnInfo(name = "local_id")
	var localId: String? = null

	@ColumnInfo(name = "remote_id")
	var remoteId: String? = null

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
