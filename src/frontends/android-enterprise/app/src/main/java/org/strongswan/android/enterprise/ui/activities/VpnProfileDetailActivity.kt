package org.strongswan.android.enterprise.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import org.strongswan.android.enterprise.R
import org.strongswan.android.enterprise.databinding.ActivityVpnProfileDetailBinding
import org.strongswan.android.enterprise.io.entities.VpnProfile
import org.strongswan.android.enterprise.ui.viewModels.VpnProfileDetailViewModel

@AndroidEntryPoint
class VpnProfileDetailActivity : AppCompatActivity() {

	private val viewModel: VpnProfileDetailViewModel by viewModels()

	private lateinit var binding: ActivityVpnProfileDetailBinding

	private var vpnProfile: VpnProfile? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = setContentView(ActivityVpnProfileDetailBinding::inflate)

		intent.extras?.getLong(VpnProfile.ID)?.let { id ->
			viewModel.getVpnProfile(id).observe(this) { profile ->
				vpnProfile = profile
				vpnProfile?.let(::showVpnProfile)
			}
		}
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.vpn_profile_detail, menu)
		return super.onCreateOptionsMenu(menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		return when (item.itemId) {
			R.id.menu_save_vpn_profile -> saveVpnProfile()
			else -> false
		}
	}

	private fun saveVpnProfile(): Boolean {
		vpnProfile?.let {
			updateVpnProfile(it)
		} ?: run {
			createNewVpnProfile()
		}
		finish()
		return true
	}

	private fun showVpnProfile(vpnProfile: VpnProfile) {
		binding.vpnProfileName.setText(vpnProfile.name)
		binding.vpnProfileGateway.setText(vpnProfile.gateway)
		binding.vpnProfileUsername.setText(vpnProfile.username)
		binding.vpnProfilePassword.setText(vpnProfile.password)
		binding.vpnProfileCertificate.setText(vpnProfile.certificate)
		binding.vpnProfileUserCertificate.setText(vpnProfile.userCertificate)
	}

	private fun createNewVpnProfile() {
		val vpnProfile = VpnProfile(
			binding.vpnProfileName.value(),
			binding.vpnProfileGateway.value(),
			binding.vpnProfileUsername.value()
		)
		setVpnProfileFields(vpnProfile)

		viewModel.insertVpnProfile(vpnProfile)
	}

	private fun updateVpnProfile(vpnProfile: VpnProfile) {
		vpnProfile.name = binding.vpnProfileName.value()
		vpnProfile.gateway = binding.vpnProfileGateway.value()
		vpnProfile.username = binding.vpnProfileUsername.value()
		setVpnProfileFields(vpnProfile)

		viewModel.updateVpnProfile(vpnProfile)
	}

	private fun setVpnProfileFields(vpnProfile: VpnProfile) {
		vpnProfile.password = binding.vpnProfilePassword.valueOrNull()
		vpnProfile.certificate = binding.vpnProfileCertificate.valueOrNull()
		vpnProfile.userCertificate = binding.vpnProfileUserCertificate.valueOrNull()
	}
}
