package cn.com.jxd.studyui.ktx.navigation.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import cn.com.jxd.commonutil.ui.BaseFragment
import cn.com.jxd.commonutil.ui.BaseViewBindingFragment
import cn.com.jxd.studyui.R
import cn.com.jxd.studyui.databinding.FragmentNavigationFistBinding
import cn.com.jxd.studyui.mlifecycle.LifecycleExample

/**
 * @author xiangdong.jia
 * @description: 第一个Fragment
 * @date :2021/4/28 下午2:16
 */
class FistFragment : BaseViewBindingFragment<FragmentNavigationFistBinding>() {

    companion object{
        fun instance() : BaseFragment{
            return FistFragment()
        }
    }
    override fun createViewBinding() = FragmentNavigationFistBinding.inflate(layoutInflater)

    override fun initView(view: View) {
        lifecycle.addObserver(LifecycleExample("FistFragment"))
    }

    override fun initData() {
        //点击也可跳转到SecondFragment
        mViewBinding.tvFist.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(SecondFragment.TAG_ID, "123")
            // 如需传参 可以使用 Bundle 接受参数 和之前一样
            // 传参还可以使用 Safe Args 方式 我不会
            findNavController().navigate(R.id.action_nav_fist_fragment_to_nav_second_fragment,bundle)
        }
    }
}