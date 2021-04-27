package cn.com.jxd.studyui.ktx.navigation.ui

import cn.com.jxd.base.ui.BaseViewBindingActivity
import cn.com.jxd.studyui.config.UiRouterPath
import cn.com.jxd.studyui.databinding.ActivityKtxNavigationBinding
import com.alibaba.android.arouter.facade.annotation.Route

/**
 * @author xiangdong.jia
 * @description:
 * @date :2021/4/27 下午7:41
 */
@Route(path = UiRouterPath.ACTIVITY_NAVIGATION)
class NavigationActivity : BaseViewBindingActivity<ActivityKtxNavigationBinding>() {
    override fun createViewBinding() = ActivityKtxNavigationBinding.inflate(layoutInflater)

    override fun initView() {
    }

    override fun initData() {
    }
}