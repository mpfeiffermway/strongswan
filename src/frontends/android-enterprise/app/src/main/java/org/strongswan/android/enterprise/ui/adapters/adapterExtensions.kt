package org.strongswan.android.enterprise.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

inline fun <T : ViewBinding> ViewGroup.bind(
	viewBindingInflate: (inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean) -> T,
): T {
	val inflater = LayoutInflater.from(context)
	return viewBindingInflate.invoke(inflater, this, false)
}

fun <VH : RecyclerView.ViewHolder> ((view: View) -> VH).create(
	parent: ViewGroup,
	@LayoutRes
	layoutResId: Int,
): VH {
	val inflater = LayoutInflater.from(parent.context)
	val view = inflater.inflate(layoutResId, parent, false)
	return this.invoke(view)
}
