package material.com.base.ui.flow

/**
 * Created by cangwang on 2017/4/1.
 */

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

import java.util.ArrayList

import material.com.base.R

/**
 * Created by cangwang on 16/4/16.
 */
class FlowLayout @JvmOverloads constructor(private val mContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ViewGroup(mContext, attrs, defStyleAttr) {
    private var usefulWidth: Int = 0 // the space of a line we can use(line's width minus the sum of left and right padding
    private var lineSpacing = 0 // the spacing between lines in flowlayout
    internal var childList: MutableList<View> = ArrayList()
    internal var lineNumList: MutableList<Int> = ArrayList()

    init {
        val mTypedArray = mContext.obtainStyledAttributes(attrs,
                R.styleable.FlowLayout)
        lineSpacing = mTypedArray.getDimensionPixelSize(
                R.styleable.FlowLayout_lineSpacing, 0)
        mTypedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val mPaddingLeft = paddingLeft
        val mPaddingRight = paddingRight
        val mPaddingTop = paddingTop
        val mPaddingBottom = paddingBottom

        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        var lineUsed = mPaddingLeft + mPaddingRight
        var lineY = mPaddingTop
        var lineHeight = 0
        for (i in 0..this.childCount - 1) {
            val child = this.getChildAt(i)
            if (child.visibility == View.GONE) {
                continue
            }
            var spaceWidth = 0
            var spaceHeight = 0
            val childLp = child.layoutParams
            if (childLp is ViewGroup.MarginLayoutParams) {
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, lineY)
                val mlp = childLp
                spaceWidth = mlp.leftMargin + mlp.rightMargin
                spaceHeight = mlp.topMargin + mlp.bottomMargin
            } else {
                measureChild(child, widthMeasureSpec, heightMeasureSpec)
            }

            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight
            spaceWidth += childWidth
            spaceHeight += childHeight

            if (lineUsed + spaceWidth > widthSize) {
                //approach the limit of width and move to next line
                lineY += lineHeight + lineSpacing
                lineUsed = mPaddingLeft + mPaddingRight
                lineHeight = 0
            }
            if (spaceHeight > lineHeight) {
                lineHeight = spaceHeight
            }
            lineUsed += spaceWidth
        }
        setMeasuredDimension(
                widthSize,
                if (heightMode == View.MeasureSpec.EXACTLY) heightSize else lineY + lineHeight + mPaddingBottom
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val mPaddingLeft = paddingLeft
        val mPaddingRight = paddingRight
        val mPaddingTop = paddingTop

        var lineX = mPaddingLeft
        var lineY = mPaddingTop
        val lineWidth = r - l
        usefulWidth = lineWidth - mPaddingLeft - mPaddingRight
        var lineUsed = mPaddingLeft + mPaddingRight
        var lineHeight = 0
        var lineNum = 0

        lineNumList.clear()
        for (i in 0..this.childCount - 1) {
            val child = this.getChildAt(i)
            if (child.visibility == View.GONE) {
                continue
            }
            var spaceWidth = 0
            var spaceHeight = 0
            var left = 0
            var top = 0
            var right = 0
            var bottom = 0
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            val childLp = child.layoutParams
            if (childLp is ViewGroup.MarginLayoutParams) {
                val mlp = childLp
                spaceWidth = mlp.leftMargin + mlp.rightMargin
                spaceHeight = mlp.topMargin + mlp.bottomMargin
                left = lineX + mlp.leftMargin
                top = lineY + mlp.topMargin
                right = lineX + mlp.leftMargin + childWidth
                bottom = lineY + mlp.topMargin + childHeight
            } else {
                left = lineX
                top = lineY
                right = lineX + childWidth
                bottom = lineY + childHeight
            }
            spaceWidth += childWidth
            spaceHeight += childHeight

            if (lineUsed + spaceWidth > lineWidth) {
                //approach the limit of width and move to next line
                lineNumList.add(lineNum)
                lineY += lineHeight + lineSpacing
                lineUsed = mPaddingLeft + mPaddingRight
                lineX = mPaddingLeft
                lineHeight = 0
                lineNum = 0
                if (childLp is ViewGroup.MarginLayoutParams) {
                    val mlp = childLp
                    left = lineX + mlp.leftMargin
                    top = lineY + mlp.topMargin
                    right = lineX + mlp.leftMargin + childWidth
                    bottom = lineY + mlp.topMargin + childHeight
                } else {
                    left = lineX
                    top = lineY
                    right = lineX + childWidth
                    bottom = lineY + childHeight
                }
            }
            child.layout(left, top, right, bottom)
            lineNum++
            if (spaceHeight > lineHeight) {
                lineHeight = spaceHeight
            }
            lineUsed += spaceWidth
            lineX += spaceWidth
        }
        // add the num of last line
        lineNumList.add(lineNum)
    }

    /**
     * resort child elements to use lines as few as possible
     */
    fun relayoutToCompress() {
        post { compress() }
    }

    private fun compress() {
        val childCount = this.childCount
        if (0 == childCount) {
            //no need to sort if flowlayout has no child view
            return
        }
        var count = 0
        for (i in 0..childCount - 1) {
            val v = getChildAt(i)
            if (v is BlankView) {
                //BlankView is just to make childs look in alignment, we should ignore them when we relayout
                continue
            }
            count++
        }
        val childs = arrayOfNulls<View>(count)
        val spaces = IntArray(count)
        var n = 0
        for (i in 0..childCount - 1) {
            val v = getChildAt(i)
            if (v is BlankView) {
                //BlankView is just to make childs look in alignment, we should ignore them when we relayout
                continue
            }
            childs[n] = v
            val childLp = v.layoutParams
            val childWidth = v.measuredWidth
            if (childLp is ViewGroup.MarginLayoutParams) {
                val mlp = childLp
                spaces[n] = mlp.leftMargin + childWidth + mlp.rightMargin
            } else {
                spaces[n] = childWidth
            }
            n++
        }
        val compressSpaces = IntArray(count)
        for (i in 0..count - 1) {
            compressSpaces[i] = if (spaces[i] > usefulWidth) usefulWidth else spaces[i]
        }
        sortToCompress(childs, compressSpaces)
        this.removeAllViews()
        for (v in childList) {
            this.addView(v)
        }
        childList.clear()
    }

    private fun sortToCompress(childs: Array<View?>, spaces: IntArray) {
        var childs = childs
        val childCount = childs!!.size
        var table: Array<IntArray>? = Array(childCount + 1) { IntArray(usefulWidth + 1) }
        for (i in 0..childCount + 1 - 1) {
            for (j in 0..usefulWidth - 1) {
                table!![i][j] = 0
            }
        }
        var flag: BooleanArray = BooleanArray(childCount)
        for (i in 0..childCount - 1) {
            flag[i] = false
        }
        for (i in 1..childCount) {
            for (j in spaces[i - 1]..usefulWidth) {
                table!![i][j] = if (table[i - 1][j] > table[i - 1][j - spaces[i - 1]] + spaces[i - 1]) table[i - 1][j] else table[i - 1][j - spaces[i - 1]] + spaces[i - 1]
            }
        }
        var v = usefulWidth
        run {
            var i = childCount
            while (i > 0 && v >= spaces[i - 1]) {
                if (table!![i][v] == table!![i - 1][v - spaces[i - 1]] + spaces[i - 1]) {
                    flag[i - 1] = true
                    v = v - spaces[i - 1]
                }
                i--
            }
        }
        var rest = childCount
        val restArray: Array<View?>
        val restSpaces: IntArray
        for (i in flag!!.indices) {
            if (flag[i] == true) {
                childList.add(childs[i]!!)
                rest--
            }
        }

        if (0 == rest) {
            return
        }
        restArray = arrayOfNulls<View>(rest)
        restSpaces = IntArray(rest)
        var index = 0
        for (i in flag.indices) {
            if (flag[i] == false) {
                restArray[index] = childs[i]
                restSpaces[index] = spaces[i]
                index++
            }
        }
//        table = null
//        childs = null
//        flag = null
        sortToCompress(restArray, restSpaces)
    }

    /**
     * add some blank view to make child elements look in alignment
     */
    fun relayoutToAlign() {
        post { align() }
    }

    private fun align() {
        val childCount = this.childCount
        if (0 == childCount) {
            //no need to sort if flowlayout has no child view
            return
        }
        var count = 0
        for (i in 0..childCount - 1) {
            val v = getChildAt(i)
            if (v is BlankView) {
                //BlankView is just to make childs look in alignment, we should ignore them when we relayout
                continue
            }
            count++
        }
        val childs = arrayOfNulls<View>(count)
        val spaces = IntArray(count)
        var n = 0
        for (i in 0..childCount - 1) {
            val v = getChildAt(i)
            if (v is BlankView) {
                //BlankView is just to make childs look in alignment, we should ignore them when we relayout
                continue
            }
            childs[n] = v
            val childLp = v.layoutParams
            val childWidth = v.measuredWidth
            if (childLp is ViewGroup.MarginLayoutParams) {
                val mlp = childLp
                spaces[n] = mlp.leftMargin + childWidth + mlp.rightMargin
            } else {
                spaces[n] = childWidth
            }
            n++
        }
        var lineTotal = 0
        var start = 0
        this.removeAllViews()
        run {
            var i = 0
            while (i < count) {
                if (lineTotal + spaces[i] > usefulWidth) {
                    val blankWidth = usefulWidth - lineTotal
                    val end = i - 1
                    val blankCount = end - start
                    if (blankCount >= 0) {
                        if (blankCount > 0) {
                            val eachBlankWidth = blankWidth / blankCount
                            val lp = ViewGroup.MarginLayoutParams(eachBlankWidth, 0)
                            for (j in start..end - 1) {
                                this.addView(childs[j])
                                val blank = BlankView(mContext)
                                this.addView(blank, lp)
                            }
                        }
                        this.addView(childs[end])
                        start = i
                        i--
                        lineTotal = 0
                    } else {
                        this.addView(childs[i])
                        start = i + 1
                        lineTotal = 0
                    }
                } else {
                    lineTotal += spaces[i]
                }
                i++
            }
        }
        for (i in start..count - 1) {
            this.addView(childs[i])
        }
    }

    /**
     * use both of relayout methods together
     */
    fun relayoutToCompressAndAlign() {
        post {
            compress()
            align()
        }
    }

    /**
     * cut the flowlayout to the specified num of lines
     * @param line_num_now
     */
    fun specifyLines(line_num_now: Int) {
        post {
            var line_num = line_num_now
            var childNum = 0
            if (line_num > lineNumList.size) {
                line_num = lineNumList.size
            }
            for (i in 0..line_num - 1) {
                childNum += lineNumList[i]
            }
            val viewList = ArrayList<View>()
            for (i in 0..childNum - 1) {
                viewList.add(getChildAt(i))
            }
            removeAllViews()
            for (v in viewList) {
                addView(v)
            }
        }
    }

    override fun generateLayoutParams(p: ViewGroup.LayoutParams): ViewGroup.LayoutParams {
        return ViewGroup.MarginLayoutParams(p)
    }

    override fun generateLayoutParams(attrs: AttributeSet): ViewGroup.LayoutParams {
        return ViewGroup.MarginLayoutParams(context, attrs)
    }

    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams {
        return ViewGroup.MarginLayoutParams(super.generateDefaultLayoutParams())
    }

    internal inner class BlankView(context: Context) : View(context)
}
