package org.strongswan.android.enterprise.ui.viewModels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.strongswan.android.enterprise.io.entities.VpnProfile
import org.strongswan.android.enterprise.io.room.dao.VpnProfileDao
import javax.inject.Inject

@HiltViewModel
class VpnProfileListViewModel @Inject constructor(
	private val vpnProfileDao: VpnProfileDao,
) : ViewModel(), CoroutineScope by MainScope() {
	val vpnProfiles
		get() = vpnProfileDao.fetchAll()

	fun deleteVpnProfile(vpnProfile: VpnProfile) {
		launch {
			vpnProfileDao.delete(vpnProfile)
		}
	}

	fun insertVpnProfile(vpnProfile: VpnProfile) {
		launch {
			vpnProfileDao.insert(vpnProfile)
		}
	}
}
