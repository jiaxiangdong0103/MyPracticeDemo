package cn.com.jxd.studyui.config

import cn.com.jxd.base.router.PageJumpUtil

/**
 * @author xiangdong.jia
 * @description:
 * @date :2021/4/27 下午7:43
 */
object UiRouter {
    fun gotoTestKTXActivity() {
        PageJumpUtil.gotoActivity(UiRouterPath.ACTIVITY_TEST_KTX)
    }

    fun gotoNavigationActivity() {
        PageJumpUtil.gotoActivity(UiRouterPath.ACTIVITY_NAVIGATION)
    }

    fun gotoTestLiveDataActivity() {
        PageJumpUtil.gotoActivity(UiRouterPath.ACTIVITY_TEST_LIVE_DATA)
    }


}