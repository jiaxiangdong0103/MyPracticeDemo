package cn.com.jxd.studyui.mlifecycle

import cn.com.jxd.commonutil.log.LogUtils

/**
 * @author xiangdong.jia
 * @description:
 * @date :2021/1/26 下午4:06
 */
class LifecycleExample(private val className: String = "") : TestLifecycle {
    override fun onCreate() {
        LogUtils.j("-- $className onCreate --")
    }

    override fun onStart() {
        LogUtils.j("-- $className onStart --")
    }

    override fun onResume() {
        LogUtils.j("-- $className onResume --")
    }

    override fun onPause() {
        LogUtils.j("-- $className onPause --")
    }

    override fun onStop() {
        LogUtils.j("-- $className onStop --")
    }

    override fun onDestroy() {
        LogUtils.j("-- $className onDestroy --")
    }
    //这个但凡走个生命周期 就会执行
    override fun onAny() {
//        LogUtils.j("-- onAny --")
    }
}