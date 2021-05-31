package cn.com.jxd.studyui.work

import android.annotation.SuppressLint
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.workDataOf

/**
 * @author xiangdong.jia
 * @description:
 * @date :2021/5/27 下午1:48
 */
class LoadImageResponse {
    companion object {
        const val TAG_IMAGE_STATE = "tag_image_state"//下载结果
        const val TAG_IMAGE_SAVE_PATH = "tag_image_save_path"//下载成功后的地址
        const val TAG_IMAGE_FAILURE_MESSAGE = "tag_image_failure_message"//失败原因
    }

    fun loadSuccess(imagePath: String = ""): ListenableWorker.Result {
        return ListenableWorker.Result.success(getData(true, imagePath))
    }

    fun loadFailure(message: String = ""): ListenableWorker.Result {
        return ListenableWorker.Result.failure(getData(false, failureMessage = message))
    }


    @SuppressLint("RestrictedApi")
    private fun getData(state: Boolean, imagePath: String = "", failureMessage: String = ""): Data {
        val data = Data.Builder()
        data.put(TAG_IMAGE_STATE, state)
        if (state) {
            data.put(TAG_IMAGE_SAVE_PATH, imagePath)
        } else {
            data.put(TAG_IMAGE_FAILURE_MESSAGE, failureMessage)
        }
        return data.build()
    }
}