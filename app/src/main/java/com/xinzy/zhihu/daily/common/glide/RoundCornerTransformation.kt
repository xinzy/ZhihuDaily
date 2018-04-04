package com.xinzy.zhihu.daily.common.glide

import android.content.Context
import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation

/**
 * Created by Xinzy on 2018/3/29.
 */

class RoundCornerTransformation : BitmapTransformation {

    private var mStatus = 0
    private var mRadius = -1    //-1：圆角

    companion object {
        val LEFT_TOP = 0x1
        val RIGHT_TOP = 0x2
        val LEFT_BOTTOM = 0x4
        val RIGHT_BOTTOM = 0x8

        val ALL = LEFT_BOTTOM or LEFT_TOP or RIGHT_BOTTOM or RIGHT_TOP
    }

    constructor(context: Context) : this(context, ALL)

    constructor(context: Context, status: Int) : this(context, status, -1)

    constructor(context: Context, status: Int, radius: Int) : super(context) {
        mStatus = status
        mRadius = radius
    }

    override fun getId() = "RoundCornerTransformation"

    override fun transform(pool: BitmapPool?, toTransform: Bitmap?, outWidth: Int, outHeight: Int): Bitmap? {
        if (toTransform == null) return null

        val width = if (mRadius == -1) Math.min(toTransform.width, toTransform.height) else toTransform.width
        val height = if (mRadius == -1) Math.min(toTransform.width, toTransform.height) else toTransform.height

        if (mRadius == -1) mRadius = width / 2

        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val canvas = Canvas(output)
        val rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
        canvas.drawRoundRect(rectF, mRadius.toFloat(), mRadius.toFloat(), paint)

        // TODO
        if (mStatus and LEFT_TOP == 0) {
            canvas.drawRect(0f, 0f, mRadius.toFloat(), mRadius.toFloat(), paint)
        }
        if (mStatus and RIGHT_TOP == 0) {
            canvas.drawRect((width - mRadius).toFloat(), 0f, mRadius.toFloat(), mRadius.toFloat(), paint)
        }
        if (mStatus and LEFT_BOTTOM == 0) {
            canvas.drawRect(0f, (height - mRadius).toFloat(), mRadius.toFloat(), mRadius.toFloat(), paint)
        }
        if (mStatus and RIGHT_BOTTOM == 0) {
            canvas.drawRect((width - mRadius).toFloat(), (height - mRadius).toFloat(), mRadius.toFloat(), mRadius.toFloat(), paint)
        }

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        val rect = Rect(0, 0, width, height)
        canvas.drawBitmap(toTransform, rect, rect, paint)

        return output
    }
}