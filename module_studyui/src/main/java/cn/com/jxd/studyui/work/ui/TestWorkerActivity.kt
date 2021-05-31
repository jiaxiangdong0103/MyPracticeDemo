package cn.com.jxd.studyui.work.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.work.*
import cn.com.jxd.commonutil.db.DataStorage
import cn.com.jxd.commonutil.ui.BaseViewBindingActivity
import cn.com.jxd.commonutil.log.LogUtils
import cn.com.jxd.studyui.config.UiRouterPath
import cn.com.jxd.studyui.databinding.ActivityTestWorkerBinding
import cn.com.jxd.studyui.work.MyWork
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit


/**
 * @author xiangdong.jia
 * @description: 练习worker
 * @date :2021/5/8 下午4:25
 */
@Route(path = UiRouterPath.ACTIVITY_TEST_WORKER)
class TestWorkerActivity : BaseViewBindingActivity<ActivityTestWorkerBinding>() {

    private val myWorkManager by lazy {
        WorkManager
            .getInstance(mActivity)
    }

    override fun createViewBinding() = ActivityTestWorkerBinding.inflate(layoutInflater)

    override fun initView() {
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @InternalCoroutinesApi
    override fun initData() {
        mViewBinding.apply {
            tvStart.setOnClickListener {

                val myWork: WorkRequest =
                    OneTimeWorkRequestBuilder<MyWork>()
                        .addTag("MyTag")
                        .build()
                myWorkManager
                    .enqueue(myWork)

            }
            tvOneTime.setOnClickListener {
                helloOneTime()
            }
            tvPeriodic.setOnClickListener {
                helloPeriodic()
            }
            tvConstraints.setOnClickListener {
//                helloConstraints()
                val content = DataStorage.getString(MyWork.RESULT_URL)
                LogUtils.d(content)
            }
            tvBackoffCriteria.setOnClickListener {
                helloBackoffCriteria()
            }
            tvCancel.setOnClickListener {
                helloCancel()
            }

        }
    }

    private fun helloOneTime() {
        //OneTimeWorkRequestBuilder 适用于调度非重复性工作 有创建WorkRequest两种方式
        // val myWorkOne: WorkRequest = OneTimeWorkRequest.from(MyWork::class.java)
        val myWorkOne: WorkRequest =
            OneTimeWorkRequestBuilder<MyWork>()
                .setInputData(workDataOf(MyWork.INPUT_URL to "1234567890"))
                .build()

        WorkManager
            .getInstance(mActivity)
            .enqueue(myWorkOne)

        WorkManager
            .getInstance(mActivity)
            .getWorkInfoByIdLiveData(myWorkOne.id)
            .observe(this,  { info->
                if(info!=null && info.state.isFinished){
                    val myRequest = info.outputData.getString(MyWork.RESULT_URL)
                    LogUtils.d("返回的结果 $myRequest")
                }
            })
    }


    private fun helloPeriodic() {

        //调度以一定间隔重复执行的工作
        val myWorkPeriodic = PeriodicWorkRequestBuilder<MyWork>(15, TimeUnit.SECONDS)
            .setInputData(workDataOf(MyWork.INPUT_URL to "http:xxxxx.png"))
            .addTag("tag1")
            .build()
        myWorkManager
            .enqueue(myWorkPeriodic)
        myWorkManager.getWorkInfoByIdLiveData(myWorkPeriodic.id)
            .observe(this,  { info->
                if(info!=null && info.state.isFinished){
                    val myRequest = info.outputData.getString(MyWork.RESULT_URL)
                    LogUtils.d("返回的结果 $myRequest")
                }
            })
    }

    @SuppressLint("IdleBatteryChargingConstraints")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun helloConstraints() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)//手机的网络状态
            .setRequiresBatteryNotLow(true)//如果为true 则电量不足模式下 不执行
            .setRequiresCharging(true) //如果为true 则没有充电状态下不执行
            .setRequiresDeviceIdle(false)//如果为true 则设备空闲状态下才执行
            .setRequiresStorageNotLow(false)//如果为true 则存储空间不足时 不会运行
            .build()
        LogUtils.d("开始执行")
        val myWork: WorkRequest =
            OneTimeWorkRequestBuilder<MyWork>()
                .setConstraints(constraints)//设置约束，有多个约束的时候 所有条件都满足 才会执行，如果不满足 会等待满足的时候 自动执行
                .setInitialDelay(10, TimeUnit.SECONDS)//加入队列后 延迟指定的时间后执行
                .build()

        WorkManager
            .getInstance(mActivity)
            .enqueue(myWork)
    }

    @SuppressLint("IdleBatteryChargingConstraints")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun helloBackoffCriteria() {
        //适用于调度非重复性工作
        val myWork: WorkRequest =
            OneTimeWorkRequestBuilder<MyWork>()
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,//退避政策 LINEAR 比如你设置的失败后10s后重试，那第一次失败后10s后开始重试 第二次 20s 第三次 30s，就是累加的意思 EXPONENTIAL 就是第一次 10s 第二次 20s 第三次 40s 这样 相当于x2
                    OneTimeWorkRequest.MIN_BACKOFF_MILLIS,//最小的时间间隔 不能大于10s
                    TimeUnit.MILLISECONDS//时间单位
                )
                .addTag("MyTag")
                .build()

        WorkManager
            .getInstance(mActivity)
            .enqueue(myWork)
    }
    //--------------------------cancel-------------------------------------------
    private fun helloCancel(){
       lifecycleScope.launch {
           val myWork = OneTimeWorkRequestBuilder<MyWork>()
               .setInputData(workDataOf(MyWork.INPUT_URL to "http:xxxxx.png")).build()
           LogUtils.d("开始执行")
           myWorkManager.enqueue(myWork).state.observe(mActivity, {
               LogUtils.d("收到回调 $it")
           })

           myWorkManager
               .getWorkInfoByIdLiveData(myWork.id)
               .observe(mActivity,  { info->
                   if(info!=null && info.state.isFinished){
                       val myRequest = info.outputData.getString(MyWork.RESULT_URL)
                       LogUtils.d("返回的结果 $myRequest")
                   }
               })

           delay(1500)
           LogUtils.d("取消执行")
           myWorkManager.cancelWorkById(myWork.id)
       }
    }




}