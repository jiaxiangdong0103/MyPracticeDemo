package cn.com.jxd.base.extension

import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import cn.com.jxd.base.log.LogUtils

/**
 * @author xiangdong.jia
 * @description: view的扩展属性
 * @date :2021/4/30 下午2:27
 */
fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}
