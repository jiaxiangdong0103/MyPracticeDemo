package cn.com.jxd.studynet.ui.fragment

import android.view.View
import cn.com.jxd.commonutil.ui.BaseFragment
import cn.com.jxd.commonutil.ui.BaseViewBindingFragment
import cn.com.jxd.studynet.databinding.FragmentNetBinding

/**
 * @author xiangdong.jia
 * @description:
 * @date :2021/4/27 下午4:44
 */
class NetFragment : BaseViewBindingFragment<FragmentNetBinding>() {
    override fun createViewBinding() = FragmentNetBinding.inflate(layoutInflater)

    override fun initView(view: View) {
    }

    override fun initData() {
    }

    companion object{
        fun instance():BaseFragment{
            return NetFragment()
        }
    }
}