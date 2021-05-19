package cn.com.jxd.practice

import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import cn.com.jxd.commonutil.ui.BaseFragment
import cn.com.jxd.commonutil.ui.BaseViewBindingActivity
import cn.com.jxd.practice.config.AppRouterPath
import cn.com.jxd.practice.databinding.ActivityHomeBinding
import cn.com.jxd.studynet.ui.fragment.NetFragment
import cn.com.jxd.studyui.ui.fragment.UIFragment
import com.alibaba.android.arouter.facade.annotation.Route

/**
 * @author xiangdong.jia
 * @description: 主页
 * @date :2021/4/27 下午3:35
 */
@Route(path = AppRouterPath.ACTIVITY_HOME)
class HomeActivity : BaseViewBindingActivity<ActivityHomeBinding>() {
    private var mPosition = 0
    private val mFragments = mutableListOf<BaseFragment>()
    override fun createViewBinding() = ActivityHomeBinding.inflate(layoutInflater)

    override fun initView() {
        switchType(0)
    }

    override fun initData() {
        mViewBinding.ivUI.setOnClickListener {
            if (mPosition != 0) {
                switchType(0)
            }
        }
        mViewBinding.ivNet.setOnClickListener {
            if (mPosition != 1) {
                switchType(1)
            }
        }
    }

    private fun switchType(position: Int) {
        supportFragmentManager.commit(true) {
            if(mFragments.isEmpty()){
                mFragments.add(UIFragment.instance())
                mFragments.add(NetFragment.instance())
                add(R.id.fl_content, mFragments[0])
                add(R.id.fl_content, mFragments[1])
            }
            for (i in mFragments.indices) {
                if (i == position) {
                    show(mFragments[i])
                    setMaxLifecycle(mFragments[i], Lifecycle.State.RESUMED)
                } else {
                    hide(mFragments[i])
                    setMaxLifecycle(mFragments[i], Lifecycle.State.STARTED)
                }
            }
        }
        mPosition = position
    }
}