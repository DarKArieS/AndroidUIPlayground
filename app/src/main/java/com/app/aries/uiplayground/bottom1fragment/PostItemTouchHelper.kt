package com.app.aries.uiplayground.bottom1fragment

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.app.aries.uiplayground.R
import timber.log.Timber
import java.util.*

class PostItemTouchHelper(val context: Context,
                          val adapter:HomeRecyclerViewAdapter) :
    ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT
    )
{
    init{

    }

    override fun canDropOver(
        recyclerView: RecyclerView,
        current: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return super.canDropOver(recyclerView, current, target)
    }

    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX_: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val dX :Float
        // max = 1440 in my test device
        if(dX_<-400){
            dX = -400f
        }else{
            dX = dX_
        }

        Timber.tag("TouchHelper").d("onChildDraw $dX_ , $dX")

        val icon = ContextCompat.getDrawable(context, R.drawable.ic_settings_white_24dp)!!
        val background = ColorDrawable(Color.RED)

        val itemView = viewHolder.itemView
        val backgroundCornerOffset = 20

        val iconMargin = (itemView.getHeight() - icon.intrinsicHeight) / 2
        val iconTop = itemView.getTop() + (itemView.getHeight() - icon.intrinsicHeight) / 2
        val iconBottom = iconTop + icon.intrinsicHeight

        if (dX > 0) { // Swiping to the right
            val iconLeft = itemView.left + iconMargin + icon.intrinsicWidth
            val iconRight = itemView.left + iconMargin
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)


            background.setBounds(
                itemView.getLeft(), itemView.getTop(),
                (itemView.getLeft() + dX).toInt() + backgroundCornerOffset,
                itemView.getBottom()
            )
        } else if (dX < 0) { // Swiping to the left
            val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
            val iconRight = itemView.right - iconMargin
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

            background.setBounds(
                (itemView.getRight() + dX).toInt() - backgroundCornerOffset,
                itemView.getTop(), itemView.getRight(), itemView.getBottom()
            )
        } else { // view is unSwiped
            background.setBounds(0, 0, 0, 0)
        }
        background.draw(canvas)
        icon.draw(canvas)

         super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        Timber.tag("TouchHelper").d("onSelectedChanged")
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        Timber.tag("TouchHelper").d("clearView")
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        Timber.tag("TouchHelper").d("onMove")

        Collections.swap(adapter.itemList,viewHolder.getAdapterPosition(),target.getAdapterPosition())
        adapter.notifyItemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());

        return true
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.15f
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        Timber.tag("TouchHelper").d("onSwiped")


    }

}