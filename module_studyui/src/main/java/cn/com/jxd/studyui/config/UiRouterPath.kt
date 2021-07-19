package cn.com.jxd.studyui.config

/**
 * @author xiangdong.jia
 * @description:
 * @date :2021/4/27 下午7:43
 */
object UiRouterPath {
    private const val GROUP = "/uiGroup/"
    const val ACTIVITY_NAVIGATION = "${GROUP}NavigationActivity"
    const val ACTIVITY_TEST_LIVE_DATA = "${GROUP}TestLiveDataActivity"
    const val ACTIVITY_TEST_KTX = "${GROUP}TestKtxActivity"
    const val ACTIVITY_TEST_COROUTINES = "${GROUP}TestCoroutinesActivity"
    const val ACTIVITY_TEST_COROUTINES_EXP = "${GROUP}TestCoroutinesExpActivity"
    const val ACTIVITY_TEST_COROUTINES_FLOW = "${GROUP}TestCoroutinesFlowActivity"
    const val ACTIVITY_TEST_GUIDE = "${GROUP}TestGuideActivity"
    const val ACTIVITY_WORKER = "${GROUP}WorkerActivity"
    const val ACTIVITY_TEST_WORKER = "${GROUP}TestWorkerActivity"
    const val ACTIVITY_TEST_WORKER_EXPERT = "${GROUP}WorkerExpertActivity"
    const val ACTIVITY_TEST_WORKER_PRIMARY = "${GROUP}WorkerPrimaryActivity"
    const val ACTIVITY_TEST_STORAGE = "${GROUP}TestStorageActivity"
    const val ACTIVITY_TEST_IMAGE = "${GROUP}TestImageActivity"
}