package org.strongswan.android.enterprise.ui.widget

import android.content.Context
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import org.strongswan.android.enterprise.util.Named

class NamedValue<E : Named>(
	val enum: E,
	val name: String,
) {
	companion object {
		fun <E : Named> adapter(
			context: Context,
			@LayoutRes
			resource: Int,
			values: Array<E>,
		): ArrayAdapter<NamedValue<E>> {
			val objects = values.map { NamedValue(it, context.getString(it.nameResId)) }

			return ArrayAdapter(
				context,
				resource,
				objects,
			)
		}
	}

	override fun toString() = name
}
