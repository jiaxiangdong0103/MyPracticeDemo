package cn.com.jxd.studyui.ktx.navigation.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import cn.com.jxd.commonutil.ui.BaseFragment
import cn.com.jxd.commonutil.ui.BaseViewBindingFragment
import cn.com.jxd.studyui.R
import cn.com.jxd.studyui.databinding.FragmentNavigationSecondBinding
import cn.com.jxd.studyui.mlifecycle.LifecycleExample

/**
 * @author xiangdong.jia
 * @description:第二个Fragment
 * @date :2021/4/28 下午2:16
 */
class SecondFragment : BaseViewBindingFragment<FragmentNavigationSecondBinding>() {
    val mID by lazy {
        arguments?.getString(TAG_ID) ?: "-1"
    }
    val mName by lazy {
        arguments?.getString("name") ?: "-1"
    }
    companion object {
        const val TAG_ID = "TAG_ID"
        fun instance(id: String = ""): BaseFragment {
            val mFragment = SecondFragment()
            val bundle = Bundle()
            bundle.putString(TAG_ID, id)
            mFragment.arguments = bundle
            return mFragment
        }
    }

    override fun createViewBinding() = FragmentNavigationSecondBinding.inflate(layoutInflater)

    override fun initView(view: View) {
        lifecycle.addObserver(LifecycleExample("SecondFragment"))
    }

    override fun initData() {
        //点击也可跳转到FistFragment
        mViewBinding.tvSecond.apply {
            text = "第一个fragment \n id=$mID name=$mName"
            setOnClickListener {
                findNavController().navigate(R.id.action_nav_second_fragment_to_nav_fist_fragment)
            }
        }

    }
}