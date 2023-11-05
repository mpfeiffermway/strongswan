package org.strongswan.android.enterprise.ui.widget

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ItemSwipeHelper private constructor(
	onItemSwiped: suspend (RecyclerView.ViewHolder) -> Unit,
) : ItemTouchHelper(ItemSwipeCallback(onItemSwiped)) {

	companion object {
		fun RecyclerView.onItemSwiped(
			onItemSwiped: suspend (RecyclerView.ViewHolder) -> Unit,
		) {
			val itemDragHelper = ItemSwipeHelper(onItemSwiped)
			itemDragHelper.attachToRecyclerView(this)
		}
	}

	private class ItemSwipeCallback(val onSwipe: suspend (RecyclerView.ViewHolder) -> Unit) :
		SimpleCallback(0, START or END),
		CoroutineScope by MainScope() {

		override fun onMove(
			recyclerView: RecyclerView,
			viewHolder: RecyclerView.ViewHolder,
			target: RecyclerView.ViewHolder,
		) = false

		override fun getSwipeDirs(
			recyclerView: RecyclerView,
			viewHolder: RecyclerView.ViewHolder,
		): Int {
			return when (viewHolder is SupportsSwipe && viewHolder.isLocked) {
				true -> 0
				else -> super.getSwipeDirs(recyclerView, viewHolder)
			}
		}

		override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
			launch { onSwipe(viewHolder) }
		}

		override fun isLongPressDragEnabled() = false
	}

	interface SupportsSwipe {
		val isLocked: Boolean
			get() = false
	}
}
