package com.spoelt.simpsonsquotes.domain.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.spoelt.simpsonsquotes.R
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    //TODO("Replace with Coil?")
    url?.let {
        Picasso.get()
            .load(url)
            //.placeholder(R.drawable.ic_no_image)
            // if the image is not delivered from memory, picasso looks at disk cache - skip that!
            .networkPolicy(NetworkPolicy.OFFLINE, NetworkPolicy.NO_CACHE)
            .error(R.drawable.ic_no_image)
            .into(view)
    }

}
