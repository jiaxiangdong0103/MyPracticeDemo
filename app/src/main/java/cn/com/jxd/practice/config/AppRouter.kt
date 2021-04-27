package cn.com.jxd.practice.config

import cn.com.jxd.base.router.PageJumpUtil

/**
 * @author xiangdong.jia
 * @description:
 * @date :2021/4/27 下午5:29
 */
object AppRouter {
    fun gotoHomeActivity() {
        PageJumpUtil.gotoActivity(AppRouterPath.ACTIVITY_HOME)
    }
}