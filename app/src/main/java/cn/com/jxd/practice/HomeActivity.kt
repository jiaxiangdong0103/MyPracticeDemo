package cn.com.jxd.practice

import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import cn.com.jxd.base.ui.BaseViewBindingActivity
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
    private lateinit var transaction : FragmentTransaction
    private val mFragments = mutableListOf(UIFragment.instance(),NetFragment.instance())
    override fun createViewBinding() = ActivityHomeBinding.inflate(layoutInflater)

    override fun initView() {
        transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_content,mFragments[0])
        transaction.add(R.id.fl_content,mFragments[1])
        switchType(transaction,mPosition)
    }

    override fun initData() {
        mViewBinding.ivUI.setOnClickListener {
            if(mPosition!=0){
                switchType(supportFragmentManager.beginTransaction(),0)
            }
        }
        mViewBinding.ivNet.setOnClickListener {
            if(mPosition!=1){
                switchType(supportFragmentManager.beginTransaction(),1)
            }
        }
    }
    private fun switchType( transaction: FragmentTransaction,position: Int){
        for(i in mFragments.indices){
            if(i==position){
                transaction.show(mFragments[i])
                transaction.setMaxLifecycle(mFragments[i], Lifecycle.State.RESUMED)
            }else{
                transaction.hide(mFragments[i])
                transaction.setMaxLifecycle(mFragments[i], Lifecycle.State.STARTED)
            }
        }
        transaction.commitAllowingStateLoss()
        mPosition = position
    }
}