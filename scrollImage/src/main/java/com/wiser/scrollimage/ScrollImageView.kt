package com.wiser.scrollimage

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.ViewSwitcher

/**
 * @author Wiser
 *
 * 会滚动的图片
 */
class ScrollImageView(context: Context, var attr: AttributeSet) : FrameLayout(context, attr),
    ViewSwitcher.ViewFactory {

    private var isFirstInit = true

    private var imageSwitcher: ImageSwitcher? = null

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
     * 延迟播放时间
     */
    private var delayPlayDuration: Int = 500

    /**
     * 切换图片间隔时间
     */
    private var switchIntervalDuration: Int = 1500

    /**
     * 播放速度
     * 优先级 高于 播放时间
     * 单位s
     */
    private var playSpeed: Int = 0

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

    /**
     * 记录播放的位置
     */
    private var index: Int = -1

    /**
     * 是否循环播放
     */
    private var isCirclePlay: Boolean = false

    /**
     * 是否正在播放
     */
    private var isPlaying: Boolean = false

    /**
     * 切换进入动画
     */
    private var switchAnimIn: Int = android.R.anim.fade_in

    /**
     * 切换退出动画
     */
    private var switchAnimOut: Int = android.R.anim.fade_out

    /**
     * 加载网络图片监听器
     */
    private var loadNetImageListener: LoadNetImageListener? = null

    /**
     * 是否初始化切换动画
     */
    private var isInitSwitchAnim: Boolean = true;

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
        switchIntervalDuration = ta.getInt(
            R.styleable.ScrollImageView_siv_switch_interval_duration,
            switchIntervalDuration
        )
        delayPlayDuration =
            ta.getInt(R.styleable.ScrollImageView_siv_delayPlayDuration, delayPlayDuration)
        playSpeed = ta.getInt(R.styleable.ScrollImageView_siv_speed, playSpeed)
        src = ta.getResourceId(R.styleable.ScrollImageView_siv_src, -1)
        autoPlay = ta.getBoolean(R.styleable.ScrollImageView_siv_autoPlay, autoPlay)
        isCirclePlay = ta.getBoolean(R.styleable.ScrollImageView_siv_circlePlay, isCirclePlay)
        switchAnimIn = ta.getResourceId(R.styleable.ScrollImageView_siv_switch_anim_in, switchAnimIn)
        switchAnimOut = ta.getResourceId(R.styleable.ScrollImageView_siv_switch_anim_out, switchAnimOut)
        ta.recycle()

        addView(initImageSwitcher())

        initAnimator()

    }

    /**
     * 初始化ImageSwitcher
     */
    private fun initImageSwitcher(): ImageSwitcher? {
        imageSwitcher = ImageSwitcher(context, attr)
        val layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        imageSwitcher?.layoutParams = layoutParams
//        imageSwitcher?.setInAnimation(context, switchAnimIn)
//        imageSwitcher?.setOutAnimation(context, switchAnimOut)
        imageSwitcher?.setFactory(this)
        return imageSwitcher
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
        animator?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                if (!isPlaying) {
                    return
                }
                postDelayed(Runnable {
                    ++index
                    // 是否能够继续播放
                    if (isCanContinuePlay()) {
                        if (isInitSwitchAnim) {
                            imageSwitcher?.setInAnimation(context, switchAnimIn)
                            imageSwitcher?.setOutAnimation(context, switchAnimOut)
                            isInitSwitchAnim = false
                        }
                        startScroll(index)
                    } else {
                        // 是否循环播放
                        if (isCirclePlay) {
                            if (isInitSwitchAnim) {
                                imageSwitcher?.setInAnimation(context, switchAnimIn)
                                imageSwitcher?.setOutAnimation(context, switchAnimOut)
                                isInitSwitchAnim = false
                            }
                            startScroll(0)
                        }
                    }
                }, switchIntervalDuration.toLong())
            }
        })
    }

    /**
     * 设置加载网络图片监听
     */
    fun setLoadNetImageListener(loadNetImageListener: LoadNetImageListener?) {
        this.loadNetImageListener = loadNetImageListener
    }

    /**
     * 获取图片加载控件
     */
    fun getImageSwitcher(): ImageSwitcher? = imageSwitcher

    /**
     * 设置图片组 本地图片
     */
    fun setResIds(resIds: MutableList<Int>?,isPlay: Boolean = false) {
        this.resIds = resIds
        resIds?.get(0)?.let {
            this.index = 0
            imageSwitcher?.setImageResource(it)
        }
        if (isPlay && !isPlaying) startScroll()
    }

    /**
     * 设置图片组 网络图片
     */
    fun setUrls(urls: MutableList<String>?,isPlay: Boolean = false) {
        this.urls = urls
        urls?.get(0)?.let {
            this.index = 0
            loadNetImageListener?.loadNetImage(it)
        }
        if (isPlay && !isPlaying) startScroll()
    }

    /**
     * 获取本地资源图片id集合
     */
    fun getResIds(): MutableList<Int>? = resIds

    /**
     * 获取网络图片地址集合
     */
    fun getUrls(): MutableList<String>? = urls

    /**
     * 获取播放位置
     */
    fun getPlayIndex(): Int = index

    /**
     * 设置播放速度
     */
    fun setPlaySpeed(playSpeed: Int) {
        this.playSpeed = playSpeed
    }

    /**
     * 获取播放速度
     */
    fun getPlaySpeed(): Int = playSpeed

    /**
     * 获取播放方向
     */
    fun getDirection(): Int = direction

    /**
     * 是否循环播放
     */
    fun isCirclePlay(): Boolean = isCirclePlay

    /**
     * 设置循环播放
     */
    fun setCirclePlay(isCirclePlay: Boolean) {
        this.isCirclePlay = isCirclePlay
    }

    /**
     * 是否内部有子View
     */
    private fun isHaveView(): Boolean = childCount == 1

    /**
     * 子View
     */
    private fun childView(): View? {
        if (isHaveView()) return imageSwitcher?.currentView
        return null
    }

    /**
     * 开始滚动
     */
    fun startScroll(index: Int = -1) {

        setIndex(index)
        setImageViewResource()

        // 没有资源就不滚动
        if ((src == -1 && (urls == null || urls?.size == 0) && (resIds == null || resIds?.size == 0))) {
            isPlaying = false
            return
        }

        postDelayed(Runnable {
            isPlaying = true
            var scrollDistance = 0f
            when (direction) {
                LANDSCAPE -> scrollDistance =
                    (childView()?.width?.minus(measuredWidth))?.toFloat() ?: 0f
                PORTRAIT -> scrollDistance =
                    (childView()?.height?.minus(measuredHeight))?.toFloat() ?: 0f
            }
            animator?.setFloatValues(0f, scrollDistance)
            // 根据速度计算时间，优先级高于同时有设置了时间
            if (playSpeed > 0 && scrollDistance > 0) {
                animator?.duration = ((scrollDistance / playSpeed) * 1000).toLong()
            }
            animator?.start()
        }, delayPlayDuration.toLong())
    }

    /**
     * 暂停滚动
     */
    fun pauseScroll() {
        animator?.pause()
        isPlaying = false
    }

    /**
     * 恢复滚动
     */
    fun resumeScroll() {
        animator?.resume()
        isPlaying = true
    }

    /**
     * 停止
     */
    fun stopPlay() {
        isPlaying = false
        animator?.cancel()
        resetTranslation()
    }

    /**
     * 设置图片资源
     */
    private fun setImageViewResource() {
        resIds?.let {
            if (it.size > index && index >= 0) {
                it[index].let { resId ->
                    imageSwitcher?.setImageResource(resId)
                    resetTranslation()
                }
            }
        }
        urls?.let {
            if (it.size > index && index >= 0) {
                it[index].let { url ->
                    loadNetImageListener?.loadNetImage(url)
                }
            }
        }
    }

    /**
     * 恢复移动距离
     */
    fun resetTranslation() {
        when (direction) {
            LANDSCAPE -> childView()?.translationX = 0f
            PORTRAIT -> childView()?.translationY = 0f
        }
    }

    /**
     * 设置播放的位置
     */
    private fun setIndex(position: Int) {
        resIds?.let {
            if (it.size > position && position >= 0) {
                this.index = position
            }
        }
        urls?.let {
            if (it.size > position && position >= 0) {
                this.index = position
            }
        }
    }

    /**
     * 是否能够继续播放
     */
    private fun isCanContinuePlay(): Boolean {
        if (index >= 0 && (resIds?.size ?: 0 > index || urls?.size ?: 0 > index)) {
            return true
        }
        return false
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        if (hasWindowFocus && isFirstInit) {
            if (autoPlay) {
                startScroll()
            }
            isFirstInit = false
        }
    }

    override fun makeView(): View {
        // 设置图片属性
        val fitImageView = FitImageView(context, attr)
        val layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        fitImageView.scaleType = ImageView.ScaleType.CENTER_CROP
        if (src != -1) {
            fitImageView.setImageResource(src)
        }
        fitImageView.setDirection(direction)
        fitImageView.layoutParams = layoutParams
        return fitImageView
    }

    /**
     * 销毁
     */
    fun detach() {
        imageSwitcher = null
        index = -1
        animator?.cancel()
        animator = null
        resIds?.clear()
        resIds = null
        urls?.clear()
        urls = null
        loadNetImageListener = null
    }
}