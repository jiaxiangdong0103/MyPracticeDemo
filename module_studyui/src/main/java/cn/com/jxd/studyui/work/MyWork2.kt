package cn.com.jxd.studyui.work

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import cn.com.jxd.commonutil.log.LogUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author xiangdong.jia
 * @description:
 * @date :2021/5/24 下午12:02
 */
class MyWork2(appContext: Context, workParams: WorkerParameters) : Worker(appContext, workParams) {
    companion object {
        const val INPUT_URL = "input_url"
        const val RESULT_URL = "result_url"
    }
    override fun doWork(): Result {

        val url = inputData.getString(RESULT_URL)
        LogUtils.d("MyWork2 开始下载 URL = $url")
        var i = 1
        while(i <= 5 && !isStopped){
            LogUtils.d("MyWork2 正在下载$url  $i")
            i++
            Thread.sleep(500)
        }
        val outData = workDataOf(RESULT_URL to "$url")

        return Result.success(outData)
    }

    override fun onStopped() {
        super.onStopped()
        LogUtils.d("-----MyWork2 执行结束 onStopped-----")
    }

}