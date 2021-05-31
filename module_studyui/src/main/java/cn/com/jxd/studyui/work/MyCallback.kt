package cn.com.jxd.studyui.work

/**
 * @author xiangdong.jia
 * @description:
 * @date :2021/5/26 下午7:55
 */
interface MyCallback {
    fun onFailure(exception: Exception)

    fun onResponse(imagePath: String)

    fun onCancel()

}