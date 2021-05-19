package cn.com.jxd.studyui.mycoroutines

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import cn.com.jxd.base.log.LogUtils
import cn.com.jxd.base.ui.BaseViewBindingActivity
import cn.com.jxd.studyui.config.UiRouterPath
import cn.com.jxd.studyui.databinding.ActivityTestCoroutinesBinding
import cn.com.jxd.studyui.databinding.ActivityTestCoroutinesExpBinding
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.broadcast
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import java.lang.NullPointerException
import java.lang.Runnable
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.coroutineContext
import kotlin.system.measureTimeMillis


/**
 * @author xiangdong.jia
 * @description: 练习协程 异常相关
 * @date :2021/5/8 下午4:25
 */
@Route(path = UiRouterPath.ACTIVITY_TEST_COROUTINES_EXP)
class TestCoroutinesExpActivity : BaseViewBindingActivity<ActivityTestCoroutinesExpBinding>() {

    override fun createViewBinding() = ActivityTestCoroutinesExpBinding.inflate(layoutInflater)

    override fun initView() {
    }

    @InternalCoroutinesApi
    override fun initData() {
        mViewBinding.apply {
            tvStartCoroutines.setOnClickListener {
                lifecycleScope.launch() {
                    helloException()
                }
            }
        }
    }

    private suspend fun helloException() {
        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            LogUtils.d("捕获异常 ${throwable.message}")
        }
        supervisorScope {
            val job1 = launch(handler) {
                for (i in 1..3) {
                    delay(1000)
                    if (i == 3) {
                        throw NullPointerException()
                    }
                    LogUtils.j("job1 打印$i")
                }
            }
            val job2 = launch {
                for (i in 1..3) {
                    delay(1000)
                    LogUtils.j("job2 打印$i")
                }
            }
        }
    }

}