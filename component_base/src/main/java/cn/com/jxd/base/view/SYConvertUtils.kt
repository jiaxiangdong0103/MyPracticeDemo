package cn.com.jxd.base.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.View
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * @author xiangdong.jia
 * @description:
 * @date :2021/6/2 下午5:23
 */
class SYConvertUtils {

    private val hexDigits =
        charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')




    /**
     * chars转bytes
     *
     * @param chars
     * @return
     */
    fun chars2Bytes(chars: CharArray?): ByteArray? {
        if (chars == null || chars.size <= 0) return null
        val len = chars.size
        val bytes = ByteArray(len)
        for (i in 0 until len) {
            bytes[i] = chars[i].toByte()
        }
        return bytes
    }



    /**
     * 十六进制字符串转bytes
     *
     * @param hexString
     * @return
     */
    fun hexString2Bytes(hexString: String): ByteArray? {
        var hexString = hexString
        if (TextUtils.isEmpty(hexString)) return null
        var len = hexString.length
        if (len % 2 != 0) {
            hexString = "0$hexString"
            len = len + 1
        }
        val hexBytes = hexString.toUpperCase().toCharArray()
        val ret = ByteArray(len shr 1)
        var i = 0
        while (i < len) {
            ret[i shr 1] = (hex2Int(hexBytes[i]) shl 4 or hex2Int(hexBytes[i + 1])).toByte()
            i += 2
        }
        return ret
    }



    /**
     * 十六进制转int
     *
     * @param hexChar
     * @return
     */
    private fun hex2Int(hexChar: Char): Int {
        return if (hexChar >= '0' && hexChar <= '9') {
            hexChar - '0'
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            hexChar - 'A' + 10
        } else {
            throw IllegalArgumentException()
        }
    }

    /**
     * bytes转Bitmap
     *
     * @param bytes
     * @return
     */
    fun bytes2Bitmap(bytes: ByteArray?): Bitmap? {
        return if (bytes == null || bytes.size == 0) null else BitmapFactory.decodeByteArray(
            bytes,
            0,
            bytes.size
        )
    }

    /**
     * drawable转Bitmap
     *
     * @param drawable
     * @return
     */
    fun drawable2Bitmap(drawable: Drawable): Bitmap? {
        if (drawable is BitmapDrawable) {
            val bitmapDrawable = drawable
            if (bitmapDrawable.bitmap != null) {
                return bitmapDrawable.bitmap
            }
        }
        val bitmap: Bitmap
        bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            Bitmap.createBitmap(
                1, 1,
                if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
            )
        } else {
            Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
            )
        }
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    /**
     * Bitmap转drawable
     *
     * @param context
     * @param bitmap
     * @return
     */
    fun bitmap2Drawable(context: Context, bitmap: Bitmap?): Drawable? {
        return if (bitmap == null) null else BitmapDrawable(context.resources, bitmap)
    }

    /**
     * view转Bitmap
     *
     * @param view
     * @return
     */
    fun view2Bitmap(view: View?): Bitmap? {
        if (view == null) return null
        val ret = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(ret)
        val bgDrawable = view.background
        if (bgDrawable != null) {
            bgDrawable.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return ret
    }


}