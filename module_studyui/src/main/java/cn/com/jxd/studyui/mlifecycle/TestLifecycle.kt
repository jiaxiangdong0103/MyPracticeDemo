package cn.com.jxd.studyui.mlifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * @author xiangdong.jia
 * @description:
 * @date :2021/1/26 下午4:01
 */
interface TestLifecycle : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate()
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart()
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume()
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause()
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop()
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy()
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny()

}