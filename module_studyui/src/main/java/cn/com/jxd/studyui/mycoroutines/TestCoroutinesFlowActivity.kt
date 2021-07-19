package cn.com.jxd.studyui.mycoroutines

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import cn.com.jxd.commonutil.log.LogUtils
import cn.com.jxd.commonutil.ui.BaseViewBindingActivity
import cn.com.jxd.studyui.config.UiRouterPath
import cn.com.jxd.studyui.databinding.ActivityTestCoroutinesBinding
import cn.com.jxd.studyui.databinding.ActivityTestCoroutinesExpBinding
import cn.com.jxd.studyui.databinding.ActivityTestCoroutinesFlowBinding
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.NullPointerException
import java.lang.Runnable
import java.lang.StringBuilder


/**
 * @author xiangdong.jia
 * @description: 练习协程 流
 * @date :2021/5/8 下午4:25
 */
@Route(path = UiRouterPath.ACTIVITY_TEST_COROUTINES_FLOW)
class TestCoroutinesFlowActivity : BaseViewBindingActivity<ActivityTestCoroutinesFlowBinding>(),
    CoroutineScope by MainScope() {

    override fun createViewBinding() = ActivityTestCoroutinesFlowBinding.inflate(layoutInflater)

    override fun initView() {
    }

    @InternalCoroutinesApi
    override fun initData() {
        mViewBinding.apply {
            tvHelloSimple.setOnClickListener {
                launch {
                    helloSimple()
                }
            }
            tvHelloFlow.setOnClickListener {
                launch {
                    helloFlow()
                }
            }
            tvHelloAsFlow.setOnClickListener {
                lifecycleScope.launch(Dispatchers.Default) {
                    helloAsFlow()
                }
            }
            tvHelloReduce.setOnClickListener {
                lifecycleScope.launch {
                    helloReduce()
                }
            }
            tvHelloFlowOn.setOnClickListener {
                helloFlowOn()
            }
        }
    }

    //-----------------sequence------------------------------
    private fun helloSimple() {
        sequenceSimple().forEach { i ->
            LogUtils.d("收到simple发射的消息 $i")
        }
    }

    private fun sequenceSimple(): Sequence<Int> = sequence {
        for (i in 0..3) {
            Thread.sleep(1000)
            yield(i)
        }
    }

    //-----------------flow---------------------
    private suspend fun helloFlow() {
        flowSimple().collect {
            LogUtils.d("收到flowSimple发射的消息 $it")
        }
    }

    private fun flowSimple() = flow {
        for (i in 0..3) {
            delay(1000)
            emit(i)
        }
    }

    //-----------------------------asFlow-------------------------------------------------------
    private suspend fun helloAsFlow() {
        (1..10).asFlow()
            .take(8)//取前几位数据
            .conflate()//只处理最新的数据
            .filter {// 过滤 返回boolean类型 true的话 才会往下发
                it % 2 == 0
            }
            .transform { value -> //转换
                emit(helloTransform(value))
            }
//            .map { value-> //转换
//                helloTransform(value)
//            }
            .collect {
                delay(1000)
                LogUtils.d("收到asFlow发射的消息 $it")
            }
    }

    private fun helloTransform(value: Int): String {
        return "map转换 $value"
    }

    //-------asFlow---------------
    private suspend fun helloReduce() {
        val mReduce = (1..3).asFlow()
            .reduce { a, b ->
                a + b
            }
        LogUtils.d("reduce求和= $mReduce")


        val mFold = (1..3).asFlow()
            .fold(10) { a, b ->
                a + b
            }
        LogUtils.d("fold= $mFold")


        val mFold1 = (1..3).asFlow()
            .fold(StringBuilder()) { a, b ->
                a.append("$b").append(",")
            }
        LogUtils.d("StringBuilder()= $mFold1")
    }

    //-----------------flowOn-----------------------------
    private fun helloFlowOn() {
        lifecycleScope.launch {
            testFlowOn()
                .collect {
                    delay(500)
                    LogUtils.d("收到消息 $it")
                }
        }
    }

    private fun testFlowOn() = flow {
        for (i in 0..5) {
            LogUtils.d("发送消息 $i")
            emit(i)
        }
    }.flowOn(Dispatchers.Default)
}