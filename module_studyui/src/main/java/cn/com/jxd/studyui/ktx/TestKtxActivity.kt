package cn.com.jxd.studyui.ktx

import cn.com.jxd.commonutil.ui.BaseViewBindingActivity
import cn.com.jxd.studyui.config.UiRouter
import cn.com.jxd.studyui.config.UiRouterPath
import cn.com.jxd.studyui.databinding.ActivityTestKtxBinding
import cn.com.jxd.studyui.ktx.livedata.TestViewModel
import com.alibaba.android.arouter.facade.annotation.Route

/**
 * @author xiangdong.jia
 * @description:
 * @date :2021/4/26 下午7:00
 */
@Route(path = UiRouterPath.ACTIVITY_TEST_KTX)
class TestKtxActivity : BaseViewBindingActivity<ActivityTestKtxBinding>() {


    override fun createViewBinding() = ActivityTestKtxBinding.inflate(layoutInflater)

    override fun initView() {

    }

    override fun initData() {
        mViewBinding.tvNavigation.setOnClickListener {
            UiRouter.gotoNavigationActivity()
        }
        mViewBinding.tvLiveData.setOnClickListener {
            UiRouter.gotoTestLiveDataActivity()
        }
    }
}