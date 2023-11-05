package org.strongswan.android.enterprise.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.strongswan.android.enterprise.R
import org.strongswan.android.enterprise.databinding.FragmentVpnProfileListBinding
import org.strongswan.android.enterprise.io.entities.VpnProfile
import org.strongswan.android.enterprise.ui.activities.VpnProfileDetailActivity
import org.strongswan.android.enterprise.ui.adapters.VpnProfileAdapter
import org.strongswan.android.enterprise.ui.viewModels.VpnProfileListViewModel
import org.strongswan.android.enterprise.ui.widget.ItemSwipeHelper.Companion.onItemSwiped
import javax.inject.Inject

@AndroidEntryPoint
class VpnProfileListFragment : Fragment() {

	private val viewModel: VpnProfileListViewModel by viewModels()

	@Inject
	internal lateinit var vpnProfileAdapter: VpnProfileAdapter

	private lateinit var binding: FragmentVpnProfileListBinding

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		binding = FragmentVpnProfileListBinding.inflate(inflater, container, false)

		val decoration = DividerItemDecoration(context, RecyclerView.VERTICAL)

		binding.vpnProfileList.adapter = vpnProfileAdapter
		binding.vpnProfileList.addItemDecoration(decoration)

		binding.addVpnProfile.setOnClickListener(::createVpnProfile)
		vpnProfileAdapter.setOnVpnProfileEditListener(::editVpnProfile)
		binding.vpnProfileList.onItemSwiped(::deleteVpnProfile)

		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		viewModel.vpnProfiles.observe(this.viewLifecycleOwner, vpnProfileAdapter::submitList)
	}

	private fun createVpnProfile(view: View) {
		val intent = Intent(view.context, VpnProfileDetailActivity::class.java)
		startActivity(intent)
	}

	private fun editVpnProfile(vpnProfile: VpnProfile) {
		val intent = Intent(context, VpnProfileDetailActivity::class.java)
		intent.putExtra(VpnProfile.ID, vpnProfile.id)
		startActivity(intent)
	}

	private fun deleteVpnProfile(viewHolder: ViewHolder) {
		val vpnProfile = vpnProfileAdapter.currentList[viewHolder.absoluteAdapterPosition]
		viewModel.deleteVpnProfile(vpnProfile)

		val message = getString(R.string.vpn_profile_deleted, vpnProfile.name)
		val snackbar = Snackbar.make(binding.coordinator, message, Snackbar.LENGTH_INDEFINITE)

		snackbar.setAction(R.string.action_undo) {
			viewModel.insertVpnProfile(vpnProfile)
		}
		snackbar.show()
	}
}
