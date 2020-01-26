package com.example.stunitastest.presentation.bindingAdapter

import android.graphics.ColorFilter
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.signature.ObjectKey
import com.example.stunitastest.R
import com.example.stunitastest.entity.Document

object ImageBindingAdapter {
    @JvmStatic
    @BindingAdapter("srcCompat")
    fun srcCompat(view: ImageView, data: Document) {
        Glide.with(view.context).load(data.image_url).thumbnail(0.1f)
            .error(R.drawable.img_error)
            .signature(ObjectKey(data))
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("srcCompat")
    fun srcCompat(view: ImageView, url: String) {
        var circularProgressDrawable = CircularProgressDrawable(view.context)
        circularProgressDrawable.strokeWidth = 10f
        circularProgressDrawable.centerRadius = 40f
        circularProgressDrawable.start()

        Glide.with(view.context).load(url).thumbnail(0.1f)
            .placeholder(circularProgressDrawable)
            .error(R.drawable.img_error)
            .into(view)
    }
}