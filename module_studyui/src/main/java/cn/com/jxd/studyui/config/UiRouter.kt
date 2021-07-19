package cn.com.jxd.studyui.config

import cn.com.jxd.commonutil.router.PageJumpUtil

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
    //协程
    fun gotoTestCoroutinesActivity() {
        PageJumpUtil.gotoActivity(UiRouterPath.ACTIVITY_TEST_COROUTINES)
    }
    //协程
    fun gotoTestCoroutinesExpActivity() {
        PageJumpUtil.gotoActivity(UiRouterPath.ACTIVITY_TEST_COROUTINES_EXP)
    }
    //协程
    fun gotoTestCoroutinesFlowActivity() {
        PageJumpUtil.gotoActivity(UiRouterPath.ACTIVITY_TEST_COROUTINES_FLOW)
    }
    //新手引导
    fun gotoTestGuideActivity() {
        PageJumpUtil.gotoActivity(UiRouterPath.ACTIVITY_TEST_GUIDE)
    }

    //Worker
    fun gotoWorkerActivity() {
        PageJumpUtil.gotoActivity(UiRouterPath.ACTIVITY_WORKER)
    }
    //Worker
    fun gotoWorkerBasisActivity() {
        PageJumpUtil.gotoActivity(UiRouterPath.ACTIVITY_TEST_WORKER)
    }
    //Worker 高级操作
    fun gotoWorkerExpertActivity() {
        PageJumpUtil.gotoActivity(UiRouterPath.ACTIVITY_TEST_WORKER_EXPERT)
    }
    //Worker 初级操作
    fun gotoWorkerPrimaryActivity() {
        PageJumpUtil.gotoActivity(UiRouterPath.ACTIVITY_TEST_WORKER_PRIMARY)
    }
    //存储相关
    fun gotoTestStorageActivity() {
        PageJumpUtil.gotoActivity(UiRouterPath.ACTIVITY_TEST_STORAGE)
    }
    //
    fun gotoTestImageActivity() {
        PageJumpUtil.gotoActivity(UiRouterPath.ACTIVITY_TEST_IMAGE)
    }
}