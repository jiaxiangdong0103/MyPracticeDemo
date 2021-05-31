package cn.com.jxd.studyui.work

import android.content.Context
import androidx.concurrent.futures.CallbackToFutureAdapter
import androidx.work.*
import cn.com.jxd.commonutil.log.LogUtils
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.NullPointerException
import java.util.concurrent.TimeoutException

/**
 * @author xiangdong.jia
 * @description:
 * @date :2021/5/24 下午12:02
 */
class MyTestAsynWork(appContext: Context, workParams: WorkerParameters) :
    ListenableWorker(appContext, workParams) {
    companion object {
        const val PROGRESS = "Progress"
        const val INPUT_URL = "input_url"
    }

    override fun startWork(): ListenableFuture<Result> {
        return CallbackToFutureAdapter.getFuture { completer ->
            val callback = object : MyCallback {

                override fun onFailure(exception: Exception) {
                    completer.set(LoadImageResponse().loadFailure(exception.message ?: "未知错误"))
                }

                override fun onResponse(imagePath: String) {
                    completer.set(LoadImageResponse().loadSuccess(imagePath))
                }

                override fun onCancel() {

                }
            }

            val url = inputData.getString(INPUT_URL)

             loadImage(url, callback)

        }
    }

    private  fun loadImage(imageUrl: String?, callBack: MyCallback) {
        if (imageUrl == null) {
            callBack.onFailure(NullPointerException("URL 为空"))
        }

        var i = 1
        while (i <= 10 && !isStopped) {
            val current = i
            LogUtils.d("正在下载$imageUrl  进度${current * 10}%")
            setProgressAsync(workDataOf(PROGRESS to current * 10))
            i++

//            if (i == 8) {//模拟一个错误
//                callBack.onFailure(TimeoutException("超时了"))
//                return
//            }
            Thread.sleep(500)
        }
        callBack.onResponse("下载成功地址：$imageUrl")
    }
}