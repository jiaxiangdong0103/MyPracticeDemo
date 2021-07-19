package cn.com.jxd.studyui.image

import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import android.view.View
import cn.com.jxd.base.view.PathManager
import cn.com.jxd.base.view.SYImageUtils
import cn.com.jxd.commonutil.log.LogUtils
import cn.com.jxd.commonutil.ui.BaseViewBindingActivity
import cn.com.jxd.commonutil.utils.SYFileUtils
import cn.com.jxd.studyui.config.UiRouterPath
import cn.com.jxd.studyui.databinding.ActivityTestImageBinding
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.MainScope


/**
 * @author xiangdong.jia
 * @description: 练习存储
 * @date :2021/5/8 下午4:25
 */
@Route(path = UiRouterPath.ACTIVITY_TEST_IMAGE)
class TestImageActivity : BaseViewBindingActivity<ActivityTestImageBinding>(),
    CoroutineScope by MainScope() {
    companion object{
        const val Name = "myDemo"
    }
    override fun createViewBinding() = ActivityTestImageBinding.inflate(layoutInflater)

    override fun initView() {
    }

    @InternalCoroutinesApi
    override fun initData() {
        mViewBinding.apply {
            tvPermission.setOnClickListener {
//                getURl("test", "test/tea01.txt")
                assets.list("").forEach { companyName->
                    if(companyName.startsWith("test")){
                        LogUtils.d("$companyName")
                        assets.list(companyName).forEach {
//                            val goodsName = it.subSequence(0,it.indexOf(".txt")).toString()
//                            LogUtils.d("goodsName=$goodsName")
                            getURl(companyName, "$companyName/$it")
                        }
                    }

                }
            }
            tvStoragePath.setOnClickListener {

            }
        }

    }

    private val shopeeName = "Shopee"
    private fun getURl(companyName:String,goodsName:String){
        val urlList = mutableListOf<String>()
        val inputStream = assets.open(goodsName)
        val lenght = inputStream.available()
        val buffer = ByteArray(lenght)
        inputStream.read(buffer)
        var result = String(buffer)

        while (result.contains("src")){
            result = result.drop(result.indexOf("src") + 5)
            val end = result.indexOf("\"")
            urlList.add(result.substring(0, end))
            result = result.drop(end)
        }
        loadBitmap(companyName,goodsName.substring(0,goodsName.length-4),urlList)
    }

    private fun loadBitmap(companyName:String,goodsName: String,urlList:List<String>){
        for(i in urlList.indices){
            ImageUtil.loadBitmap(this, urlList[i]){
                downloadImage(it,companyName,goodsName,"image$i")
            }
        }

    }

    private fun downloadImage(bitmap: Bitmap,companyName:String, goodsName: String, fileName:String) {
        var filePath = "${getExternalFilesDir(null).absolutePath}/$shopeeName/$companyName/$goodsName/$fileName.png"

//        var filePath = "${Environment.getExternalStorageDirectory().absolutePath}/$shopeeName/$companyName/$goodsName/$fileName.png"
        if (SYFileUtils().createOrExistsFile(filePath)) {
            Log.e("downloadImage:", "创建文件成功$filePath")
            if (SYImageUtils.saveBitmapToFile(bitmap, filePath)) {
                Log.e("downloadImage:", "保存文件成功$filePath")
            } else {
                Log.e("downloadImage:", "保存文件失败")
            }
        } else {
            Log.e("downloadImage:", "创建文件失败")
        }
    }
}