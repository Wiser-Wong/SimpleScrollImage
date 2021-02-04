package com.wiser.splashscrollimage

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PointF
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

@SuppressLint("WrongConstant")
class ScrollLinearLayoutManager(
    private val context: Context, @RecyclerView.Orientation direction: Int = VERTICAL,
    reverseLayout: Boolean = false
) : LinearLayoutManager(context, direction, reverseLayout) {

    private var speed = 5f //修改可以改变数据,越大速度越慢

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView,
        state: RecyclerView.State?,
        position: Int
    ) {
        val linearSmoothScroller: LinearSmoothScroller =
            object : LinearSmoothScroller(recyclerView.context) {
                override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
                    return this@ScrollLinearLayoutManager
                        .computeScrollVectorForPosition(targetPosition)
                }

                override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                    return speed / displayMetrics.density
                    //返回滑动一个pixel需要多少毫秒
                }
            }
        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }

    //可以用来设置速度
    fun setSpeedSlow(x: Float) {
        //自己在这里用density去乘，希望不同分辨率设备上滑动速度相同
        //0.3f是自己估摸的一个值，可以根据不同需求自己修改
        speed = context.resources.displayMetrics.density * 0.3f + x
    }
}