package com.wiser.splashscrollimage

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.ceil

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
                    height = MeasureSpec.getSize(widthMeasureSpec)
                    width = ceil(
                        (height.toFloat() * drawable.intrinsicWidth.toFloat() / drawable.intrinsicHeight
                            .toFloat()).toDouble()
                    ).toInt()
                }
            }

            setMeasuredDimension(width, height)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

}