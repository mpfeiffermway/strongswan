package org.strongswan.android.enterprise.io.converters

import androidx.room.TypeConverter
import org.strongswan.android.enterprise.io.entities.VpnType

class VpnTypeConverter {

	@TypeConverter
	fun toVpnType(value: String): VpnType {
		return if (value.isBlank()) {
			VpnType.IKEV2_EAP
		} else {
			enumValueOf(value)
		}
	}

	@TypeConverter
	fun fromVpnType(value: VpnType) = value.name
}
