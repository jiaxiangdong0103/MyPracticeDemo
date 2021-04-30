package cn.com.jxd.studyui.ktx.livedata

import androidx.lifecycle.*
import cn.com.jxd.studyui.bean.User
import kotlinx.coroutines.launch

/**
 * @author xiangdong.jia
 * @description:
 * @date :2021/4/26 下午7:06
 */
class TestViewModel : ViewModel() {

    // 普通用法
    val mUserLiveData = MutableLiveData<User>()
    fun getUserMessage() {

        val mRunnable = Runnable {
            kotlin.run {
                val user = User("使用常规方式 获取来的 name")
                mUserLiveData.postValue(user)
            }
        }
        Thread(mRunnable).start()
    }

    //liveData Ktx 用法
    val mUserLiveDataKtx: LiveData<User> = liveData {
        val user = User("使用liveData Ktx 获取来的 name")
        emit(user)
    }

    //viewModelScope 用法
    val mUserLiveDataViewModel = MutableLiveData<User>()
    fun getUserMessageViewModel() {
        val user = User("使用viewModelScope 获取来的 name")
        viewModelScope.launch {
            mUserLiveDataViewModel.value = user
        }
    }
}