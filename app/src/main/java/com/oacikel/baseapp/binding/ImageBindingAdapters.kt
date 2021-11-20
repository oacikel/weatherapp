package com.oacikel.baseapp.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.request.RequestOptions


object ImageBindingAdapters {

    @JvmStatic
    @BindingAdapter("imageCode")
    fun setImage(img: ImageView, imageCode: String?) {
    val imageUrl= "http://openweathermap.org/img/wn/$imageCode@2x.png"
        if (imageCode != null) {
            val multiLeft = MultiTransformation(
                CenterInside(),
                FitCenter(),
            )

            Glide.with(img.context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions.bitmapTransform(multiLeft))
                .into(img)
        }

    }
}