package cn.com.jxd.base.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.Bitmap.CompressFormat
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import cn.com.jxd.commonutil.utils.SYFileUtils
import cn.com.jxd.commonutil.utils.SYViewUtil
import java.io.*

/**
 * @author xiangdong.jia
 * @description:
 * @date :2021/6/2 下午5:05
 */
object SYImageUtils {


    /**
     * bitmap转bytes
     *
     * @param bitmap
     * @param format
     * @return
     */
    fun bitmap2Bytes(bitmap: Bitmap?, format: CompressFormat?): ByteArray? {
        if (bitmap == null) return null
        val baos = ByteArrayOutputStream()
        bitmap.compress(format, 100, baos)
        return baos.toByteArray()
    }

    /**
     * bytes转bitmap
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
     * drawable转bitmap
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
     * bitmap转drawable
     *
     * @param context
     * @param bitmap
     * @return
     */
    fun bitmap2Drawable(context: Context, bitmap: Bitmap?): Drawable? {
        return if (bitmap == null) null else BitmapDrawable(context.resources, bitmap)
    }

    /**
     * dawable转bytes
     *
     * @param drawable
     * @param format
     * @return
     */
    fun drawable2Bytes(drawable: Drawable?, format: CompressFormat?): ByteArray? {
        return if (drawable == null) null else bitmap2Bytes(drawable2Bitmap(drawable), format)
    }

    /**
     * bytes转drawable
     *
     * @param context
     * @param bytes
     * @return
     */
    fun bytes2Drawable(context: Context, bytes: ByteArray?): Drawable? {
        return bitmap2Drawable(context, bytes2Bitmap(bytes))
    }

