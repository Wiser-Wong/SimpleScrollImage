package com.wiser.splashscrollimage.landscape

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wiser.splashscrollimage.R
import kotlinx.android.synthetic.main.activity_scroll_image_landscape.*

class LandscapeScrollImageActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_image_landscape)

        siv?.setResIds(mutableListOf(R.drawable.aa,R.drawable.bb,R.drawable.b,R.drawable.e),true)

    }

}