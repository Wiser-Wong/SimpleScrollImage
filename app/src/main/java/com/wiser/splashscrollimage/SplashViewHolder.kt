package com.wiser.splashscrollimage

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.wiser.scrollimage.FitImageView

class SplashViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val pic: FitImageView = itemView.findViewById(R.id.pic)
}