package cn.com.jxd.common.guide

import android.app.Activity
import android.text.TextUtils
import android.view.View
import cn.com.jxd.commonutil.db.DataStorage
import cn.com.jxd.commonutil.log.LogUtils
import cn.com.jxd.component_commin.R

/**
 * 使用方式
 * new CommonGuideViewUtils(mActivity)
 * .setGuideViewTag(tag) 必传 一个新手指导对应一个tag
 * .setContentViewId(布局id) 想要用自己的布局的时候调用 使用默认的布局时候 不要调用
 * .setPositionView(view) 根据该view的位置显示 guideview
 * .setGuideTipsViewSrc(R.drawable.account_homepage_share_card_guide_tips) guideTipsView的图片路径 不传的话 认为没有 ， guideview 则居中显示  用自己的布局的时候不需要传
 * .setTipsViewPosition(4) guideTipsView相对于guideview方位  不展示guideTipsView的时候 不需要调用
 * .setTipsViewAlignment(3) guideTipsView相对于guideview对齐方式  不展示guideTipsView的时候 不需要调用
 * .build();
 */
/**
 * @author xiangdong.jia
 * @description: 新手指导view的工具类
 * @date :2020/9/3 下午6:03
 */
class CommonGuideViewUtils(private val mActivity: Activity) {
    companion object {
        const val TAG_FIRST = "tag_first" // 第一个
        const val TAG_SECOND = "tag_second" //第二个
    }

    private var tipsTitle = ""
    private var tipsDesc = ""
    private var isRound = false
    private var tipsIconBg = -1

    //指导view的id 目标view的src 提示view的src 提示弹窗相对于目标view的方位 提示弹窗相对于目标view的对齐方式
    private var targetViewSrc = -1
    private var tipsViewPosition = -1
    private var tipsViewAlignment = mutableListOf<Int>()
    private lateinit var targetView: View //目标位置view mIvGuideTargetView 会根据该view的位置显示
    private lateinit var mGuideViewTag: String
    private var onDismissListener: CommonGuideDialog.GuideViewDismissListener? = null

    //判断是否展示过
    fun getGuideViewIsShow(mGuideViewTag: String): Boolean {
        LogUtils.j("" + DataStorage.getBoolean(mGuideViewTag, false))
        return DataStorage.getBoolean(mGuideViewTag, false)
    }

    fun setGuideViewTag(guideViewTag: String): CommonGuideViewUtils {
        mGuideViewTag = guideViewTag
        setTitleAndDesc(guideViewTag)
        return this
    }


    //目标view 会覆盖到该view上
    fun setTargetView(targetView: View): CommonGuideViewUtils {
        this.targetView = targetView
        return this
    }

    //设置指导view的src
    fun setGuideViewSrc(targetViewSrc: Int): CommonGuideViewUtils {
        this.targetViewSrc = targetViewSrc
        return this
    }

    //设置指导的消失
    fun setDismissListener(dismissListener: CommonGuideDialog.GuideViewDismissListener?): CommonGuideViewUtils {
        onDismissListener = dismissListener
        return this
    }

    /**
     * 提示弹窗相对于目标view的方位
     *
     * @param tipsViewPosition 1：左边  2：上面  3：右面 4：下面
     * @return
     */
    private fun setTipsViewPosition(tipsViewPosition: Int) {
        this.tipsViewPosition = tipsViewPosition
    }

    /**
     * 提示弹窗相对于目标view的对齐方式
     *
     * @param tipsViewAlignment 1：左对齐  2：上对齐  3：右对齐 4：下对齐 5:特殊情况
     * @return
     */
    private fun setTipsViewAlignment(vararg tipsViewAlignment: Int) {
        tipsViewAlignment.forEach {
            this.tipsViewAlignment.add(it)
        }
    }

    fun build() {
        // TODO: 2021/5/10 一般来说 新手引导都是展示一次就可以了，为了演示 我把这个先注释了
//        if (getGuideViewIsShow(mGuideViewTag)) {
//            return
//        }
        if (TextUtils.isEmpty(mGuideViewTag)) {
            LogUtils.j("请先设置GuideViewTag")
            return
        }
        if (targetView == null) {
            LogUtils.j("请设置targetView")
            return
        }
        if (tipsViewPosition == -1) {
            LogUtils.j("请设置tipsViewPosition")
            return
        }
        targetView.post {
            CommonGuideDialog(mActivity)
                .loadContentView()
                .setTargetView(targetView)
                .setTargetViewSrc(targetViewSrc)
                .setGuideTipsViewContent(isRound)
                .setGuideTipsViewContent(tipsTitle, tipsDesc, tipsIconBg)
                .setTipsViewPosition(tipsViewPosition)
                .setTipsViewAlignment(tipsViewAlignment)
                .setOnDismissListener(onDismissListener)
                .show()
        }

        DataStorage.put(mGuideViewTag, true)
    }

    /**
     * 根据引导的tag 设置 title desc 及 图片
     * @param mGuideViewTag String
     */
    private fun setTitleAndDesc(mGuideViewTag: String) {
        when (mGuideViewTag) {
            TAG_FIRST -> {
                tipsTitle = "第一个新手引导"
                tipsDesc = "来看看怎么样吧"
                setTipsViewPosition(4)
                setTipsViewAlignment(1, 3)
            }
            TAG_SECOND -> {
                tipsTitle = "第二个新手引导"
                tipsDesc = "来看看怎么样吧"
                isRound = true
                tipsIconBg = R.color.ptb_ffffff
                setTipsViewPosition(2)
                setTipsViewAlignment(1, 3)
            }

        }
    }

}