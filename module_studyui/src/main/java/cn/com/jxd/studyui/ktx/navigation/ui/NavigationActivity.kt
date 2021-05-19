package cn.com.jxd.studyui.ktx.navigation.ui

import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import cn.com.jxd.commonutil.ui.BaseViewBindingActivity
import cn.com.jxd.studyui.R
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
        // 这个是使用 BottomNavigationView 作为底部的tab
        mViewBinding.bottomTab.setupWithNavController(findNavController(R.id.fragment))


        // 这个是自己写两个Tab按钮 切换报错
        mViewBinding.apply {
            tvFistFragment.setOnClickListener {
                findNavController(R.id.fragment).navigate(
                    R.id.action_nav_second_fragment_to_nav_fist_fragment
                )
            }
            tvSecondFragment.setOnClickListener {
                findNavController(R.id.fragment).navigate(
                    R.id.action_nav_fist_fragment_to_nav_second_fragment
                )
            }
        }
    }
}