package cn.com.jxd.studyui.ktx.livedata

import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import cn.com.jxd.commonutil.log.LogUtils
import cn.com.jxd.commonutil.ui.BaseViewBindingActivity
import cn.com.jxd.studyui.config.UiRouterPath
import cn.com.jxd.studyui.databinding.ActivityTestLiveBinding
import com.alibaba.android.arouter.facade.annotation.Route
import kotlin.system.measureTimeMillis

/**
 * @author xiangdong.jia
 * @description:
 * @date :2021/4/26 下午7:00
 */
@Route(path = UiRouterPath.ACTIVITY_TEST_LIVE_DATA)
class TestLiveDataActivity : BaseViewBindingActivity<ActivityTestLiveBinding>() {
    //使用fragment-ktx后的写法
    private  val mViewModel by viewModels<TestViewModel>()

    override fun createViewBinding() = ActivityTestLiveBinding.inflate(layoutInflater)

    override fun initView() {

    }

    override fun initData() {
        //之前的写法
        //mViewModel = ViewModelProvider(this).get(TestViewModel::class.java)

        //普通用法
        mViewBinding.tvGetUserName.setOnClickListener {
            val time = measureTimeMillis {
                for (i in 0..10000){
                    mViewModel.getUserMessage()
                }
            }
            LogUtils.j("执行时间 ： $time")

        }
        mViewModel.mUserLiveData.observe(this) {
            mViewBinding.tvUserName.text = it.name
        }

        //liveData Ktx 用法
        mViewModel.mUserLiveDataKtx.observe(this){
            mViewBinding.tvUserName.text = it.name
        }

        //viewModelScope 用法
        mViewBinding.tvGetUserName2.setOnClickListener {
            val time = measureTimeMillis {
                for (i in 0..10000){
                    mViewModel.getUserMessageViewModel()
                }
            }
            LogUtils.j("使用viewModelScope 执行时间 ： $time")

        }
        mViewModel.mUserLiveDataViewModel.observe(this) {
            mViewBinding.tvUserName.text = it.name
        }
    }
}