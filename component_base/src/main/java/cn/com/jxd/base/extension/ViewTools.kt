package cn.com.jxd.base.extension

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import cn.com.jxd.base.log.LogUtils

/**
 * @author xiangdong.jia
 * @description:
 * @date :2021/4/30 下午2:27
 */
fun FragmentManager.commit2(
    allowStateLoss: Boolean = false,
    body: FragmentTransaction.() -> Unit
) {
    LogUtils.j("1")
    val transaction = beginTransaction()
    LogUtils.j("2")
    LogUtils.j(transaction)
    transaction.body()

    LogUtils.j("3")
//    if (allowStateLoss) {
        transaction.commitAllowingStateLoss()
    LogUtils.j("4")
//    } else {
//        transaction.commit()
//    }
}