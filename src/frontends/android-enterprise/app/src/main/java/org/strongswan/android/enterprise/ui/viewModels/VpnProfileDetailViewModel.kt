package org.strongswan.android.enterprise.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.strongswan.android.enterprise.io.entities.VpnProfile
import org.strongswan.android.enterprise.io.room.dao.VpnProfileDao
import javax.inject.Inject

@HiltViewModel
class VpnProfileDetailViewModel @Inject constructor(
	private val vpnProfileDao: VpnProfileDao,
) : ViewModel(), CoroutineScope by MainScope() {

	fun getVpnProfile(id: Long): LiveData<VpnProfile?> {
		return vpnProfileDao.fetchById(id)
	}

	fun insertVpnProfile(vpnProfile: VpnProfile) {
		launch {
			vpnProfileDao.insert(vpnProfile)
		}
	}

	fun updateVpnProfile(vpnProfile: VpnProfile) {
		launch {
			vpnProfileDao.update(vpnProfile)
		}
	}
}
