package cn.com.jxd.studyui.work

import android.content.Context
import androidx.work.*
import cn.com.jxd.commonutil.log.LogUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author xiangdong.jia
 * @description:
 * @date :2021/5/24 下午12:02
 */
class MyProgressWork(appContext: Context, workParams: WorkerParameters) : CoroutineWorker(appContext, workParams) {
    companion object {
        const val Progress = "Progress"
        const val INPUT_URL = "input_url"
        const val RESULT_URL = "result_url"
    }

    override suspend fun doWork(): Result {

        val url = inputData.getString(INPUT_URL)
        LogUtils.d("开始一个耗时任务  得到的URL = $url")
        var i = 1
        while(i <= 10 && !isStopped){
            val current = i
            LogUtils.d("正在下载$url  进度${current}%")
            setProgress(workDataOf(Progress to current))
            i++
            Thread.sleep(500)
        }
        val outData = workDataOf(RESULT_URL to "$url")
        return Result.success(outData)
    }

}