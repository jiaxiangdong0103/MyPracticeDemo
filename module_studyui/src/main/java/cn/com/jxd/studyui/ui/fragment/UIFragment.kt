package cn.com.jxd.studyui.ui.fragment

import android.view.View
import cn.com.jxd.base.ui.BaseFragment
import cn.com.jxd.base.ui.BaseViewBindingFragment
import cn.com.jxd.studyui.config.UiRouter
import cn.com.jxd.studyui.databinding.FragmentUiBinding

/**
 * @author xiangdong.jia
 * @description:
 * @date :2021/4/27 下午4:44
 */
class UIFragment : BaseViewBindingFragment<FragmentUiBinding>() {
    override fun createViewBinding() = FragmentUiBinding.inflate(layoutInflater)

    override fun initView(view: View) {
    }

    override fun initData() {
        mViewBinding.tvKTX.setOnClickListener {
            UiRouter.gotoTestKTXActivity()
        }
    }
    companion object{
        fun instance(): BaseFragment {
            return UIFragment()
        }
    }
}