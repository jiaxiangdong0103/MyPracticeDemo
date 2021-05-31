package cn.com.jxd.studyui.work.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.work.*
import cn.com.jxd.commonutil.ui.BaseViewBindingActivity
import cn.com.jxd.commonutil.log.LogUtils
import cn.com.jxd.studyui.config.UiRouterPath
import cn.com.jxd.studyui.databinding.ActivityWorkerPrimaryBinding
import cn.com.jxd.studyui.work.MyProgressWork
import cn.com.jxd.studyui.work.MyWork
import cn.com.jxd.studyui.work.MyWork1
import cn.com.jxd.studyui.work.MyWork2
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit


/**
 * @author xiangdong.jia
 * @description: worker 初级操作
 * @date :2021/5/8 下午4:25
 */
@Route(path = UiRouterPath.ACTIVITY_TEST_WORKER_PRIMARY)
class WorkerPrimaryActivity : BaseViewBindingActivity<ActivityWorkerPrimaryBinding>() {

    private val myWorkManager by lazy {
        WorkManager
            .getInstance(mActivity)
    }

    override fun createViewBinding() = ActivityWorkerPrimaryBinding.inflate(layoutInflater)

    override fun initView() {
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @InternalCoroutinesApi
    override fun initData() {
        mViewBinding.apply {
            tvStart.setOnClickListener {
                helloEnqueue()
            }
            tvSelect.setOnClickListener {
                helloSelect()
            }
            tvCancel.setOnClickListener {
                helloCancel()
            }
            tvProgress.setOnClickListener {
                helloProgress()
            }
            tvList.setOnClickListener {
                helloListWork()
            }
        }
    }


    /**
     * 创建一个任务，并加入到队列中 1.5s后 在加一次
     *
     * ExistingWorkPolicy.XXX
     * Keep 保留现有工作，并忽略新工作 ,
     * REPLACE 取消现有的 使用新的，
     * APPEND 追加到现有的后面 但是新任务状态依赖于现有任务，
     * APPEND_OR_REPLACE 和APPEND类似 单不依赖现有任务
     */
    private fun helloEnqueue() {
        val myWorkRequest =
            OneTimeWorkRequestBuilder<MyWork>()

        lifecycleScope.launch {
            for (i in 1..2) {
                val myWork = myWorkRequest.setInputData(workDataOf(MyWork.INPUT_URL to "$i$i$i$i"))
                    .addTag("Tag 001")
                    .build()

                myWorkManager
                    //enqueueUniqueWork 用于一次性工作
                    .enqueueUniqueWork(
                        "One", //work 的name
                        ExistingWorkPolicy.REPLACE,
                        myWork //WorkRequest
                    )
                delay(1500)
            }
        }
    }

    /**
     * 查询工作
     */
    private fun helloSelect() {
        val workQuery = WorkQuery.Builder
            .fromTags(listOf("tag1", "tag2", "Tag 001"))
            .build()
        val workInfo = myWorkManager.getWorkInfos(workQuery)
        workInfo.get().forEach {
            LogUtils.d("-- $it")
        }
    }


    private fun helloCancel() {
        val workQuery = WorkQuery.Builder
            .fromTags(listOf("tag1", "tag2", "Tag 001"))
            .build()
        val workInfo = myWorkManager.getWorkInfos(workQuery)
        workInfo.get().forEach {
            LogUtils.d("取消 ${it.id}")
            myWorkManager.cancelWorkById(it.id)
        }

    }


    private fun helloProgress() {
        val myWorkRequest =
            OneTimeWorkRequestBuilder<MyProgressWork>()
                .setInputData(workDataOf(MyProgressWork.INPUT_URL to "http:xxxxxxx.png"))
                .build()
        myWorkManager.enqueue(myWorkRequest)

        myWorkManager.getWorkInfoByIdLiveData(myWorkRequest.id)
            .observe(this, {
                if (it.state == WorkInfo.State.RUNNING) {
                    val progress = it.progress
                    val value = progress.getInt(MyProgressWork.Progress, 0)
                    LogUtils.d("收到执行进度 $value% ")
                }
            })
    }


    private fun helloListWork() {
        val myWork = OneTimeWorkRequestBuilder<MyWork>()
            .setInputData(workDataOf(MyWork.INPUT_URL to "http:000.png"))
            .build()
        val myWork1 = OneTimeWorkRequestBuilder<MyWork1>()
            .setInputData(workDataOf(MyWork1.INPUT_URL to "http:111.png"))
            .build()
        val myWork2 = OneTimeWorkRequestBuilder<MyWork2>()
            .setInputMerger(ArrayCreatingInputMerger::class.java)
            .build()
        myWorkManager.beginWith(listOf(myWork,myWork1))
            .then(myWork2)
            .enqueue()
    }


}