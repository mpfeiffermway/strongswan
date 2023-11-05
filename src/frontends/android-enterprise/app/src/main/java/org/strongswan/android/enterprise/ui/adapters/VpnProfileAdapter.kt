package org.strongswan.android.enterprise.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.strongswan.android.enterprise.databinding.ListItemVpnProfileBinding
import org.strongswan.android.enterprise.io.entities.VpnProfile
import org.strongswan.android.enterprise.ui.widget.ItemSwipeHelper
import javax.inject.Inject

class VpnProfileAdapter @Inject constructor(
) : ListAdapter<VpnProfile, VpnProfileAdapter.VpnProfileViewHolder>(VpnProfileItemCallback()) {

	private var editor: (vpnProfile: VpnProfile) -> Unit = {}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VpnProfileViewHolder {
		return VpnProfileViewHolder(parent.bind(ListItemVpnProfileBinding::inflate))
	}

	override fun onBindViewHolder(holder: VpnProfileViewHolder, position: Int) {
		val vpnProfile = getItem(position)

		holder.binding.profileName.text = vpnProfile.name
		holder.binding.profileGateway.text = vpnProfile.gateway
		holder.binding.profileUsername.text = vpnProfile.username
		holder.binding.profileCertificate.text = vpnProfile.certificate

		holder.binding.editProfile.setOnClickListener {
			editor.invoke(vpnProfile)
		}
	}

	fun setOnVpnProfileEditListener(listener: (vpnProfile: VpnProfile) -> Unit) {
		editor = listener
	}

	inner class VpnProfileViewHolder(val binding: ListItemVpnProfileBinding) :
		ViewHolder(binding.root), ItemSwipeHelper.SupportsSwipe
}