    /**
     * view转bitmap
     *
     * @param view
     * @return
     */
    fun view2Bitmap(view: View): Bitmap? {
        var h = 0
        if (view is ViewGroup) {
            val viewGroup = view
            for (i in 0 until viewGroup.childCount) {
//                View childView = viewGroup.getChildAt(i);
                h += viewGroup.getChildAt(i).height
            }
        } else {
            h = SYViewUtil.getViewHeight(view)
        }
        if (view == null) return null
        val ret = Bitmap.createBitmap(
            SYViewUtil.getViewWidth(view),
            h,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(ret)
        val bgDrawable = view.background
        bgDrawable?.draw(canvas)
        canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return ret
    }

    /**
     * 获取bitmap
     *
     * @param file
     * @return
     */
    fun getBitmap(file: File?): Bitmap? {
        return if (file == null) {
            null
        } else BitmapFactory.decodeFile(file.absolutePath)
    }

    /**
     * 获取bitmap
     *
     * @param file
     * @param maxWidth
     * @param maxHeight
     * @return
     */
    fun getBitmap(file: File?, maxWidth: Int, maxHeight: Int): Bitmap? {
        if (file == null) return null
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(file.absolutePath, options)
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(file.absolutePath, options)
    }

    /**
     * 获取bitmap
     *
     * @param filePath
     * @param maxWidth
     * @param maxHeight
     * @return
     */
    fun getBitmap(filePath: String?, maxWidth: Int, maxHeight: Int): Bitmap? {
        if (TextUtils.isEmpty(filePath)) return null
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(filePath, options)
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(filePath, options)
    }

    /**
     * 获取bitmap
     *
     * @param is
     * @return
     */
    fun getBitmap(`is`: InputStream?): Bitmap? {
        return if (`is` == null) {
            null
        } else BitmapFactory.decodeStream(`is`)
    }


    /**
     * 获取bitmap
     *
     * @param data
     * @param offset
     * @return
     */
    fun getBitmap(data: ByteArray, offset: Int): Bitmap? {
        return if (data.size == 0) null else BitmapFactory.decodeByteArray(data, offset, data.size)
    }

    /**
     * 获取bitmap
     *
     * @param data
     * @param offset
     * @param maxWidth
     * @param maxHeight
     * @return
     */
    fun getBitmap(data: ByteArray, offset: Int, maxWidth: Int, maxHeight: Int): Bitmap? {
        if (data.size == 0) return null
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeByteArray(data, offset, data.size, options)
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeByteArray(data, offset, data.size, options)
    }

    /**
     * 获取bitmap
     *
     * @param context
     * @param resId
     * @return
     */
    fun getBitmap(context: Context?, @DrawableRes resId: Int): Bitmap? {
        val drawable = ContextCompat.getDrawable(context!!, resId)
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(
            drawable!!.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        return bitmap
    }

    /**
     * 获取bitmap
     *
     * @param context
     * @param resId
     * @param maxWidth
     * @param maxHeight
     * @return
     */
    fun getBitmap(
        context: Context,
        @DrawableRes resId: Int,
        maxWidth: Int,
        maxHeight: Int
    ): Bitmap? {
        val options = BitmapFactory.Options()
        val resources = context.resources
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, resId, options)
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(resources, resId, options)
    }


    /**
     * 保存bitmap到内部存储
     *
     * @param context
     * @param view    要保存的的view
     * @return
     */
    fun downloadImageToCache(context: Activity, view: View): String? {
        return downloadImage(view, PathManager.getInsideImageStoragePath(context))
    }

    /**
     * 保存bitmap到相册
     * 调用出 需要判断权限
     *
     * @param context
     * @param view    要保存的的view
     * @return
     */
    fun downloadImageToAlbum(context: Activity, view: View): String? {
        val filePath = downloadImage(view, PathManager.getOutsideImageStoragePath())
        if (!TextUtils.isEmpty(filePath)) {
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            val uri = Uri.fromFile(File(filePath))
            intent.data = uri
            context.sendBroadcast(intent) // 发送广播通知相册
        }
        return filePath
    }

    /**
     * 保存bitmap到指定路径
     *
     * @param parentPath
     * @param view       要保存的的view
     * @return
     */
    fun downloadImage(view: View, parentPath: String): String? {
        var filePath: String? = parentPath + "/" + System.currentTimeMillis() + ".png"
        var shareBitmap: Bitmap? = view2Bitmap(view)
        if (SYFileUtils().createOrExistsFile(filePath)) {
            Log.e("downloadImage:", "创建文件成功$filePath")
            if (saveBitmapToFile(shareBitmap, filePath)) {
                Log.e("downloadImage:", "保存文件成功$filePath")
            } else {
                filePath = null
                Log.e("downloadImage:", "保存文件失败")
            }
        } else {
            filePath = null
            Log.e("downloadImage:", "创建文件失败")
        }
        return filePath
    }

    /**
     * 获取bitmap
     *
     * @param fd
     * @return
     */
    fun getBitmap(fd: FileDescriptor?): Bitmap? {
        return if (fd == null) {
            null
        } else BitmapFactory.decodeFileDescriptor(fd)
    }

    /**
     * 获取bitmap
     *
     * @param fd
     * @param maxWidth
     * @param maxHeight
     * @return
     */
    fun getBitmap(fd: FileDescriptor?, maxWidth: Int, maxHeight: Int): Bitmap? {
        if (fd == null) {
            return null
        }
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFileDescriptor(fd, null, options)
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFileDescriptor(fd, null, options)
    }

    /**
     * 缩放图片
     *
     * @param src
     * @param newWidth
     * @param newHeight
     * @return
     */
    fun scale(src: Bitmap, newWidth: Int, newHeight: Int): Bitmap? {
        return scale(src, newWidth, newHeight, false)
    }

    /**
     * 缩放图片
     *
     * @param src
     * @param newWidth
     * @param newHeight
     * @param recycle
     * @return
     */
    fun scale(src: Bitmap, newWidth: Int, newHeight: Int, recycle: Boolean): Bitmap? {
        if (isEmptyBitmap(src)) return null
        val ret = Bitmap.createScaledBitmap(src, newWidth, newHeight, true)
        if (recycle && !src.isRecycled && ret != src) src.recycle()
        return ret
    }

    /**
     * 缩放图片
     *
     * @param src
     * @param scaleWidth
     * @param scaleHeight
     * @return
     */
    fun scale(src: Bitmap, scaleWidth: Float, scaleHeight: Float): Bitmap? {
        return scale(src, scaleWidth, scaleHeight, false)
    }

    /**
     * 缩放图片
     *
     * @param src
     * @param scaleWidth
     * @param scaleHeight
     * @param recycle
     * @return
     */
    fun scale(src: Bitmap, scaleWidth: Float, scaleHeight: Float, recycle: Boolean): Bitmap? {
        if (isEmptyBitmap(src)) return null
        val matrix = Matrix()
        matrix.setScale(scaleWidth, scaleHeight)
        val ret = Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
        if (recycle && !src.isRecycled && ret != src) src.recycle()
        return ret
    }

    /**
     * 旋转图片
     *
     * @param src
     * @param degrees
     * @param px
     * @param py
     * @return
     */
    fun rotate(src: Bitmap, degrees: Int, px: Float, py: Float): Bitmap? {
        return rotate(src, degrees, px, py, false)
    }

    /**
     * 旋转图片
     *
     * @param src
     * @param degrees
     * @param px
     * @param py
     * @param recycle
     * @return
     */
    fun rotate(src: Bitmap, degrees: Int, px: Float, py: Float, recycle: Boolean): Bitmap? {
        if (isEmptyBitmap(src)) return null
        if (degrees == 0) return src
        val matrix = Matrix()
        matrix.setRotate(degrees.toFloat(), px, py)
        val ret = Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
        if (recycle && !src.isRecycled && ret != src) src.recycle()
        return ret
    }

    /**
     * 获取图片旋转角度
     *
     * @param filePath
     * @return
     */
    fun getRotateDegree(filePath: String?): Int {
        return try {
            val exifInterface = ExifInterface(filePath!!)
            val orientation = exifInterface.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                ExifInterface.ORIENTATION_ROTATE_180 -> 180
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                else -> 0
            }
        } catch (e: IOException) {
            e.printStackTrace()
            -1
        }
    }

    /**
     * 按采样大小压缩图片
     *
     * @param options
     * @param maxWidth
     * @param maxHeight
     * @return
     */
    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        maxWidth: Int,
        maxHeight: Int
    ): Int {
        var height = options.outHeight
        var width = options.outWidth
        var inSampleSize = 1
        while (height > maxHeight || width > maxWidth) {
            height = height shr 1
            width = width shr 1
            inSampleSize = inSampleSize shl 1
        }
        return inSampleSize
    }

    /**
     * 判断是不是空图片
     *
     * @param src
     * @return
     */
    private fun isEmptyBitmap(src: Bitmap?): Boolean {
        return src == null || src.width == 0 || src.height == 0
    }

    /**
     * 将bitmap保存到SD卡
     */
    fun saveBitmapToFile(bt: Bitmap?, target_path: String?): Boolean {
        if (bt == null || target_path == null || target_path.isEmpty()) {
            return false
        }
        val file = File(target_path)
        try {
            val out = FileOutputStream(file)
            bt.compress(CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
            return true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }

}