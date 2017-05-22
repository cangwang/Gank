package material.com.base.ui.circleprogress

/**
 * Created by cangwang on 2017/3/31.
 */

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.net.Uri
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView

import material.com.base.R

/**
 * Private class created to work around issues with AnimationListeners being
 * called before the animation is actually complete and support shadows on older
 * platforms.
 */
class CircleProgressBar : ImageView {

    private var mListener: Animation.AnimationListener? = null
    private var mShadowRadius: Int = 0
    private var mBackGroundColor: Int = 0
    private var mProgressColor: Int = 0
    private var mProgressStokeWidth: Int = 0
    private var mArrowWidth: Int = 0
    private var mArrowHeight: Int = 0
    private var mProgress: Int = 0
    var max: Int = 0
    private var mDiameter: Int = 0
    private var mInnerRadius: Int = 0
    private var mTextPaint: Paint? = null
    private var mTextColor: Int = 0
    private var mTextSize: Int = 0
    var isShowProgressText: Boolean = false
    var isShowArrow: Boolean = false
    private var mProgressDrawable: MaterialProgressDrawable? = null
    private var mBgCircle: ShapeDrawable? = null
    private var mCircleBackgroundEnabled: Boolean = false
    private var mColors = intArrayOf(Color.BLACK)

    constructor(context: Context) : super(context) {
        init(context, null, 0)

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, 0)

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val a = context.obtainStyledAttributes(
                attrs, R.styleable.CircleProgressBar, defStyleAttr, 0)
        //        <attr name="mlpb_inner_radius" format="dimension"/>
        //        <attr name="mlpb_background_color" format="color"/>
        //        <attr name="mlpb_progress_color" format="color"/>
        //        <attr name="mlpb_progress_stoke_width" format="dimension"/>
        //        <attr name="mlpb_arrow_width" format="dimension"/>
        //        <attr name="mlpb_arrow_height" format="dimension"/>
        //
        //        <attr name="mlpb_progress" format="integer"/>
        //        <attr name="mlpb_max" format="integer"/>
        //
        //
        //        <attr name="mlpb_progress_text_size" format="dimension"/>
        //        <attr name="mlpb_progress_text_color" format="color"/>
        //
        //        <attr name="mlpb_progress_text_offset" format="dimension"/>
        //
        //        <attr name="mlpb_progress_text_visibility" format="enum">
        //        <enum name="visible" value="0"/>
        //        <enum name="invisible" value="1"/>
        //        </attr>
        val density = getContext().resources.displayMetrics.density

        mBackGroundColor = a.getColor(
                R.styleable.CircleProgressBar_mlpb_background_color, DEFAULT_CIRCLE_BG_LIGHT)

        mProgressColor = a.getColor(
                R.styleable.CircleProgressBar_mlpb_progress_color, DEFAULT_CIRCLE_BG_LIGHT)
        mColors = intArrayOf(mProgressColor)

        mInnerRadius = a.getDimensionPixelOffset(
                R.styleable.CircleProgressBar_mlpb_inner_radius, -1)

        mProgressStokeWidth = a.getDimensionPixelOffset(
                R.styleable.CircleProgressBar_mlpb_progress_stoke_width, (STROKE_WIDTH_LARGE * density).toInt())
        mArrowWidth = a.getDimensionPixelOffset(
                R.styleable.CircleProgressBar_mlpb_arrow_width, -1)
        mArrowHeight = a.getDimensionPixelOffset(
                R.styleable.CircleProgressBar_mlpb_arrow_height, -1)
        mTextSize = a.getDimensionPixelOffset(
                R.styleable.CircleProgressBar_mlpb_progress_text_size, (DEFAULT_TEXT_SIZE * density).toInt())
        mTextColor = a.getColor(
                R.styleable.CircleProgressBar_mlpb_progress_text_color, Color.BLACK)

        isShowArrow = a.getBoolean(R.styleable.CircleProgressBar_mlpb_show_arrow, false)
        mCircleBackgroundEnabled = a.getBoolean(R.styleable.CircleProgressBar_mlpb_enable_circle_background, true)


