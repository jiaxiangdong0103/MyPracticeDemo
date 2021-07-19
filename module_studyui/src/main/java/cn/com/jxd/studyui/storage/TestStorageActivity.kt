package cn.com.jxd.studyui.storage

import android.content.Context
import android.os.Environment
import cn.com.jxd.base.view.PathManager
import cn.com.jxd.base.view.SYImageUtils
import cn.com.jxd.commonutil.common.CommonPermissionUtil
import cn.com.jxd.commonutil.log.LogUtils
import cn.com.jxd.commonutil.ui.BaseViewBindingActivity
import cn.com.jxd.commonutil.utils.SYFileUtils
import cn.com.jxd.commonutil.utils.SYPermissionUtils
import cn.com.jxd.commonutil.utils.ToastUtils
import cn.com.jxd.studyui.config.UiRouterPath
import cn.com.jxd.studyui.databinding.ActivityTestStorageBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.yanzhenjie.permission.runtime.Permission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.MainScope
import java.io.File


/**
 * @author xiangdong.jia
 * @description: 练习存储
 * @date :2021/5/8 下午4:25
 */
@Route(path = UiRouterPath.ACTIVITY_TEST_STORAGE)
class TestStorageActivity : BaseViewBindingActivity<ActivityTestStorageBinding>(),
    CoroutineScope by MainScope() {
    companion object{
        const val Name = "myDemo"
    }
    override fun createViewBinding() = ActivityTestStorageBinding.inflate(layoutInflater)

    override fun initView() {
    }

    @InternalCoroutinesApi
    override fun initData() {
        mViewBinding.apply {
            tvPermission.setOnClickListener {
                CommonPermissionUtil.checkStoragePermission(mActivity){
                }
            }
            tvStoragePath.setOnClickListener {
                SYFileUtils().createOrExistsDir("${filesDir.absolutePath}/1001.png")
                SYFileUtils().listFilesInDir(File(filesDir.absolutePath))?.forEach {
                    LogUtils.d(it?.name ?: "")
                }
                LogUtils.d("--------------------")
                SYFileUtils().createOrExistsDir("${cacheDir.absolutePath}/2001.png")
                SYFileUtils().listFilesInDir(File(cacheDir.absolutePath))?.forEach {
                    LogUtils.d(it?.name ?: "")
                }
                LogUtils.d("--------------------")
                SYFileUtils().createOrExistsDir("${getExternalFilesDir(Environment.DIRECTORY_MUSIC)}/3001.png")
                SYFileUtils().listFilesInDir(File(getExternalFilesDir(Environment.DIRECTORY_MUSIC)?.absolutePath))?.forEach {
                    LogUtils.d(it?.name ?: "")
                }
                LogUtils.d("--------------------")
                SYFileUtils().createOrExistsDir("${externalCacheDir}/4001.png")
                SYFileUtils().listFilesInDir(File(externalCacheDir?.absolutePath))?.forEach {
                    LogUtils.d(it?.name ?: "")
                }

                SYImageUtils.downloadImageToAlbum(mActivity, tvStoragePath)

                SYFileUtils().createOrExistsDir("${Environment.getExternalStorageDirectory().absolutePath}/5001.txt")
                SYFileUtils().listFilesInDir(File(Environment.getExternalStorageDirectory().absolutePath))?.forEach {
                    LogUtils.d(it?.name ?: "")
                }
                LogUtils.d(filesDir.absolutePath)
                LogUtils.d(cacheDir.absolutePath)
                LogUtils.d(getExternalFilesDir("Name")?.absolutePath ?: "")
                LogUtils.d(externalCacheDir?.absolutePath ?: "")

                LogUtils.d(Environment.getExternalStorageDirectory().absolutePath)
                LogUtils.d(getExternalFilesDir(Environment.DIRECTORY_MUSIC)?.absolutePath?:"")
            }
        }

    }

}