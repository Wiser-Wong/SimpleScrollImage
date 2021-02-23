package com.wiser.scrollimage

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.ceil

/**
 * 超过所展示的界面不裁剪也不拉伸，平铺展示
 * @author Wiser
 */
class FitImageView(context: Context, attr: AttributeSet) : AppCompatImageView(context, attr) {

    private var direction: Int = PORTRAIT

    companion object {
        const val PORTRAIT = 1
        const val LANDSCAPE = 0
    }

    init {
        val ta: TypedArray = context.obtainStyledAttributes(attr, R.styleable.FitImageView)
        direction = ta.getInt(R.styleable.FitImageView_fiv_direction, PORTRAIT)
        ta.recycle()
    }

    fun setDirection(direction: Int) {
        this.direction = direction
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (drawable != null) {
            var width = 0
            var height = 0
            when (direction) {
                PORTRAIT -> {
                    width = MeasureSpec.getSize(widthMeasureSpec)
                    height = ceil(
                        (width.toFloat() * drawable.intrinsicHeight.toFloat() / drawable.intrinsicWidth
                            .toFloat()).toDouble()
                    ).toInt()
                }
                LANDSCAPE -> {
                    height = MeasureSpec.getSize(heightMeasureSpec)
                    width = ceil(
                        (height.toFloat() * drawable.intrinsicWidth.toFloat() / drawable.intrinsicHeight
                            .toFloat()).toDouble()
                    ).toInt()
                }
            }

            setMeasuredDimension(
                if (width > MeasureSpec.getSize(widthMeasureSpec)) width else MeasureSpec.getSize(
                    widthMeasureSpec
                ),
                if (height > MeasureSpec.getSize(heightMeasureSpec)) height else MeasureSpec.getSize(
                    heightMeasureSpec
                )
            )
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

}