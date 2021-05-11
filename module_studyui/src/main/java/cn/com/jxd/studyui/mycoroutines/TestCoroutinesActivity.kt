package cn.com.jxd.studyui.mycoroutines

import cn.com.jxd.base.log.LogUtils
import cn.com.jxd.base.ui.BaseViewBindingActivity
import cn.com.jxd.studyui.config.UiRouterPath
import cn.com.jxd.studyui.databinding.ActivityTestCoroutinesBinding
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.coroutines.*


/**
 * @author xiangdong.jia
 * @description: 练习协程
 * @date :2021/5/8 下午4:25
 */
@Route(path = UiRouterPath.ACTIVITY_TEST_COROUTINES)
class TestCoroutinesActivity : BaseViewBindingActivity<ActivityTestCoroutinesBinding>() {


    override fun createViewBinding() = ActivityTestCoroutinesBinding.inflate(layoutInflater)

    override fun initView() {

    }

    override fun initData() {
        LogUtils.j("helloCoroutines 执行前 ")
        helloCoroutines()
        LogUtils.j("helloCoroutines 执行后 ")
    }

    private  fun helloCoroutines() = runBlocking {
        val job = GlobalScope.launch (start = CoroutineStart.LAZY){ //修改start 为 CoroutineStart.LAZY延迟执行 并获取的返回的job
            delay(1000) //这个是延迟的意思
            LogUtils.j("你好 协程1 ${Thread.currentThread().name}")
        }
        job.join() //相当于在这里开启
    }
}