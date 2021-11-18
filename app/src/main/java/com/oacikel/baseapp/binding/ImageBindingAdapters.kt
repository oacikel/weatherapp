package com.oacikel.baseapp.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.oacikel.baseapp.db.entity.marvelEntities.CharacterEntity
import com.oacikel.baseapp.db.entity.marvelEntities.ComicEntity

object ImageBindingAdapters {
    @JvmStatic
    @BindingAdapter("entityOfImage")
    fun showEntityImageOnView(img: ImageView, entity: Any?) {
        if (entity != null) {
            var path: String = ""
            if (entity is CharacterEntity) {
                path = entity.thumbnail!!.path + "." + entity.thumbnail!!.extension
            } else if (entity is ComicEntity) {
                path = entity.thumbnail!!.path + "." + entity.thumbnail!!.extension
            }
            if (entity != null) {
                val multiLeft = MultiTransformation(
                    CenterInside(),
                    FitCenter(),
                )
                Glide.with(img.context)
                    .load(path)
                    .apply(RequestOptions.bitmapTransform(multiLeft))
                    .into(img)
            }
        }
    }
}