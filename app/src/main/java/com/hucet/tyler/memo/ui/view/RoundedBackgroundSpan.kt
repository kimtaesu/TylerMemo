package com.hucet.tyler.memo.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.text.style.ReplacementSpan
import android.util.TypedValue
import com.hucet.tyler.memo.utils.Utils


class RoundedBackgroundSpan(
        private val context: Context,
        private val backgroundColor: Int,
        private val textColor: Int,
        textSize: Float,
        private val radius: Float = 12f) : ReplacementSpan()
/**
 * @param backgroundColor color value, not res id
 * @param textSize        in pixels
 */
{


    private val pixelTextSize = Utils.getPixels(TypedValue.COMPLEX_UNIT_SP, textSize)
    //    private val PADDING_X = Utils.dpToPx(12)
    private val PADDING_X = 24
    //    private val PADDING_Y = GeneralUtils.convertDpToPx(2)
    private val PADDING_Y = 4

    //    private val MAGIC_NUMBER = GeneralUtils.convertDpToPx(2)
    private val MAGIC_NUMBER = 4

    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        var paint = paint
        paint = Paint(paint) // make a copy for not editing the referenced paint

        paint.textSize = pixelTextSize.toFloat()

        // Draw the rounded background
        paint.setColor(backgroundColor)
//        val textHeightWrapping = GeneralUtils.convertDpToPx(4)
        val textHeightWrapping = 6
        val tagBottom = top.toFloat() + textHeightWrapping + PADDING_Y + pixelTextSize + PADDING_Y + textHeightWrapping
        val tagRight = x + getTagWidth(text, start, end, paint)
        val rect = RectF(x, top.toFloat(), tagRight, tagBottom)
        canvas.drawRoundRect(rect, radius, radius, paint)

        // Draw the text
        paint.color = textColor
        canvas.drawText(text, start, end, x + PADDING_X, tagBottom - PADDING_Y - textHeightWrapping - MAGIC_NUMBER, paint)
    }

    private fun getTagWidth(text: CharSequence?, start: Int, end: Int, paint: Paint): Int {
        return Math.round(PADDING_X + paint.measureText(text?.subSequence(start, end).toString()) + PADDING_X)
    }

    override fun getSize(paint: Paint?, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        var paint = paint
        paint = Paint(paint) // make a copy for not editing the referenced paint
        paint.textSize = pixelTextSize.toFloat()
        return getTagWidth(text, start, end, paint)
    }
}