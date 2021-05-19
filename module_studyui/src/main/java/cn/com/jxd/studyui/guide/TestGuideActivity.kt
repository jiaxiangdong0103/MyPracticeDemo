package cn.com.jxd.studyui.guide

import cn.com.jxd.commonutil.ui.BaseViewBindingActivity
import cn.com.jxd.common.guide.CommonGuideDialog
import cn.com.jxd.common.guide.CommonGuideViewUtils
import cn.com.jxd.studyui.config.UiRouter
import cn.com.jxd.studyui.config.UiRouterPath
import cn.com.jxd.studyui.databinding.ActivityTestGuideBinding
import cn.com.jxd.studyui.databinding.ActivityTestKtxBinding
import cn.com.jxd.studyui.ktx.livedata.TestViewModel
import com.alibaba.android.arouter.facade.annotation.Route

/**
 * @author xiangdong.jia
 * @description:
 * @date :2021/4/26 下午7:00
 */
@Route(path = UiRouterPath.ACTIVITY_TEST_GUIDE)
class TestGuideActivity : BaseViewBindingActivity<ActivityTestGuideBinding>() {


    override fun createViewBinding() = ActivityTestGuideBinding.inflate(layoutInflater)

    override fun initView() {

    }

    override fun initData() {
        mViewBinding.tv1.setOnClickListener {
            CommonGuideViewUtils(this)
                .setGuideViewTag(CommonGuideViewUtils.TAG_FIRST)
                .setTargetView(mViewBinding.tv1)
                .setDismissListener(object : CommonGuideDialog.GuideViewDismissListener{
                    override fun onDismissListener() {
                        //新手引导消失后的监听
                    }

                })
                .build()
        }
        mViewBinding.iv2.setOnClickListener {
            CommonGuideViewUtils(this)
                .setGuideViewTag(CommonGuideViewUtils.TAG_SECOND)
                .setTargetView(mViewBinding.iv2)
                .build()
        }
    }
}