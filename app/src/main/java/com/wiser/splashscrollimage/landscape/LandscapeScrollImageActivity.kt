package com.wiser.splashscrollimage.landscape

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.wiser.splashscrollimage.R
import com.wiser.splashscrollimage.ScrollLinearLayoutManager
import kotlinx.android.synthetic.main.activity_scroll_image.*

class LandscapeScrollImageActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_image)

//        rlv?.layoutManager = ScrollLinearLayoutManager(this,LinearLayoutManager.HORIZONTAL)
//        rlv?.adapter = LandscapeAdapter()
//
//        rlv?.smoothScrollToPosition(Int.MAX_VALUE / 2)
    }

}