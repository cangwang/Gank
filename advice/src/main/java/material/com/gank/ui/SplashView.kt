package material.com.gank.ui

import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/**
 * Created by cangwang on 2017/5/23.
 */
class SplashView:FrameLayout{
    var splashImageView:ImageView?=null
    var skipButton:TextView?=null
    companion object {
        @JvmStatic val IMG_URL: String = "splash_img_rul"
        @JvmStatic val ACT_URL: String = "splash_act_rul"
        @JvmStatic val SP_NAME:String = "splash"
        @JvmStatic val skipButtonSizeInDip = 36f
        @JvmStatic val skipButtonAMarginInDip = 16f
        @JvmStatic val delayTime:Long = 1000
        @JvmStatic var IMAG_PATH:String?=null

        /**
         * static method, show splashView on above of the activity
         * you should called after setContentView()
         * @param activity  activity instance
         * @param durationTime  time to countDown
         * @param defaultBitmapRes  if there's no cached bitmap, show this default bitmap;
         *                          if null == defaultBitmapRes, then will not show the splashView
         * @param listener  splash view listener contains onImageClick and onDismiss
         */
        @JvmStatic fun showSplashView(activity:Activity,durationTime:Int,defaultBitmapRes:Int,listener: OnSplashViewActionListener?){
            showSplashView(activity,durationTime,null,defaultBitmapRes,listener)
        }

        /**
         * static method, show splashView on above of the activity
         * you should called after setContentView()
         * @param activity  activity instance
         * @param durationTime  time to countDown
         * @param defaultBitmapRes  if there's no cached bitmap, show this default bitmap;
         *                          if null == defaultBitmapRes, then will not show the splashView
         * @param listener  splash view listener contains onImageClick and onDismiss
         */
        @JvmStatic fun showSplashView(activity: Activity,
                                             durationTime: Int?,
                                             showBitmap: Bitmap?,
                                             defaultBitmapRes: Int?,
                                             listener: OnSplashViewActionListener?) {
            val contentView = activity.window.decorView.findViewById<ViewGroup>(android.R.id.content)
            if (null == contentView || 0 == contentView.childCount) {
                throw IllegalStateException("You should call showSplashView() after setContentView() in Activity instance")
            }
            IMAG_PATH = activity.filesDir.absolutePath.toString() + "/splash_img.jpg"
            val splashView = SplashView(activity)
            val param = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            if (listener !=null)
                splashView.setOnSplashImageClickListener(listener)
            if (null != durationTime) splashView.setDuration(durationTime)
            var bitmapToShow: Bitmap? = null

            if (isExistsLocalSplashData(activity)) {
                bitmapToShow = BitmapFactory.decodeFile(IMAG_PATH)
                val sp = activity.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                splashView.setImgUrl(sp.getString(IMG_URL, null))
                splashView.setActUrl(sp.getString(ACT_URL, null))
            } else if (null != showBitmap) {
                bitmapToShow = showBitmap
            } else if (null != defaultBitmapRes) {
                bitmapToShow = BitmapFactory.decodeResource(activity.resources, defaultBitmapRes)
            }

            if (null == bitmapToShow) return
            splashView.setImage(bitmapToShow)
            activity.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
            if (activity is AppCompatActivity) {
                val supportActionBar = activity.supportActionBar
                if (null != supportActionBar) {
                    supportActionBar.setShowHideAnimationEnabled(false)
                    splashView.isActionBarShowing = supportActionBar.isShowing
                    supportActionBar.hide()
                }
            } else if (activity is Activity) {
                val actionBar = activity.actionBar
                if (null != actionBar) {
                    splashView.isActionBarShowing = actionBar.isShowing
                    actionBar.hide()
                }
            }
            contentView.addView(splashView, param)
        }

        @JvmStatic fun simpleShowSplashView(activity:Activity){
            showSplashView(activity,0,0,null)
        }
        @JvmStatic fun isExistsLocalSplashData(activity: Activity): Boolean {
            val sp = activity.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val imgUrl = sp.getString(IMG_URL, null)
            return !TextUtils.isEmpty(imgUrl) && isFileExist(IMAG_PATH)
        }

        @JvmStatic
        private fun getAndSaveNetWorkBitmap(urlString: String) {
            val getAndSaveImageRunnable = Runnable {
                var imgUrl: URL? = null
                var bitmap: Bitmap? = null
                try {
                    imgUrl = URL(urlString)
                    val urlConn = imgUrl.openConnection() as HttpURLConnection
                    urlConn.doInput = true
                    urlConn.connect()
                    val `is` = urlConn.inputStream
                    bitmap = BitmapFactory.decodeStream(`is`)
                    `is`.close()
                    saveBitmapFile(bitmap, IMAG_PATH)
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            Thread(getAndSaveImageRunnable).start()
        }

        @JvmStatic
        @Throws(IOException::class)
        private fun saveBitmapFile(bm: Bitmap, filePath: String?) {
            if(filePath==null) return
            val myCaptureFile = File(filePath)
            val bos = BufferedOutputStream(FileOutputStream(myCaptureFile))
            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos)
            bos.flush()
            bos.close()
        }

        @JvmStatic
        fun isFileExist(filePath: String?): Boolean {
            if (TextUtils.isEmpty(filePath)) {
                return false
            } else {
                val file = File(filePath)
                return file.exists() && file.isFile
            }
        }
        /**
         * static method, update splash view data
         * @param imgUrl - url of image which you want to set as splash image
         * *
         * @param actionUrl - related action url, such as webView etc.
         */
        @JvmStatic fun updateSplashData(activity: Activity, imgUrl: String, actionUrl: String?) {
            IMAG_PATH = activity.filesDir.absolutePath.toString() + "/splash_img.jpg"

            val editor = activity.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit()
            editor.putString(IMG_URL, imgUrl)
            editor.putString(ACT_URL, actionUrl)
            editor.apply()

            getAndSaveNetWorkBitmap(imgUrl)
        }

    }
    private var duration:Int = 6
    private var imgUrl:String ?=null
    private var actUrl:String ?=null
    private var isActionBarShowing:Boolean = true
    private var mActivity:Activity?=null
    private var mOnSplashViewActionListener:OnSplashViewActionListener?=null
    private var splashSkipButtonBg:GradientDrawable = GradientDrawable()

    interface OnSplashViewActionListener{
        fun onSplashImageClick(actionUrl:String?)
        fun onSplashViewDismiss(initiativeDismiss:Boolean)
    }

    var ha = object :Handler(){}
    var timerRunnable = object:Runnable {
        override fun run() {
            if (0==duration){
                dismissSplashView(false)
            }else{
                setDuration(--duration)
                ha.postDelayed(this, delayTime)
            }
        }
    }

    fun setImage(image:Bitmap){
        if(splashImageView !=null && image !=null)
            splashImageView!!.setImageBitmap(image)
    }

    constructor(context: Context) : super(context) {
        mActivity = context as Activity
        init(context, null, 0)

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mActivity = context as Activity
        init(context, attrs, 0)

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mActivity = context as Activity
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        splashSkipButtonBg.shape = GradientDrawable.OVAL
        splashSkipButtonBg.setColor(Color.parseColor("#66333333"))
        splashImageView = ImageView(mActivity)
        splashImageView!!.scaleType = ImageView.ScaleType.FIT_XY
        splashImageView!!.setBackgroundColor(mActivity!!.resources.getColor(android.R.color.white))
        var imageViewLayoutParams:LayoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        addView(splashImageView,imageViewLayoutParams)
        splashImageView!!.isClickable = true

        skipButton = TextView(mActivity)
        var skipButtonSize:Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, skipButtonSizeInDip,mActivity!!.resources.displayMetrics).toInt()
        var skipButtonLayoutParams:LayoutParams = LayoutParams(skipButtonSize,skipButtonSize)
        skipButtonLayoutParams.gravity = Gravity.TOP or  Gravity.RIGHT
        var skipButtonMargin:Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, skipButtonAMarginInDip,mActivity!!.resources.displayMetrics).toInt()
        skipButtonLayoutParams.setMargins(0,skipButtonMargin,skipButtonMargin,0)
        skipButton!!.gravity = Gravity.CENTER
        skipButton!!.setTextColor(mActivity!!.resources.getColor(android.R.color.white))
        skipButton!!.setBackgroundDrawable(splashSkipButtonBg)
        skipButton!!.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10f)
        addView(skipButton,skipButtonLayoutParams)
        skipButton!!.setOnClickListener { dismissSplashView(true) }
        setDuration(duration)
        ha.postDelayed(timerRunnable, delayTime)
    }

    private fun setImgUrl(imgUrl:String){
        this.imgUrl=imgUrl
    }

    private fun setActUrl(actUrl:String){
        this.actUrl= actUrl
    }

    private fun setDuration(duration: Int){
        this.duration = duration
        skipButton!!.text = String.format("跳过\n%d s",duration)
    }

    private fun setOnSplashImageClickListener(listener:OnSplashViewActionListener){
        mOnSplashViewActionListener = listener
        splashImageView!!.setOnClickListener {
            listener.onSplashImageClick(actUrl)
        }
    }

    private fun dismissSplashView(initiativeDismiss: Boolean) {
        if (null != mOnSplashViewActionListener) mOnSplashViewActionListener!!.onSplashViewDismiss(initiativeDismiss)

        ha.removeCallbacks(timerRunnable)
        if(this.parent !=null) {
            val parent = this.parent as ViewGroup
            if (null != parent) {
                val animator = ObjectAnimator.ofFloat(this@SplashView, "scale", 0.0f, 0.5f).setDuration(600)
                animator.start()
                animator.addUpdateListener { animation ->
                    val cVal = animation.animatedValue as Float
                    this@SplashView.alpha = 1.0f - 2.0f * cVal
                    this@SplashView.scaleX = 1.0f + cVal
                    this@SplashView.scaleY = 1.0f + cVal
                }
                animator.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {

                    }

                    override fun onAnimationEnd(animation: Animator) {
                        parent.removeView(this@SplashView)
                        showSystemUi()
                    }

                    override fun onAnimationCancel(animation: Animator) {
                        parent.removeView(this@SplashView)
                        showSystemUi()
                    }

                    override fun onAnimationRepeat(animation: Animator) {

                    }
                })
            }
        }
    }

    private fun showSystemUi() {
        mActivity!!.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        if (mActivity is AppCompatActivity) {
            val supportActionBar = (mActivity as AppCompatActivity).supportActionBar
            if (null != supportActionBar) {
                if (isActionBarShowing) supportActionBar.show()
            }
        } else if (mActivity is Activity) {
            val actionBar = mActivity!!.getActionBar()
            if (null != actionBar) {
                if (isActionBarShowing) actionBar.show()
            }
        }
    }

}
