package cn.com.jxd.studyui.image

import android.app.Activity
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition

/**
 * @author xiangdong.jia
 * @description:
 * @date :2021/6/10 下午2:10
 */
object ImageUtil {
    fun loadBitmap(
        context: Activity,
        url: String,
        resourceBitmap: ((bitmap: Bitmap) -> Unit)
    ) {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    resourceBitmap.invoke(resource)
                }

            })

    }
}