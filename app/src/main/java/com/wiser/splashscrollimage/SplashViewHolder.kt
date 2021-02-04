package com.wiser.splashscrollimage

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SplashViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val pic: FitImageView = itemView.findViewById(R.id.pic)
}