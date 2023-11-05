package org.strongswan.android.enterprise.io.entities

import androidx.annotation.StringRes
import org.strongswan.android.enterprise.R
import org.strongswan.android.enterprise.util.Named

enum class VpnType(
	@StringRes
	override val nameResId: Int,
) : Named {
	IKEV2_EAP(R.string.vpn_type_ikev2_eap),
	IKEV2_CERT(R.string.vpn_type_ikev2_cert),
	IKEV2_CERT_EAP(R.string.vpn_type_ikev2_cert_eap),
	IKEV2_EAP_TLS(R.string.vpn_type_ikev2_eap_tls),
	IKEV2_BYOD_EAP(R.string.vpn_type_ikev2_byod_eap),
}
