package cn.com.jxd.studyui.ui.fragment

import android.view.View
import cn.com.jxd.commonutil.ui.BaseFragment
import cn.com.jxd.commonutil.ui.BaseViewBindingFragment
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
        UiRouter.gotoTestImageActivity()
        mViewBinding.apply {
            tvKTX.setOnClickListener {
                UiRouter.gotoTestKTXActivity()
            }
            tvGuide.setOnClickListener {
                UiRouter.gotoTestCoroutinesActivity()
            }
            tvCoroutines.setOnClickListener {
                UiRouter.gotoTestCoroutinesActivity()
            }
            tvCoroutinesExp.setOnClickListener {
                UiRouter.gotoTestCoroutinesExpActivity()
            }
            tvCoroutinesFlow.setOnClickListener {
                UiRouter.gotoTestCoroutinesFlowActivity()
            }
            tvWorker.setOnClickListener {
                UiRouter.gotoWorkerActivity()
            }
            tvFile.setOnClickListener {
                UiRouter.gotoTestStorageActivity()
            }
        }


    }
    companion object{
        fun instance(): BaseFragment {
            return UIFragment()
        }
    }
}