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
import org.strongswan.android.enterprise.io.entities.VpnType
import org.strongswan.android.enterprise.ui.viewModels.VpnProfileDetailViewModel
import org.strongswan.android.enterprise.ui.widget.NamedValue

@AndroidEntryPoint
class VpnProfileDetailActivity : AppCompatActivity() {

	private val viewModel: VpnProfileDetailViewModel by viewModels()

	private lateinit var binding: ActivityVpnProfileDetailBinding

	private var vpnProfile: VpnProfile? = null
	private var selectedVpnType = VpnType.IKEV2_EAP

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = setContentView(ActivityVpnProfileDetailBinding::inflate)

		val vpnTypeArrayAdapter = NamedValue.adapter(
			this,
			android.R.layout.simple_dropdown_item_1line,
			VpnType.values()
		)

		binding.vpnProfileType.setAdapter(vpnTypeArrayAdapter)
		binding.vpnProfileType.setOnItemClickListener { _, _, position, _ ->
			vpnTypeArrayAdapter.getItem(position)?.let {
				selectedVpnType = it.enum
			}
		}

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
		selectedVpnType = vpnProfile.vpnType
		val vpnTypeName = getString(selectedVpnType.nameResId)

		binding.vpnProfileName.setText(vpnProfile.name)
		binding.vpnProfileGateway.setText(vpnProfile.gateway)
		binding.vpnProfileUsername.setText(vpnProfile.username)
		binding.vpnProfilePassword.setText(vpnProfile.password)
		binding.vpnProfileCertificate.setText(vpnProfile.certificate)
		binding.vpnProfileUserCertificate.setText(vpnProfile.userCertificate)
		binding.vpnProfileType.setText(vpnTypeName, false)
		binding.vpnProfileMtu.setText(vpnProfile.mtu?.toString())
		binding.vpnProfilePort.setText(vpnProfile.port?.toString())
		binding.vpnProfileSplitTunneling.setText(vpnProfile.splitTunneling?.toString())
		binding.vpnProfileLocalId.setText(vpnProfile.localId)
		binding.vpnProfileRemoteId.setText(vpnProfile.remoteId)
	}

	private fun createNewVpnProfile() {
		val vpnProfile = VpnProfile(
			binding.vpnProfileName.value(),
			binding.vpnProfileGateway.value(),
			selectedVpnType,
		)
		setVpnProfileFields(vpnProfile)

		viewModel.insertVpnProfile(vpnProfile)
	}

	private fun updateVpnProfile(vpnProfile: VpnProfile) {
		vpnProfile.name = binding.vpnProfileName.value()
		vpnProfile.gateway = binding.vpnProfileGateway.value()
		vpnProfile.vpnType = selectedVpnType
		setVpnProfileFields(vpnProfile)

		viewModel.updateVpnProfile(vpnProfile)
	}

	private fun setVpnProfileFields(vpnProfile: VpnProfile) {
		vpnProfile.username = binding.vpnProfileUsername.valueOrNull()
		vpnProfile.password = binding.vpnProfilePassword.valueOrNull()
		vpnProfile.certificate = binding.vpnProfileCertificate.valueOrNull()
		vpnProfile.userCertificate = binding.vpnProfileUserCertificate.valueOrNull()
		vpnProfile.mtu = binding.vpnProfileMtu.value().toIntOrNull()
		vpnProfile.port = binding.vpnProfilePort.value().toIntOrNull()
		vpnProfile.splitTunneling = binding.vpnProfileSplitTunneling.value().toIntOrNull()
		vpnProfile.localId = binding.vpnProfileLocalId.valueOrNull()
		vpnProfile.remoteId = binding.vpnProfileRemoteId.valueOrNull()
	}
}
