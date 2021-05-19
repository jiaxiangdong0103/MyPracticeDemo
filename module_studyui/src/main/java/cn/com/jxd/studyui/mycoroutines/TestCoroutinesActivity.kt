package cn.com.jxd.studyui.mycoroutines

import androidx.lifecycle.lifecycleScope
import cn.com.jxd.base.log.LogUtils
import cn.com.jxd.base.ui.BaseViewBindingActivity
import cn.com.jxd.studyui.config.UiRouterPath
import cn.com.jxd.studyui.databinding.ActivityTestCoroutinesBinding
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.broadcast
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import java.lang.NullPointerException
import java.lang.Runnable
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.system.measureTimeMillis


/**
 * @author xiangdong.jia
 * @description: 练习协程
 * @date :2021/5/8 下午4:25
 */
@Route(path = UiRouterPath.ACTIVITY_TEST_COROUTINES)
class TestCoroutinesActivity : BaseViewBindingActivity<ActivityTestCoroutinesBinding>(),
    CoroutineScope by MainScope() {

    override fun createViewBinding() = ActivityTestCoroutinesBinding.inflate(layoutInflater)

    override fun initView() {
    }

    @InternalCoroutinesApi
    override fun initData() {
        mViewBinding.apply {
            tvStartCoroutines.setOnClickListener {
                helloCoroutines()
            }
            tvHelloRunBlocking.setOnClickListener {
                helloRunBlocking()
            }
            tvHelloAsync.setOnClickListener {
                lifecycleScope.launch {
                    helloAsync()
                }
            }
            tvHelloProduce.setOnClickListener {
                lifecycleScope.launch {
                    helloProduce()
                }
            }
            tvHelloBroadcast.setOnClickListener {
                lifecycleScope.launch {
                    helloBroadcast()
                }
            }
            tvStartCancel.setOnClickListener {
                helloWithTimeout()
            }
            tvCancel.setOnClickListener {
                lifecycleScope.launch {
                    testCancel()
                }
            }
        }
    }

    private fun helloCoroutines() {
        GlobalScope.launch(Dispatchers.Main) {//GlobalScope 是一个全局的 不会因为activity的消失而关闭 所以需要手动关闭
            for (i in 0..5) {
                delay(1000) //这个是延迟的意思
                LogUtils.j("你好 GlobalScope ${Thread.currentThread().name}")
            }
            cancel()
        }


        launch {
            for (i in 0..50) {
                delay(1000) //这个是延迟的意思
                LogUtils.j("你好 launch $i ${Thread.currentThread().name}")
            }

        }

        lifecycleScope.launch {//lifecycleScope 是Lifecycle KTX 为每一个 Lifecycle 对象定义一个 LifecycleScope。在此范围内启动的协程会在 Lifecycle 被销毁时取消
            LogUtils.j("你好 lifecycleScope ${Thread.currentThread().name}")
        }
    }

    private val job by lazy {
        Job()
    }

    //继承CoroutineScope 就可以使用launch ，重写coroutineContext 相当于指定上下文
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default


    //在 runBlocking 不要做耗时的任务 因为runBlocking走完 才会走以后的代码
    private fun helloRunBlocking() = runBlocking { //runBlocking是一个阻塞函数 执行完{}里面的内容 才会继续走
        GlobalScope.launch {
            for (i in 0..10) {
                delay(1000) //这个是延迟的意思
                LogUtils.j("你好 协程1 ${Thread.currentThread().name}")
            }
        }
    }


    //--------------------------Async--------------------------------------
    private suspend fun helloAsync() {
        //async 可有一个返回值 使用await接收
        val time = measureTimeMillis {
            val taskOne = async { asyncTaskOne() }
            val taskTwo = async { asyncTaskTwo() }
            LogUtils.j("计算结果 ${taskOne.await() + taskTwo.await()}")
        }
        LogUtils.j("耗时 $time")

    }

    private suspend fun asyncTaskOne(): Int {
        delay(1000)
        LogUtils.j("你好 asyncTaskOne ${Thread.currentThread().name} ")
        return 10
    }

    private suspend fun asyncTaskTwo(): Int {
        delay(1000)
        LogUtils.j("你好 asyncTaskTwo ${Thread.currentThread().name}")
        return 20
    }

    //--------------------------produce--------------------------------------
    @InternalCoroutinesApi
    private suspend fun helloProduce() {
        val p = produce(
            capacity = 2,
            onCompletion = object : CompletionHandler {
                override fun invoke(cause: Throwable?) {
                    LogUtils.j("你好 produce 执行完毕")
                }
            }) {
            for (i in 0..10) {
                delay(1000) //这个是延迟的意思
                LogUtils.j(" produce 发送数据 $i")
                send(i)
            }
        }
        launch {
            p.consumeEach {
                LogUtils.j("接收 produce $it")
            }
        }
        launch {
            p.consumeEach {
                LogUtils.j("接收 produce2 $it")
            }
        }
    }

    //--------------------------broadcast--------------------------------------
    @InternalCoroutinesApi
    private suspend fun helloBroadcast() {
        val p = broadcast(//produce 每次发送数据 都只有一个接收者可以成功接收 想要多个接收者接收 需使用broadcast
            capacity = 2,
            onCompletion = object : CompletionHandler {
                override fun invoke(cause: Throwable?) {
                    LogUtils.j("你好 broadcast 执行完毕")
                }
            }) {
            for (i in 0..10) {
                delay(1000) //这个是延迟的意思
                LogUtils.j("你好 broadcast 发送数据 $i")
                send(i)
            }
        }
        launch {
            p.openSubscription().consumeEach {
                LogUtils.j("接收 broadcast1 $it")
            }
        }
        launch {
            p.openSubscription().consumeEach {
                LogUtils.j("接收 broadcast2 $it")
            }
        }
    }

    //--------------------------join--------------------------------------
    private var testJobCancel: Job? = null
    private suspend fun testCancelJob() {
        val startTime = System.currentTimeMillis()
        testJobCancel = launch(start = CoroutineStart.LAZY) {
            var i = 0
            var nextPrintTime = startTime
            while (i < 5 && isActive) {// 只有这一点改动
                if (System.currentTimeMillis() >= nextPrintTime) {
                    LogUtils.j("hello testCancel ${i++}")
                    nextPrintTime += 500L
                }
            }
        }
        LogUtils.j("join前")
        testJobCancel?.join()
        LogUtils.j("join后")
    }

    private fun testCancel() {
        LogUtils.j("执行cancel")
        testJobCancel?.cancel()
    }

    //--------------------------withTimeout--------------------------------------
    private fun helloWithTimeout() {
        launch {
            try {
                withTimeout(5000) {
                    for (i in 0..10) {
                        LogUtils.j("helloWithTimeout $i")
                        delay(1000)
                    }
                }
            } finally {
                LogUtils.j("执行完了")
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}