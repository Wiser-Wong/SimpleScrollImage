package com.wiser.splashscrollimage.portrait

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wiser.splashscrollimage.R
import com.wiser.splashscrollimage.ScrollLinearLayoutManager
import kotlinx.android.synthetic.main.activity_scroll_image.*

class PortraitScrollImageActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_image)

//        rlv?.layoutManager = ScrollLinearLayoutManager(this)
//        rlv?.adapter = PortraitAdapter()
//
//        rlv?.smoothScrollToPosition(Int.MAX_VALUE / 2)
    }

}