package cn.com.jxd.base.view

import android.content.Context
import android.os.Build
import android.os.Environment
import java.io.File

/**
 * @author xiangdong.jia
 * @description:
 * @date :2021/6/2 下午5:09
 */
object PathManager {
    val APP_CACHE = "appCache"
    var APP_ABBREVIATION = "MyDemo" //app 简称 外部存储都放到这个下面

    val SHARE = "share"
    val VIDEO = "video"
    val IMAGE = "Image"
    val MUSIC = "music"
    val ADVERT = "advert"
    val APK = "apk"
    val FACEMODE = "facemode"

    fun setAppName(appName: String) {
        APP_ABBREVIATION = appName
    }

    //获取机身存储的外部存储 不需要获取权限
    fun getInsideStoragePath(context: Context): String {
        return getInsideStoragePath(context, APP_CACHE)
    }

    fun getInsideStoragePath(context: Context, name: String?): String {
        var file = context.getExternalFilesDir(name)
        if (file == null) {
            file = File(context.filesDir, name)
            if (!file.exists()) {
                file.mkdirs()
            }
        }
        return file.absolutePath
    }

    fun getInsideImageStoragePath(context: Context): String {
        return getInsideStoragePath(context) + "/" + IMAGE
    }


    fun getInsideVideoStoragePath(context: Context): String {
        return getInsideStoragePath(context) + "/" + VIDEO
    }

    fun getInsideShareStoragePath(context: Context): String {
        return getInsideStoragePath(context) + "/" + SHARE
    }

    fun getInsideApkStoragePath(context: Context): String {
        return getInsideStoragePath(context) + "/" + APK
    }

    fun getInsideMusicStoragePath(context: Context): String {
        val musicPath = getInsideStoragePath(context) + "/" + MUSIC
        val file = File(musicPath)
        if (!file.exists()) file.mkdirs()
        return musicPath
    }

    fun getInsideAdvertStoragePath(context: Context): String? {
        return getInsideStoragePath(context, ADVERT)
    }

    //获取外部存储路径
    fun getOutsideStoragePath(): String {
        //判断外置sd卡是否可用
        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            //返回外置sd卡路劲
            Environment.getExternalStorageDirectory().absolutePath + "/" + APP_ABBREVIATION
        } else ""
    }

    fun getOutsideImageStoragePath(): String {
        return getOutsideStoragePath() + "/" + IMAGE
    }

    fun getOutsideVideoStoragePath(): String {
        return getOutsideStoragePath() + "/" + VIDEO
    }

    fun getOutsideShareStoragePath(): String {
        return getOutsideStoragePath() + "/" + SHARE
    }

    fun getSystemPath(): String {
        val path: String
        val brand = Build.BRAND
        path = if (brand == "xiaomi") { // 小米手机brand.equals("xiaomi")
            Environment.getExternalStorageDirectory().path + "/DCIM/Camera/"
        } else if (brand.equals("Huawei", ignoreCase = true)) {
            Environment.getExternalStorageDirectory().path + "/DCIM/Camera/"
        } else { // Meizu 、Oppo
            Environment.getExternalStorageDirectory().path + "/DCIM/"
        }
        return path
    }
}