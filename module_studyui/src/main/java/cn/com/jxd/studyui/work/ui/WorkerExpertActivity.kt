package cn.com.jxd.studyui.work.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.work.*
import cn.com.jxd.commonutil.ui.BaseViewBindingActivity
import cn.com.jxd.commonutil.log.LogUtils
import cn.com.jxd.studyui.config.UiRouterPath
import cn.com.jxd.studyui.databinding.ActivityTestWorkerBinding
import cn.com.jxd.studyui.databinding.ActivityWorkerExpertBinding
import cn.com.jxd.studyui.work.LoadImageResponse
import cn.com.jxd.studyui.work.MyAsynWork
import cn.com.jxd.studyui.work.MyWork
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit


/**
 * @author xiangdong.jia
 * @description: worker 高级操作
 * @date :2021/5/8 下午4:25
 */
@Route(path = UiRouterPath.ACTIVITY_TEST_WORKER_EXPERT)
class WorkerExpertActivity : BaseViewBindingActivity<ActivityWorkerExpertBinding>() {

    private val myWorkManager by lazy {
        WorkManager
            .getInstance(mActivity)
    }

    override fun createViewBinding() = ActivityWorkerExpertBinding.inflate(layoutInflater)

    override fun initView() {
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @InternalCoroutinesApi
    override fun initData() {
        mViewBinding.apply {
            tvStart.setOnClickListener {
                holleAsynWork()
            }

            tvBackoffCriteria.setOnClickListener {
            }
            tvCancel.setOnClickListener {
            }

        }
    }

    private fun holleAsynWork() {
        val myWorkRequest = OneTimeWorkRequestBuilder<MyAsynWork>()
            .addTag("MyAsynWork")
            .setInputData(workDataOf(MyAsynWork.INPUT_URL to "http:xxxxx.png"))
            .build()
        myWorkManager.enqueue(myWorkRequest)

        myWorkManager.getWorkInfoByIdLiveData(myWorkRequest.id).observe(this, {
         if (it.state == WorkInfo.State.FAILED || it.state == WorkInfo.State.SUCCEEDED) {

                val outData = it.outputData

                val loadState = outData.getBoolean(LoadImageResponse.TAG_IMAGE_STATE, false)

                if (loadState) {
                    val path = outData.getString(LoadImageResponse.TAG_IMAGE_SAVE_PATH)
                    LogUtils.d("下载成功 保存的地址为：$path")
                } else {
                    val message = outData.getString(LoadImageResponse.TAG_IMAGE_FAILURE_MESSAGE)
                    LogUtils.d("下载失败 失败原因：$message")
                }

                myWorkManager.cancelWorkById(myWorkRequest.id)
            }
        })

    }

}