        mProgress = a.getInt(R.styleable.CircleProgressBar_mlpb_progress, 0)
        max = a.getInt(R.styleable.CircleProgressBar_mlpb_max, 100)
        val textVisible = a.getInt(R.styleable.CircleProgressBar_mlpb_progress_text_visibility, 1)
        if (textVisible != 1) {
            isShowProgressText = true
        }

        mTextPaint = Paint()
        mTextPaint!!.style = Paint.Style.FILL
        mTextPaint!!.color = mTextColor
        mTextPaint!!.textSize = mTextSize.toFloat()
        mTextPaint!!.isAntiAlias = true
        a.recycle()
        mProgressDrawable = MaterialProgressDrawable(getContext(), this)
        super.setImageDrawable(mProgressDrawable)
    }


    private fun elevationSupported(): Boolean {
        return android.os.Build.VERSION.SDK_INT >= 21
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (!elevationSupported()) {
            setMeasuredDimension(measuredWidth + mShadowRadius * 2, measuredHeight + mShadowRadius * 2)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        val density = context.resources.displayMetrics.density
        mDiameter = Math.min(measuredWidth, measuredHeight)
        if (mDiameter <= 0) {
            mDiameter = density.toInt() * DEFAULT_CIRCLE_DIAMETER
        }
        if (background == null && mCircleBackgroundEnabled) {
            val shadowYOffset = (density * Y_OFFSET).toInt()
            val shadowXOffset = (density * X_OFFSET).toInt()
            mShadowRadius = (density * SHADOW_RADIUS).toInt()

            if (elevationSupported()) {
                mBgCircle = ShapeDrawable(OvalShape())
                ViewCompat.setElevation(this, SHADOW_ELEVATION * density)
            } else {
                val oval = OvalShadow(mShadowRadius, mDiameter - mShadowRadius * 2)
                mBgCircle = ShapeDrawable(oval)
                ViewCompat.setLayerType(this, ViewCompat.LAYER_TYPE_SOFTWARE, mBgCircle!!.paint)
                mBgCircle!!.paint.setShadowLayer(mShadowRadius.toFloat(), shadowXOffset.toFloat(), shadowYOffset.toFloat(),
                        KEY_SHADOW_COLOR)
                val padding = mShadowRadius.toInt()
                // set padding so the inner image sits correctly within the shadow.
                setPadding(padding, padding, padding, padding)
            }
            mBgCircle!!.paint.color = mBackGroundColor
            setBackgroundDrawable(mBgCircle)
        }
        mProgressDrawable!!.setBackgroundColor(mBackGroundColor)
        mProgressDrawable!!.setColorSchemeColors(*mColors)
        mProgressDrawable!!.setSizeParameters(mDiameter.toDouble(), mDiameter.toDouble(),
                (if (mInnerRadius <= 0) (mDiameter - mProgressStokeWidth * 2) / 4 else mInnerRadius).toDouble(),
                mProgressStokeWidth.toDouble(),
                (if (mArrowWidth < 0) mProgressStokeWidth * 4 else mArrowWidth).toFloat(),
                (if (mArrowHeight < 0) mProgressStokeWidth * 2 else mArrowHeight).toFloat())
        if (isShowArrow) {
            mProgressDrawable!!.showArrowOnFirstStart(true)
            mProgressDrawable!!.setArrowScale(1f)
            mProgressDrawable!!.showArrow(true)
        }
        super.setImageDrawable(null)
        super.setImageDrawable(mProgressDrawable)
        mProgressDrawable!!.alpha = 255
        if (visibility == View.VISIBLE) {
            mProgressDrawable!!.start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isShowProgressText) {
            val text = String.format("%s%%", mProgress)
            val x = width / 2 - text.length * mTextSize / 4
            val y = height / 2 + mTextSize / 4
            canvas.drawText(text, x.toFloat(), y.toFloat(), mTextPaint!!)
        }
    }

    override fun setImageResource(resId: Int) {

    }


    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
    }

    override fun setImageDrawable(drawable: Drawable?) {}

    fun setAnimationListener(listener: Animation.AnimationListener) {
        mListener = listener
    }

    public override fun onAnimationStart() {
        super.onAnimationStart()
        if (mListener != null) {
            mListener!!.onAnimationStart(animation)
        }
    }

    public override fun onAnimationEnd() {
        super.onAnimationEnd()
        if (mListener != null) {
            mListener!!.onAnimationEnd(animation)
        }
    }


    /**
     * Set the color resources used in the progress animation from color resources.
     * The first color will also be the color of the bar that grows in response
     * to a user swipe gesture.

     * @param colorResIds
     */
    fun setColorSchemeResources(vararg colorResIds: Int) {
        val res = resources
        val colorRes = IntArray(colorResIds.size)
        for (i in colorResIds.indices) {
            colorRes[i] = res.getColor(colorResIds[i])
        }
        setColorSchemeColors(*colorRes)
    }

    /**
     * Set the colors used in the progress animation. The first
     * color will also be the color of the bar that grows in response to a user
     * swipe gesture.

     * @param colors
     */
    fun setColorSchemeColors(vararg colors: Int) {
        mColors = colors
        if (mProgressDrawable != null) {
            mProgressDrawable!!.setColorSchemeColors(*colors)
        }
    }

    /**
     * Update the background color of the mBgCircle image view.
     */
    override fun setBackgroundColor(colorRes: Int) {
        if (background is ShapeDrawable) {
            val res = resources
            (background as ShapeDrawable).paint.color = res.getColor(colorRes)
        }
    }

    var progress: Int
        get() = mProgress
        set(progress) {
            if (max > 0) {
                mProgress = progress
            }
        }


    fun circleBackgroundEnabled(): Boolean {
        return mCircleBackgroundEnabled
    }

    fun setCircleBackgroundEnabled(enableCircleBackground: Boolean) {
        this.mCircleBackgroundEnabled = enableCircleBackground
    }

    override fun getVisibility(): Int {
        return super.getVisibility()
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        if (mProgressDrawable != null) {
            mProgressDrawable!!.setVisible(visibility == View.VISIBLE, false)
            if (visibility != View.VISIBLE) {
                mProgressDrawable!!.stop()
            } else {
                if (mProgressDrawable!!.isRunning) {
                    mProgressDrawable!!.stop()
                }
                mProgressDrawable!!.start()
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (mProgressDrawable != null) {
            mProgressDrawable!!.stop()
            mProgressDrawable!!.setVisible(visibility == View.VISIBLE, false)

            requestLayout()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (mProgressDrawable != null) {
            mProgressDrawable!!.stop()
            mProgressDrawable!!.setVisible(false, false)
        }
    }


    private inner class OvalShadow(private val mShadowRadius: Int, private val mCircleDiameter: Int) : OvalShape() {
        private val mRadialGradient: RadialGradient
        private val mShadowPaint: Paint

        init {
            mShadowPaint = Paint()
            mRadialGradient = RadialGradient((mCircleDiameter / 2).toFloat(), (mCircleDiameter / 2).toFloat(),
                    mShadowRadius.toFloat(), intArrayOf(FILL_SHADOW_COLOR, Color.TRANSPARENT), null, Shader.TileMode.CLAMP)
            mShadowPaint.shader = mRadialGradient
        }

        override fun draw(canvas: Canvas, paint: Paint) {
            val viewWidth = this@CircleProgressBar.width
            val viewHeight = this@CircleProgressBar.height
            canvas.drawCircle((viewWidth / 2).toFloat(), (viewHeight / 2).toFloat(), (mCircleDiameter / 2 + mShadowRadius).toFloat(),
                    mShadowPaint)
            canvas.drawCircle((viewWidth / 2).toFloat(), (viewHeight / 2).toFloat(), (mCircleDiameter / 2).toFloat(), paint)
        }
    }

    companion object {

        private val KEY_SHADOW_COLOR = 0x1E000000
        private val FILL_SHADOW_COLOR = 0x3D000000
        // PX
        private val X_OFFSET = 0f
        private val Y_OFFSET = 1.75f
        private val SHADOW_RADIUS = 3.5f
        private val SHADOW_ELEVATION = 4


        private val DEFAULT_CIRCLE_BG_LIGHT = 0xFFFAFAFA.toInt()
        private val DEFAULT_CIRCLE_DIAMETER = 56
        private val STROKE_WIDTH_LARGE = 3
        val DEFAULT_TEXT_SIZE = 9
    }
}
