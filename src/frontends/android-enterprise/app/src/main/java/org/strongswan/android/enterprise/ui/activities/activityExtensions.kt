package org.strongswan.android.enterprise.ui.activities

import android.app.Activity
import android.view.LayoutInflater
import android.widget.EditText
import androidx.viewbinding.ViewBinding

fun <T : ViewBinding> Activity.setContentView(
	viewBindingInflate: (inflater: LayoutInflater) -> T,
): T {
	val viewBinding = viewBindingInflate.invoke(layoutInflater)
	setContentView(viewBinding.root)
	return viewBinding
}

fun EditText.value(): String {
	return this.text.toString()
}

fun EditText.valueOrNull(): String? {
	return this.value().ifBlank { null }
}
