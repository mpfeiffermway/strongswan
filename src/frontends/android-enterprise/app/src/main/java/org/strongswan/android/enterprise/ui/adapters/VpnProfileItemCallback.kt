package org.strongswan.android.enterprise.ui.adapters

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import org.strongswan.android.enterprise.io.entities.VpnProfile

class VpnProfileItemCallback : ItemCallback<VpnProfile>() {

	override fun areItemsTheSame(oldItem: VpnProfile, newItem: VpnProfile): Boolean {
		return oldItem.id == newItem.id
	}

	override fun areContentsTheSame(oldItem: VpnProfile, newItem: VpnProfile): Boolean {
		return oldItem.name == newItem.name
			&& oldItem.gateway == newItem.gateway
			&& oldItem.username == newItem.username
			&& oldItem.certificate == newItem.certificate
	}
}
