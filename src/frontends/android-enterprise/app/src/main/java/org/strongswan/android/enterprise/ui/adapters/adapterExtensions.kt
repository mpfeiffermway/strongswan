package org.strongswan.android.enterprise.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

inline fun <T : ViewBinding> ViewGroup.bind(
	viewBindingInflate: (inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean) -> T,
): T {
	val inflater = LayoutInflater.from(context)
	return viewBindingInflate.invoke(inflater, this, false)
}
