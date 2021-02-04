package com.wiser.splashscrollimage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.wiser.splashscrollimage.landscape.LandscapeScrollImageActivity
import com.wiser.splashscrollimage.portrait.PortraitScrollImageActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun portraitScrollImage(view: View) {
        startActivity(Intent(this, PortraitScrollImageActivity::class.java))
    }

    fun landscapeScrollImage(view: View) {
//        startActivity(Intent(this, LandscapeScrollImageActivity::class.java))
        siv?.startScroll()
    }
}