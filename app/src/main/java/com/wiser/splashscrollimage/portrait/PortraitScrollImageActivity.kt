package com.wiser.splashscrollimage.portrait

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.wiser.scrollimage.LoadNetImageListener
import com.wiser.splashscrollimage.R
import kotlinx.android.synthetic.main.activity_scroll_image_portrait.*

class PortraitScrollImageActivity : AppCompatActivity() {

    private var isClick = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_image_portrait)

//        rlv?.layoutManager = ScrollLinearLayoutManager(this)
//        rlv?.adapter = PortraitAdapter()
//
//        rlv?.smoothScrollToPosition(Int.MAX_VALUE / 2)

//        siv?.setResIds(mutableListOf(R.drawable.d,R.drawable.c))

        siv?.setLoadNetImageListener(object : LoadNetImageListener {
            override fun loadNetImage(url: String) {
                Glide.with(this@PortraitScrollImageActivity).load(url).asBitmap().into(object :
                    SimpleTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap?,
                        glideAnimation: GlideAnimation<in Bitmap>?
                    ) {
                        siv?.getImageSwitcher()?.setImageDrawable(BitmapDrawable(null, resource))
                        siv?.resetTranslation()
                    }
                })
            }
        })
        siv?.setUrls(
            mutableListOf(
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F202003%2F12%2F2020031281829_NGYhH.thumb.400_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616652276&t=569fb9467fc24675d2c8a7a549ff3acf",
                "https://upload-images.jianshu.io/upload_images/5196125-6accb784544121d4.jpeg?imageMogr2/auto-orient/strip|imageView2/2/w/348",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fcms-bucket.nosdn.127.net%2F08d4ff98e8c546f3ab663ff01f23ea8120180421142038.jpeg%3FimageView%26thumbnail%3D550x0&refer=http%3A%2F%2Fcms-bucket.nosdn.127.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616582270&t=b96a2d11d4b5987ff761732571494580",
                "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1029839458,1788453591&fm=26&gp=0.jpg",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwx1.sinaimg.cn%2Fmw690%2F712d936bgy1gb20obq9etj20m81uo7hk.jpg&refer=http%3A%2F%2Fwx1.sinaimg.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616578893&t=39d0d648985ae44d038f0a049aece607",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fww3.sinaimg.cn%2Fmw690%2F6f107358gy1gn4lurzd0gj21wx4211ky.jpg&refer=http%3A%2F%2Fwww.sina.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616582193&t=a19970a9e3cb7bb07f8518ba8d1d25dc",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fcdn.huodongxing.com%2Ffile%2Fue%2F20150707%2F115844BC472646D72F29133C40FE7AE146%2F30753979790503190.jpeg&refer=http%3A%2F%2Fcdn.huodongxing.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616578893&t=163ec094e0c53e406c7466e5c8cf9043"
            )
        )

        siv?.setOnClickListener {
            isClick = if (!isClick) {
                siv?.pauseScroll()
                true
            } else {
                siv?.resumeScroll()
                false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        siv?.detach()
    }

}