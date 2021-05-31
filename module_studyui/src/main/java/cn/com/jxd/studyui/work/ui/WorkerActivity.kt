package cn.com.jxd.studyui.work.ui

import android.os.Build
import androidx.annotation.RequiresApi
import cn.com.jxd.commonutil.ui.BaseViewBindingActivity
import cn.com.jxd.studyui.config.UiRouter
import cn.com.jxd.studyui.config.UiRouterPath
import cn.com.jxd.studyui.databinding.ActivityWorkerBinding
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.coroutines.*


/**
 * @author xiangdong.jia
 * @description: 练习worker
 * @date :2021/5/8 下午4:25
 */
@Route(path = UiRouterPath.ACTIVITY_WORKER)
class WorkerActivity : BaseViewBindingActivity<ActivityWorkerBinding>() {

    override fun createViewBinding() = ActivityWorkerBinding.inflate(layoutInflater)

    override fun initView() {
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @InternalCoroutinesApi
    override fun initData() {
        mViewBinding.apply {
            tv1.setOnClickListener {
                UiRouter.gotoWorkerBasisActivity()
            }
            tv2.setOnClickListener {
                UiRouter.gotoWorkerPrimaryActivity()
            }
            tv3.setOnClickListener {
                UiRouter.gotoWorkerExpertActivity()
            }
        }
    }
}