package com.wiser.splashscrollimage

import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout

/**
 * @author Wiser
 *
 * 会滚动的图片
 */
class ScrollImageView(context: Context, attr: AttributeSet) : FrameLayout(context, attr) {

    private var isFirstInit = true

    private var fitImageView: FitImageView? = null

    /**
     * 方向
     * @see PORTRAIT 纵向
     * @see LANDSCAPE 横向
     */
    private var direction: Int = PORTRAIT

    /**
     * 图片id
     */
    private var src: Int = -1

    /**
     * 是否自动播放
     */
    private var autoPlay: Boolean = false

    /**
     * 播放时长
     */
    private var duration: Int = 5000

    /**
     * 动画
     */
    private var animator: ValueAnimator? = null

    /**
     * 图片数组 本地图片
     */
    private var resIds: MutableList<Int>? = null

    /**
     * 图片数组 网络图片
     */
    private var urls: MutableList<String>? = null

    companion object {
        /**
         * 纵向
         */
        const val PORTRAIT = 1

        /**
         * 横向
         */
        const val LANDSCAPE = 0
    }

    init {
        val ta = context.obtainStyledAttributes(attr, R.styleable.ScrollImageView)
        direction = ta.getInt(R.styleable.ScrollImageView_siv_direction, PORTRAIT)
        duration = ta.getInt(R.styleable.ScrollImageView_siv_duration, duration)
        src = ta.getResourceId(R.styleable.ScrollImageView_siv_src, -1)
        autoPlay = ta.getBoolean(R.styleable.ScrollImageView_siv_autoPlay, false)
        ta.recycle()

        // 设置图片属性
        fitImageView = FitImageView(context, attr)
        val layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        if (src != -1) {
            fitImageView?.setImageResource(src)
        }
        fitImageView?.setDirection(direction)
        fitImageView?.layoutParams = layoutParams
        addView(fitImageView)

        initAnimator()
    }

    /**
     * 初始化动画
     */
    private fun initAnimator() {
        animator = ValueAnimator.ofFloat()
        animator?.duration = duration.toLong()
        animator?.interpolator = LinearInterpolator()
        animator?.addUpdateListener {
            val value: Float? = it.animatedValue as? Float
            if (value != null) {
                when (direction) {
                    LANDSCAPE -> childView()?.translationX = -value
                    PORTRAIT -> childView()?.translationY = -value
                }
            }
        }
    }

    /**
     * 设置动画监听
     */
    fun setAnimatorListener(animatorListenerAdapter: AnimatorListenerAdapter?) {
        animator?.addListener(animatorListenerAdapter)
    }

    /**
     * 获取图片加载控件
     */
    fun getImageView(): FitImageView? = fitImageView

    /**
     * 设置图片组 本地图片
     */
    fun setResIds(resIds: MutableList<Int>?) {
        this.resIds = resIds
    }

    /**
     * 设置图片组 网络图片
     */
    fun setUrls(urls: MutableList<String>) {
        this.urls = urls
    }

    /**
     * 是否内部有子View
     */
    private fun isHaveView(): Boolean = childCount == 1

    /**
     * 子View
     */
    private fun childView(): View? {
        if (isHaveView()) return getChildAt(0)
        return null
    }

    /**
     * 开始滚动
     */
    fun startScroll() {
        var scrollDistance = 0f
        when (direction) {
            LANDSCAPE -> scrollDistance =
                (childView()?.width?.minus(measuredWidth))?.toFloat() ?: 0f
            PORTRAIT -> scrollDistance =
                (childView()?.height?.minus(measuredHeight))?.toFloat() ?: 0f
        }
        animator?.setFloatValues(0f, scrollDistance)
        animator?.start()
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        if (hasWindowFocus && isFirstInit) {
            if (autoPlay) {
                postDelayed(Runnable {
                    startScroll()
                }, 800)
            }
            isFirstInit = false
        }
    }


}