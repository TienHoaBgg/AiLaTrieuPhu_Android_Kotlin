package com.nth.demoailatrieuphu.Utils


import android.content.ContentResolver
import android.content.Context
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.nth.demoailatrieuphu.R
import java.io.File


class Utils {

    companion object {

        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(iv: ImageView, link:Int) {
            if (link == null) {
                Glide.with(iv)
                    .load(R.drawable.ic_logo)
                    .into(iv)
            } else {
                Glide.with(iv)
                    .load(link)
                    .placeholder(R.drawable.ic_logo)
                    .error(R.drawable.ic_logo)
                    .into(iv)
            }
        }

        @JvmStatic
        @BindingAdapter("updateText")
        fun updateText(tv: TextView, value: String?) {
            tv.text = value
        }

        @JvmStatic
        @BindingAdapter("updateLevel")
        fun updateLevel(tv: TextView, value: Int?) {
            tv.text = "Câu $value"
        }
        @JvmStatic
        @BindingAdapter("updateBonus")
        fun updateBonus(tv: TextView, value: Int?) {
            if (value != null) {
                if(value in 1..9){
                    tv.text = "0$value"
                }else{
                    tv.text = "$value"
                }
            }
        }
        fun getRawUri(context:Context,filename: String): Uri? {
            return Uri.parse(
                ContentResolver.SCHEME_ANDROID_RESOURCE +File.pathSeparator +"/"+  File.separator.toString() + context.packageName
                    .toString() + "/raw/" + filename
            )
        }
        fun audioClick(context: Context) {
            val media = MediaPlayer.create(context, R.raw.touch_sound)
            media.isLooping = false
            media.start()
            media.setOnCompletionListener {
                media.release()
            }
        }

    }
}