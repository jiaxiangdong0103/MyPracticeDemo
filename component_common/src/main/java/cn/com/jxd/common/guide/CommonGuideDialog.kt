package cn.com.jxd.common.guide

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import cn.com.jxd.base.extension.visible
import cn.com.jxd.base.utils.SYScreenUtils
import cn.com.jxd.base.utils.SYViewUtil
import cn.com.jxd.component_commin.R
import cn.com.jxd.component_commin.databinding.CommonMyGuideViewBinding

/**
 * @author xiangdong.jia
 * @description: 新手指导弹窗
 * @date :2020/8/14 下午8:33
 */
class CommonGuideDialog(private val mContext: Context) : Dialog(mContext, R.style.dialog_style) {
    private lateinit var mContentView: View //根布局
    private lateinit var mTargetView  : View// 目标view mIvGuideTargetView 会根据该view的位置显示
    private  var mOnDismissListener : GuideViewDismissListener? = null //引导消失时候的监听
    private lateinit var llGuideView : ConstraintLayout //引导view
    private lateinit var mClGuideTipsView : ConstraintLayout //提示语view

    private lateinit var mViewBinding : CommonMyGuideViewBinding

    init {
        initView()
    }

    private fun initView() {
        this.window?.apply {
            setWindowAnimations(-1)
            //解决进度弹窗出现时，状态栏变成黑色
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                statusBarColor = Color.TRANSPARENT
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    try {
                        val decorViewClazz = Class.forName("com.android.internal.policy.DecorView")
                        val field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor")
                        field.isAccessible = true
                        field.setInt(decorView, Color.TRANSPARENT)
                    } catch (e: Exception) {
                    }
                }
            }
        }

    }

    /**
     * 设置指导view的id
     * @param contentView 想要展示指导的view  使用默认的时候不传
     * @return
     */
    @SuppressLint("InflateParams")
    fun loadContentView(contentView: Int = 0 ): CommonGuideDialog {
        if (contentView == 0) {
            mContentView =
                LayoutInflater.from(context).inflate(R.layout.common_my_guide_view, null, false)
            mViewBinding = CommonMyGuideViewBinding.bind(mContentView)
            mClGuideTipsView  = mContentView.findViewById(R.id.cl_guide_tips_view)
            llGuideView = mContentView.findViewById(R.id.ll_guide_target_view)
        } else {
            mContentView = LayoutInflater.from(context).inflate(contentView, null, false)
        }
        setContentView(mContentView)
        return this
    }

    /**
     * 设置指导view的src
     *
     * @param TargetViewSrc 目标view的src
     * @return
     */
    fun setTargetViewSrc(TargetViewSrc: Int): CommonGuideDialog {
        if (TargetViewSrc == -1) {
            return this
        }
        mViewBinding.ivGuideTargetView.visibility = View.VISIBLE
        mViewBinding.ivGuideTargetView.setImageDrawable(
            ContextCompat.getDrawable(
                    mContext,
                TargetViewSrc
            )
        )
        return this
    }

    /**
     * 目标位置view
     *
     * @param mTargetView 目标view 会覆盖到该view上
     * @return
     */
    fun setTargetView(mTargetView: View): CommonGuideDialog {
        this.mTargetView = mTargetView
        return this
    }

    /**
     * 设置指导view的内容
     *
     * @param TargetViewSrc 目标view的src
     * @return
     */
    fun setGuideTipsViewContent(title: String, desc: String,  tipsIconBg: Int): CommonGuideDialog {
        mViewBinding.tvTipsTitle.text =title
        mViewBinding.tvTipsDesc.text =desc

        if(tipsIconBg!=-1){
            mViewBinding.ivGuideTargetView.setBackgroundColor(
                ContextCompat.getColor(
                        mContext,
                    tipsIconBg
                )
            )
        }
        return this
    }
    /**
     * 提示弹窗相对于目标view的方位
     *
     * @param position 1：左边  2：上面  3：右面 4：下面
     * @return
     */
    fun setTipsViewPosition(position: Int): CommonGuideDialog {
        val targetViewID = R.id.ll_guide_target_view
        val lp = mClGuideTipsView.layoutParams as ConstraintLayout.LayoutParams
        val margin = SYScreenUtils.dp2px(8f).toInt()
        when (position) {
            1 -> {
                lp.endToStart = targetViewID
                lp.rightMargin = margin
            }
            2 -> {
                lp.bottomToTop = targetViewID
                lp.bottomMargin = margin
            }
            3 -> {
                lp.startToEnd = targetViewID
                lp.leftMargin = margin
            }
            4 -> {
                lp.topToBottom = targetViewID
                lp.topMargin = margin
            }
        }
        mClGuideTipsView.layoutParams = lp
        return this
    }

    /**
     * 提示弹窗相对于目标view的对齐方式
     *
     * @param alignment 1：左对齐  2：上对齐  3：右对齐 4：下对齐
     * @return
     */
    fun setTipsViewAlignment(alignment: MutableList<Int>): CommonGuideDialog {
        val targetViewID = R.id.ll_guide_target_view
        val lp = mClGuideTipsView.layoutParams as ConstraintLayout.LayoutParams
        alignment.forEach {
            when (it) {
                1 -> lp.startToStart = targetViewID
                2 -> lp.topToTop = targetViewID
                3 -> lp.endToEnd = targetViewID
                4 -> lp.bottomToBottom = targetViewID
            }
        }
        mClGuideTipsView.layoutParams = lp
        return this
    }

    /**
     * 指导view消失时的监听
     *
     * @param onDismissListener 监听事件
     */
    fun setOnDismissListener(onDismissListener: GuideViewDismissListener?): CommonGuideDialog {
        mOnDismissListener = onDismissListener
        return this
    }

    /**
     * 是否是圆形
     * @param isRound
     * @return
     */
    fun setGuideTipsViewContent(isRound: Boolean): CommonGuideDialog {
            mViewBinding.ivGuideTargetView.isOval = isRound
            llGuideView.setBackgroundResource(if (isRound) R.drawable.common_guide_target_view_r56_bg else R.drawable.common_guide_target_view_r4_bg)
        return this
    }

    override fun onStart() {
        if(mViewBinding.ivGuideTargetView.visibility == View.GONE){
            mViewBinding.ivGuideTargetView.setImageBitmap(view2Bitmap(mTargetView))
            mViewBinding.ivGuideTargetView.visible()
        }
        this.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setGravity(Gravity.LEFT or Gravity.TOP)
        }
        if (mClGuideTipsView.visibility == View.VISIBLE) {
            //先隐藏
            llGuideView.alpha = 0.toFloat()
            mClGuideTipsView.alpha = 0.toFloat()
            //找到目标view的中心点
            val location = IntArray(2)
            mTargetView.getLocationOnScreen(location)
            val x = location[0] + mTargetView.width / 2
            val y = location[1] + mTargetView.height / 2

            llGuideView.post {
                //根据目标view的中心点 得到引导view 应该展示的坐标
                val newLeftMargin = x - llGuideView.width / 2
                var newTopMargin = y - llGuideView.height / 2

                //默认指导view的Top 应该是0 ，但是有点手机的会是状态栏的高度 如果是的话 新坐标需要减状态栏的高度
                val guideViewLocation = IntArray(2)
                llGuideView.getLocationOnScreen(guideViewLocation)
                if (guideViewLocation[1] > 0) {
                    val mStatusBarHeight = SYScreenUtils.getStatusBarHeight()
                    newTopMargin -= mStatusBarHeight
                }

                //animator 从隐藏到显示的一个动画
                val animator = ObjectAnimator.ofFloat(llGuideView, "alpha", 0f, 1f)
                animator.duration = 350
                //animator1 animator2 移动到新的坐标
                val animator1 =
                        ObjectAnimator.ofFloat(llGuideView, "translationX", 0f, newLeftMargin.toFloat())
                animator1.duration = 10
                val animator2 =
                        ObjectAnimator.ofFloat(llGuideView, "translationY", 0f, newTopMargin.toFloat())
                animator2.duration = 10

                // 先移动到指定的位置 然后再显示出来
                val aa = AnimatorSet()
                aa.play(animator1).with(animator2).before(animator)
                animator2.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        llGuideView.alpha = 1.toFloat()
                        mClGuideTipsView.alpha = 1.toFloat()
                    }
                })
                aa.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        mContentView.setOnClickListener { v: View? -> dismiss() }
                    }
                })
                aa.start()

                //将提示语 也用相同的动画 移动到新的位置
                val tipsViewAnimator = ObjectAnimator.ofFloat(mClGuideTipsView, "alpha", 0f, 1f)
                tipsViewAnimator.duration = 350
                val tipsViewAnimator1 = ObjectAnimator.ofFloat(
                        mClGuideTipsView,
                        "translationX",
                        0f,
                        newLeftMargin.toFloat()
                )
                tipsViewAnimator1.duration = 10
                val tipsViewAnimator2 = ObjectAnimator.ofFloat(
                        mClGuideTipsView,
                        "translationY",
                        0f,
                        newTopMargin.toFloat()
                )
                tipsViewAnimator2.duration = 10
                val tipsViewAnimatorSet = AnimatorSet()
                tipsViewAnimatorSet.play(tipsViewAnimator1).with(tipsViewAnimator2)
                        .before(tipsViewAnimator)
                tipsViewAnimatorSet.start()

            }
        } else {
            //这个是 全屏展示另一种 引导view
            llGuideView.alpha = 0.toFloat()
            val lp = llGuideView.layoutParams as ConstraintLayout.LayoutParams
            lp.rightToRight = R.id.guide_view_content
            lp.bottomToBottom = R.id.guide_view_content
            llGuideView.layoutParams = lp
            mContentView.setOnClickListener { dismiss() }
        }
        super.onStart()
    }

    override fun dismiss() {
        mOnDismissListener?.onDismissListener()
        super.dismiss()
    }

    interface GuideViewDismissListener {
        fun onDismissListener()
    }

    /**
     * 将view转成bitmap
     * @param view View?
     * @return Bitmap?
     */
    private fun view2Bitmap(view: View?): Bitmap? {
        if (view == null) return null

        val h = SYViewUtil.getViewHeight(view)
        val ret = Bitmap.createBitmap(
            SYViewUtil.getViewWidth(view),
            h,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(ret)
        val bgDrawable = view.background
        bgDrawable?.draw(canvas)
        canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return ret
    }
}