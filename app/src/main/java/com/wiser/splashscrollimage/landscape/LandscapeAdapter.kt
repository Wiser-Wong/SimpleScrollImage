package com.wiser.splashscrollimage.landscape

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wiser.splashscrollimage.R
import com.wiser.splashscrollimage.SplashViewHolder

class LandscapeAdapter :
    RecyclerView.Adapter<SplashViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SplashViewHolder =
        SplashViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.splash_landscape_image_item, parent, false)
        )

    override fun onBindViewHolder(holder: SplashViewHolder, position: Int) {
    }

    override fun getItemCount(): Int = 1

}