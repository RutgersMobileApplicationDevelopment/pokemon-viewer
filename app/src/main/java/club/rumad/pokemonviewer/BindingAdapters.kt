package club.rumad.pokemonviewer

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    if (url != null) {
        Glide.with(this).load(url).into(this)
    } else {
        setImageDrawable(null)
    }
}
